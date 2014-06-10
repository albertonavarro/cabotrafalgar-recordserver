#
# Cookbook Name:: navid-recordserver
# Recipe:: default
#
# Copyright 2014, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

include_recipe "java"
include_recipe "simple_iptables"
include_recipe "zabbix"
include_recipe "daemontools"

# Reject packets other than those explicitly allowed
simple_iptables_policy "INPUT" do
  policy "DROP"
end

# The following rules define a "system" chain; chains
# are used as a convenient way of grouping rules together,
# for logical organization.

# Allow all traffic on the loopback device
simple_iptables_rule "system" do
  rule "--in-interface lo"
  jump "ACCEPT"
end

# Allow any established connections to continue, even
# if they would be in violation of other rules.
simple_iptables_rule "system" do
  rule "-m conntrack --ctstate ESTABLISHED,RELATED"
  jump "ACCEPT"
end

# Allow SSH
simple_iptables_rule "system" do
  rule "--proto tcp --dport 22"
  jump "ACCEPT"
end

# Allow HTTP, HTTPS
simple_iptables_rule "tomcat" do
  rule [ "--proto tcp --dport 8080",
         "--proto tcp --dport 8443" ]
  jump "ACCEPT"
end

# Allow HTTP, HTTPS
simple_iptables_rule "zabbix" do
  rule [ "--proto tcp --dport 10050",
         "--proto udp --dport 10050" ]
  jump "ACCEPT"
end

# Allow HTTP, HTTPS
simple_iptables_rule "ajp" do
  rule [ "--proto tcp --dport 8019",
         "--proto udp --dport 8019" ]
  jump "ACCEPT"
end

remote_file "/root/jetty-deployable.jar" do
   source "http://repo.cabotrafalgar.mooo.com/libs-release-local/com/navid/record-server/jetty-endpoint/${project.version}/jetty-endpoint-${project.version}.jar"
end

directory "/root/navidconfig" do
  owner "root"
  group "root"
  mode 00644
  action :create
end

template "/root/navidconfig/recordserver.overrides" do
  mode 0755
  owner "root"
  group "root"
end

# script file used by service to launch your java program
file "/root/run_recordserver.cmd" do
    content "java -jar /root/jetty-deployable.jar\n"
end

# setup the service (based on the script above),
# start it, and make it start at boot
cookbook_file '/etc/init.d/recordserver' do
    source 'RecordServerService'
end

service "myservice" do
    supports :restart => true, :start => true, :stop => true, :reload => true
    action [:enable]
end