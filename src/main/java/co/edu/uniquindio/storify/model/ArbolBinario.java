package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArbolBinario<T extends Comparable<T> & Serializable> implements Serializable, Iterable<T> {

    public static class Nodo<T extends Comparable<T> & Serializable> implements Serializable {

        private T valor;
        private Nodo<T> izq;
        private Nodo<T> der;

        public Nodo(T valor, Nodo<T> izq, Nodo<T> der) {
            super();
            this.valor = valor;
            this.izq = izq;
            this.der = der;
        }

        public Nodo(){

        }

        public T getValor() {
            return valor;
        }

        public void setValor(T valor) {
            this.valor = valor;
        }

        public Nodo<T> getIzq() {
            return izq;
        }

        public void setIzq(Nodo<T> izq) {
            this.izq = izq;
        }

        public Nodo<T> getDer() {
            return der;
        }

        public void setDer(Nodo<T> der) {
            this.der = der;
        }
    }

    private Nodo<T> raiz;

    public ArbolBinario() {
        this.raiz = null;
    }

    public Nodo<T> getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo<T> raiz) {
        this.raiz = raiz;
    }

    public void insertar(T valor) {
        Nodo<T> nuevoNodo = new Nodo<>(valor, null, null);
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
                // Caso: el nodo tiene dos hijos
                Nodo<T> sucesor = nodoActual.getIzq();
                Nodo<T> padreSucesor = nodoActual;

                // Buscamos el nodo más grande en el subárbol izquierdo
                while (sucesor.getDer() != null) {
                    padreSucesor = sucesor;
                    sucesor = sucesor.getDer();
                }

                // Reemplazamos el nodo a eliminar por su sucesor
                nodoActual.setValor(sucesor.getValor());

                // Eliminamos el sucesor del subárbol izquierdo
                if (padreSucesor == nodoActual) {
                    nodoActual.setIzq(eliminar(nodoActual.getIzq(), sucesor.getValor()));
                } else {
                    padreSucesor.setDer(eliminar(padreSucesor.getDer(), sucesor.getValor()));
                }
            }
        }
        return nodoActual;
    }

    public T buscar(T valor) {
        Nodo<T> nodo = buscar(raiz, valor);
        if (nodo == null){
            return null;
        }else{
            return  nodo.getValor();
        }
    }

    private Nodo<T> buscar(Nodo<T> nodoActual, T valor) {
        if (nodoActual == null) {
            return null; // valor no encontrado
        }

        int comparacion = valor.compareTo(nodoActual.getValor());

        if (comparacion == 0) {
            return nodoActual; // valor encontrado
        } else if (comparacion < 0) {
            return buscar(nodoActual.getIzq(), valor); // buscar en el subárbol izquierdo
        } else {
            return buscar(nodoActual.getDer(), valor); // buscar en el subárbol derecho
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
































