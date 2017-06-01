/******************************************************************************
                                Smart Robot
                                
Inteligencia Artificial: Proyecto No 1
* Oscar Bedoya

Presentado por:
* Madeleine Bustamante Silva  -  201410017
* Kelly Johana Cordoba        -  201410029
* Yully Andrea Guzman         -  201410055

Clase: Avara
* Para el calculo de la Heuristica se utilizo la distancia manhattan de la 
* posicion del robot al item1 y item2
* Metodo HeuristicaInicial: calcula la primera heuristica es decir entre el robot
* y los items, para luego verificar cual es el mas cercano 
* Heuristica: Calcula la heuristica en general
******************************************************************************/
package busquedas;
import smartrobot2.*;
import logica.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class algoritmoInfAvara implements IdObjetos{

    Estado [][] matrizDEstadosRobot;
    private int items;
    private int trajeRobot;
    private final int objetosEncontrar;
    private final ColaPrioridad cola;
    private final ArrayList<Estado> listaSolucion;
    private Estado estadoInicial;
    int costoGeneral;
    private final ArrayList<Estado> estadoFinal;
    private ArrayList<Estado> Items;
    long tiempoInicio, tiempoFInal;

    public algoritmoInfAvara(Estado[][] matrizEstado, Estado Inicial, ArrayList<Estado> aLIstaEStados) {
        tiempoInicio = System.currentTimeMillis();
        this.Items = new ArrayList<>();
        this.Items = (ArrayList<Estado>) aLIstaEStados.clone();
        this.objetosEncontrar = Items.size();
        this.estadoFinal = new ArrayList<>();
        this.matrizDEstadosRobot = matrizEstado.clone();
        this.listaSolucion = new ArrayList<>();
        this.cola = new ColaPrioridad();
        this.estadoInicial = new Estado();
        this.estadoInicial = Inicial;
        Inicial.raiz = true;
        Inicial.setUltimoMovimiento("raiz");
        Inicial.setPadre(Inicial);
        this.cola.push(Inicial);
        ordenarObjetivos();
    }

    public ArrayList<Estado> BusquedaAvara() {
        while (items != objetosEncontrar) {
            if (cola.vacio()) {
                System.out.println("Error cola vacia");
            }
            Estado n = new Estado();
            n = cola.pop();
            if (n.getIdObjetos()!= ID_MURO) {
                listaSolucion.add(n); //Evita ciclos 
                expandir(n);

            }
        }
        return estadoFinal;
    }

    public void ordenarObjetivos() {
        Estado primero = new Estado();
        Estado segundo = new Estado();
        primero = Items.get(0);
        primero.setCosto(HeuristicaInicial(primero));
        segundo = Items.get(1);
        segundo.setCosto(HeuristicaInicial(segundo));
        if(segundo.getCosto()<primero.getCosto())
        {
            Items.clear();
            Items.add(segundo);
            Items.add(primero);          
        }
    }
    
    //Comp. la izquierda
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
                salida = matrizDEstadosRobot[(entrada.x - 1)][entrada.y];
                salida.setUltimoMovimiento("izquierda");
                salida.setPadre(entrada);
                int costoNodo = Costo(salida, entrada);
                salida.setCosto(costoNodo);
                if (!listaSolucion.contains(salida)) {
                    if (salida.getIdObjetos()!= ID_MURO) {
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
        if (entrada.x != matrizDEstadosRobot.length-1) {
            // Evita expansión del padre dicho nodo
            if (!entrada.getUltimoMovimiento().equalsIgnoreCase("izquierda")) {
                salida = matrizDEstadosRobot[(entrada.x + 1)][entrada.y];
                salida.setPadre(entrada);
                salida.setUltimoMovimiento("derecha");
                int costoNodo = Costo(salida, entrada);
                salida.setCosto(costoNodo);
                if (!listaSolucion.contains(salida)) {
                    if (salida.getIdObjetos()!= ID_MURO) {
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
                salida = matrizDEstadosRobot[(entrada.x)][(entrada.y - 1)];
                salida.setPadre(entrada);
                salida.setUltimoMovimiento("arriba");
                int costoNodo = Costo(salida, entrada);
                salida.setCosto(costoNodo);
                if (!listaSolucion.contains(salida)) {
                    if (salida.getIdObjetos()!= ID_MURO) {
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
        if (entrada.y != matrizDEstadosRobot.length-1) {
            // Evita expansión del padre dicho nodo
            if (!entrada.getUltimoMovimiento().equalsIgnoreCase("arriba")) {
                salida = matrizDEstadosRobot[(entrada.x)][(entrada.y + 1)];
                salida.setPadre(entrada);
                salida.setUltimoMovimiento("abajo");
                int costoNodo = Costo(salida, entrada);
                salida.setCosto(costoNodo);
                if (!listaSolucion.contains(salida)) {
                    if (salida.getIdObjetos()!= ID_MURO) {
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

        if (intro.getIdObjetos()== ID_ITEM) {
            items += 1;       
            ArrayList<Estado> n;
            n = camino(intro);
            Collections.reverse(n);
            estadoFinal.addAll(n);
            /*Elimina el primer Item de busqueda para la Heuristica*/
            Items.remove(0);
            Imprimir(camino(intro));
            cola.clear();
            cola.push(intro);
            listaSolucion.clear();
            Inicial.raiz = true;
            Inicial.setUltimoMovimiento("raiz");
            Inicial.setPadre(Inicial);
            this.cola.push(Inicial);
            matrizDEstadosRobot[intro.x][intro.y].setIdObjetos(0);
            salida = true;

        }
        if (intro.getIdObjetos()== ID_TRAJE) {
            trajeRobot += 1;
            
            matrizDEstadosRobot[intro.x][intro.y].setIdObjetos(0);
            ArrayList<Estado> n;
            n = camino(intro);
            Collections.reverse(n);
            estadoFinal.addAll(n);
            Imprimir(camino(intro));
            salida = true;
            cola.clear();
            cola.push(intro);
            listaSolucion.clear();
            Inicial.raiz = true;
            Inicial.setUltimoMovimiento("raiz");
            Inicial.setPadre(Inicial);
            this.cola.push(Inicial);
        }

        return salida;
    }

    void Imprimir(ArrayList<Estado> n) {
        for (int i = 0; i < n.size(); i++) {
            System.out.println(" Paso:" + i + " x = " + n.get(i).x +
                    " y = " + n.get(i).y + " padre " + 
                    n.get(i).getUltimoMovimiento() + " Costo " + n.get(i).getCosto());
        }
        if (items == objetosEncontrar) {
            tiempoFInal = System.currentTimeMillis();
            tiempoFInal = (tiempoFInal - tiempoInicio);
            JOptionPane.showMessageDialog(null, "Numero de Nodos expandidos: " + 
                    listaSolucion.size() + "\n" + "Profundidad del arbol: " + n.size() + 
                    "\n" + "Costo: " + costoGeneral + "\n" + "Tiempo: " + tiempoFInal +
                    " Milisegundos", "REPORTE",  
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/imagenes/reporte.png"));
        }
    }

    public ArrayList<Estado> camino(Estado input) {
        ArrayList<Estado> salida = new ArrayList<>();
        costoGeneral = input.getCosto();
        Estado nuevo = new Estado();
        nuevo = input;
        salida.add(nuevo);
        int i = 0;
        
        while (true) {
            nuevo = nuevo.getPadre();
            
            salida.add(nuevo);
            if (nuevo.raiz) {
                break;
            }
            i++;
        }
        return salida;
    }

    //Retorna el costo
    private int Costo(Estado hijo, Estado padre) {
        int salida = VALOR_POR_DEFECTO_CASILLAS;
        salida = Heuristica(hijo);
        return salida;
    }

    /*Calculo de la Heuristica en general*/
    private int Heuristica(Estado hijo) {        
        int salida = 0;
        if (!Items.isEmpty()) {
            int variablePrimera = hijo.getX() - Items.get(0).getX();
            variablePrimera = Math.abs(variablePrimera);
            int variableSegunda = hijo.getY() - Items.get(0).getY();
            variableSegunda = Math.abs(variableSegunda);
            salida = variablePrimera + variableSegunda;
            System.out.println("Heuriatica: " + salida);
        }
        return salida;

    }
    
    /*Calculo de la Heuristica por primera vez*/
    private int HeuristicaInicial(Estado item) {      
        int salida = 0;
        if (!Items.isEmpty()) {
            int variablePrimera = item.getX() - estadoInicial.getX();
            variablePrimera = Math.abs(variablePrimera);
            int variableSegunda = item.getY() - estadoInicial.getY();
            variableSegunda = Math.abs(variableSegunda);
            salida = variablePrimera + variableSegunda;
            System.out.println("Heuriatica" + salida);
        }
        return salida;
    }
}
