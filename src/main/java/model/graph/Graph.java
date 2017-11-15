package model.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {

    private Map<String,Node> nodes = new HashMap<String, Node>();

    public Graph(){

    }

    public boolean addNode(Node node){
        if(nodes.containsKey(node.getId())) {
            return false;
        } else {
            nodes.put(node.getId(),node);
            return true;
        }
    }

    //todo fälle abfangen
    public boolean tryConnect(Node node1,Node node2){
        if(node1.isConnectable() && node2.isConnectable()) {
            node1.addNeighbour(node2,"");
            node2.addNeighbour(node1,"");
            return true;
        } else {
            return false;
        }
    }

    public Map<String,Node> getNodes(){
        return nodes;
    }



}
