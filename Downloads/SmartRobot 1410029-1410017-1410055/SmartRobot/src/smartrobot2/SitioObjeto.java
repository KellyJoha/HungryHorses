/******************************************************************************
                                Smart Robot
                                
Inteligencia Artificial: Proyecto No 1
* Oscar Bedoya

Presentado por:
* Madeleine Bustamante Silva  -  201410017
* Kelly Johana Cordoba        -  201410029
* Yully Andrea Guzman         -  201410055

Clase: SitioObjeto
******************************************************************************/
package smartrobot2;

public class SitioObjeto {
    private int IniFila = 0;
    private int IniColumna = 0;
    
    public SitioObjeto(){}    
       
    public SitioObjeto(int fila, int columna)
    {
        this.IniFila = fila;
        this.IniColumna = columna;
    }        
    
    public int getIniFila() {
        return IniFila;
    }

    public void setIniFila(int IniFila) {
        this.IniFila = IniFila;
    }

    public int getIniColumna() {
        return IniColumna;
    }

    public void setInicioy(int IniColumna) {
        this.IniColumna = IniColumna;
    }     
}
