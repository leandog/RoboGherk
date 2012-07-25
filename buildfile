require "buildr/openjpa"
include Buildr::OpenJPA

VERSION_NUMBER = "0.0.1"

GROUP = "RoboGherk"
COPYRIGHT = "LeanDog"

repositories.remote << "http://repo1.maven.org/maven2"

desc "The RoboGherk project"
define "RoboGherk" do

  project.version = VERSION_NUMBER
  project.group = GROUP
  manifest["Implementation-Vendor"] = COPYRIGHT

  define "HelloRoboGherk" do
    compile.with 'com.google.android:android:jar:4.0.1.2','com.google.android:android-test:jar:4.0.1.2'
  end

  define "RoboGherk" do
    compile.with 'com.google.android:android:jar:4.0.1.2',
      'com.google.android:android-test:jar:4.0.1.2',
      'junit:junit:jar:3.8.2',
      'com.jayway.android.robotium:robotium-solo:jar:3.3'
  end

  define "RoboGherkUnits" do
  end

end
