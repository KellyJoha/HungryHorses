/******************************************************************************
                                Smart Robot
                                
Inteligencia Artificial: Proyecto No 1
* Oscar Bedoya

Presentado por:
* Madeleine Bustamante Silva  -  201410017
* Kelly Johana Cordoba        -  201410029
* Yully Andrea Guzman         -  201410055

Clase: Matriz
******************************************************************************/
package smartrobot2;

import java.util.ArrayList;
import java.util.Vector;

public final class Matriz implements IdObjetos {
    private int dimension;
    private Vector<int[]> vectorFila;
    private int [][] matriz;
    private Estado [][] matrizEstados;
    private ArrayList<Estado> objetivos; 
    int contadorID = 0;
    public SitioObjeto inicio;
    public Estado init;
      
    public Matriz(Vector<int[]> filas){
        this.objetivos = new ArrayList<>();
        this.dimension = filas.get(0)[0];
        init = new Estado();
        matriz = new int[dimension][dimension];
        matrizEstados = new Estado [dimension][dimension];
        this.vectorFila=filas;
        construirMatriz();    
    }

    public Matriz() {
        
    }
    
    public Estado [][] construirMatriz(){
       for(int fila=1;fila<vectorFila.size();fila++){
           for(int columna=0;columna<dimension;columna++){
                matriz[fila-1][columna]=vectorFila.elementAt(fila)[columna]; 
                
               if (matriz[fila - 1][columna]== ID_ROBOT) {
                    inicio = new SitioObjeto(fila - 1,columna);
                    Estado E = new Estado(ID_ROBOT);
                    E.x = fila - 1;
                    E.y = columna;
                    E.raiz = true;
                    E.setIdentificador(contadorID);
                    init = E;
                }
               
                if (matriz[fila - 1][columna]== ID_ITEM) {   
                    inicio = new SitioObjeto(fila - 1,columna);
                    Estado E = new Estado(ID_ITEM);
                    E.x = fila - 1;
                    E.y = columna;
                    E.setIdentificador(contadorID);
                    objetivos.add(E);
                }
               
                Estado E = new Estado(matriz[fila - 1][columna]);
                E.x = fila - 1;
                E.y = columna;
                E.setIdentificador(contadorID);
                matrizEstados[fila - 1][columna] = E;
                contadorID++;
           }
       }
        return matrizEstados;
    }
    
    public ArrayList<Estado> getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(ArrayList<Estado> _Objetivos) {
        this.objetivos = _Objetivos;
    }
    
    public Estado [][] getMatrizEstados() {      
        return this.matrizEstados;
    }

    public void setMatrizEstados(Estado [][] _matrizEstados) {
        this.matrizEstados = _matrizEstados;
    }
  
    public void actualizarCasilla(int fila,int columna,int nuevoValor){
        matriz[fila][columna]=nuevoValor;
    }
    

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension){
        this.dimension = dimension;
    }
    
    public int[][] getMatriz(){
        return this.matriz;
    }
    
    public void setMatriz(int[][] nuevaMatriz){
        this.matriz=nuevaMatriz; 
    }
       
    public void imprimirMatriz(){
        for(int i=0;i<dimension;i++)
        {
            for(int j=0;j<dimension;j++)
               System.out.print(matriz[i][j]+"\t");
            System.out.print("\n"); 
        }
    }
}
