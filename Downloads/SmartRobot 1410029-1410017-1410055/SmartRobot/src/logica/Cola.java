/******************************************************************************
                                Smart Robot
                                
Inteligencia Artificial: Proyecto No 1
* Oscar Bedoya

Presentado por:
* Madeleine Bustamante Silva  -  201410017
* Kelly Johana Cordoba        -  201410029
* Yully Andrea Guzman         -  201410055

Clase: Cola
******************************************************************************/
package logica;

import java.util.ArrayList;
import smartrobot2.*;

public class Cola {

    private ArrayList<Estado> cola = new ArrayList<>();

    public void push(Estado a) {
        Estado nodo;
        nodo = new Estado();
        nodo = a;
        cola.add(nodo);
    }

    public Estado pop() {
        Estado nodo;
        nodo = new Estado();
        if (cola.size() > 0) {
            nodo = cola.get(0);
            cola.remove(0);
        } else if (cola.size() == 0) {
            nodo = null;
        }

        return nodo;
    }

    public void clear() {
        cola.clear();
    }

    public boolean vacio() {
        boolean salida = cola.isEmpty();
        return salida;
    }

    public ArrayList<Estado> getCola() {
        return cola;
    }

    public void setCola(ArrayList<Estado> cola) {
        this.cola = cola;
    }

}
