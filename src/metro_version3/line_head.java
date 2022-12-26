
package metro_version3;

import java.util.ArrayList;


public class line_head { 
    public station_node head;
    public station_node tail;
    
   
    public station_node addBack(String station,String line,int id) {
        if (head == null) {
            head = new station_node(station,line,id, null, null);
            tail = head;
            return head;
        } else {
            station_node line_head = new station_node(station,line,id, null, tail);
            this.tail.next =line_head;
            this.tail = line_head;
            return line_head;
        }
    }
    

    public class station_node { 
        String data; 
        String line;
        int id;
        station_node next;
        station_node prev;
        ArrayList<station_node> trasb=new ArrayList<>();
        public station_node(String d,String line,int id,station_node next,station_node previous){
            this.data = d; 
            this.line=line;
            this.id=id;
            this.next=next;
            this.prev=previous;
            this.trasb.add(null);
        }
        public String getName(){
            return data;
        }
        public String getline(){
            return line;
        }
        public int getid(){
            return id;
        }
    }
    
}
