package Interfaz;

import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public class Linea_conectora  {
    public int peso;
    public Nodo origen;
    public Nodo destino;
    public Label peso_texto;
    public Line linea;
    public int getPeso(){
        return this.peso;
    }
    public void set_datos(Nodo origen,Nodo destino,int peso,Label pes_texto){
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
    public void actualizar_bounds(){
        this.linea.startXProperty().bind(origen.circulo.layoutXProperty().add(origen.circulo.getBoundsInParent().getWidth() /2.0));
        this.linea.startYProperty().bind(origen.circulo.layoutYProperty().add(origen.circulo.getBoundsInParent().getHeight() /2.0));
        this.linea.endXProperty().bind(destino.circulo.layoutXProperty().add(destino.circulo.getBoundsInParent().getWidth()/2.0));
        this.linea.endYProperty().bind(destino.circulo.layoutYProperty().add(destino.circulo.getBoundsInParent().getHeight()/2.0));
    }

}
