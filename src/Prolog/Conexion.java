package Prolog;


import org.jpl7.Query;
import org.jpl7.Term;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Conexion {
    private ArrayList<String>  parse(String s){
        int x=s.length();
        int i=0;
        String data="";
        ArrayList<String> result=new ArrayList<>();
        while(x!=i){
            String c= Character.toString(s.charAt(i));{
                if (c.equals(" ")){
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

    private String getResult(String s){
        int x=s.length();
        int i=0;
        String result="";
        while(x!=i){
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
    public ArrayList<String> getLugares(){
        String prog="consult('Lugares.pl')";
        Query q1=new Query(prog);
        System.out.println(prog +(q1.hasSolution()? "Coneccion completada" :"Conec+cion Fallida"));
        ArrayList<String> result= new ArrayList<>();
        String lugar="lugar(X)";
        Query q2 = new Query(lugar);
        Map<String,Term>[]data= q2.allSolutions();
        for (int i=0;i<data.length;i++){
            result.add(getResult(data[i].get("X").toString()));
        }
        System.out.println(result);
        return result;
    }
    public ArrayList<ArrayList<String>> getArcos(){
        String prog="consult('Arcos.pl')";
        Query q1=new Query(prog);
        System.out.println(prog +(q1.hasSolution()? "Coneccion completada" :"Conec+cion Fallida"));
        ArrayList<ArrayList<String>> result= new ArrayList<>();
        String arco="edge(X,Y,I)";
        Query q2 = new Query(arco);
        Map<String,Term>[]data= q2.allSolutions();
        for (int i=0;i<data.length;i++){
            ArrayList<String>temp=new ArrayList<>();
            temp.add(getResult(data[i].get("X").toString()));
            temp.add(getResult(data[i].get("Y").toString()));
            temp.add(getResult(data[i].get("I").toString()));
            result.add(temp);
        }
        System.out.println(result);
        return result;

    }
    public String pregunta(String s, int x){
        String prog="consult('GLC.pl')";
        Query q1=new Query(prog);
        String result ="No le entendi";
        if (x==1){
            String data="q1("+s+",X)";
            Query q= new Query(data);
            result= q.oneSolution().get("X").toString();
            System.out.println(result);
        }
        else if(x==2){
                String data="q2("+s+",X)";
                Query q= new Query(data);
                result= q.oneSolution().get("X").toString();
                System.out.println(result);
        }
        else if(x==3) {
            String data = "q3(" + s + ",X)";
            Query q = new Query(data);
            result = q.oneSolution().get("X").toString();
            System.out.println(result);
        }
        else if(x==5) {
            String data = "q5(" + s + ",X)";
            Query q = new Query(data);
            result = q.oneSolution().get("X").toString();
            System.out.println(result);
        }
        return getResult(result);

    }
    public void addLugar(String lugar) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Lugares.pl",true));
        String data="\n"+"lugar"+"(["+"'"+lugar+"'"+"]).";
        writer.append(data);
        writer.close();
    }
    public void addArco(String inicio,String destino,int distancia) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Arcos.pl",true));
        String data="\n"+"edge("+"'"+inicio+"'"+","+"'"+destino+"'"+","+distancia+").";
        writer.append(data);
        writer.close();
    }
    public ArrayList<String> getCamino(String destinos, String origen){
        String prog="consult('GLC.pl')";
        Query q1=new Query(prog);
        System.out.println(prog +(q1.hasSolution()? "Coneccion completada" :"Conec+cion Fallida"));
        ArrayList<String> result= new ArrayList<>();
        String path="get_whole_path("+destinos+","+"'"+origen+"'"+",X,Y)";
        Query q2 = new Query(path);
        Map<String,Term> data= q2.oneSolution();
        result=parse(getResult(data.get("X").toString()));
        System.out.println(result);
        return result;
    }
}
