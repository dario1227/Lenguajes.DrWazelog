package Prolog;


import org.jpl7.Query;
import org.jpl7.Term;

public class Conexion {
    String prog="consult('GLC.pl')";
    Query q1=new Query(prog);
    public  void test(){
        System.out.println(prog +(q1.hasSolution()? "Coneccion completada" :"Coneccion Fallida"));
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
    public String pregunta(String s, int x){
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

}
