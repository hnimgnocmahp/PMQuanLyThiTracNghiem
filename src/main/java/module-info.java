module org.example.phanmemthitracnghiem {
    requires javafx.controls;
    requires javafx.fxml;
    requires net.synedra.validatorfx;
    requires java.sql;
    requires java.naming;
    requires java.desktop;
    requires jdk.jfr;
    requires javafx.base;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens org.example.phanmemthitracnghiem to javafx.fxml;
    opens DTO to javafx.base;
    exports org.example.phanmemthitracnghiem;

}