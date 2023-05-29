package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * La clase ArbolBinario representa un árbol binario de búsqueda.
 * Permite insertar, eliminar y buscar elementos en el árbol.
 * También implementa la interfaz Iterable para poder recorrer los elementos del árbol.
 *
 * @param <T> el tipo de elementos que se almacenan en el árbol, debe implementar Comparable y Serializable.
 */
public class ArbolBinario<T extends Comparable<T> & Serializable> implements Serializable, Iterable<T> {

    /**
     * La clase Nodo representa un nodo del árbol binario.
     * Cada nodo contiene un valor, así como referencias a los nodos izquierdo y derecho.
     *
     * @param <T> el tipo de elementos que se almacenan en el nodo.
     */
    public static class Nodo<T extends Comparable<T> & Serializable> implements Serializable {

        private T valor;
        private Nodo<T> izq;
        private Nodo<T> der;

        /**
         * Crea un nuevo nodo con el valor especificado y referencias nulas a los nodos izquierdo y derecho.
         *
         * @param valor el valor del nodo.
         */
        public Nodo(T valor) {
            this.valor = valor;
            this.izq = null;
            this.der = null;
        }

        /**
         * Obtiene el valor almacenado en el nodo.
         *
         * @return el valor del nodo.
         */
        public T getValor() {
            return valor;
        }

        /**
         * Establece el valor del nodo.
         *
         * @param valor el nuevo valor del nodo.
         */
        public void setValor(T valor) {
            this.valor = valor;
        }

        /**
         * Obtiene el nodo izquierdo.
         *
         * @return el nodo izquierdo.
         */
        public Nodo<T> getIzq() {
            return izq;
        }

        /**
         * Establece el nodo izquierdo.
         *
         * @param izq el nuevo nodo izquierdo.
         */
        public void setIzq(Nodo<T> izq) {
            this.izq = izq;
        }

        /**
         * Obtiene el nodo derecho.
         *
         * @return el nodo derecho.
         */
        public Nodo<T> getDer() {
            return der;
        }

        /**
         * Establece el nodo derecho.
         *
         * @param der el nuevo nodo derecho.
         */
        public void setDer(Nodo<T> der) {
            this.der = der;
        }
    }

    private Nodo<T> raiz;

    /**
     * Crea un nuevo árbol binario vacío.
     */
    public ArbolBinario() {
        this.raiz = null;
    }

    /**
     * Obtiene la raíz del árbol.
     *
     * @return la raíz del árbol.
     */
    public Nodo<T> getRaiz() {
        return raiz;
    }

    /**
     * Establece la raíz del árbol.
     *
     * @param raiz la nueva raíz del árbol.
     */
    public void setRaiz(Nodo<T> raiz) {
        this.raiz = raiz;
    }

    /**
     * Inserta un nuevo valor en el árbol.
     *
     * @param valor el valor a insertar.
     */
    public void insertar(T valor) {
        Nodo<T> nuevoNodo = new Nodo<>(valor);
        if (raiz == null) {
            raiz = nuevoNodo;
        } else {
            insertar(raiz, nuevoNodo);
        }
    }

    private void insertar(Nodo<T> nodoActual, Nodo<T> nuevoNodo) {
        if (nuevoNodo.getValor().compareTo(nodoActual.getValor()) <= 0) {
            if (nodoActual.getIzq() == null) {
                nodoActual.setIzq(nuevoNodo);
            } else {
                insertar(nodoActual.getIzq(), nuevoNodo);
            }
        } else {
            if (nodoActual.getDer() == null) {
                nodoActual.setDer(nuevoNodo);
            } else {
                insertar(nodoActual.getDer(), nuevoNodo);
            }
        }
    }

    /**
     * Elimina un valor del árbol.
     *
     * @param valor el valor a eliminar.
     */
    public void eliminar(T valor) {
        raiz = eliminar(raiz, valor);
    }

    private Nodo<T> eliminar(Nodo<T> nodoActual, T valor) {
        if (nodoActual == null) {
            return null;
        }
        int comparacion = valor.compareTo(nodoActual.getValor());
        if (comparacion < 0) {
            nodoActual.setIzq(eliminar(nodoActual.getIzq(), valor));
        } else if (comparacion > 0) {
            nodoActual.setDer(eliminar(nodoActual.getDer(), valor));
        } else {
            if (nodoActual.getIzq() == null) {
                return nodoActual.getDer();
            } else if (nodoActual.getDer() == null) {
                return nodoActual.getIzq();
            } else {
                Nodo<T> sucesor = nodoActual.getIzq();
                Nodo<T> padreSucesor = nodoActual;

                while (sucesor.getDer() != null) {
                    padreSucesor = sucesor;
                    sucesor = sucesor.getDer();
                }

                nodoActual.setValor(sucesor.getValor());

                if (padreSucesor == nodoActual) {
                    nodoActual.setIzq(eliminar(nodoActual.getIzq(), sucesor.getValor()));
                } else {
                    padreSucesor.setDer(eliminar(padreSucesor.getDer(), sucesor.getValor()));
                }
            }
        }
        return nodoActual;
    }

    /**
     * Busca un valor en el árbol.
     *
     * @param valor el valor a buscar.
     * @return el valor encontrado, o null si no se encuentra.
     */
    public T buscar(T valor) {
        Nodo<T> nodo = buscar(raiz, valor);
        if (nodo == null) {
            return null;
        } else {
            return nodo.getValor();
        }
    }

    private Nodo<T> buscar(Nodo<T> nodoActual, T valor) {
        if (nodoActual == null) {
            return null;
        }

        int comparacion = valor.compareTo(nodoActual.getValor());

        if (comparacion == 0) {
            return nodoActual;
        } else if (comparacion < 0) {
            return buscar(nodoActual.getIzq(), valor);
        } else {
            return buscar(nodoActual.getDer(), valor);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArbolIterator();
    }

    private class ArbolIterator implements Iterator<T> {
        private final Pila<Nodo<T>> pila;

        public ArbolIterator() {
            pila = new Pila<>();
            if (raiz != null) {
                avanzarHastaPrimerNodo(raiz);
            }
        }

        @Override
        public boolean hasNext() {
            return !pila.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Nodo<T> siguienteNodo = pila.pop();
            if (siguienteNodo.getDer() != null) {
                avanzarHastaPrimerNodo(siguienteNodo.getDer());
            }
            return siguienteNodo.getValor();
        }

        private void avanzarHastaPrimerNodo(Nodo<T> nodo) {
            while (nodo != null) {
                pila.push(nodo);
                nodo = nodo.getIzq();
            }
        }
    }
}
































