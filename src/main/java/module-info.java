module alejandrodfd.sensor {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;
    requires java.base;

    opens alejandrodfd.sensor to javafx.fxml;
    exports alejandrodfd.sensor;
}
