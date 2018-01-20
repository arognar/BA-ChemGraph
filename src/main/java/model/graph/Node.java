package model.graph;

import java.util.*;

public class Node {
    private LinkedHashMap<Node,String> neighbours = new LinkedHashMap<>();
    private String id;
    private String label;
    private int connections = 4;
    private int maxConnections = 4;


    public Node(){
        id = UUID.randomUUID().toString();
    }

    public String getId(){
        return id;
    }
    public void setId(String s){
        id = s ;
    }

    public String getLabel(){
        return label;
    }

    public void setLabel(String label){
        this.label = label;
    }

    protected void addNeighbour(Node node, String c){
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
    public LinkedHashMap<Node, String> getNeighbourMap(){
        return neighbours;
    }

    public int getConnections() {
        return connections;
    }

    public void setConnections(int connections) {
        this.connections = connections;
    }

    public int getMaxConnections(){
        return maxConnections;
    }
}
