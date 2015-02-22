#
# Cookbook Name:: navid-recordserver
# Recipe:: default
#
# Copyright 2014, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

remote_file "/root/jetty-deployable.jar" do
   source "http://repo.cabotrafalgar.mooo.com/libs-release-local/com/navid/record-server/springboot/${project.version}/springboot-${project.version}.war"
end

directory "/root/config" do
  owner "root"
  group "root"
  mode 00644
  action :create
end

template "/root/logback.xml" do
  mode 0755
  owner "root"
  group "root"
end

template "/root/config/application.properties" do
  mode 0755
  owner "root"
  group "root"
end

# script file used by service to launch your java program
file "/root/run_recordserver.cmd" do
    content "java -jar /root/jetty-deployable.jar\n"
    mode 0755
    owner "root"
    group "root"
end

# setup the service (based on the script above),
# start it, and make it start at boot
cookbook_file '/etc/init.d/recordserver' do
    source 'RecordServerService'
    mode 0755
    owner "root"
    group "root"
end

service "recordserver" do
    supports :restart => true, :start => true, :stop => true, :reload => true
    action [:enable, :restart]
end
