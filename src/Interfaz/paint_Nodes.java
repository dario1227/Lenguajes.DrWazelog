package Interfaz;

import Estructuras_logica.Grafo;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static Estructuras_logica.Grafo.get_Nodo;

public class paint_Nodes {
    public static Nodo destino;
    public static Nodo origen;
    public static ArrayList<Nodo> caminos;
    public static ArrayList<Linea_conectora> lineas_conectan;
public static void pintar_camino(ArrayList<String> camino ){

    for (int i = 0; i<camino.size();i++){
       Nodo nodografo = Grafo.get_Nodo(camino.get(i));
       nodografo.circulo.setFill(Color.RED);
       caminos.add(nodografo);
    }
    origen = caminos.get(0);
    destino= caminos.get(caminos.size()-1);
    ArrayList<Linea_conectora> lineas = Grafo.get_lineas(caminos);
    lineas_conectan = lineas;
    paint_line(lineas);
}
public static void paint_line(ArrayList<Linea_conectora> linea){
    for (int i = 0;i<linea.size();i++){
        linea.get(i).linea.setStroke(Color.RED);
    }
}
}
