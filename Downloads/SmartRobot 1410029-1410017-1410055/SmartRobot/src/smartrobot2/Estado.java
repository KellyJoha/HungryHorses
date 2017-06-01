/******************************************************************************
                                Smart Robot
                                
Inteligencia Artificial: Proyecto No 1
* Oscar Bedoya

Presentado por:
* Madeleine Bustamante Silva  -  201410017
* Kelly Johana Cordoba        -  201410029
* Yully Andrea Guzman         -  201410055

Clase: Estado
******************************************************************************/
package smartrobot2;

public class Estado {   
    private Matriz matriz = new Matriz(); 
    private int IdObjetos;
    private String ultimoMovimiento;
    private Estado padre;
    private int identificador;
    public int x = 0;
    public int y = 0;
    public boolean raiz;
    private int costo;
    
    public Estado() {
        this.IdObjetos = 0;
        this.ultimoMovimiento = "";
        this.raiz = false;
        this.costo = 0;
    }
    
    public Estado(Matriz matriz){
        this.matriz=matriz;        
    }
    
    public Estado(int IdObjetos) {
        this.IdObjetos = IdObjetos;
    }

    public String getUltimoMovimiento() {
        return ultimoMovimiento;
    }

    public void setUltimoMovimiento(String ultimoMovimiento) {
        this.ultimoMovimiento = ultimoMovimiento;
    }


    public int getIdObjetos() {
        return IdObjetos;
    }
    
     public void setIdObjetos(int IdObjetos) {
         this.IdObjetos=IdObjetos;
    }
     
    public Matriz getMatriz(){
        return this.matriz;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Estado getPadre() {
        return padre;
    }

    public void setPadre(Estado padre) {
        this.padre = padre;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int comparo(Estado E) {
        int salida = 0;
        if (costo > E.getCosto()) {
            salida = 1;
        }
        if (costo < E.getCosto()) {
            salida = -1;
        }
        return salida;
    }

    public void imprimirEstados(){
        matriz.imprimirMatriz();
    } 
}
