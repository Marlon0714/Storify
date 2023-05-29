package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaCircular<T extends Serializable> implements Serializable, Iterable<T> {

    /**
     * Clase interna Nodo que representa un nodo en la lista circular.
     *
     * @param <T> el tipo de elemento almacenado en el nodo.
     */
    public static class Nodo<T extends Serializable> implements Serializable {
        // Variable en la cual se va a guardar el valor.
        private T valor;
        // Variable para enlazar los nodos.
        private Nodo<T> siguiente;
        private Nodo<T> anterior;

        /**
         * Constructor que inicializa el valor de las variables.
         *
         * @param valor     el valor del nodo.
         * @param siguiente el nodo siguiente.
         * @param anterior  el nodo anterior.
         */
        public Nodo(T valor, Nodo<T> siguiente, Nodo<T> anterior) {
            super();
            this.valor = valor;
            this.siguiente = siguiente;
            this.anterior = anterior;
        }

        /**
         * Constructor por defecto de la clase Nodo.
         */
        public Nodo() {

        }

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

        /**
         * Obtiene el nodo anterior.
         *
         * @return el nodo anterior.
         */
        public Nodo<T> getAnterior() {
            return anterior;
        }

        /**
         * Establece el nodo anterior.
         *
         * @param anterior el nodo anterior a establecer.
         */
        public void setAnterior(Nodo<T> anterior) {
            this.anterior = anterior;
        }
    }

    private Nodo<T> inicio;  // Primer nodo de la lista circular.
    private Nodo<T> fin;     // Último nodo de la lista circular.

    /**
     * Constructor de la clase ListaCircular que inicializa los atributos.
     */
    public ListaCircular() {
        this.inicio = null;
        this.fin = null;
    }

    /**
     * Verifica si la lista circular está vacía.
     *
     * @return true si la lista está vacía, false de lo contrario.
     */
    public boolean isEmpty() {
        return inicio == null;
    }

    /**
     * Inserta un nuevo elemento al final de la lista circular.
     *
     * @param valor el valor del elemento a insertar.
     */
    public void insertar(T valor) {
        Nodo<T> nuevoNodo = new Nodo<>(valor, null, null);
        if (inicio == null) {
            // Si la lista está vacía, el nuevo nodo será el inicio y el fin
            inicio = nuevoNodo;
            nuevoNodo.setAnterior(nuevoNodo);
            nuevoNodo.setSiguiente(nuevoNodo);
            fin = inicio;
        } else {
            // Si la lista no está vacía, se enlaza el nuevo nodo al final de la lista
            nuevoNodo.setAnterior(fin);
            fin.setSiguiente(nuevoNodo);
            nuevoNodo.setSiguiente(inicio);
            inicio.setAnterior(nuevoNodo);
            fin = nuevoNodo;
        }
    }

    /**
     * Elimina un elemento de la lista circular.
     *
     * @param valor el valor del elemento a eliminar.
     */
    public void eliminar(T valor) {
        if (inicio == null) {
            return;
        }

        Nodo<T> nodoActual = inicio;
        do {
            if (nodoActual.getValor().equals(valor)) {
                if (inicio == fin) {
                    // Si solo hay un nodo en la lista, se eliminan las referencias al inicio y al fin
                    inicio = null;
                    fin = null;
                } else if (nodoActual == inicio) {
                    // Si el nodo a eliminar es el inicio, se actualizan las referencias al inicio y al fin
                    inicio = inicio.getSiguiente();
                    inicio.setAnterior(fin);
                    fin.setSiguiente(inicio);
                } else if (nodoActual == fin) {
                    // Si el nodo a eliminar es el fin, se actualizan las referencias al inicio y al fin
                    fin = fin.getAnterior();
                    fin.setSiguiente(inicio);
                    inicio.setAnterior(fin);
                } else {
                    // Si el nodo a eliminar está en medio de la lista, se actualizan las referencias de los nodos vecinos
                    nodoActual.getAnterior().setSiguiente(nodoActual.getSiguiente());
                    nodoActual.getSiguiente().setAnterior(nodoActual.getAnterior());
                }
                return;
            }
            nodoActual = nodoActual.getSiguiente();
        } while (nodoActual != inicio);
    }

    /**
     * Busca un elemento en la lista circular.
     *
     * @param valor el valor del elemento a buscar.
     * @return true si el elemento está en la lista, false de lo contrario.
     */
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

    /**
     * Ordena los elementos de la lista circular utilizando un comparador dado.
     *
     * @param comparador el comparador a utilizar para ordenar los elementos.
     */
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

    /**
     * Inserta un nuevo elemento en una posición dada de la lista circular.
     *
     * @param posicion la posición en la que se insertará el elemento.
     * @param valor    el valor del elemento a insertar.
     */
    public void insertarEnPosicion(int posicion, T valor) {
        Nodo<T> nuevoNodo = new Nodo<>(valor, null, null);
        if (inicio == null) {
            // Si la lista está vacía, el nuevo nodo será el inicio y el fin
            inicio = nuevoNodo;
            fin = nuevoNodo;
            nuevoNodo.setAnterior(nuevoNodo);
            nuevoNodo.setSiguiente(nuevoNodo);
        } else if (posicion <= 0) {
            // Si la posición es menor o igual a cero, insertar al inicio
            nuevoNodo.setSiguiente(inicio);
            nuevoNodo.setAnterior(fin);
            inicio.setAnterior(nuevoNodo);
            fin.setSiguiente(nuevoNodo);
            inicio = nuevoNodo;
        } else {
            // Buscar el nodo en la posición deseada
            Nodo<T> actual = inicio;
            int contador = 0;
            while (contador < posicion - 1) {
                actual = actual.getSiguiente();
                contador++;
                // Si llegamos al final de la lista, volver al inicio
                if (actual == inicio) {
                    actual = actual.getSiguiente();
                }
            }
            // Insertar el nuevo nodo después del nodo actual
            nuevoNodo.setSiguiente(actual.getSiguiente());
            nuevoNodo.setAnterior(actual);
            actual.getSiguiente().setAnterior(nuevoNodo);
            actual.setSiguiente(nuevoNodo);
            // Si se inserta al final, actualizar el fin
            if (nuevoNodo.getSiguiente() == inicio) {
                fin = nuevoNodo;
            }
        }
    }

    /**
     * Retorna un iterador para la lista circular.
     *
     * @return un iterador para la lista circular.
     */
    @Override
    public Iterator<T> iterator() {
        return new ListaIterator();
    }

    /**
     * Clase interna ListaIterator que implementa el iterador para la lista circular.
     */
    private class ListaIterator implements Iterator<T> {

        private Nodo<T> nodoActual;

        /**
         * Constructor de la clase ListaIterator que inicializa el nodo actual.
         */
        public ListaIterator() {
            nodoActual = inicio;
            fin.setSiguiente(null);
            inicio.setAnterior(null);
        }

        /**
         * Verifica si el iterador está vacío.
         *
         * @return true si el iterador está vacío, false de lo contrario.
         */
        public boolean isEmpty() {
            return inicio == null;
        }

        /**
         * Verifica si hay un siguiente elemento en el iterador.
         *
         * @return true si hay un siguiente elemento, false de lo contrario.
         */
        @Override
        public boolean hasNext() {
            return nodoActual != null;
        }

        /**
         * Retorna el siguiente elemento del iterador.
         *
         * @return el siguiente elemento del iterador.
         * @throws NoSuchElementException si no hay más elementos en el iterador.
         */
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
