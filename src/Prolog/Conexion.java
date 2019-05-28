package Prolog;


import org.jpl7.Query;
import org.jpl7.Term;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Conexion {
    // Funcion que recibe un string de lugares que luego son parseados para que elimine los espacion y retorne uns arrya de estod
    private ArrayList<String>  parse(String s){
        int x=s.length();
        int i=0;
        String data="";
        ArrayList<String> result=new ArrayList<>();
        while(x!=i){
            //elimina espacios
            String c= Character.toString(s.charAt(i));{
                if (c.equals(" ")){
                    //caso especial de san jose
                    if (data.equals("san")){
                        data+=" ";
                    }
                    else{
                        result.add(data);
                        data="";
                    }

                }
                else{
                    data+=c;
                }
            }
            i++;
        }
        return result;

    }
//La consulta retorna el resultado con varios caracteres innecesarios, asi que los elimina, recibe el resultado de la
//consulta y retorna el resultado limpio
    private String getResult(String s){
        int x=s.length();
        int i=0;
        String result="";
        while(x!=i){
            //elimina todos los carcteres innecesarios
            String c= Character.toString(s.charAt(i));{
                if (c.equals("(") || c.equals(")")|| c.equals("|")|| c.equals("'")|| c.equals(",")|| c.equals("[") || c.equals("]")){
                    result+="";
                }
                else{
                    result+=c;
                }
            }
            i++;
        }
        return result;

    }
    //consulta los lugares que estan en los hechos, retorna un array de los,lugares
    public ArrayList<String> getLugares(){
        String prog="consult('Lugares.pl')";//consulta prolog
        Query q1=new Query(prog);
        System.out.println(prog +(q1.hasSolution()? "Coneccion completada" :"Conec+cion Fallida"));
        ArrayList<String> result= new ArrayList<>();
        String lugar="lugar(X)";
        Query q2 = new Query(lugar);
        Map<String,Term>[]data= q2.allSolutions();//obtiene todas las soluciones
        for (int i=0;i<data.length;i++){
            result.add(getResult(data[i].get("X").toString()));
        }
        System.out.println(result);
        return result;
    }
    //consulta todos los arcos existentes, retonrna una matriz con los arrays de los arcos
    public ArrayList<ArrayList<String>> getArcos(){
        String prog="consult('Arcos.pl')";//consulta prolog
        Query q1=new Query(prog);
        System.out.println(prog +(q1.hasSolution()? "Coneccion completada" :"Conec+cion Fallida"));
        ArrayList<ArrayList<String>> result= new ArrayList<>();
        String arco="edge(X,Y,I)";
        Query q2 = new Query(arco);
        Map<String,Term>[]data= q2.allSolutions();
        for (int i=0;i<data.length;i++){
            ArrayList<String>temp=new ArrayList<>();//crea un array con los datos de cada arco
            temp.add(getResult(data[i].get("X").toString()));
            temp.add(getResult(data[i].get("Y").toString()));
            temp.add(getResult(data[i].get("I").toString()));
            result.add(temp);
        }
        System.out.println(result);
        return result;

    }
    //Agrega un lugar al archivo de prolog, recibe el nombre del lugar
    public void addLugar(String lugar) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Lugares.pl",true));
        String data="\n"+"lugar"+"(["+"'"+lugar+"'"+"]).";
        writer.append(data);
        writer.close();
    }
    //agrega un arco, recibe el inicio, el destino y la distancia respectiva
    public void addArco(String inicio,String destino,int distancia) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Arcos.pl",true));
        String data="\n"+"edge("+"'"+inicio+"'"+","+"'"+destino+"'"+","+distancia+").";
        writer.append(data);
        writer.close();
    }
    //retorna un array del camino mas corto, recibe un string del array de los destinos y su punto de origen
    public ArrayList<String> getCamino(String destinos, String origen){
        try {

            String prog = "consult('GLC.pl')";//consulta prolog
            Query q1 = new Query(prog);
            System.out.println(prog + (q1.hasSolution() ? "Coneccion completada" : "Conec+cion Fallida"));
            ArrayList<String> result = new ArrayList<>();
            String path = "get_whole_path(" + destinos + "," + "'" + origen + "'" + ",X,Y)";
            Query q2 = new Query(path);
            Map<String, Term> data = q2.oneSolution();//recibe un solucion
            result = parse(getResult(data.get("X").toString()));
            System.out.println(result);
            return result;
        }
        catch (Exception e){
            return null;
        }
    }
}
