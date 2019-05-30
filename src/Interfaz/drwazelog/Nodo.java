package Interfaz.drwazelog;

import Estructuras_logica.drwazelog.Grafo;
import Prolog.drwazelog.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Esta clase nodo como lo dice representa un nodo del grafo
 * Circle es un circulo que pertenece al grafo
 * orgScenex y orgSceneY son variables double para manejar los eventos drag and drop
 * Label es el nombre del nodo
 * adjacencia son todos los nodos a los que esta conectado
 */
public class Nodo  {
    private String node_name;
    public Circle circulo;
    public Label label;
    public double orgSceneX, orgSceneY;
    public ArrayList<Nodo> adjacencia = new ArrayList<>();

    /**
     *
     * @return el nombre del nodo
     */
    public String getnode_name(){
        return node_name;
    }

    /**
     * Esta es el constructor del nodo
     * @param texto este es el nombre qie tiene el circulo
     * @param circulo este es un circulo de java
     * @param label este es un label conteniendo el nombre
     */
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

    /**
     * Esta es una funcion que maneja el evento de drag and drop, actualiza las posiciones necesarias
     */
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
    /**
     * Este es el evento que determina cuando es presionado un nodo, lo cual lo manda hacia en frente
     * del scene
     */
    private EventHandler<MouseEvent> mousePressedEventHandler = (t) ->
    {
        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();
        Circle c = this.circulo;
        c.toFront();
        label.toFront();
    };
    /**
     * Esta lo que hace es desplegar el menu de contexto
     */
    private EventHandler<ContextMenuEvent> when_context = (t) ->
    {
        ContextMenu menu = menucontexto_config();
        menu.show(this.circulo, t.getScreenX(), t.getScreenY());

    };

    /**
     * Esta lo que hace es crear un menucontexto
     * @return
     */
    public ContextMenu menucontexto_config(){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menu1 = new MenuItem("Seleccionar punto partida");
        menu1.setGraphic(Fabrica_elementos_interfaz.get_Image(1));
        menu1.setOnAction(evento_1);
        MenuItem menu2 = new MenuItem("Seleccionar punto llegada");
        menu2.setGraphic(Fabrica_elementos_interfaz.get_Image(2));
        menu2.setOnAction(evento_3);
        MenuItem menu3 = new MenuItem("Crear nuevo lugar");
        menu3.setOnAction(evento_2);
        menu3.setGraphic(Fabrica_elementos_interfaz.get_Image(3));
        MenuItem menu4 = new MenuItem("Añadir calle");
        menu4.setOnAction(evento4);
        menu4.setGraphic(Fabrica_elementos_interfaz.get_Image(4));
        MenuItem menu5 = new MenuItem("Calles");
        menu5.setOnAction(evento_5);
        menu5.setGraphic(Fabrica_elementos_interfaz.get_Image(5));
        contextMenu.getItems().addAll(menu1,menu2,menu3,menu4,menu5);
        return contextMenu;
    }

    /**
     * Esta lo que genera es un evento cuando se selecciona una de las opciones del menu de cobtexto;
     * genera una ventana que permite a la persona crear localizaciones
     */
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
        Stage stage = (Stage)dialog.getDialogPane().getScene().getWindow().getScene().getWindow();
        stage.getIcons().add(Fabrica_elementos_interfaz.get_Image(3).getImage());
        dialogPane.setGraphic(Fabrica_elementos_interfaz.get_Image(3));
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
                Fabrica_elementos_interfaz.alerta("Kilometros","Error,Km numero","Los kilometros deben de ser un numero entero");

