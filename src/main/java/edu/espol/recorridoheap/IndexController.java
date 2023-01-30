/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.espol.recorridoheap;

import edu.espol.recorridoheap.tda.Heap;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
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
        //botonInsertar.set(keyEvent -> insertarHeap());


    }

    @FXML
    public void insertarHeap() {    
        this.deshabilitarBotones(false);
        int insertar = Integer.parseInt(this.insertarField.getText());
        
        Heap<Integer> heap = graficaArbol.getHeap();
        heap.offer(insertar);
        
        graficaArbol.displayTree();
        
        if(heap.tamano() == 15){
            botonInsertar.setDisable(true);
        }
    }
    
    @FXML
    public void limpiarPantalla() throws IOException{
        App.setRoot("index");
    }

    private void deshabilitarBotones(boolean habilitarse) {
        this.botonLimpiar.setDisable(habilitarse);
        this.botonPreOrden.setDisable(habilitarse);
        this.botonEnOrden.setDisable(habilitarse);
        this.botonPostOrden.setDisable(habilitarse);
    }
    
    @FXML
    public void recorrerPreOrden(){
        String recorrido = graficaArbol.getHeap().recorridoPreOrden();
        this.recorridoField.setText(recorrido);
    }
    
    @FXML
    public void recorrerEnOrden(){
        String recorrido = graficaArbol.getHeap().recorridoEnOrden();
        this.recorridoField.setText(recorrido);
    }
    
    @FXML
    public void recorrerPostOrden(){
        String recorrido = graficaArbol.getHeap().recorridoPostOrden();
        this.recorridoField.setText(recorrido);
    }
}
