package Interfaz;

import Estructuras_logica.Grafo;
import Prolog.Conexion;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
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
        this.node_name = texto;
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
        label.setLayoutX(c.getCenterX() -30 );
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
        ContextMenu menu = menucontexto_config();
        menu.show(this.circulo, t.getScreenX(), t.getScreenY());

    };
    public ContextMenu menucontexto_config(){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menu1 = new MenuItem("Seleccionar punto partida");
        menu1.setOnAction(evento_1);
        MenuItem menu2 = new MenuItem("Seleccionar punto llegada");
        menu2.setOnAction(evento_3);
        contextMenu.getItems().addAll(menu1,menu2);
        return contextMenu;
    }
    private EventHandler<ActionEvent> evento_2 = (t)->
    {

    };
    private EventHandler<ActionEvent> evento_3 = (t)->
    {
        Conexion conectado = new Conexion();
        paint_Nodes.destino = this;
        if(paint_Nodes.origen !=null  && paint_Nodes.destino!=null){
            ArrayList<String> camino = conectado.getCamino("["+paint_Nodes.destino.getnode_name()+"]",paint_Nodes.origen.getnode_name());
            if(camino==null){
                return;
            }
            if(paint_Nodes.origen.equals(paint_Nodes.destino)){
                return;
            }
            paint_Nodes.pintar_camino(camino);
        }

    };
    private EventHandler<ActionEvent> evento_1 = (t)->
    {
        Conexion conectaod = new Conexion();
        paint_Nodes.origen = this;
        if(paint_Nodes.origen !=null  && paint_Nodes.destino!=null){

            ArrayList<String> camino = conectaod.getCamino("["+paint_Nodes.destino.getnode_name()+"]",paint_Nodes.origen.getnode_name());
            if(camino==null ){
                return;
            }
            if(paint_Nodes.origen.equals(paint_Nodes.destino)){
                return;
            }
            paint_Nodes.pintar_camino(camino);
        }

    };

}
