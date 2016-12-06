#!/bin/sh

export PGPASSWORD="paradox"


##SET session_replication_role = replica;

#dropdb -h 192.168.1.15 -U postgres base1
#createdb -h 192.168.1.15 -U user1 base1
#psql -h 192.168.1.15 -U user1 -a -e -f dumps/aristocrat20140818-22.10.2016-12.26.14.sql base1

psql -h 192.168.1.15 -U user1 -a -e -f clean.sql base1


