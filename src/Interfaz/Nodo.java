package Interfaz;

import Estructuras_logica.Grafo;
import javafx.event.EventHandler;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import java.awt.*;
import java.util.ArrayList;

public class Nodo  {
    private String node_name;
    public Circle circulo;
    public Label label;
    public double orgSceneX, orgSceneY;
    public ArrayList<Nodo> adjacencia = new ArrayList<>();
    public    double orgTranslateX, orgTranslateY;

    public String getnode_name(){
        return node_name;
    }

    public   Nodo( String texto,Circle circulo,Label label){

        this.label = label;
        this.circulo = circulo;
        Grafo.nodos.add(this);
        this.circulo.setOnMousePressed(mousePressedEventHandler);
        this.circulo.setOnContextMenuRequested(when_context);
        this.circulo.setOnMouseDragged(mouseDraggedEventHandler);
        this.label.setOnMousePressed(mousePressedEventHandler);
        this.label.setOnContextMenuRequested(when_context);
        this.label.setOnMouseDragged(mouseDraggedEventHandler);


    }

    private EventHandler<MouseEvent> mouseDraggedEventHandler = (t) ->
    {
        double offsetX = t.getSceneX() - orgSceneX;
        double offsetY = t.getSceneY() - orgSceneY;

        Circle c = this.circulo;

        c.setCenterX(c.getCenterX() + offsetX);
        c.setCenterY(c.getCenterY() + offsetY);
        label.setLayoutY(c.getCenterY() -10 );
        label.setLayoutX(c.getCenterX() -10 );
        label.toFront();

        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();
    };

    private EventHandler<MouseEvent> mousePressedEventHandler = (t) ->
    {
        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();

        // bring the clicked circle to the front

        Circle c = this.circulo;
        c.toFront();
        label.toFront();
    };
    private EventHandler<ContextMenuEvent> when_context = (t) ->
    {
        System.out.println("LOL derecho");
    };

}
