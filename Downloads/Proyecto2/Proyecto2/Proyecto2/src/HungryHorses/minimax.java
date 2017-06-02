/**
* ****************************************************************************
* Hungry Horse
*
* Inteligencia Artificial: Proyecto No 2 Oscar Bedoya
*
* Presentado por: 
* Madeleine Bustamante - 201410017 
* Kelly Johana Cordoba - 201410029
* Yully Andrea Guzman  - 201410055 
* 
* Clase: minimax
*Almacena el estado actual, la profundidad y la posicion de las movidas
* ****************************************************************************
*/
package HungryHorses;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class minimax {

    private Estado actual;
    private int profundidad;
    private Point movida;

    public Estado getActual() {        
        return actual;    
    }

    public void setActual(Estado actual) {        
        this.actual = actual;    
    }

    public int getProfundidad() {        
        return profundidad;    
    }

    public void setProfundidad(int profundidad) {        
        this.profundidad = profundidad;    
    }

    public Point getMovida() {        
        return movida;    
    }

    public minimax(Estado actual) {        
        this.actual = actual;    
    }
       
    public void decisionMx(Estado actual, int limite) {
        Point decision = new Point();
        List acciones = actual.movidasValidas();
        Point accionRep;
        Double utilidad = Double.NEGATIVE_INFINITY;
        Iterator it = acciones.iterator(); //recorre la lista de acciones
        while (it.hasNext()) {
            Object accion = it.next();
            accionRep = (Point) accion;
            Estado siguiente = actual.resultado(accionRep);
            Double utilidadSiguiente = valorMin(siguiente, limite);
            if (utilidadSiguiente > utilidad) {
                decision = accionRep;
                utilidad = utilidadSiguiente;
            }
        }
        movida = decision;
    }

    public Point [] decisionMn(Estado actual, int limite) {
        Point decision = new Point();
        List acciones = actual.movidasValidas();
        Point accionRep;
        Double utilidad = Double.NEGATIVE_INFINITY;
        Iterator it = acciones.iterator();
        Point [] puntos=new Point [acciones.size()];
        int z=-1;
        
        while (it.hasNext()) {
            z=z+1;
            Object accion = it.next();
            accionRep = (Point) accion;
            puntos [z]=accionRep;
            Estado siguiente = actual.resultado(accionRep);
            Double utilidadSiguiente = valorMax(siguiente, limite);
            decision = accionRep;
            utilidad = utilidadSiguiente;
        }
        
        movida = decision;
        
        return puntos;
    }

    public Double valorMax(Estado actual, int limite) {
        Double utilidad = Double.NEGATIVE_INFINITY;
        if (actual.terminal(limite)) {
            return actual.calcularUtilidad();
        } 
        List acciones = actual.movidasValidas();
        Point accionRep;
        Iterator it = acciones.iterator();
        while (it.hasNext()) {
            Object accion = it.next();
            accionRep = (Point) accion;
            utilidad = Math.max(utilidad, valorMin(actual.resultado(accionRep), limite));
        }
        return utilidad;
    }

    public double valorMin(Estado actual, int limite) {
        Double utilidad2 = Double.POSITIVE_INFINITY;
        if (actual.terminal(limite)) {
            return actual.calcularUtilidad();
        } 
        List acciones = actual.movidasValidas();
        Point accionRep;
        Iterator it = acciones.iterator();
        while (it.hasNext()) {
            Object accion = it.next();
            accionRep = (Point) accion;
            utilidad2 = Math.min(utilidad2, valorMax(actual.resultado(accionRep), limite));
        }
        return utilidad2;
    }
    
}