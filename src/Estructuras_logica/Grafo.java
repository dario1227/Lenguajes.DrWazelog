package Estructuras_logica;

import Interfaz.Fabrica_elementos_interfaz;
import Interfaz.Linea_conectora;
import Interfaz.Nodo;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
    public static ArrayList<Nodo> nodos;
    public static ArrayList<Linea_conectora> vertices;
    public Grafo(){
        this.nodos = new ArrayList<Nodo>();
        this.vertices = new ArrayList<Linea_conectora>();
    }
    public void add_nodes(String origen,String destino,int peso,int coordx, int coordy,int coordx2, int coordy2){
        Nodo origen_aux = get_Nodo(origen);
        Nodo destino_aux = get_Nodo(destino);
        if(origen_aux == null){
            origen_aux = Fabrica_elementos_interfaz.create_Nodo(origen,coordx,coordy);
            nodos.add(origen_aux);
        }
        if(destino_aux== null){
            destino_aux = Fabrica_elementos_interfaz.create_Nodo(destino,coordx2,coordy2);
            nodos.add(destino_aux);
        }
        Linea_conectora linea = Fabrica_elementos_interfaz.crear_linea(origen_aux,destino_aux,peso);
        vertices.add(linea);
        linea.actualizar_bounds();
    }
    public Nodo get_Nodo(String nombre){
        for (int i = 0  ; i != this.nodos.size() ;i++){
            if(nodos.get(i).getnode_name() == nombre){
                return  nodos.get(i);
            }
        }
        return null;
    }

}
