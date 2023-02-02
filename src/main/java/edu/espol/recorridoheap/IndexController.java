/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.espol.recorridoheap;

import edu.espol.recorridoheap.tda.Heap;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author amado
 */
public class IndexController implements Initializable {

    @FXML
    private Button botonLimpiar;
    @FXML
    private TextField insertarField;
    @FXML
    private Button botonInsertar;
    @FXML
    private Button botonPreOrden;
    @FXML
    private Button botonEnOrden;
    @FXML
    private Button botonPostOrden;
    @FXML
    private TextArea recorridoField;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label recorridoLabel;

    private GraficaArbol graficaArbol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        graficaArbol = new GraficaArbol();
        borderPane.setCenter(graficaArbol);

        this.deshabilitarBotones(true);
        UnaryOperator<Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };

        insertarField.setTextFormatter(
                new TextFormatter<>(new IntegerStringConverter(), 0, integerFilter));
        insertarField.setOnKeyPressed(eh -> {
            if (eh.getCode().equals(KeyCode.ENTER)) {
                insertarHeap();
            }
        });
    }

    @FXML
    public void insertarHeap() {
        if (this.insertarField.getText() != null) {
            int insertar = Integer.parseInt(this.insertarField.getText());
            Heap<Integer> heap = graficaArbol.getHeap();
            if (heap.contains(insertar)) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Info");
                alert.setHeaderText("No se agregó el elemento");
                alert.setContentText("El árbol ya contiene ese elemento, entonces no se puede se puede agregar");
                alert.showAndWait();
                return;
            }

            this.deshabilitarBotones(false);

            heap.offer(insertar);

            graficaArbol.displayTree();

            if (heap.tamano() == 15) {
                botonInsertar.setDisable(true);
                insertarField.setOnKeyPressed(eh -> {
                    if (eh.getCode().equals(KeyCode.ENTER)) {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setHeaderText("Árbol lleno");
                        alert.setTitle("Info");
                        alert.setContentText("El árbol se encuentra lleno y no se pueden agregar más hijos");
                        alert.showAndWait();
                    }
                });

            }
        }
    }

    @FXML
    public void limpiarPantalla() throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Limpiar Pantalla");
        alert.setHeaderText("¿Estas seguro de eliminar el árbol y los recorridos?");
        Optional<ButtonType> action = alert.showAndWait();
        ButtonType botonAplastado = action.orElse(ButtonType.CANCEL);

        if (botonAplastado.equals(ButtonType.OK)) {
            App.setRoot("index");
        }
    }

    private void deshabilitarBotones(boolean habilitarse) {
        this.botonLimpiar.setDisable(habilitarse);
        this.botonPreOrden.setDisable(habilitarse);
        this.botonEnOrden.setDisable(habilitarse);
        this.botonPostOrden.setDisable(habilitarse);
    }

    @FXML
    public void recorrerPreOrden() {
        recorridoLabel.setText("Recorrido Pre Orden:");
        String recorrido = graficaArbol.getHeap().recorridoPreOrden();
        this.recorridoField.setText(recorrido);
    }

    @FXML
    public void recorrerEnOrden() {

        recorridoLabel.setText("Recorrido En Orden:");
        String recorrido = graficaArbol.getHeap().recorridoEnOrden();
        this.recorridoField.setText(recorrido);
    }

    @FXML
    public void recorrerPostOrden() {
        recorridoLabel.setText("Recorrido Post Orden:");
        String recorrido = graficaArbol.getHeap().recorridoPostOrden();
        this.recorridoField.setText(recorrido);
    }
    
    @FXML
    public void irPresentacion() throws IOException{
        App.setRoot("presentacion");
    }
}
