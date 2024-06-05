module Ginasio {
    requires jakarta.persistence;
    requires jakarta.xml.bind;
    requires org.hibernate.orm.core;
    requires spring.context;
    requires java.sql;
    requires java.persistence;


    opens entity to org.hibernate.orm.core;
    opens bll to org.hibernate.orm.core;


    exports entity;
    exports bll;
    exports database;
}