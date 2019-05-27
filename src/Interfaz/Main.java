package Interfaz;

import Estructuras_logica.Grafo;
import Estructuras_logica.Poblador_grafo;
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
        Conexion conectado = new Conexion();
        Poblador_grafo.poblar_grafo();
        System.out.println(conectado.getCamino("["+"'puntarenas'"+"]","guanacaste2.0"));
        primaryStage.show();
    }


    public static void main(String[] args)throws IOException {
        Conexion conexion=new Conexion();
        conexion.getCamino("['san jose','limon']","cartago");
        launch(args);
    }
}
