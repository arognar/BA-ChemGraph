package model.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Node {
    private Map<Node,String> neighbours = new HashMap<Node, String>();
    private String id;
    private String label;
    private int connections = 4;


    public Node(){
        id = UUID.randomUUID().toString();
    }

    public String getId(){
        return id;
    }

    public String getLabel(){
        return label;
    }

    public void setLabel(String label){
        this.label = label;
    }

    protected void addNeighbour(Node node, String c){
        //System.out.println("added");
        neighbours.put(node,c);
        connections--;
    }

    protected void deleteNeighbour(Node node){
        neighbours.remove(node);
    }

    public boolean isConnectable(){
        return connections>0;
    }

    public ArrayList<Node> getNeighbours(){
        return new ArrayList<Node>(neighbours.keySet());
    }

    public int getConnections() {
        return connections;
    }

    public void setConnections(int connections) {
        this.connections = connections;
    }
}
