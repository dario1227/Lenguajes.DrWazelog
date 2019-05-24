package Interfaz;

import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;

public   class Fabrica_elementos_interfaz {
    public static Pane canvas_princ;

    public static void setCanvas_princ(Pane canvas){
        canvas_princ = canvas;
    }
    public static Nodo create_Nodo(String nombre,int coordx ,int coordy){
        Random rand = new Random();
        Circle circle = new Circle(50,Color.rgb(rand.nextInt(100),rand.nextInt(100),rand.nextInt(100)));
        circle.setStroke(Color.rgb(rand.nextInt(100),rand.nextInt(100),rand.nextInt(100)));
        circle.setStrokeWidth(2);
        Label label = new Label(nombre);
        canvas_princ.getChildren().addAll(circle,label);
        circle.relocate(coordx,coordy);
        label.setLabelFor(circle);
        label.relocate(coordx+25,coordy+25);
        Nodo nodo = new Nodo(nombre,circle, label);
        return nodo;
    }

    public static Nodo create_Nodo(String nombre){
        Random rand = new Random();
        Circle circle = new Circle(50,Color.rgb(rand.nextInt(100),rand.nextInt(100),rand.nextInt(100)));
        circle.setStroke(Color.rgb(rand.nextInt(100),rand.nextInt(100),rand.nextInt(100)));
        circle.setStrokeWidth(2);
        canvas_princ.getChildren().addAll(circle);
        circle.relocate(0,0);
        Label label = new Label(nombre);
        Nodo nodo = new Nodo(nombre,circle,label);
        return nodo;
    }
    public static Linea_conectora crear_linea( Nodo origen , Nodo destino,int peso ){
        return  null;
    }
}
