/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.espol.recorridoheap;

import edu.espol.recorridoheap.tda.Heap;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 *
 * @author amado
 */
public final class GraficaArbol extends Pane {
    private Heap<Integer> heap;
    private double radius = 30;
    private double vGap = 75;

    public GraficaArbol() {
        this.heap = new Heap<>((Integer i1, Integer i2) -> Integer.compare(i1, i2));
        setStatus("No se ha creado el árbol");
//        setBackground(new Background(new BackgroundFill(Color.web("#" + "6495ED"), CornerRadii.EMPTY, Insets.EMPTY)));
    }


    public void setStatus(String msg) {
        getChildren().add(new Text(20, 20, msg));
    }

    public void displayTree() {
        this.getChildren().clear();
        if (heap.get(0) != null) {
            displayTree(0, getWidth() / 2, vGap, getWidth() / 4, Color.MEDIUMPURPLE);
        }
    }

    protected void displayTree(int indice, double x, double y, double hGap, Color color) {
        if (heap.get(heap.indiceHijoIzquierdo(indice)) != null) {
            getChildren().add(new Line(x - hGap, y + vGap, x, y));
            displayTree(heap.indiceHijoIzquierdo(indice), x - hGap, y + vGap, hGap / 2, color);
        }

        if (heap.get(heap.indiceHijoDerecho(indice)) != null) {
            getChildren().add(new Line(x + hGap, y + vGap, x, y));
            displayTree(heap.indiceHijoDerecho(indice), x + hGap, y + vGap, hGap / 2, color);
        }

        Circle circle = new Circle(x, y, radius);
        circle.setFill(color);
        circle.setStroke(Color.BLACK);
        getChildren().addAll(circle, new Text(x - 5, y + 4, heap.get(indice) + ""));
    }
    
    public Heap<Integer> getHeap(){
        return this.heap;
    }
}
