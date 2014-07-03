cabotrafalgar-recordserver
==========================

For executing component-tests:
mvn clean install -Dcouchdb_host={your couchdb host, port defaults to 5984} (no credential option yet)

For executing component-tests within the entire product compilation
mvn clean install -Pcomponent-test -Dcouchdb_host={your couchdb host, port defaults to 5984} (no credential option yet)

For easy active development, create a file in {user home}/navidconfig/recordserver-ct.overrides with content:
couchdb_host={your couchdb host, port defaults to 5984}
Please notice this file will contain sensitive information and it's purposely out of git for that reason.


These tests spawns a local jetty service and a local mock-server (mock-server.com) that will emulate lazy-login dependency.
Therefore, the only dependency that's required is coachdb running instance with no credentials (yet).

