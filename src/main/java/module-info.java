module org.example.phanmemthitracnghiem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires java.sql;
    requires java.naming;
    requires java.desktop;
    requires jdk.jfr;

    opens org.example.phanmemthitracnghiem to javafx.fxml;
    opens DTO to org.hibernate.orm.core;
    exports org.example.phanmemthitracnghiem;

}