package Interfaz;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import java.awt.*;

public class Nodo  {
    private String node_name;
    public Circle circulo;
    public Label label;
    public double orgSceneX, orgSceneY;
    public    double orgTranslateX, orgTranslateY;

    public String getnode_name(){
        return node_name;
    }

    public   Nodo( String texto,Circle circulo,Label label){
        this.label = label;
        this.circulo = circulo;

    }

    EventHandler<MouseEvent> canvasOnMouseDraggedEventHandler = new EventHandler<MouseEvent>()
    {
        @Override
        public void handle(MouseEvent mouseEvent)
        {
            double offsetX = mouseEvent.getSceneX() - orgSceneX;
            double offsetY = mouseEvent.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            ((Circle) (mouseEvent.getSource())).setTranslateX(newTranslateX);  //transform the object
            ((Circle) (mouseEvent.getSource())).setTranslateY(newTranslateY);
        }
    };
}
