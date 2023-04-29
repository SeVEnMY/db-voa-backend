-- SQLINES LICENSE FOR EVALUATION USE ONLY
select table_name, 
       cast(extractvalue(xmltype(dbms_xmlgen.getxml(Concat('select count(*) c from ',ifnull(table_name, '')))),'/ROWSET/ROW/C') as float) as count
from USER_tables
where table_name like 'NQZ%'
