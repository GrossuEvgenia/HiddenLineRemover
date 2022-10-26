module com.example.lr2_kg {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.lr2_kg to javafx.fxml;
    exports com.example.lr2_kg;
}