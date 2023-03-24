drop table if exists flight;
create table flight(id LONG GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, flight_number varchar2(4), carrier_code varchar2(2),
origin varchar2(3), destination varchar2(3), flight_date varchar2(20));