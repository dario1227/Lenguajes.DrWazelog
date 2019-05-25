package Interfaz;

import Estructuras_logica.Grafo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import Prolog.Conexion;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Graph");
        Pane panel = new Pane();
        Fabrica_elementos_interfaz.setCanvas_princ(panel);
        Scene escena_princ = new Scene(panel, 800, 800);
        primaryStage.setScene(escena_princ);
        Nodo nodo = Fabrica_elementos_interfaz.create_Nodo("LOL",100, 100);
        Nodo nodo2 = Fabrica_elementos_interfaz.create_Nodo("LOL",400, 100);
        Nodo nodo3 = Fabrica_elementos_interfaz.create_Nodo("KK",500, 100);
        Linea_conectora linea = Fabrica_elementos_interfaz.crear_linea(nodo,nodo2,32);
        Linea_conectora linea2 = Fabrica_elementos_interfaz.crear_linea(nodo2,nodo3,5);
        System.out.println(Grafo.check_if_node_reachable(nodo3,nodo,new ArrayList<>()));
        //System.out.println(Grafo.saca_linea(nodo,nodo3)!=null);
        System.out.println(nodo.adjacencia.size());
        System.out.println(Grafo.nodos.size());
        System.out.println(nodo.circulo.getCenterX());
        primaryStage.show();
    }



    public static void main(String[] args)throws IOException {
        Conexion conexion= new Conexion();
        conexion.addLugar("HOLAS");
        conexion.addArco("dada","jeje",5);
        conexion.getLugares();
        conexion.getArcos();
        launch(args);
    }
}
