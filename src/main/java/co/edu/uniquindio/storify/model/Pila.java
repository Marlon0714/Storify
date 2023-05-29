package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.NoSuchElementException;

public class Pila<T extends Serializable> implements Serializable {
    // Clase interna Nodo que representa un nodo en la pila.
    private static class Nodo<T extends Serializable> implements Serializable {
        private T valor;
        private Nodo<T> siguiente;

        /**
         * Constructor que crea un nuevo nodo con el valor especificado.
         *
         * @param valor el valor del nodo.
         */
        public Nodo(T valor) {
            this.valor = valor;
            this.siguiente = null;
        }

        /**
         * Constructor por defecto de la clase Nodo.
         */
        public Nodo() {}

        // Métodos get y set para los atributos.

        /**
         * Obtiene el valor almacenado en el nodo.
         *
         * @return el valor almacenado en el nodo.
         */
        public T getValor() {
            return valor;
        }

        /**
         * Establece el valor del nodo.
         *
         * @param valor el valor a establecer.
         */
        public void setValor(T valor) {
            this.valor = valor;
        }

        /**
         * Obtiene el nodo siguiente.
         *
         * @return el nodo siguiente.
         */
        public Nodo<T> getSiguiente() {
            return siguiente;
        }

        /**
         * Establece el nodo siguiente.
         *
         * @param siguiente el nodo siguiente a establecer.
         */
        public void setSiguiente(Nodo<T> siguiente) {
            this.siguiente = siguiente;
        }
    }

    private Nodo<T> top;

    /**
     * Constructor de la clase Pila.
     * Crea una nueva pila vacía.
     */
    public Pila() {
        top = null;
    }

    /**
     * Agrega un elemento a la pila.
     *
     * @param valor el valor a agregar.
     */
    public void push(T valor) {
        Nodo<T> nuevoNodo = new Nodo<>(valor);
        nuevoNodo.setSiguiente(top);
        top = nuevoNodo;
    }

    /**
     * Elimina y devuelve el elemento en la parte superior de la pila.
     *
     * @return el elemento en la parte superior de la pila.
     * @throws NoSuchElementException si la pila está vacía.
     */
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("La pila está vacía");
        }

        T valor = top.getValor();
        top = top.getSiguiente();
        return valor;
    }

    /**
     * Verifica si la pila está vacía.
     *
     * @return true si la pila está vacía, false de lo contrario.
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Obtiene el elemento en la parte superior de la pila sin eliminarlo.
     *
     * @return el elemento en la parte superior de la pila.
     * @throws NoSuchElementException si la pila está vacía.
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("La pila está vacía");
        }

        return top.getValor();
    }
}
