module com.example.cringecoding {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.naming;
    requires org.hibernate.commons.annotations;

    opens com.example.cringecoding.DTO to javafx.base;
    opens com.example.cringecoding.Models to org.hibernate.orm.core;
    opens com.example.cringecoding.Controllers to javafx.fxml;

    exports com.example.cringecoding.Models;
    exports com.example.cringecoding.Controllers;
    exports com.example.cringecoding.DTO;
}
