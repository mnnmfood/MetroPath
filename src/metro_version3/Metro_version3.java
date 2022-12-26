//< >
package metro_version3;

import java.util.Stack;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Scanner;


public class Metro_version3 {
    static Scanner sc=new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("        CALCULADOR DE TRAYECTO"+ "\n"
        +"ATENCION: si estas utilizando codificacion distinta de utf-8 el programa la normalizara a ASCII, asi que no es necesario escribir caracteres especiales "+"\n\n");
        System.out.println("Introduce la estacion de partida:");
        String station_f=normalizador(sc.nextLine());
        System.out.println("Introduce la estacion de destino:");
        String station_i=normalizador(sc.nextLine());
        System.out.println("");

        
        try{
            buscador busc=new buscador();
            Stack<String> path=busc.buscador_1(station_i, station_f);       
            Stack<String> lines_stack=busc.getlines();
            if(path!=null){
                logger.mandarMsg("Se ha encontrado ruta con exito. Salida:"+station_f+"   Llegada:"+ station_i + "   Tiempo de busqueda:"+busc.time_running/(Math.pow(10,6))+"  milisegundos" , "Informacion");
                System.out.println(path_withFormat(path,lines_stack));       
                System.out.println("\n\n"+"El tiempo de viaje es aproximadamente: "+ (int)((float)(busc.Time_path)/10+(float)5) + " minutos"); 
                System.out.println("El tiempo de busqueda es: " + busc.time_running/(Math.pow(10,6))+"  milisegundos");  
            }
        }catch(Exception e){
            System.out.println("");
        }    
    }

    public static String normalizador(String input){
        input=Normalizer.normalize(input,Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        input=input.toUpperCase();
        return input;
    }

    public static String path_withFormat(Stack<String> path,Stack<String> lines_stack){
        ArrayList<String> path_list = new ArrayList<String>(path);
        ArrayList<String> lines_list = new ArrayList<String>(lines_stack);
        int size=path.size();
        String output="";
        String line;

        if(path_list.get(0).equals(path_list.get(1))){
            path_list.remove(0);
            lines_list.remove(0);
            size=path_list.size();
        }if(path_list.get(size-1).equals(path_list.get(size-2))){
            path_list.remove(size-1);
            lines_list.remove(size-1);
        }
        size=path_list.size(); 
        line=lines_list.get(0); 
        output+="LINEA:"+line+"    "+path_list.get(0);  
        for(int i=1;i<size;i++){
            if(lines_list.get(i).equals(line)){
            output+="---"+path_list.get(i);
            }else{
                line=lines_list.get(i);
                output+="\n\n"+"TRASBORDO LINEAS: "+ lines_list.get(i)+"--"+lines_list.get(i-1)+"\n\n"+"LINEA:"+line+"    "+path_list.get(i); 
            }
        }
        return output;        
    }
}
