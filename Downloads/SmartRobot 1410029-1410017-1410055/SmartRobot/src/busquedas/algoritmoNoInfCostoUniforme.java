/**
 * ****************************************************************************
 * Smart Robot
 *
 * Inteligencia Artificial: Proyecto No 1 Oscar Bedoya
 *
 * Presentado por: 
 * Madeleine Bustamante - 201410017 
 * Kelly Johana Cordoba - 201410029
 * Yully Andrea Guzman  - 201410055 
 * 
 * Clase:Algoritmo de Costo Uniforme
 * ****************************************************************************
 */
package busquedas;
import smartrobot2.*;
import logica.ColaPrioridad;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class algoritmoNoInfCostoUniforme implements IdObjetos{
     
    Estado [][] matrizDEstadosRobot;
    private int Items;
    private int trajeRobot;
    private final ColaPrioridad cola;
    private final ArrayList<Estado> listaSolucion;
    private Estado estadoInicial;
    int costoGeneral;
    private final ArrayList<Estado> entregaFinal;
    long tiempoInicio, tiempoFinal;
    //agregado
    //ArrayList<String> operadores ;
    
    public algoritmoNoInfCostoUniforme(Estado[][] matrizEstados, Estado Inicial) {   
        tiempoInicio = System.currentTimeMillis();
        this.entregaFinal = new ArrayList<>();
        this.matrizDEstadosRobot = matrizEstados.clone();
        this.listaSolucion = new ArrayList<>();
        this.cola = new ColaPrioridad();
        this.estadoInicial = new Estado();
        this.estadoInicial = Inicial;
        Inicial.raiz = true;
        Inicial.setUltimoMovimiento("raiz");
    //    operadores.add("raiz");
        Inicial.setPadre(Inicial);
        this.cola.push(Inicial);
    }


    public ArrayList<Estado> bCostoUniforme() {
        while (Items != 2) {
            if (cola.vacio()) {
                System.out.println("Error Cola Vacia");
            }
            Estado n = new Estado();
            n = cola.pop();
            if (n.getIdObjetos() != 1) {
                listaSolucion.add(n);
                expandir(n);

            }
        }
        return entregaFinal;
    }
    //Comp. la izq
    void expandir(Estado entrada) {
        izq(entrada);
        der(entrada);
        arri(entrada);
        bajo(entrada);
    }

    Estado izq(Estado entrada) {
        Estado salida = new Estado();
        salida = null;
        if (entrada.x != 0) {
            if (!entrada.getUltimoMovimiento().equals("derecha")) {
                salida = matrizDEstadosRobot[(entrada.x - 1)][entrada.y];
                salida.setUltimoMovimiento("izquierda");
                //operadores.add("izquierda");
                salida.setPadre(entrada);
                int costoNodo = Costo(salida, entrada);
                salida.setCosto(costoNodo);
                if (!listaSolucion.contains(salida)) {
                    if (salida.getIdObjetos() != 1) {
                        cola.push(salida);
                        ObtencionObjetos(salida);
                        listaSolucion.add(salida);
                    }
                }
            }
        }
        return salida;
    }

    Estado der(Estado entrada) {
        Estado p = new Estado();
        p = entrada;
        Estado salida = new Estado();
        salida = null;
        if (entrada.x != matrizDEstadosRobot.length-1) {
            System.out.print(matrizDEstadosRobot.length-1);
          // Evita expansión del padre dicho nodo    
            if (!entrada.getUltimoMovimiento().equalsIgnoreCase("izquierda")) {
                salida = matrizDEstadosRobot[(entrada.x + 1)][entrada.y];
                salida.setPadre(entrada);
                salida.setUltimoMovimiento("derecha");
//                operadores.add("derecha");
                int costoNodo = Costo(salida, entrada);
                salida.setCosto(costoNodo);
                if (!listaSolucion.contains(salida)) {
                    if (salida.getIdObjetos() != 1) {
                        cola.push(salida);
                        ObtencionObjetos(salida);
                        listaSolucion.add(salida);
                    }
                }
            }
        }
        return salida;
    }

    //Comp. Arriba
    Estado arri(Estado entrada) {
        Estado salida = new Estado();
        salida = null;
        if (entrada.y != 0) {
            if (!entrada.getUltimoMovimiento().equalsIgnoreCase("abajo")) {
                // Evita expansión del padre dicho nodo    
                salida = matrizDEstadosRobot[(entrada.x)][(entrada.y - 1)];
                salida.setPadre(entrada);
                salida.setUltimoMovimiento("arriba");
//                operadores.add("arriba");
                int costoNodo = Costo(salida, entrada);
                salida.setCosto(costoNodo);
                if (!listaSolucion.contains(salida)) {
                    if (salida.getIdObjetos() != 1) {
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
//                operadores.add("abajo");
                int costoNodo = Costo(salida, entrada);
                salida.setCosto(costoNodo);
                if (!listaSolucion.contains(salida)) {
                    if (salida.getIdObjetos() != 1) {
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
            Items += 1;
            ArrayList<Estado> n;
            n = ruta(intro);
            Collections.reverse(n);
            entregaFinal.addAll(n);
            ImprimirMatriz(ruta(intro));
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
        if (intro.getIdObjetos() == ID_TRAJE) {
            trajeRobot += 1;
            matrizDEstadosRobot[intro.x][intro.y].setIdObjetos(0);
            ArrayList<Estado> n;
            n = ruta(intro);
            Collections.reverse(n);
            entregaFinal.addAll(n);
            ImprimirMatriz(ruta(intro));
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

    void ImprimirMatriz(ArrayList<Estado> n) {
        for (int i = 0; i < n.size(); i++) {
            System.out.println(" Paso:" + i + " X = " + n.get(i).x + " Y = " 
                    + n.get(i).y + " Padre Nodo:" + n.get(i).getUltimoMovimiento() 
                    + " Costo: " + n.get(i).getCosto());
        }
        
        if (Items == 2) {
            tiempoFinal = System.currentTimeMillis();
            tiempoFinal = (tiempoFinal - tiempoInicio);
            JOptionPane.showMessageDialog(null, "Numero de Nodos Expandidos: " 
                    + listaSolucion.size() + "\n" + "Profundidad del arbol: " 
                    + n.size() + "\n" + "Costo: " + costoGeneral+ "\n" 
                    + "Tiempo: "+tiempoFinal+" Milisegundos",  "REPORTE",  
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/imagenes/reporte.png"));           
        }
    }
    
    public ArrayList<Estado> ruta(Estado input) {
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
    }//Cierre de ruta

    //Retorna el costo
    private int Costo(Estado hijo, Estado padre) {
        int salida = VALOR_POR_DEFECTO_CASILLAS;
        if (trajeRobot == 0) {
            //Campo Tipo  1
            if (hijo.getIdObjetos() == ID_CAMPO1) {
                salida = 3 + VALOR_POR_DEFECTO_CASILLAS;
            }
            
            //Campo  Tipo 2
            if (hijo.getIdObjetos() == ID_CAMPO2) {
                salida = 6 + VALOR_POR_DEFECTO_CASILLAS;
            }
        }
        salida += padre.getCosto();
        return salida;
    }
    
    
//e
    public Estado[][] getMatrizDEstadosRobot() {
        return matrizDEstadosRobot;
    }

    public int getItems() {
        return Items;
    }

    public ColaPrioridad getCola() {
        return cola;
    }

    public ArrayList<Estado> getListaSolucion() {
        return listaSolucion;
    }

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public int getCostoGeneral() {
        return costoGeneral;
    }

    public ArrayList<Estado> getEntregaFinal() {
        return entregaFinal;
    }

    public long getTiempoInicio() {
        return tiempoInicio;
    }

    public long getTiempoFinal() {
        return tiempoFinal;
    }
    
    
    
    
}//Cierre de clase
