/******************************************************************************
                                Smart Robot
                                
Inteligencia Artificial: Proyecto No 1
* Oscar Bedoya

Presentado por:
* Madeleine Bustamante Silva  -  201410017
* Kelly Johana Cordoba        -  201410029
* Yully Andrea Guzman         -  201410055

Clase: Amplitud
******************************************************************************/
package busquedas;
import smartrobot2.*;
import logica.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class algoritmoNoInfAmplitud implements IdObjetos{
    Estado[][] matrizEstadoRobot;
    private int itemsRecoger;
    private int escudoRobot;
    private Cola cola;
    private ArrayList<Estado> listaSolucion;
    private Estado estadoInicial;
    int costoGeneral;
    long tiempoInicio, tiempoFinal;     
    private ArrayList<Estado> estadoFinal;

    public algoritmoNoInfAmplitud(Estado[][] matrizEstados, Estado Inicial) {
        tiempoInicio = System.currentTimeMillis();
        this.estadoFinal = new ArrayList<>();
        this.matrizEstadoRobot = matrizEstados.clone();
        this.listaSolucion = new ArrayList<>();
        this.cola = new Cola();
        this.estadoInicial = new Estado();
        this.estadoInicial = Inicial;
        Inicial.raiz = true;
        Inicial.setUltimoMovimiento("raiz");
        Inicial.setPadre(Inicial);
        this.cola.push(Inicial);
    }

    public ArrayList<Estado> BusquedaAmplitud() {
        while (itemsRecoger != 2) {
            if (cola.vacio()) {
                System.out.println("Error de cola vacia");
            }
            Estado n = new Estado();
            n = cola.pop();
            if (n.getIdObjetos()!= ID_MURO) {
                listaSolucion.add(n);//Evita ciclos
                expandir(n);

            }
        }
        return estadoFinal;
    }
    
    //Compr. la izquierda
    void expandir(Estado entrada) {
        izquierda(entrada);
        derecha(entrada);
        arriba(entrada);
        bajo(entrada);
    }

    Estado izquierda(Estado entrada) {
        Estado salida = new Estado();
        salida = null;
        if (entrada.x != 0) {
            // Evita expansión del padre dicho nodo    
            if (!entrada.getUltimoMovimiento().equals("derecha")) {
                salida = matrizEstadoRobot[(entrada.x - 1)][entrada.y];
                salida.setUltimoMovimiento("izquierda");
                salida.setPadre(entrada);
                int costoNodo = Costo(salida, entrada);
                salida.setCosto(costoNodo);
                if (!listaSolucion.contains(salida)) {
                    if (salida.getIdObjetos() != ID_MURO) {
                        cola.push(salida);
                        ObtencionObjetos(salida);
                        listaSolucion.add(salida);
                    }
                }
            }
        }
        return salida;
    }

    Estado derecha(Estado entrada) {
        Estado p = new Estado();
        p = entrada;
        Estado salida = new Estado();
        salida = null;
        if (entrada.x != matrizEstadoRobot.length-1) {
            // Evita expansión del padre dicho nodo    
            if (!entrada.getUltimoMovimiento().equalsIgnoreCase("izquierda")) {
                salida = matrizEstadoRobot[(entrada.x + 1)][entrada.y];
                salida.setPadre(entrada);
                salida.setUltimoMovimiento("derecha");
                int costoNodo = Costo(salida, entrada);
                salida.setCosto(costoNodo);
                if (!listaSolucion.contains(salida)) {
                    if (salida.getIdObjetos() != ID_MURO) {
                        cola.push(salida);
                        ObtencionObjetos(salida);
                        listaSolucion.add(salida);
                    }
                }
            }
        }
        return salida;
    }

    Estado arriba(Estado entrada) {
        Estado salida = new Estado();
        salida = null;
        if (entrada.y != 0) {
            // Evita expansión del padre dicho nodo    
            if (!entrada.getUltimoMovimiento().equalsIgnoreCase("abajo")) {
                salida = matrizEstadoRobot[(entrada.x)][(entrada.y - 1)];
                salida.setPadre(entrada);
                salida.setUltimoMovimiento("arriba");
                int costoNodo = Costo(salida, entrada);
                salida.setCosto(costoNodo);
                if (!listaSolucion.contains(salida)) {
                    if (salida.getIdObjetos() != ID_MURO) {
                        cola.push(salida);
                        ObtencionObjetos(salida);
                        listaSolucion.add(salida);
                    }
                }
            }
        }
        return salida;
    }

    Estado bajo(Estado entrada) {
        Estado salida = new Estado();
        salida = null;
        if (entrada.y != matrizEstadoRobot.length-1) {
           // Evita expansión del padre dicho nodo    
            if (!entrada.getUltimoMovimiento().equalsIgnoreCase("arriba")) {
                salida = matrizEstadoRobot[(entrada.x)][(entrada.y + 1)];
                salida.setPadre(entrada);
                salida.setUltimoMovimiento("abajo");
                int costoNodo = Costo(salida, entrada);
                salida.setCosto(costoNodo);
                if (!listaSolucion.contains(salida)) {
                    if (salida.getIdObjetos() != ID_MURO) {
                        cola.push(salida);
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
        
        if (intro.getIdObjetos() == ID_ITEM) {
            itemsRecoger += 1;  
            ArrayList<Estado> n;
            n = camino(intro);
            Collections.reverse(n);
            estadoFinal.addAll(n);
            Imprimir(camino(intro));
            cola.clear();
            cola.push(intro);
            listaSolucion.clear();
            Inicial.raiz = true;
            Inicial.setUltimoMovimiento("raiz");
            Inicial.setPadre(Inicial);
            this.cola.push(Inicial);
            salida = true;
        }
        if (intro.getIdObjetos() == ID_TRAJE) {
            escudoRobot += 1;
        }
        return salida;
    }

    void Imprimir(ArrayList<Estado> n) {
        for (int i = 0; i < n.size(); i++) {
            System.out.println(" Paso : " + i + " X = " + n.get(i).x + 
                    " Y = " + n.get(i).y + " Padre : " + 
                    n.get(i).getUltimoMovimiento()  + " ID : " +
                    n.get(i).getIdentificador());
        }
        if (itemsRecoger == 2) {
            tiempoFinal = System.currentTimeMillis();
            tiempoFinal = (tiempoFinal - tiempoInicio);
            JOptionPane.showMessageDialog(null, "Numero de nodos expandidos : " +
                    listaSolucion.size() + "\n" + "Profundidad del arbol : " + 
                    n.size() + "\n" + "Costo : " + costoGeneral+ "\n" +
                    "Tiempo : "+tiempoFinal+" Milisegundos",  "REPORTE",  
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/imagenes/reporte.png"));
        }
    }

    public ArrayList<Estado> camino(Estado input) {
        costoGeneral = input.getCosto();
        ArrayList<Estado> salida = new ArrayList<>();
        Estado nuevo = new Estado();
        nuevo = input;
        salida.add(nuevo);
        while (true) {
            nuevo = nuevo.getPadre();
            salida.add(nuevo);
            if (nuevo.raiz) {
                break;
            }
        }
        return salida;
    }

    /*Esta funcion retorna el costo*/
    private int Costo(Estado hijo, Estado padre) {
        int salida = 1;
        if (escudoRobot == 0) {
            /*Campo de tipo 1*/
            if (hijo.getIdObjetos() == ID_CAMPO1) {
                salida = 3;
            }
            /*Campo de tipo 2*/
            if (hijo.getIdObjetos() == ID_CAMPO2) {
                salida = 6;
            }

        }
        salida += padre.getCosto();

        return salida;
    }

}
