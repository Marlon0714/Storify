package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.NoSuchElementException;

public class Pila<T extends Serializable> implements Serializable {
    private static class Nodo<T extends Serializable> implements Serializable {
        private T valor;
        private Nodo<T> siguiente;

        public Nodo(T valor) {
            this.valor = valor;
            this.siguiente = null;
        }
        public Nodo(){}

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
    }
    private Nodo<T> top;

    public Pila() {
        top = null;
    }

    public void push(T valor) {
        Nodo<T> nuevoNodo = new Nodo<>(valor);
        nuevoNodo.setSiguiente(top);
        top = nuevoNodo;
    }

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("La pila está vacía");
        }

        T valor = top.getValor();
        top = top.getSiguiente();
        return valor;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("La pila está vacía");
        }

        return top.getValor();
    }
}
