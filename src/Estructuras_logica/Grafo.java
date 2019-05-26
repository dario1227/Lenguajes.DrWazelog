package Estructuras_logica;

import Interfaz.Fabrica_elementos_interfaz;
import Interfaz.Linea_conectora;
import Interfaz.Nodo;
import javafx.scene.shape.Line;

import java.security.cert.TrustAnchor;
import java.util.ArrayList;
import java.util.List;

public class Grafo {
    public static ArrayList<Nodo> nodos = new ArrayList<>();
    public static ArrayList<Linea_conectora> vertices = new ArrayList<>();
    public Grafo(){
        this.nodos = new ArrayList<Nodo>();
        this.vertices = new ArrayList<Linea_conectora>();
    }

//    public void add_nodes(String origen,String destino,int peso,int coordx, int coordy,int coordx2, int coordy2){
//        Nodo origen_aux = get_Nodo(origen);
//        Nodo destino_aux = get_Nodo(destino);
//        if(origen_aux == null){
//            origen_aux = Fabrica_elementos_interfaz.create_Nodo(origen,coordx,coordy);
//            nodos.add(origen_aux);
//        }
//        if(destino_aux== null){
//            destino_aux = Fabrica_elementos_interfaz.create_Nodo(destino,coordx2,coordy2);
//            nodos.add(destino_aux);
//        }
//        Linea_conectora linea = Fabrica_elementos_interfaz.crear_linea(origen_aux,destino_aux,peso);
//        vertices.add(linea);
//        linea.actualizar_bounds();
//    }
    public static boolean check_if_node_reachable(Nodo inicio, Nodo ending,ArrayList<Nodo> visitados){
        if(visitados.contains(inicio)){
            return false;
        }
        visitados.add(inicio);

        if (inicio.equals(ending)) {
            return true;
        }
        for(int i =0; i<inicio.adjacencia.size();i++){
            if(check_if_node_reachable(inicio.adjacencia.get(i),ending,visitados) ){
                return true;
            }
        }
        return false;
    }


    /**
     * Recibe un nombre y retorna el nodo segun el valor
     * @param nombre es el parametro de busqueda
     * @return retorna un nodo con el nombre anteriormente mencionado
     */
    public static Nodo get_Nodo(String nombre){
        for (int i = 0  ; i != nodos.size() ;i++){
            if(nodos.get(i).getnode_name().equals(nombre)){
                return  nodos.get(i);
            }
        }
        return null;
    }
    public static Linea_conectora sacaarcos(Nodo nodo1 ,Nodo nodo2){
        for (int i = 0;i<vertices.size();i++){
            if(vertices.get(i).origen.equals(nodo1) && vertices.get(i).destino.equals(nodo2)){
                return vertices.get(i);
            }
        }
        return null;

    }

    public static ArrayList<Linea_conectora> get_lineas(ArrayList<Nodo> caminos) {
    int x = caminos.size();
    int y = 0;
    ArrayList<Linea_conectora> conectoras = new ArrayList<>();
    while (y<x-1){
        conectoras.add(sacaarcos(caminos.get(y),caminos.get(y+1)));
        y++;
    }
    return conectoras;
    }

}
