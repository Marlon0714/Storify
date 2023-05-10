package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.Comparator;

public class ListaCircular<T extends Serializable> implements Serializable{

    public static class Nodo<T extends Serializable> implements Serializable{
        // Variable en la cual se va a guardar el valor.
        private T valor;
        // Variable para enlazar los nodos.
        private Nodo<T> siguiente;
        private Nodo<T> anterior;

        /**
         * Constructor que inicializamos el valor de las variables.
         */
        public Nodo(T valor, Nodo<T> siguiente, Nodo<T> anterior) {
            super();
            this.valor = valor;
            this.siguiente = siguiente;
            this.anterior = anterior;
        }

        public Nodo() {

        }

        // Métodos get y set para los atributos.

        public T getValor() {
            return valor;
        }

        public void setValor(T valor) {
            this.valor = valor;
        }

        public Nodo<T> getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Nodo<T> siguiente) {
            this.siguiente = siguiente;
        }

        public Nodo<T> getAnterior() {
            return anterior;
        }

        public void setAnterior(Nodo<T> anterior) {
            this.anterior = anterior;
        }
    }

    Nodo<T> inicio;
    Nodo<T> fin;
    public ListaCircular(){
        this.inicio = null;
        this.fin = null;
    }
    public void insertar(T valor) {
        Nodo<T> nuevoNodo = new Nodo<>(valor, null, null);
        if (inicio == null) {
            inicio = nuevoNodo;
            nuevoNodo.setAnterior(nuevoNodo);
            nuevoNodo.setSiguiente(nuevoNodo);
            fin = inicio;
        } else {
            nuevoNodo.setAnterior(fin);
            fin.setSiguiente(nuevoNodo);
            nuevoNodo.setSiguiente(inicio);
            inicio.setAnterior(nuevoNodo);
            fin = nuevoNodo;
        }
    }

    public void eliminar(T valor) {
        if (inicio == null) {
            return;
        }

        Nodo<T> nodoActual = inicio;
        do {
            if (nodoActual.getValor().equals(valor)) {
                if (inicio == fin) {
                    inicio = null;
                    fin = null;
                } else if (nodoActual == inicio) {
                    inicio = inicio.getSiguiente();
                    inicio.setAnterior(fin);
                    fin.setSiguiente(inicio);
                } else if (nodoActual == fin) {
                    fin = fin.getAnterior();
                    fin.setSiguiente(inicio);
                    inicio.setAnterior(fin);
                } else {
                    nodoActual.getAnterior().setSiguiente(nodoActual.getSiguiente());
                    nodoActual.getSiguiente().setAnterior(nodoActual.getAnterior());
                }
                return;
            }
            nodoActual = nodoActual.getSiguiente();
        } while (nodoActual != inicio);
    }

    public boolean buscar(T valor) {
        if (inicio == null) {
            return false;
        }

        Nodo<T> nodoActual = inicio;
        do {
            if (nodoActual.getValor().equals(valor)) {
                return true;
            }
            nodoActual = nodoActual.getSiguiente();
        } while (nodoActual != inicio);

        return false;
    }

    public void ordenar(Comparator<T> comparador) {
        // Convertir la lista circular en una lista doblemente enlazada
        if (inicio == null) {
            return;
        }
        fin.setSiguiente(null);
        inicio.setAnterior(null);

        // Aplicar el algoritmo de ordenación por inserción
        Nodo<T> actual = inicio.getSiguiente();
        while (actual != null) {
            Nodo<T> nodoInsertado = actual;
            T valorInsertado = nodoInsertado.getValor();
            Nodo<T> nodoComparado = nodoInsertado.getAnterior();
            while (nodoComparado != null && comparador.compare(nodoComparado.getValor(), valorInsertado) > 0) {
                nodoInsertado.setValor(nodoComparado.getValor());
                nodoInsertado = nodoComparado;
                nodoComparado = nodoComparado.getAnterior();
            }
            nodoInsertado.setValor(valorInsertado);
            actual = actual.getSiguiente();
        }

        // Convertir la lista doblemente enlazada en una lista circular
        fin.setSiguiente(inicio);
        inicio.setAnterior(fin);
    }

    public void insertarEnPosicion(int posicion, T valor) {
        Nodo<T> nuevoNodo = new Nodo<>(valor,null,null);
        if(inicio == null){
            // Si la lista está vacía, el nuevo nodo será el inicio y el fin
            inicio = nuevoNodo;
            fin = nuevoNodo;
            nuevoNodo.setAnterior(nuevoNodo);
            nuevoNodo.setSiguiente(nuevoNodo);
        }else if(posicion <= 0){
            // Si la posición es menor o igual a cero, insertar al inicio
            nuevoNodo.setSiguiente(inicio);
            nuevoNodo.setAnterior(fin);
            inicio.setAnterior(nuevoNodo);
            fin.setSiguiente(nuevoNodo);
            inicio = nuevoNodo;
        }else{
            // Buscar el nodo en la posición deseada
            Nodo<T> actual = inicio;
            int contador = 0;
            while(contador < posicion - 1){
                actual = actual.getSiguiente();
                contador++;
                // Si llegamos al final de la lista, volver al inicio
                if(actual == inicio){
                    actual = actual.getSiguiente();
                }
            }
            // Insertar el nuevo nodo después del nodo actual
            nuevoNodo.setSiguiente(actual.getSiguiente());
            nuevoNodo.setAnterior(actual);
            actual.getSiguiente().setAnterior(nuevoNodo);
            actual.setSiguiente(nuevoNodo);
            // Si se inserta al final, actualizar el fin
            if(nuevoNodo.getSiguiente() == inicio){
                fin = nuevoNodo;
            }
        }
    }
}
