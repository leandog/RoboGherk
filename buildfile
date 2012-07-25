require "buildr/openjpa"
include Buildr::OpenJPA

VERSION_NUMBER = "0.0.1"

GROUP = "RoboGherk"
COPYRIGHT = "LeanDog"

repositories.remote << "http://repo1.maven.org/maven2"

desc "The Robogherk project"
define "RoboGherk" do

  project.version = VERSION_NUMBER
  project.group = GROUP
  manifest["Implementation-Vendor"] = COPYRIGHT

  define "HelloRoboGherk" do
  end

  define "RoboGherk" do
  end

  define "RoboGherkUnits" do
  end

end
