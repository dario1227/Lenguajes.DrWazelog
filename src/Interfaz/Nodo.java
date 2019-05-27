package Interfaz;

import Estructuras_logica.Grafo;
import Prolog.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import jdk.internal.org.objectweb.asm.tree.IntInsnNode;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

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
        MenuItem menu3 = new MenuItem("Crear nuevo lugar");
        menu3.setOnAction(evento_2);
        MenuItem menu4 = new MenuItem("Añadir calle");
        menu4.setOnAction(evento4);
        menu2.setOnAction(evento_3);
        MenuItem menu5 = new MenuItem("Calles");
        menu5.setOnAction(evento_5);
        contextMenu.getItems().addAll(menu1,menu2,menu3,menu4,menu5);
        return contextMenu;
    }

    private EventHandler<ActionEvent> evento_2 = (t)->
    {
        Dialog<Entrys_crear> dialog = new Dialog<>();
        dialog.setTitle("Crear localizacion");
        dialog.setHeaderText("Por favor introduce los datos");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField textField = new TextField("Nombre Lugar");
        TextField textField2 = new TextField("Km camino");
        dialogPane.setContent(new VBox(8, textField, textField2));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                if (button == ButtonType.OK) {
                    return new Entrys_crear(textField.getText(),this.node_name,textField2.getText());
                }
                return null;
            }
            return null;
        });

        Optional<Entrys_crear> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((Entrys_crear results) -> {
            int x;
            try{
                x = Integer.parseInt(results.peso);
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Kilometros");
                alert.setHeaderText("Error,Km numero");
                alert.setContentText("Los kilometros deben de ser un numero entero");
                alert.showAndWait();
                return;
            }
            if (results.peso ==null || results.peso=="Km camino" ||results.peso==""){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No numero");
                alert.setHeaderText("Error,Km numero");
                alert.setContentText("Debe de insertar un kilometraje");
                alert.showAndWait();
                return;
            }
            if(results.nombre_nuevo.isEmpty() || results.nombre_nuevo=="Nombre Lugar"||results.nombre_nuevo ==null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No nombre");
                alert.setHeaderText("Debe insertarse nombre");
                alert.showAndWait();
                return;
            }
            if(Grafo.existencia(results.nombre_nuevo)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ya existe");
                alert.setHeaderText("El nodo ya existe, el programa no diferencia entre mayuscula/espacios/minusculas");
                alert.showAndWait();
                return;
            }
            Conexion conectado = new Conexion();
            try {

                String nodo = results.nombre_nuevo.toLowerCase();
                nodo = nodo.replaceAll("\\s+","");
                Fabrica_elementos_interfaz.create_Nodo(nodo);
                conectado.addArco(nodo,results.nombre_destino,x);
                conectado.addLugar(nodo);
                Fabrica_elementos_interfaz.crear_linea(Grafo.get_Nodo(nodo),Grafo.get_Nodo(results.nombre_destino),x);

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Archivo no encontrado");
                alert.setHeaderText("El archivo de prolog inexistente/corrupto");
                alert.setContentText("El archivo de prolog esta corrupto o es inexistente");
                alert.showAndWait();
                return;
            }
        });


    };
    private EventHandler<ActionEvent> evento_3 = (t)->
    {
        Conexion conectado = new Conexion();
        paint_Nodes.destino = this;
        if(paint_Nodes.origen !=null  && paint_Nodes.destino!=null){
            ArrayList<String> camino = conectado.getCamino("['"+paint_Nodes.destino.getnode_name()+"']",paint_Nodes.origen.getnode_name());
            if(camino==null ){
                if(paint_Nodes.origen.equals(paint_Nodes.destino)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Error el nodo de origen no puede ser igual al de llegada");
                    alert.showAndWait();
                    paint_Nodes.destino = null;
                    paint_Nodes.origen = null;
                    paint_Nodes.reset();
                    return;
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No existe un camino entre los dos lugares");

                alert.showAndWait();
                paint_Nodes.destino = null;
                paint_Nodes.origen = null;
                paint_Nodes.destino = null;
                paint_Nodes.origen = null;
                paint_Nodes.reset();
                return;}
            if(paint_Nodes.origen.equals(paint_Nodes.destino)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error el nodo de origen no puede ser igual al de llegada");

                alert.showAndWait();
                paint_Nodes.destino = null;
                paint_Nodes.origen = null;
                paint_Nodes.reset();
            }

            paint_Nodes.pintar_camino(camino);
        }

    };
    private EventHandler<ActionEvent> evento_1 = (t)->
    {
        Conexion conectaod = new Conexion();
        paint_Nodes.origen = this;
        if(paint_Nodes.origen !=null  && paint_Nodes.destino!=null){

            ArrayList<String> camino = conectaod.getCamino("['"+paint_Nodes.destino.getnode_name()+"']",paint_Nodes.origen.getnode_name());
            if(camino==null ){
                if(paint_Nodes.destino.equals(paint_Nodes.origen)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Error el nodo de origen no puede ser igual al de llegada");

                    alert.showAndWait();
                    paint_Nodes.reset();
                    paint_Nodes.destino = null;
                    paint_Nodes.origen = null;
                    paint_Nodes.reset();
                    return;
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No existe un camino entre los dos lugares");
                alert.showAndWait();
                paint_Nodes.destino = null;
                paint_Nodes.origen = null;
                paint_Nodes.reset();
                return;
            }
            if(paint_Nodes.origen.equals(paint_Nodes.destino)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error el nodo de origen no puede ser igual al de llegada");
                alert.showAndWait();
                paint_Nodes.destino = null;
                paint_Nodes.origen = null;
                paint_Nodes.reset();
            }
            if (camino==null){
                return;
            }
            paint_Nodes.pintar_camino(camino);
        }

    };
    private EventHandler<ActionEvent> evento4 = (t)->
    {
        ArrayList<String> name_of_nodes = Grafo.get_names(this.node_name);
        ObservableList<String> options = FXCollections.observableArrayList(name_of_nodes);
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.getSelectionModel().selectFirst();
        Dialog<Entrys_crear> dialog = new Dialog<>();
        dialog.setTitle("Nueva calle");
        dialog.setHeaderText("Selecciona el destino y el kilometraje");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField textField2 = new TextField("Km camino");
        dialogPane.setContent(new VBox(8, textField2, comboBox));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new Entrys_crear(this.node_name,comboBox.getValue(),textField2.getText());
            }
            return null;
        });
        Optional<Entrys_crear> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((Entrys_crear results) -> {
            int x;
            try{
                x = Integer.parseInt(results.peso);
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Kilometros");
                alert.setHeaderText("Error,Km numero");
                alert.setContentText("Los kilometros deben de ser un numero entero");
                alert.showAndWait();
                return;
            }

            if(Grafo.sacaarcos(Grafo.get_Nodo(results.nombre_nuevo),Grafo.get_Nodo(results.nombre_destino))!=(null)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("La calle ya existe");
                alert.showAndWait();
                return;
            }
            if (results.peso ==null || results.peso=="Km camino" ||results.peso==""){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No numero");
                alert.setHeaderText("Error,Km numero");
                alert.setContentText("Debe de insertar un kilometraje");
                alert.showAndWait();
                return;
            }
            Conexion conectado = new Conexion();
            try {

                String nodo = results.nombre_nuevo.toLowerCase();
                nodo = nodo.replaceAll("\\s+","");
                conectado.addArco(results.nombre_nuevo,results.nombre_destino,x);
                Fabrica_elementos_interfaz.crear_linea(Grafo.get_Nodo(nodo),Grafo.get_Nodo(results.nombre_destino),x);

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Archivo no encontrado");
                alert.setHeaderText("El archivo de prolog inexistente/corrupto");
                alert.setContentText("El archivo de prolog esta corrupto o es inexistente");
                alert.showAndWait();
                return;
            }
        });
    };

    private EventHandler<ActionEvent> evento_5 = (t)->
    {
        VBox box =new VBox(8);
        Dialog<Entrys_crear> dialog = new Dialog<>();
        dialog.setTitle("Caminos ");
        dialog.setHeaderText("Estas son las diferentes calles");

        for (int x=0;x<Grafo.vertices.size();x++){
            String nombre_origen = Grafo.vertices.get(x).origen.getnode_name();
            String nombre_destino = Grafo.vertices.get(x).destino.getnode_name();
            String km = Integer.toString(Grafo.vertices.get(x).peso);
            Label label = new Label("Origen: "+ nombre_origen +" Destino: " + nombre_destino+ " " + km +"km");
            box.getChildren().addAll(label);
        }
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialogPane.setContent(box);
        dialog.showAndWait();
    };
}
