set PGPASSWORD="paradox"


set base="base20140108"

C:\PostgreSQL\8.4\bin\pg_dump -U user1 -W -F c -f dump.backup %base%


C:\PostgreSQL\8.4\bin\dropdb -U user1 -W %base%

C:\PostgreSQL\8.4\bin\createdb -U user1 -W %base%


C:\PostgreSQL\8.4\bin\psql -U user1 -W -f create_objects.sql %base%


C:\PostgreSQL\8.4\bin\pg_restore -U postgres -W -d %base% -a --disable-triggers dump.backup



