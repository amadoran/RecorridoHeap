module edu.espol.recorridoheap {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens edu.espol.recorridoheap to javafx.fxml;
    exports edu.espol.recorridoheap;
}
