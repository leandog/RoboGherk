require 'buildr/core/common'

module BuildrExt
  module Android
    include Extension

    class Android
      attr_reader :project

      attr_accessor :keystore
      attr_accessor :keystorepass
      attr_accessor :keyalias
      attr_accessor :keypass

      def initialize(project)
        @project = project
        @compiled_libraries = []
        @merged_libraries = []
        @debug = true
        @instrument = false
        @test = false
        self.resources = project.path_to('res')
        self.native_libraries = project.path_to('libs')
        self.assets = project.path_to('assets')
        self.manifest = project.path_to('AndroidManifest.xml')
      end

      def home
        home_dir = ENV['ANDROID_HOME'] || user_settings['home']
        android_home = File.expand_path(home_dir) if home_dir
        raise RuntimeError.new("Android SDK home directory is not defined") unless android_home
        android_home
      end

      def tools_dir
        "#{home}/tools"
      end

      def platform_tools_dir
        "#{home}/platform-tools"
      end

      def platform_version_tools_dir
        "#{platform_dir}/tools"
      end

      attr_writer :platform

      def platform
        @platform || build_settings['platform']
      end

      def platform_dir
        android_home = home

        android_platform = platform
        raise RuntimeError.new("Android platform name has not been set") unless android_platform

        "#{android_home}/platforms/#{android_platform}"
      end

      def platform_jar
        platform_dir + '/android.jar'
      end

      attr_accessor :resources

      attr_accessor :native_libraries

      attr_writer :library

      def library?
        @library
      end

      attr_writer :debug

      def debug?
        @debug
      end

      attr_writer :test

      def test?
        @test
      end

      attr_writer :instrument

      def instrument?
        @instrument
      end

      attr_accessor :assets

      attr_accessor :manifest

      private

      def manifest_path
        case manifest
          when Rake::Task
            manifest.name
          when String
            manifest
          else
            manifest.to_s
        end
      end

      public

      attr_accessor :compiled_libraries
      attr_accessor :merged_libraries

      def libraries
        compiled_libraries + merged_libraries
      end

      def generate_source
        FileUtils.mkdir_p(gen_source_dir)

        args = ['package', '-f', '-m', '--auto-add-overlay']
        args << '-v' if Buildr.application.options.trace
        args << '-M' << manifest_path if File.exists?(manifest_path)
        args << '-I' << platform_jar

        args << '-S' << resources if File.exists?(resources)
        libraries.each { |l| args << '-S' << l.android.resources if File.exists?(l.android.resources) }

        args << '-A' << assets if File.exists?(assets)

        args << '-J' << gen_source_dir

        aapt(*args)

        find_aidl_files.each do |aidl_file, java_file|
          FileUtils.mkdir_p(File.dirname(java_file))
          aidl(aidl_file, java_file)
        end

        FileUtils.touch(gen_source_dir)
      end

      def generated_source_out_of_date?
        !File.exists?(gen_source_dir) || r_file_out_of_date? || aidl_files_out_of_date?
      end

      private
      def r_file_out_of_date?
        stamp = File.stat(gen_source_dir).mtime
        updated_file = [Dir.glob("#{resources}/**/*"), Dir.glob("#{assets}/**/*")].flatten.find { |f| File.stat(f).mtime > stamp }
        trace "Generating R.java because source file #{updated_file} has been updated" if updated_file
        updated_file != nil
      end

      def find_aidl_files
        trace "Searching for AIDL files"
        aidl_files = {}
        gen = gen_source_dir
        source_paths = project.compile.sources
        source_paths.each do |s|
          trace "Searching for #{s}/**/*.aidl"
          Dir.glob("#{s}/**/*.aidl").each do |aidl|
            java_file = aidl.sub(s, gen).sub(/\.aidl$/, '.java')
            trace "Found #{aidl} => #{java_file}"
            aidl_files[aidl] = java_file
          end
        end

        aidl_files
      end

      def aidl_files_out_of_date?
        stamp = File.stat(gen_source_dir).mtime
        updated_file = find_aidl_files.find do |aidl_file, java_file|
          !File.exist?(java_file) ||
              File.stat(aidl_file).mtime > stamp ||
              File.stat(aidl_file).mtime > File.stat(java_file).mtime
        end
        if updated_file
          puts "Generating AIDL interfaces because source file #{updated_file} has been updated"
        else
          puts "AIDL interfaces up to date"
        end
        updated_file != nil
      end

      public
      def gen_source_dir
        project.path_to(:target, :android, :gen)
      end

      def generate_packaged_resources
        trace "Generating resources file #{packaged_resources}"
        args = ['package', '-f', '-m', '--auto-add-overlay']
        args << '-v' if Buildr.application.options.trace
        args << '-M' << manifest_path if File.exists?(manifest_path)
        args << '-I' << platform_jar

        args << '-S' << resources if File.exists?(resources)
        libraries.each { |l| args << '-S' << l.android.resources if File.exists?(l.android.resources) }
        args << '-A' << assets if File.exists?(assets)

        args << '-F' << packaged_resources

        FileUtils.mkdir_p File.dirname(packaged_resources)
        aapt(*args)
      end

      def packaged_resources_out_of_date?

      end

      def packaged_resources
        project.path_to(:target, :android, 'packaged_resources.apk')
      end

      def generate_apk(file)
        trace "Generating #{file}"
        unaligned_apk = project.path_to(:target, :android, File.basename(file) + ".unaligned")
        aligned_apk = file

        # Build unaligned APK
        FileUtils.mkdir_p File.dirname(unaligned_apk)
        apkbuilder :verbose => Buildr.application.options.trace,
                   :debugsigning => debug?,
                   :outfolder => File.dirname(packaged_resources),
                   :resourcefile => File.basename(packaged_resources),
                   :apkfilepath => unaligned_apk do |apk|
          apk.dex :path => dex_file
          apk.jarfile :path => emma_device_jar if instrument?
          apk.nativefolder :path => native_libraries if File.exists?(native_libraries)
          libraries.each { |l|
            apk.nativefolder :path => l.android.native_libraries if File.exists?(l.android.native_libraries)
          }
        end

        # Sign the APK
        unless debug?
          args = []
          args << '-verbose' if Buildr.application.options.trace
          args << '-keystore' << keystore
          args << '-storepass' << keystorepass
          args << '-keypass' << keypass
          args << unaligned_apk
          args << keyalias
          jarsigner(*args)
        end

        # Align the APK
        args = ['-f']
        args << '-v' if Buildr.application.options.trace
        args << '4'
        args << unaligned_apk << aligned_apk
        FileUtils.mkdir_p File.dirname(aligned_apk)
        zipalign(*args)
      end

      def generate_dex
        classes_dir = project.compile.target.to_s

        if (instrument?)
          FileUtils.mkdir_p project.path_to(:reports, :emma)
          Buildr.ant("emma") do |ant|
            classpath = emma_instr_jars.map(&:to_s).join(File::PATH_SEPARATOR)
            ant.taskdef :resource => 'emma_ant.properties', :classpath => classpath
            ant.emma :enabled => true do |emma|
              emma.instr :instrpath => project.compile.target,
                         :mode => 'overwrite',
                         :merge => false,
                         :metadatafile => project.path_to(:reports, :emma, 'metadata.em')
            end
          end
        end


        trace "Generating dex file #{dex_file}"
        args = ['--dex']
        args << '--no-locals' if instrument?
        args << '--verbose' if Buildr.application.options.trace
        args << "--output=#{dex_file}"

        args << classes_dir
        project.compile.dependencies.each do |d|
          args << d.to_s unless d == platform_jar
        end

        args << emma_device_jar if instrument?

        FileUtils.mkdir_p File.dirname(dex_file)
        dx(*args)
      end

      def dex_file
        project.path_to(:target, :android, 'classes.dex')
      end

      def devices
        device_re = Regexp.compile '^([-0-9A-Z]+)\s+([a-z]+)', Regexp::IGNORECASE
        devices = adb 'devices', :capture_output => true
        serials = devices[:out].lines.inject([]) do |list, line|
          trace "Testing line #{line}"
          matchdata = device_re.match line
          trace "Line matches" if matchdata
          if (matchdata && matchdata[2] == 'device')
            trace "Adding serial #{matchdata[1]}"
            list << matchdata[1]
          else
            list
          end
        end
        serials.map do |serial|
          {
              :brand => adb('-s', serial, 'shell', 'getprop', 'ro.product.brand', :capture_output => true)[:out].strip,
              :model => adb('-s', serial, 'shell', 'getprop', 'ro.product.model', :capture_output => true)[:out].strip,
              :os => adb('-s', serial, 'shell', 'getprop', 'ro.build.version.release', :capture_output => true)[:out].strip,
              :serial => serial
          }
        end
      end

      def run_tests(device, apk)
        task "android_unit_tests_#{apk.name}_#{device[:serial]}" => apk do
          trace "Executing unit tests in #{apk.name} on device #{device}"
          junit_file = "/sdcard/#{device[:serial]}-junit.xml"
          emma_file = "/sdcard/#{device[:serial]}-emma.ec"

          # TODO should uninstall old APK explicitly rather than reinstalling
          adb '-s', device[:serial], 'install', '-r', apk.name

          # Clear old unit test coverage files.
          adb '-s', device[:serial], 'shell', 'rm', junit_file
          adb '-s', device[:serial], 'shell', 'rm', emma_file if instrument?

          # Run unit tests.
          instrument_options = [
              '-s', device[:serial],
              'shell',
              'am', 'instrument', '-w',
              '-e', 'reportFile', "#{device[:serial]}-junit.xml",
              '-e', 'reportDir', '/sdcard/',
              '-e', 'testSuffix', "#{device[:brand]} #{device[:model]} (#{device[:os]})"
          ]
          instrument_options += ['-e', 'coverage', 'true', '-e', 'coverageFile', emma_file] if instrument?
          # TODO package needs to be autodetected
          instrument_options << 'mypackage/com.zutubi.android.junitreport.JUnitReportTestRunner'
          adb *instrument_options

          FileUtils.mkdir_p project.path_to(:reports, :junit)
          adb '-s', device[:serial],
              'pull',
              junit_file,
              project.path_to(:reports, :junit, "#{device[:serial]}-junit.xml")

          if instrument?
            begin
              FileUtils.mkdir_p project.path_to(:reports, :emma)
              adb '-s', device[:serial],
                  'pull',
                  emma_file,
                  project.path_to(:reports, :emma, "#{device[:serial]}-emma.ec")
            rescue
              # Prevent build from failing if emma file is missing due to crashing unit test.
            end
          end
        end
      end

      def coverage_report(report_file)
        Buildr.ant("emma") do |ant|
          classpath = emma_instr_jars.map(&:to_s).join(File::PATH_SEPARATOR)
          ant.taskdef :resource => 'emma_ant.properties', :classpath => classpath
          ant.emma :enabled => true do |emma|
            emma.report do |report|
              report.infileset :dir => project.path_to(:reports, :emma), :includes => "*.em, *.ec"
              report.xml :outfile => report_file
            end
          end
        end
      end

      def aapt(*args)
        if (Buildr::Util::win_os?)
          aapt_binary = find_tool('aapt.exe')
        else
          aapt_binary = find_tool('aapt')
        end

        run_tool(aapt_binary, *args)
      end

      def aidl(*args)
        if (Buildr::Util::win_os?)
          aidl_binary = find_tool('aidl.exe')
        else
          aidl_binary = find_tool('aidl')
        end

        run_tool(aidl_binary, *args)
      end

      def dx(*args)
        if (Buildr::Util::win_os?)
          dx_binary = find_tool('dx.bat')
        else
          dx_binary = find_tool('dx')
        end

        run_tool(dx_binary, *args)
      end

      def adb(*args)
        if (Buildr::Util::win_os?)
          adb_binary = find_tool('adb.exe')
        else
          adb_binary = find_tool('adb')
        end

        run_tool(adb_binary, *args)
      end

      def zipalign(*args)
        if (Buildr::Util::win_os?)
          zipalign_binary = find_tool('zipalign.exe')
        else
          zipalign_binary = find_tool('zipalign')
        end

        run_tool(zipalign_binary, *args)
      end

      def keytool(*args)
        if (Buildr::Util::win_os?)
          exe = 'keytool.exe'
        else
          exe = 'keytool'
        end

        tool = ENV['JAVA_HOME'] + "/bin/#{exe}"
        run_tool(tool, *args)
      end

      def jarsigner(*args)
        if (Buildr::Util::win_os?)
          exe = 'jarsigner.exe'
        else
          exe = 'jarsigner'
        end

        tool = ENV['JAVA_HOME'] + "/bin/#{exe}"
        run_tool(tool, *args)
      end

      private
      def find_tool(tool)
        tools = [platform_version_tools_dir, platform_tools_dir, tools_dir].map { |d| "#{d}/#{tool}" }.select { |f| File.exists?(f) }
        if (tools.empty?)
          raise "Could not find Android SDK tool #{tool}"
        end

        tools[0]
      end

      def apkbuilder(args, &code)
        Buildr.ant('apkbuilder') do |ant|
          jars = FileList[tools_dir + '/lib/**/*.jar', platform_tools_dir + '/lib/**/*.jar'].flatten
          classpath = jars.map(&:to_s).join(File::PATH_SEPARATOR)
          ant.taskdef :name => 'apkbuilder', :classname => 'com.android.ant.ApkBuilderTask', :classpath => classpath
          ant.apkbuilder args, &code
        end
      end

      def run_tool(tool, *args)
        options = Hash === args.last ? args.pop.dup : {}

        if Buildr.application.options.trace
          arg_str = args.inject("") { |str, arg| str << " '" << arg.to_s << "'" }
          puts "#{tool} #{arg_str}"
        end
        raise "Could not execute #{tool}: file not found" unless File.exists?(tool)

        if (options[:capture_output])
          out = StringIO.new
          old_stdout = $stdout
          old_stderr = $stderr
          begin
            $stdout = out
            $stderr = out
            system(tool, *args)
          ensure
            $stdout = old_stdout
            $stderr = old_stderr
            out.close
            out.rewind
          end

          output = out.string
        else
          system(tool, *args)
          output = ""
        end

        status = $?
        code = status.exitstatus

        raise RuntimeError.new("#{tool} exited with code #{code}: " + output.strip) if code != 0
        puts output if Buildr.application.options.trace && output && output.length > 0
        {:status => status, :code => code, :out => output}
      end

      def user_settings
        Buildr.settings.user['android'] || {}
      end

      def build_settings
        Buildr.settings.build['android'] || {}
      end

      def emma_instr_jars
        FileList[tools_dir + '/lib/emma.jar', tools_dir + '/lib/emma_ant.jar'].flatten
      end

      def emma_device_jar
        tools_dir + '/lib/emma_device.jar'
      end
    end

    class GenerateSourceTask < Rake::FileTask
      def initialize(*args)
        super
        enhance do
          android.manifest.invoke if (android.manifest.is_a?(Rake::Task))
          android.generate_source
        end
      end

      def needed?
        super || android.generated_source_out_of_date?
      end

      attr_reader :android

      def associate_with(project)
        @android = project.android
      end
    end

    class GeneratePackagedResourcesTask < Rake::FileTask
      def initialize(*args)
        super
        enhance do
          android.manifest.invoke if (android.manifest.is_a?(Rake::Task))
          android.generate_packaged_resources
        end
      end

      attr_reader :android

      def associate_with(project)
        @android = project.android
      end
    end

    class GenerateDexTask < Rake::FileTask
      def initialize(*args)
        super
        enhance do
          android.manifest.invoke if (android.manifest.is_a?(Rake::Task))
          android.generate_dex
        end
      end

      attr_reader :android

      def associate_with(project)
        @android = project.android
      end
    end

    class ApkTask < Rake::FileTask
      def initialize(*args)
        super
        enhance do
          android.manifest.invoke if (android.manifest.is_a?(Rake::Task))
          android.generate_apk(name)
        end
      end

      attr_reader :android

      def associate_with(project)
        @android = project.android
        dex = file(android.dex_file)
        trace "Will use dex file #{dex.name}"
        res = file(android.packaged_resources)
        trace "Will use resources file #{res.name}"
        keystore = file(android.keystore)
        enhance([dex, res, keystore])
      end
    end

    def android
      @android ||= Android.new(self)
    end

    def version_code(version)
      version_match = version.match(/([0-9\.]+)(.*)/)
      if version_match
        current_version = version_match[1]

        version_parts = current_version.split(/\./)
        major = version_parts[0].to_i
        minor = version_parts[1].to_i
        patch = version_parts[2].to_i
        major * 10000 + minor * 100 + patch
      else
        0 # Used for development versions
      end
    end

    before_define do |project|
      gen_src = GenerateSourceTask.define_task(project.android.gen_source_dir)
      gen_src.associate_with(project)

      gen_packed_res = GeneratePackagedResourcesTask.define_task(project.android.packaged_resources)
      gen_packed_res.associate_with(project)

      gen_dex = GenerateDexTask.define_task(project.android.dex_file)
      gen_dex.associate_with(project)

      gen_dex.enhance([project.compile])
    end

    after_define do |project|
      unless project.android.library?
        gen_src = project.android.gen_source_dir
        gen_task = file(gen_src)
        gen_task.enhance([project.compile.sources].flatten)
        project.compile.enhance([gen_task])
        project.compile.from gen_src
      end

      project.compile.with project.android.platform_jar

      library_packages = project.android.compiled_libraries.map { |l| l.packages.find_all { |pkg| pkg.type.to_s == 'jar' } }
      project.compile.with library_packages

      library_projects = project.android.merged_libraries
      library_projects.each do |p|
        project.compile.from p.compile.sources
        project.compile.with p.compile.dependencies
      end

      project.doc.with project.android.platform_jar

      if (project.android.test?)
        apks = project.packages.find_all { |pkg| pkg.type.to_s == 'apk' }
        apks.each do |apk|
          trace "Adding unit test tasks for #{apk.name}"
          device_tests = multitask :device_tests => project.android.devices.map { |device|
            trace "Adding unit test task for #{apk.name} on device #{device}"
            project.android.run_tests(device, apk)
          }
          project.integration.enhance [device_tests]
        end

        if !apks.empty? && project.android.instrument?
          emma_report = file(project.path_to(:reports, :emma, 'coverage.xml')).enhance do |t|
            project.android.coverage_report(t.name)
          end
          project.integration.enhance [emma_report]
        end
      end
    end

    def package_as_apk(file_name) #:nodoc:
      apk = ApkTask.define_task(file_name)
      apk.associate_with(project)

      apk
    end
  end
end
