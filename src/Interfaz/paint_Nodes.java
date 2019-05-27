package Interfaz;

import Estructuras_logica.Grafo;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

import static Estructuras_logica.Grafo.get_Nodo;

public class paint_Nodes {
    public static Nodo destino;
    public static Nodo origen;
    public static ArrayList<Nodo> caminos = new ArrayList<>();
    public static ArrayList<Linea_conectora> lineas_conectan;
    public static void pintar_camino(ArrayList<String> camino ){
        if(lineas_conectan!=null && !lineas_conectan.contains(null)){
            reset();
        }
        caminos = new ArrayList<>();
        for (int i = 0; i<camino.size();i++){
            String objetivo = camino.get(i);
            objetivo = objetivo.replaceAll("\\s+","");
            Nodo nodografo = Grafo.get_Nodo(objetivo);
            nodografo.circulo.setFill(Color.RED);
            caminos.add(nodografo);
        }
        origen = caminos.get(0);
        destino= caminos.get(caminos.size()-1);
        ArrayList<Linea_conectora> lineas = Grafo.get_lineas(caminos);
        lineas_conectan = lineas;
        System.out.println( "El largo fue" + caminos.size());
        paint_line(lineas);
    }

    public static void reset() {
        if(lineas_conectan==null ){
            System.out.println("LOLOL");
            return;
        }
        Random rand = new Random();
        for(int i = 0;i< caminos.size();i++){
            caminos.get(i).circulo.setFill(Color.rgb(rand.nextInt(200),rand.nextInt(200),rand.nextInt(200)));
        }
        for(int i=0;i<lineas_conectan.size();i++){
            lineas_conectan.get(i).linea.setStroke(Color.rgb(rand.nextInt(200),rand.nextInt(200),rand.nextInt(200)));
        }
        lineas_conectan = null;
    }
    public static void paint_line(ArrayList<Linea_conectora> linea){
        System.out.println("El largo lineas fue" + linea.size());
        for (int i = 0;i<linea.size();i++){
            linea.get(i).linea.setStroke(Color.RED);
            linea.get(i).linea.toFront();
            linea.get(i).destino.circulo.toFront();
            linea.get(i).origen.circulo.toFront();
            linea.get(i).origen.label.toFront();linea.get(i).destino.label.toFront();
        }
    }
}
