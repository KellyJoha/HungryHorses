/******************************************************************************
                                Smart Robot
                                
Inteligencia Artificial: Proyecto No 1
* Oscar Bedoya

Presentado por:
* Madeleine Bustamante Silva  -  201410017
* Kelly Johana Cordoba        -  201410029
* Yully Andrea Guzman         -  201410055

Clase: Profundidad
******************************************************************************/
package busquedas;
import smartrobot2.*;
import logica.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class algoritmoNoInfProfundidad implements IdObjetos{
    Estado[][] matrizEstadosRobot;
    private int itemsRecoger;
    private int escudoRobot;
    private final Pila pila;
    private int profundidad = 0;
    private final ArrayList<Estado> estadoFinal;
    private final ArrayList<Estado> listaSolucion;
    private Estado estadoInicialR;
    private int costoGeneral;
    long tiempoInicio, tiempoFInal;

    public algoritmoNoInfProfundidad(Estado[][] matrizEstados, Estado Inicial) {     
        tiempoInicio = System.currentTimeMillis();
        this.estadoFinal = new ArrayList<>();
        this.matrizEstadosRobot = matrizEstados.clone();
        this.listaSolucion = new ArrayList<>();
        this.pila = new Pila();
        this.estadoInicialR = new Estado();
        this.estadoInicialR = Inicial;
        Inicial.raiz = true;
        Inicial.setUltimoMovimiento("raiz");
        Inicial.setPadre(Inicial);
        this.pila.push(Inicial);
    }

    public ArrayList<Estado> BusquedaProfundidad() {
        while (itemsRecoger != 2) {
            if (pila.vacio()) {
                System.out.println("Error: pila vacia");
            }
            Estado n = new Estado();
            n = pila.pop();
            if (n.getIdObjetos()!= ID_MURO) {
                listaSolucion.add(n);//Ev. ciclos
                expandirNodoArbol(n);

            }
        }
        return estadoFinal;
    }

   //Comprueba la izquierda
    void expandirNodoArbol(Estado entrada) {
        bajo(entrada);
        arriba(entrada);
        derecha(entrada);
        izquierda(entrada);    
    }

    Estado centro(Estado entrada) {

        Estado salida = new Estado();
        salida = entrada;
        System.out.println("3ra: algoritmoProfundidad.izquierda()");
        pila.push(salida);
        listaSolucion.add(salida);
        ObtencionObjetos(salida);
        listaSolucion.add(salida);
        System.out.println("algoritmoProfundidad.izquierda()" + 
                listaSolucion.get(listaSolucion.size() - 1).getUltimoMovimiento());
        return salida;
    }

    Estado izquierda(Estado entrada) {
        Estado salida = new Estado();
        salida = null;
        if (entrada.x != 0) {
          // Evita expansión del padre dicho nodo
            if (!entrada.getUltimoMovimiento().equals("derecha")) {
                salida = matrizEstadosRobot[(entrada.x - 1)][entrada.y];
                salida.setUltimoMovimiento("izquierda");
                salida.setPadre(entrada);
                int costo = Costo(salida, entrada);
                salida.setCosto(costo);
                if (!listaSolucion.contains(salida)) {
                    if (salida.getIdObjetos()!= ID_MURO) {
                        System.out.println("3ra: algoritmoProfundidad.izquierda()");
                        pila.push(salida);
                        listaSolucion.add(salida);
                        ObtencionObjetos(salida);
                        listaSolucion.add(salida);
                        System.out.println("algoritmoProfundidad.izquierda()" +
                                listaSolucion.get(listaSolucion.size() - 1).getUltimoMovimiento());
                    }
                }
            }
        }
        return salida;
    }

    Estado derecha(Estado entradaNodo) {
        Estado p = new Estado();
        p = entradaNodo;
        Estado salidaNodo = new Estado();
        salidaNodo = null;
        if (entradaNodo.x != matrizEstadosRobot.length-1) {
          // Evita expansión del padre dicho nodo
            if (!entradaNodo.getUltimoMovimiento().equalsIgnoreCase("izquierda")) {
                salidaNodo = matrizEstadosRobot[(entradaNodo.x + 1)][entradaNodo.y];
                salidaNodo.setPadre(entradaNodo);
                salidaNodo.setUltimoMovimiento("derecha");
                int costo = Costo(salidaNodo, entradaNodo);
                salidaNodo.setCosto(costo);
                if (!listaSolucion.contains(salidaNodo)) {
                    if (salidaNodo.getIdObjetos()!= ID_MURO) {
                        pila.push(salidaNodo);
                        ObtencionObjetos(salidaNodo);
                        listaSolucion.add(salidaNodo);
                    }
                }
            }
        }
        return salidaNodo;
    }

    Estado arriba(Estado entradaNodo) {
        Estado salidaNodo = new Estado();
        salidaNodo = null;
        if (entradaNodo.y != 0) {
          // Evita expansión del padre dicho nodo
            if (!entradaNodo.getUltimoMovimiento().equalsIgnoreCase("abajo")) {
                salidaNodo = matrizEstadosRobot[(entradaNodo.x)][(entradaNodo.y - 1)];
                salidaNodo.setPadre(entradaNodo);
                salidaNodo.setUltimoMovimiento("arriba");
                int costoCamino = Costo(salidaNodo, entradaNodo);
                salidaNodo.setCosto(costoCamino);
                if (!listaSolucion.contains(salidaNodo)) {
                    if (salidaNodo.getIdObjetos()!= ID_MURO) {
                        pila.push(salidaNodo);
                        ObtencionObjetos(salidaNodo);
                        listaSolucion.add(salidaNodo);
                    }
                }
            }
        }
        return salidaNodo;
    }

    Estado bajo(Estado entrada) {
        Estado salida = new Estado();
        salida = null;
        if (entrada.y != matrizEstadosRobot.length-1) {
            //No Realiza  expansión de su padre
            if (!entrada.getUltimoMovimiento().equalsIgnoreCase("arriba")) {
                salida = matrizEstadosRobot[(entrada.x)][(entrada.y + 1)];
                salida.setPadre(entrada);
                salida.setUltimoMovimiento("abajo");
                int costo = Costo(salida, entrada);
                salida.setCosto(costo);
                if (!listaSolucion.contains(salida)) {
                    if (salida.getIdObjetos()!= ID_MURO) {
                        pila.push(salida);
                        ObtencionObjetos(salida);
                        listaSolucion.add(salida);

                    }
                }
            }
        }
        return salida;
    }
    
    //Encuentra el objeto
    boolean ObtencionObjetos(Estado intro) {
        boolean salida = false;
        Estado Inicial = new Estado();
        Inicial = intro;
        if (intro.getIdObjetos()== ID_ITEM) {
            itemsRecoger += 1;
            
            ArrayList<Estado> n;
            n = camino(intro);
            Collections.reverse(n);
            estadoFinal.addAll(n);
            Inicial.raiz = true;
            Inicial.setUltimoMovimiento("raiz");
            Inicial.setPadre(Inicial);
            this.pila.push(Inicial);
            Imprimir(camino(intro));
            salida = true;

        }
        if (intro.getIdObjetos()== ID_TRAJE) {
            escudoRobot += 1;
            salida = true;
        }

        return salida;
    }

    void Imprimir(ArrayList<Estado> n) {
        for (int i = 0; i < n.size(); i++) {
            System.out.println(" Paso:" + i + " x = " + n.get(i).x +
                    " y = " + n.get(i).y + " padre " + 
                    n.get(i).getUltimoMovimiento() + " ID " + n.get(i).getIdentificador());
        }
        if (itemsRecoger == 2) {
            tiempoFInal = System.currentTimeMillis();
            tiempoFInal = (tiempoFInal - tiempoInicio);
            JOptionPane.showMessageDialog(null, "Numero de Nodos expandidos: " +
                    listaSolucion.size() + "\n" + "Profundidad del arbol: " + 
                    profundidad + "\n" + "Costo: " + costoGeneral + "\n" + 
                    "Tiempo: " + tiempoFInal + " Milisegundos",  "REPORTE",  
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/imagenes/reporte.png"));
        }
    }

    public ArrayList<Estado> camino(Estado input) {
        costoGeneral = input.getCosto();
        ArrayList<Estado> salida = new ArrayList<>();
        Estado nuevo = new Estado();
        nuevo = input;
        salida.add(nuevo);
        int i = 0;
        
        while (true) {
            nuevo = nuevo.getPadre();
            salida.add(nuevo);
            profundidad++;
            if (nuevo.raiz) {
                salida.add(nuevo);
                break;
            }
            i++;
        }
        return salida;
    }

    //Retorna el costo
    private int Costo(Estado hijo, Estado padre) {
        int salidaCosto = 1;
        if (escudoRobot == 0) {
            //Campo Tipo 1
            if (hijo.getIdObjetos()== ID_CAMPO1) {
                salidaCosto = 3;
            }
            //Campo Tipo 2
            if (hijo.getIdObjetos() == ID_CAMPO2) {
                salidaCosto = 6;
            }

        }
        salidaCosto += padre.getCosto();

        return salidaCosto;
    }
    
}
