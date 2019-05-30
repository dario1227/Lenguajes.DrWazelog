package Interfaz.drwazelog;

import javafx.geometry.Insets;
import Estructuras_logica.drwazelog.Poblador_grafo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings("ALL")
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Graph");
        Pane panel = new Pane();
        primaryStage.getIcons().add(Fabrica_elementos_interfaz.get_Image(6).getImage());
        Fabrica_elementos_interfaz.setCanvas_princ(panel);
        Scene escena_princ = new Scene(panel, 800, 800);
        panel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        primaryStage.setScene(escena_princ);
        Poblador_grafo.poblar_grafo();
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        launch(args);
    }
}