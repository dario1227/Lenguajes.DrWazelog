package Interfaz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import Prolog.Conexion;
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
        primaryStage.show();
    }



    public static void main(String[] args) {
        Conexion conexion= new Conexion();
        conexion.test();
        String d="['en','cartago']";
        conexion.pregunta(d,1);
        launch(args);
    }
}
