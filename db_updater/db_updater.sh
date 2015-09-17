export PGPASSWORD="paradox"

##/opt/PostgreSQL/8.4/bin/pg_dump -U user1 -F c -f dump.backup kristall

/opt/PostgreSQL/8.4/bin/dropdb -U user1 kristall

/opt/PostgreSQL/8.4/bin/createdb -U user1 kristall

/opt/PostgreSQL/8.4/bin/psql -U user1 -f create_objects.sql kristall

/opt/PostgreSQL/8.4/bin/pg_restore -U postgres -d kristall -a --disable-triggers dump.backup



