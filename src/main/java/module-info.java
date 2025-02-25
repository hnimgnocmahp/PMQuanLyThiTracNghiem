module org.example.phanmemthitracnghiem {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires java.sql;
    requires java.naming;
    requires jdk.jfr;
    requires javafx.base;
    requires org.apache.poi.poi;
    requires java.desktop;

    opens org.example.phanmemthitracnghiem to javafx.fxml;
    opens DTO to javafx.base;
    exports org.example.phanmemthitracnghiem;

}