                return;
            }
            if (results.peso ==null || results.peso.equals("Km camino") ||results.peso==""){
                Fabrica_elementos_interfaz.alerta("No numero","Error,Km numero","Debe de insertar un kilometraje");
                return;
            }
            if(results.nombre_nuevo.isEmpty() || results.nombre_nuevo.equals("Nombre Lugar")||results.nombre_nuevo ==null){
                Fabrica_elementos_interfaz.alerta("No nombre","Debe insertarse nombre",null);

                return;
            }
            if(Grafo.existencia(results.nombre_nuevo)){
                Fabrica_elementos_interfaz.alerta("Ya existe","El nodo ya existe, el programa no diferencia entre mayuscula/espacios/minusculas",null);


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
                Fabrica_elementos_interfaz.alerta("Archivo no encontrado","El archivo de prolog inexistente/corrupto","El archivo de prolog esta corrupto o es inexistente");

                return;
            }
        });


    };
    /**
     * Este tambien es un evento y  lo que hace es seleccionar el nodo que fue tocado
     * y lo marca como un destino
     */
    private EventHandler<ActionEvent> evento_3 = (t)->
    {
        Conexion conectado = new Conexion();
        Paint_Nodes.destino = this;
        if(Paint_Nodes.origen !=null  && Paint_Nodes.destino!=null){
            ArrayList<String> camino = conectado.getCamino("['"+ Paint_Nodes.destino.getnode_name()+"']", Paint_Nodes.origen.getnode_name());
            if(camino==null ){
                if(Paint_Nodes.origen.equals(Paint_Nodes.destino)){
                    Fabrica_elementos_interfaz.alerta("Error",null,"Error el nodo de origen no puede ser igual al de llegada");
                    Paint_Nodes.destino = null;
                    Paint_Nodes.origen = null;
                    Paint_Nodes.reset();
                    return;
                }
                Fabrica_elementos_interfaz.alerta("Error",null,"No existe un camino entre los dos lugares");
                Paint_Nodes.destino = null;
                Paint_Nodes.origen = null;
                Paint_Nodes.destino = null;
                Paint_Nodes.origen = null;
                Paint_Nodes.reset();
                return;}
            if(Paint_Nodes.origen.equals(Paint_Nodes.destino)){
                Fabrica_elementos_interfaz.alerta("Error",null,"Error el nodo de origen no puede ser igual al de llegada");
                Paint_Nodes.destino = null;
                Paint_Nodes.origen = null;

                Paint_Nodes.reset();
                return;
            }

            Paint_Nodes.pintar_camino(camino);
        }

    };
    /**
     * Este evento lo que hace es definir el nodo como un origen
     */
    private EventHandler<ActionEvent> evento_1 = (t)->
    {
        Conexion conectaod = new Conexion();
        Paint_Nodes.origen = this;
        if(Paint_Nodes.origen !=null  && Paint_Nodes.destino!=null){

            ArrayList<String> camino = conectaod.getCamino("['"+ Paint_Nodes.destino.getnode_name()+"']", Paint_Nodes.origen.getnode_name());
            if(camino==null ){
                if(Paint_Nodes.destino.equals(Paint_Nodes.origen)){
                    Fabrica_elementos_interfaz.alerta("Error",null,"Error el nodo de origen no puede ser igual al de llegada");
                    Paint_Nodes.reset();
                    Paint_Nodes.destino = null;
                    Paint_Nodes.origen = null;
                    Paint_Nodes.reset();
                    return;
                }
                Fabrica_elementos_interfaz.alerta("Error",null,"No existe un camino entre los dos lugares");
                Paint_Nodes.destino = null;
                Paint_Nodes.origen = null;
                Paint_Nodes.reset();
                return;
            }
            if(Paint_Nodes.origen.equals(Paint_Nodes.destino)){
                Fabrica_elementos_interfaz.alerta("Error",null,"Error el nodo de origen no puede ser igual al de llegada");
                Paint_Nodes.destino = null;
                Paint_Nodes.origen = null;
                Paint_Nodes.reset();
                return;
            }
            if (camino==null){
                return;
            }
            Paint_Nodes.pintar_camino(camino);
        }

    };
    /**
     * Esta lo que hace es crear una calle entre dos destinos, genera la ventana de seleccion
     */
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
        Stage stage = (Stage)dialog.getDialogPane().getScene().getWindow().getScene().getWindow();
        stage.getIcons().add(Fabrica_elementos_interfaz.get_Image(4).getImage());
        dialogPane.setGraphic(Fabrica_elementos_interfaz.get_Image(4));
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
                Fabrica_elementos_interfaz.alerta("Kilometros","Error,Km numero","Los kilometros deben de ser un numero entero");
                return;
            }

            if(Grafo.sacaarcos(Grafo.get_Nodo(results.nombre_nuevo),Grafo.get_Nodo(results.nombre_destino))!=(null)){

                Fabrica_elementos_interfaz.alerta("Error",null,"La calle ya existe");
                return;
            }
            if (results.peso ==null || results.peso=="Km camino" ||results.peso==""){
                Fabrica_elementos_interfaz.alerta("No numero","Error,Km numero","Debe de insertar un kilometraje");
                return;
            }
            Conexion conectado = new Conexion();
            try {

                String nodo = results.nombre_nuevo.toLowerCase();
                nodo = nodo.replaceAll("\\s+","");
                conectado.addArco(results.nombre_nuevo,results.nombre_destino,x);
                Fabrica_elementos_interfaz.crear_linea(Grafo.get_Nodo(nodo),Grafo.get_Nodo(results.nombre_destino),x);

            } catch (IOException e) {
                Fabrica_elementos_interfaz.alerta("Archivo no encontrado","El archivo de prolog inexistente/corrupto","El archivo de prolog esta corrupto o es inexistente");
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
        Stage stage = (Stage)dialog.getDialogPane().getScene().getWindow().getScene().getWindow();
        stage.getIcons().add(Fabrica_elementos_interfaz.get_Image(5).getImage());
        dialogPane.setGraphic(Fabrica_elementos_interfaz.get_Image(5));
        dialogPane.setContent(box);
        dialog.showAndWait();
    };
}
