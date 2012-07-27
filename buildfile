
repositories.remote << "http://repo1.maven.org/maven2"

desc "The RoboGherk project"
define "RoboGherk" do

  project.version = "0.0.1"
  project.group = "com.leandog.robogherk"
  manifest["Implementation-Vendor"] = "LeanDog"

  define "HelloRoboGherk" do
    extend BuildrExt::Android
    android.platform = 'android-14'
  end

  define "RoboGherk" do
    compile.with 'com.google.android:android:jar:4.0.1.2',
      'com.google.android:android-test:jar:4.0.1.2',
      'junit:junit:jar:3.8.2',
      'com.jayway.android.robotium:robotium-solo:jar:3.3'
    test.with 'org.mockito:mockito-all:jar:1.9.0'
  end

end
