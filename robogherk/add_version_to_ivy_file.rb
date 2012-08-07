#!/usr/bin/ruby

version = ARGV.first

contents = File.open('ivy.xml','r') {|file| file.read}; 
contents.gsub!(/(<info[^>]*)>/) { $1 + " revision = \"#{version}\">"; }
File.open("bin/artifacts/ivy-#{version}.xml", 'w') do |file| 
  file.write contents
end
