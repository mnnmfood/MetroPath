package metro_version3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.text.Normalizer;
import java.text.Normalizer.Form;

//En este archivo voy a a transformar cada estacion del csv en un objeto llamado estacion_nodo
// que estara conectado por estacion_nodo.next, estacion_nodo.prev y estacion_nodo.trasb 
//a los otros nodos del grafo

public class parsecsv {
    int id;
    ArrayList<String[]> metro_arr = new ArrayList<>();

    public ArrayList<line_head.station_node> getMetro() {   
        ArrayList<String[]> archivo = new ArrayList<>(cargarCSV());        
        ArrayList<line_head.station_node> metro= new ArrayList<line_head.station_node>(); 
        String nombre;
//        [line,sentido,id,orden,nombre]
        for(String[] station:archivo){          
            //en el csv las estaciones aparecen duplicadas para leerlas en ambos sentidos
            //asi que me quedo solo con la primera copia
            if(Integer.parseInt(station[1])==1 &&  !station[0].contains("b") && !station[0].contains("-2") && !station[0].contains("B"))            
                metro_arr.add(station);
        } 
        Collections.sort(metro_arr,new Compare2()); //ordeno el arhivo por su orden en la linea (station[3]) 
        Collections.sort(metro_arr,new Compare1()); //ordeno el arhivo por su linea (station[0]) en orden creciente             
        String line=metro_arr.get(0)[0];
 
        line_head line_h=new line_head();
        id=0;
        
        for(String[] station:metro_arr){
            if(station[0].equals(line)){                
                nombre=Normalizer.normalize(station[4], Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                line_head.station_node station_node=line_h.addBack(nombre,station[0],id);
                metro.add(station_node);
                id++;

            }else{
                nombre=Normalizer.normalize(station[4], Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                line_h=new line_head();
                line_head.station_node station_node=line_h.addBack(nombre,station[0],id);
                metro.add(station_node);
                id++;
                line=station[0];
            }
        }
        // añado los trasbordos a cada nodo
        ArrayList<Integer> ids_trasb=new ArrayList<>();
        for(line_head.station_node station_node:metro){
            ids_trasb=getSameName(metro_arr.get(station_node.getid()));
            for(int id_local:ids_trasb)
                station_node.trasb.add(metro.get(id_local));
        }
        return metro;
    }  
    public ArrayList<Integer> getSameName(String[] station){
        ArrayList<Integer> id_trasb=new ArrayList<Integer>(); 
        for(String[] station_compare:metro_arr){
            if(station_compare[4].equals(station[4]) && !station_compare[2].equals(station[2])) //si tienen el mismo nombre y su id es diferente
            id_trasb.add(metro_arr.indexOf(station_compare));
        }
        return id_trasb;
    }  
    
    public ArrayList<String[]> cargarCSV() {
        ArrayList<String[]> archivo = new ArrayList<>();
        try {           
            File fichero = new File("M4_ParadasPorItinerario.csv");
            FileReader reader = new FileReader(fichero);
            BufferedReader br = new BufferedReader(reader);

            String line;   
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                String[] dato=new String[]{datos[4],datos[5],datos[7], datos[10], datos[13]};
                archivo.add(dato);
            }

            br.close();
            reader.close();
            return archivo;
       
        } catch (Exception e) {
            String msg="Error al cargar archivo que contiene el metro";
            System.out.println(msg);   
            logger.mandarMsg(msg, "Error");
        return null;
        }        
    }
    public class Compare1 implements Comparator<String[]>{
    @Override
        public int compare(String[] a, String[] b) {
            return  a[0].compareTo(b[0]);
        }
    
    }public class Compare2 implements Comparator<String[]>{
    @Override
        public int compare(String[] a, String[] b) {
            return Integer.compare(Integer.parseInt(a[3]),Integer.parseInt(b[3]));
        }
    
    }
}
