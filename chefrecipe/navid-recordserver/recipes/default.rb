#
# Cookbook Name:: navid-recordserver
# Recipe:: default
#
# Copyright 2014, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

remote_file "/root/jetty-deployable" do
   source "http://repo.cabotrafalgar.mooo.com/libs-release-local/com/navid/record-server/jetty-endpoint/${project.version}/jetty-endpoint-${project.version}.jar"
end

template "/root/navidconfig/recordserver.overrides" do
  source "text_file.txt"
  mode 0755
  owner "root"
  group "root"
end