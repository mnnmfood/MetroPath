
package metro_version3;

import java.util.ArrayList;
import static java.util.Arrays.fill;
import java.util.Stack;




public class buscador {
    
    int N_stations;
    int Time_path;
    ArrayList<line_head.station_node> metro=new ArrayList<>();
    Stack<String> path=new Stack<String>();
    Stack<String> lines_stack=new Stack<String>();
    long time_running;
    
    public Stack<String> buscador_1(String station_i,String station_f){
        parsecsv csv=new parsecsv();
        this.metro=csv.getMetro();  
        this.N_stations=csv.id;    
        line_head.station_node station_initial=getstation_node(station_i);
        line_head.station_node station_final=getstation_node(station_f);
        
        if(station_initial!=null && station_final!=null && this.metro!=null){   
            long startTime = System.nanoTime();                  
            Dijkstra_alg(station_initial,station_final);
            long endTime   = System.nanoTime();
            this.time_running = endTime - startTime; 
            return path;
        }else if(this.metro!=null){
            String msg="Una de las dos stationes no se ha podido encontrar:   Salida:"+station_f+"   Llegada:"+ station_i;
            System.out.println(msg);  
            logger.mandarMsg(msg, "Error");
            return null;
        }       
        return null;
    }
    public Stack<String> getlines(){
        return this.lines_stack;
    }
    
    public void Dijkstra_alg(line_head.station_node station_initial,line_head.station_node station_final){
        int[] Dist=new int[N_stations];
        line_head.station_node[] previo=new line_head.station_node[N_stations];
        line_head.station_node u=null;
        

        fill(Dist,N_stations*7);
        Dist[this.metro.indexOf(station_initial)]=0;
        int dist_alt;
        while(!this.metro.isEmpty()){
            u=getstation_node_MinValue(this.metro,Dist);
            this.metro.remove(u);
            if(u.id==station_final.id)
                break;
            if(null!=u.next && this.metro.indexOf(u.next)!=-1){
                dist_alt=Dist[u.id]+17;
                if(dist_alt<Dist[u.next.id]){
                    Dist[u.next.id]=dist_alt;
                    previo[u.next.id]=u;
                }
            }
            if(u.prev!=null && this.metro.indexOf(u.prev)!=-1){
                dist_alt=Dist[u.id]+17;
                if(dist_alt<Dist[u.prev.id]){
                    Dist[u.prev.id]=dist_alt;
                    previo[u.prev.id]=u;
                }
            }
            for(line_head.station_node trasb_i:u.trasb){
                if(trasb_i!=null && this.metro.indexOf(trasb_i)!=-1){
                    if(trasb_i.getName().equals(station_initial.getName()) || trasb_i.getName().equals(station_final.getName())){
                        dist_alt=Dist[u.id];
                    }else{
                        dist_alt=Dist[u.id]+70;
                    }
                    if(dist_alt<Dist[trasb_i.id]){
                        Dist[trasb_i.id]=dist_alt;
                        previo[trasb_i.id]=u;
                    }
                } 
            }
        }
        this.Time_path=Dist[station_final.getid()];
        while(u!=null){
            path.push(u.data);
            this.lines_stack.push(u.getline());
            u=previo[u.id];
        }  
    }
    
    public line_head.station_node getstation_node_MinValue(ArrayList<line_head.station_node> line_heads,int[] Dist){
        int minValue = Dist[line_heads.get(0).id];
        line_head.station_node station=line_heads.get(0);
        for(line_head.station_node station_node:line_heads){
            if(Dist[station_node.id] < minValue){
                minValue=Dist[station_node.id];
                station=station_node;
            }
        }
        return station;
    }

    public line_head.station_node getstation_node(String station_str){
        for(line_head.station_node station_node:this.metro){
            if(station_node.getName().equals(station_str))
                return station_node;
        }        
        return null;       
    }
}
