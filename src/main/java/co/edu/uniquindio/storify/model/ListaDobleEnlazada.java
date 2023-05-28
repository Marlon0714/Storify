package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaDobleEnlazada<T extends Serializable> implements Serializable, Iterable<T> {

    public static class Nodo<T extends Serializable> implements Serializable {
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
    Nodo<T> primero;
    public ListaDobleEnlazada(){
        this.primero = null;
    }

    /**
     * Método para insertar un nodo al final de la lista.
     */
    public void insertar(T valor) {
        Nodo<T> nuevoNodo = new Nodo<>(valor, null, null);

        if (primero == null) {
            primero = nuevoNodo;
        } else {
            Nodo<T> ultimo = primero;
            while(ultimo.getSiguiente() != null) {
                ultimo = ultimo.getSiguiente();
            }
            ultimo.setSiguiente(nuevoNodo);
            nuevoNodo.setAnterior(ultimo);
        }
    }

    /**
     * Método para eliminar un nodo de la lista.
     */
    public void eliminar(T valor) {
        Nodo<T> nodoActual = primero;

        while (nodoActual != null && !nodoActual.getValor().equals(valor)) {
            nodoActual = nodoActual.getSiguiente();
        }

        if (nodoActual != null) {
            Nodo<T> anterior = nodoActual.getAnterior();
            Nodo<T> siguiente = nodoActual.getSiguiente();

            if (anterior != null) {
                anterior.setSiguiente(siguiente);
            } else {
                primero = siguiente;
            }

            if (siguiente != null) {
                siguiente.setAnterior(anterior);
            }
        }
    }

    /**
     * Método para insertar un nodo en una posición específica de la lista.
     */
    public void insertarEnPosicion(T valor, int posicion) {
        if (posicion < 0) {
            throw new IndexOutOfBoundsException("La posición no puede ser negativa");
        }

        Nodo<T> nuevoNodo = new Nodo<>(valor, null, null);

        if (posicion == 0) {
            nuevoNodo.setSiguiente(primero);
            if (primero != null) {
                primero.setAnterior(nuevoNodo);
            }
            primero = nuevoNodo;
        } else {
            Nodo<T> nodoActual = primero;
            int contador = 0;

            while (nodoActual != null && contador < posicion - 1) {
                nodoActual = nodoActual.getSiguiente();
                contador++;
            }

            if (nodoActual != null) {
                Nodo<T> siguiente = nodoActual.getSiguiente();
                nuevoNodo.setSiguiente(siguiente);
                nuevoNodo.setAnterior(nodoActual);
                nodoActual.setSiguiente(nuevoNodo);
                if (siguiente != null) {
                    siguiente.setAnterior(nuevoNodo);
                }
            } else {
                throw new IndexOutOfBoundsException("La posición excede el tamaño de la lista");
            }
        }
    }

    /**
     * Método para agregar todos los elementos de otra lista a esta lista.
     */
    public void addAll(ListaDobleEnlazada<T> otraLista) {
        for (T valor : otraLista) {
            insertar(valor);
        }
    }

    public boolean isEmpty() {
        return primero == null;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListaIterator();
    }

    private class ListaIterator implements Iterator<T> {

        private Nodo<T> nodoActual;

        public ListaIterator() {
            nodoActual = primero;
        }

        @Override
        public boolean hasNext() {
            return nodoActual != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T valor = nodoActual.getValor();
                nodoActual = nodoActual.getSiguiente();
                return valor;
            }
            throw new NoSuchElementException();
        }
    }

}
