-- ----------------------------------------------------------------------------
-- Below will drop the tables and recreate them,
-- this was created for testing purposed so that
-- I could work from a clean database when adding
-- and removing from DBAccess
-- ----------------------------------------------------------------------------

DROP TABlE person;
CREATE table person(
    PERSON_ID INT Primary key, 
    LAST_NAME VARCHAR(20), 
    FIRST_NAME VARCHAR(20), 
    STREET VARCHAR(20), 
    CITY VARCHAR(20)
);

DROP TABLE `order`;
CREATE TABLE `order`(
    ORDER_ID  INT Primary key, 
    ORDER_NO  INT, 
    PERSON_ID INT 
);

-- ----------------------------------------------------------------------------
-- Query for finding users with one or more order
-- ----------------------------------------------------------------------------
SELECT p.first_name, p.last_name,p.person_id,o.person_id, o.order_no
from person p, `order` o
where o.person_id=p.person_id
group by p.first_name