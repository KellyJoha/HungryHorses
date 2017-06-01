/******************************************************************************
                                Smart Robot
                                
Inteligencia Artificial: Proyecto No 1
* Oscar Bedoya

Presentado por:
* Madeleine Bustamante Silva  -  201410017
* Kelly Johana Cordoba        -  201410029
* Yully Andrea Guzman         -  201410055

Clase: Pila
******************************************************************************/
package logica;

import java.util.ArrayList;
import smartrobot2.*;

public class Pila {
    private ArrayList<Estado> pila = new ArrayList<>();

    public void push(Estado a) {
        Estado nodo;
        nodo = new Estado();
        nodo = a;
        pila.add(nodo);
    }

    public Estado pop() {
        Estado nodo;
        nodo = new Estado();
        if (pila.size() > 0) {
            int indice = pila.size() -1;
            nodo = pila.get(indice);
            pila.remove(indice);
        } else if (pila.size() == 0) {
            nodo = null;
        }

        return nodo;
    }

    public void clear() {
        pila.clear();
    }

    public boolean vacio() {
        boolean salida = pila.isEmpty();
        return salida;
    }

    public ArrayList<Estado> getCola() {
        return pila;
    }

    public void setCola(ArrayList<Estado> cola) {
        this.pila = cola;
    }
    
}
