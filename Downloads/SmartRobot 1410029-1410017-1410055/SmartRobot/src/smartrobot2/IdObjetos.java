/******************************************************************************
                                Smart Robot
                                
Inteligencia Artificial: Proyecto No 1
* Oscar Bedoya

Presentado por:
* Madeleine Bustamante Silva  -  201410017
* Kelly Johana Cordoba        -  201410029
* Yully Andrea Guzman         -  201410055

Clase: IbObjetos
* * Clase encargada del almacenamiento de las IDs basicas.
******************************************************************************/
package smartrobot2;
//Identificadores que se envian en el txt
public interface IdObjetos {
    final int ID_VACIA=0;
    final int ID_MURO=1;
    final int ID_ROBOT=2;
    final int ID_TRAJE=3;
    final int ID_CAMPO1=4;
    final int ID_CAMPO2=5;
    final int ID_ITEM=6;
    final int ID_MARCA = -1;
    final int VALOR_POR_DEFECTO_CASILLAS = 1;
    final int ID_CELDA_INVALIDA= Integer.MIN_VALUE;
}
