package Interfaz;

import Estructuras_logica.Grafo;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Random;

public class Linea_conectora  {
    public int peso;
    public Nodo origen;
    public Nodo destino;
    public Label peso_texto;
    public Line linea;
    public int getPeso(){
        return this.peso;
    }
    public void set_datos(Nodo origen, Nodo destino, int peso, Label pes_texto){
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.peso_texto = pes_texto;
    }
    public String get_origin_name(){
        return origen.getnode_name();
    }
    public  String get_destino_name(){
        return destino.getnode_name();
    }

    public Linea_conectora(Nodo inicio, Nodo destino, int peso, Label peso_texto, Line linea){
        this.origen = inicio;
        this.destino = destino;
        this.peso = peso;
        this.peso_texto = peso_texto;
        this.linea=linea;
        Grafo.vertices.add(this);
        origen.adjacencia.add(destino);
        Random rand = new Random();
        linea.setStroke(Color.rgb(rand.nextInt(100),rand.nextInt(100),rand.nextInt(100)));
        if(Grafo.sacaarcos(destino,inicio)!=null) {
            peso_texto.layoutYProperty().bind(origen.circulo.centerYProperty().add(destino.circulo.centerYProperty()).divide(2).add(-20));
            peso_texto.layoutXProperty().bind(origen.circulo.centerXProperty().add(destino.circulo.centerXProperty()).divide(2).add(-20));
        }
        else{    peso_texto.layoutYProperty().bind(origen.circulo.centerYProperty().add(destino.circulo.centerYProperty()).divide(2).add(20));
            peso_texto.layoutXProperty().bind(origen.circulo.centerXProperty().add(destino.circulo.centerXProperty()).divide(2).add(20));

        }
    }

}
