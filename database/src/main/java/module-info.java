module Ginasio {
    requires jakarta.persistence;
    requires jakarta.xml.bind;
    requires org.hibernate.orm.core;


    opens entity to org.hibernate.orm.core;


    exports entity;
    exports bll;
    exports database;
}