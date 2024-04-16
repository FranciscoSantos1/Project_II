module ipvc.estg.desktop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens ipvc.estg.desktop.Login to javafx.fxml;

    exports ipvc.estg.desktop;
    exports ipvc.estg.desktop.Login;

    requires Ginasio;
}