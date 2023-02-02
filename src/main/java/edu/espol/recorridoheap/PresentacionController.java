/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.espol.recorridoheap;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author amado
 */
public class PresentacionController implements Initializable {
    
    @FXML
    private Button botonRetornar;
    @FXML
    private VBox vBox;
    @FXML
    private Label labelTitulo;
    @FXML
    private Label labelContenido;
    @FXML
    private Button botonAvanzar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void irIndex() throws IOException{
        App.setRoot("index");
    }
    
    @FXML
    public void verArbol(){
        botonRetornar.setDisable(false);
        botonRetornar.setOnAction(eh -> {
            try {
                App.setRoot("presentacion");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        labelTitulo.setText("Árbol Binario tipo Heap (Montículo)");
        ImageView imgV = new ImageView(new Image(App.class.getResourceAsStream("ejemploArbol.png"), 856, 238, true, true));
        labelTitulo.setGraphic(imgV);
        
        labelContenido.setText("Primero explicaremos las propiedades del árbol que se visualiza." + "\n\n"
                + "El árbol binario heap es el que respeta que los hijos sean menor (o mayor) que sus padres. Esto viene de la noción de que cuando tienes un monticulo de tierra los elementos más pesados que estén presentes, siempre van al fondo si lo sacudes. En esta implementación es lo opuesto, los valores más pequeños van abajo." + "\n\n"
                + "Se escogió este tipo de árbol binario porque es más sencilla la inserción de dados. (A continuación se explicará como es este proceso)");
        botonAvanzar.setOnAction(eh -> {
            verInsercion();
        });
    }
    
    private void verInsercion(){
        botonRetornar.setOnAction(eh -> {
            verArbol();
        });
        
        labelTitulo.setText("Insertar elementos al arbol");
        Image graficoInsertar = new Image(App.class.getResourceAsStream("HeapInsertion.png"), 368, 234, false, false);
        ImageView imgV1 = new ImageView(graficoInsertar);
        
        labelTitulo.setGraphic(imgV1);
        
        labelContenido.setText("El árbol presentado tiene una característica especial al momento de agregarle nuevos vértices, estos siempre se agregan por nivel de izquierda a derecha. Después se verifica si son menores o mayores que el padre. Para esta implementación si el hijo es mayor entonces se intercambia con el padre, así sucesivamente hasta que el padre sea el mayor." + "\n\n"
                + "Para ingresar datos se debe llenar el cuadro de texto a la derecha del boton de insertar y luego aplastar el botón \"Insertar\" o simplemente darle a la tecla enter en el teclado." + "\n\n" 
                + "A continuación se puede observar como se ven los botones al iniciar la aplicación.");
        
        Image graficoEjemplo = new Image(App.class.getResourceAsStream("ejemploInsertar.png"));
        ImageView imgV2 = new ImageView(graficoEjemplo);
        
        labelContenido.setGraphic(imgV2);
        
        botonAvanzar.setOnAction(eh -> {
            verRecorridos();
        });
    }
    
    private void verRecorridos(){
        botonAvanzar.setDisable(true);
        
        botonRetornar.setOnAction(eh ->{
            botonAvanzar.setDisable(false);
            verInsercion();
        });
        
        Image vistaRecorrido = new Image(App.class.getResourceAsStream("traversalTree.jpg"), 858, 300, false, false);
        ImageView imgV1 = new ImageView(vistaRecorrido);
        
        labelTitulo.setGraphic(imgV1);
        
        labelContenido.setGraphic(null);
        
        labelTitulo.setText("Recorridos");
        
        labelContenido.setText("La última funcionalidad del programa y la principal es la de presentar los recorridos del árbol, los cuales son mostrados en un cuadro de texto en la parte derecha. El usuario puede escoger que recorrido desea que le calcule la aplicación con los botones que se encuentran en la parte inferior de la aplicación" + "\n\n"
                + "Los botones de recorridos solo estan disponibles una vez se agregue el primer vértice del árbol." + "\n\n" 
                + "Ya puede continuar a la aplicación con el botón empezar o puede revisar de nuevo las anteriores páginas.");
    }
}
