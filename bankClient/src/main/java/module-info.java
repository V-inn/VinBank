module com.viniciusfk.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.viniciusfk.client to javafx.fxml;
    exports com.viniciusfk.client;
    exports com.viniciusfk.client.services;
}