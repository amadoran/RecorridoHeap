/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.espol.recorridoheap.tda;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author amado
 * @param <T>
 */
public class Heap<T> {

    private T[] datos;
    private int tamano;
    private final Comparator<T> comp;
    private int nivel;

    public Heap(Comparator<T> comp) {
        this.comp = comp;
        this.datos = (T[]) new Object[1];
    }

    public boolean offer(T elem) {
        if (elem == null) {
            throw new NullPointerException();
        }

        if (this.tamano >= this.datos.length) {
            this.crecer();
        }

        this.ordenarSubida(this.tamano, elem);
        tamano++;
        return true;
    }

    public T poll() {
        if (isEmpty()) {
            return null;
        }

        T resultado = this.datos[0];

        this.ordenarBajada();

        return resultado;
    }

    private void crecer() {
        nivel++;
        this.datos = Arrays.copyOf(this.datos, this.datos.length + (int) Math.pow(2, nivel));
    }

    private void ordenarSubida(int indice, T elem) {
        T nuevo = elem;

        this.datos[indice] = nuevo;

        T padre = this.datos[indicePadre(indice)];

        while (indice > 0) {
            if (comp.compare(nuevo, padre) > 0) {
                this.datos[indicePadre(indice)] = nuevo;
                this.datos[indice] = padre;
                indice = indicePadre(indice);
                padre = this.datos[indicePadre(indice)];
            } else {
                return;
            }
        }
    }

    private void ordenarBajada() {
        T ultimo = this.datos[this.tamano - 1];
        this.datos[this.tamano - 1] = null;
        tamano--;

        this.datos[0] = ultimo;

        if (tamano < Math.pow(2, nivel)) {
            nivel--;
        }

        if (isEmpty()) {
            this.datos[0] = null;
            return;
        }

        int indice = 0;
        while (indiceHijoDerecho(indice) < this.tamano || indiceHijoIzquierdo(indice) < this.tamano) {
            T hijoDerecho = this.datos[indiceHijoDerecho(indice)];
            T hijoIzquierdo = this.datos[indiceHijoIzquierdo(indice)];

            if (hijoDerecho == null) {
                hijoDerecho = hijoIzquierdo;
            }

            boolean mayorHijoDerecho = comp.compare(ultimo, hijoDerecho) < 0;
            boolean mayorHijoIzquierdo = comp.compare(ultimo, hijoIzquierdo) < 0;
            if (mayorHijoDerecho || mayorHijoIzquierdo) {
                if (comp.compare(hijoDerecho, hijoIzquierdo) > 0) {
                    this.datos[indiceHijoDerecho(indice)] = ultimo;
                    this.datos[indice] = hijoDerecho;
                } else {
                    this.datos[indiceHijoIzquierdo(indice)] = ultimo;
                    this.datos[indice] = hijoIzquierdo;
                }
            }

            if (comp.compare(hijoDerecho, hijoIzquierdo) > 0) {
                indice = indiceHijoDerecho(indice);
            } else {
                indice = indiceHijoIzquierdo(indice);
            }
        }
    }

    public static <T> List<T> heapSort(List<T> lista) {
        int tamano = lista.size();

        for (int i = tamano / 2 - 1; i >= 0; i--) {
            heapify(lista, tamano, i);
        }

        for (int i = tamano - 1; i > 0; i--) {
            T temp = lista.get(0);

            lista.set(0, lista.get(i));
            lista.set(i, temp);

            heapify(lista, i, 0);
        }

        return lista;
    }

    private static <T> void heapify(List<T> lista, int tamano, int raiz) {
        int menor = raiz;

        int indiceIzquierdo = (2 * raiz) + 1;
        int indiceDerecho = 2 * (raiz + 1);

        if (indiceIzquierdo < tamano && (((Comparable<T>) lista.get(indiceIzquierdo)).compareTo(lista.get(menor)) < 0)) {
            menor = indiceIzquierdo;
        }

        if (indiceDerecho < tamano && (((Comparable<T>) lista.get(indiceDerecho)).compareTo(lista.get(menor)) < 0)) {
            menor = indiceDerecho;
        }

        if (menor != raiz) {
            T intercambio = lista.get(raiz);
            lista.set(raiz, lista.get(menor));
            lista.set(menor, intercambio);

            heapify(lista, tamano, menor);
        }
    }

    public String recorridoPreOrden() {
        Stack<Integer> porRecorrer = new Stack<>();
        porRecorrer.push(0);
        StringBuilder sb = new StringBuilder();

        while (!porRecorrer.isEmpty()) {
            Integer tmp = porRecorrer.pop();
            sb.append(this.get(tmp));
            if (this.get(this.indiceHijoDerecho(tmp)) != null) {
                porRecorrer.push(indiceHijoDerecho(tmp));
            }
            if (this.get(this.indiceHijoIzquierdo(tmp)) != null) {
                porRecorrer.push(indiceHijoIzquierdo(tmp));
            }

            sb.append(" - ");
        }

        sb.delete(sb.length() - 2, sb.length());

        return sb.toString();
    }

    public String recorridoEnOrden() {
        Stack<Integer> porRecorrer = new Stack<>();
        int indice = 0;
        T buscador = this.get(indice);
        StringBuilder sb = new StringBuilder();

        while (buscador != null || !porRecorrer.isEmpty()) {
            while (buscador != null) {
                porRecorrer.push(indice);
                indice = indiceHijoIzquierdo(indice);
                buscador = this.get(indice);
            }

            indice = porRecorrer.pop();
            buscador = this.get(indice);
            sb.append(buscador);
            sb.append(" - ");

            indice = indiceHijoDerecho(indice);
            buscador = this.get(indice);
        }

        sb.delete(sb.length() - 2, sb.length());

        return sb.toString();
    }

    public String recorridoPostOrden() {
        Stack<Integer> s1 = new Stack<>();
        Stack<T> s2 = new Stack<>();
        StringBuilder sb = new StringBuilder();

        s1.push(0);

        while (!s1.isEmpty()) {

            int tmp = s1.pop();
            s2.push(this.get(tmp));

            if (this.get(indiceHijoIzquierdo(tmp)) != null) {
                s1.push(indiceHijoIzquierdo(tmp));
            }
            if (this.get(indiceHijoDerecho(tmp)) != null) {
                s1.push(indiceHijoDerecho(tmp));
            }
        }

        while (!s2.isEmpty()) {
            sb.append(s2.pop());
            sb.append(" - ");
        }

        sb.delete(sb.length() - 2, sb.length());

        return sb.toString();
    }

    public T get(int indice) {
        try {
            return this.datos[indice];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    
    public boolean contains(T object){
        for(int i = 0; i < tamano; i++){
            if(datos[i].equals(object)){
                return true;
            }
        }
        return false;
    }

    public int getAltura() {
        return nivel + 1;
    }

    public boolean isEmpty() {
        return tamano == 0;
    }

    public int tamano() {
        return tamano;
    }

    public int indicePadre(int indice) {
        return (indice - 1) / 2;
    }

    public int indiceHijoDerecho(int indice) {
        return 2 * (indice + 1);
    }

    public int indiceHijoIzquierdo(int indice) {
        return (2 * indice) + 1;
    }
}
