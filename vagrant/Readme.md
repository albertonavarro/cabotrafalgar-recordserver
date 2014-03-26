This project spins up a vagrant box with couchdb running inside of it

Prerequisites
=============

- Virtual Box 4
- Ruby
- vagrant gem

Usage
=====

To fire it up just type

    vagrant up

It takes about 3 minutes to startup on my system.  If you haven't used vagrant before with the lucid32 box, there will be some additional time to download that initial vm image.

Notes
=====

This contains a modified version of the opscode couchdb recipe.  That recipe didn't correctly restart the couchdb server, and now it allows specifying the bind_address so vagrant's port forwarding works.