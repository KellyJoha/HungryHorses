/******************************************************************************
                                Smart Robot
                                
Inteligencia Artificial: Proyecto No 1
* Oscar Bedoya

Presentado por:
* Madeleine Bustamante Silva  -  201410017
* Kelly Johana Cordoba        -  201410029
* Yully Andrea Guzman         -  201410055

Clase: ColaPrioridad
******************************************************************************/
package logica;


import java.util.ArrayList;
import smartrobot2.Estado;


public class ColaPrioridad {

    private ArrayList<Estado> cola = new ArrayList<>();

    public void push(Estado a) {
        Estado nodo;
        nodo = new Estado();
        nodo = a;
        cola.add(nodo);
        cola = InsertionSort(cola);
       // Imprimir(cola);
    }

    public Estado pop() {
        Estado nodo;
        nodo = new Estado();
        if (cola.size() > 0) {
            nodo = cola.get(0);
            cola.remove(0);
        } else if (cola.isEmpty()) {
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

    //Algoritmo de ordenamiento
    public ArrayList<Estado> InsertionSort(ArrayList<Estado> entrada) {
        ArrayList<Estado> temporal = entrada;
        Estado k = new Estado();
        temporal.add(0, k);

        int i = 0;
        for (int j = 0; j < temporal.size(); j++) {
            k = temporal.get(j);
            i = j - 1;

            while (i > 0 && (temporal.get(i).comparo(k) > 0)) {
                temporal.set(i + 1, temporal.get(i));
                i -= 1;
            }
            temporal.set(i + 1, k);
        }
        temporal.remove(0);
        return temporal;
    }

    void Imprimir(ArrayList<Estado> n) {
         System.out.println("Sort");
        for (int i = 0; i < n.size(); i++) {
            System.out.println(" Orden :" + i + " x = " + n.get(i).x + " y = " + n.get(i).y + " padre " + n.get(i).getUltimoMovimiento() + " Costo " + n.get(i).getCosto());
        }
        System.out.println("FIN Sort");

    }

}
