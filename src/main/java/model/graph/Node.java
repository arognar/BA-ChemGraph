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
        if(c.equals(""))neighbours.put(node,"-");
        else neighbours.put(node,c);

        if(c.equals("")) connections--;
        else if(c.equals("-")) connections--;
        else if (c.equals("=")) connections-=2;
        else if(c.equals("#")) connections-=3;

    }



    protected void deleteNeighbour(Node node){
        neighbours.remove(node);
    }

    public boolean isConnectable(String boundingType){
        if(boundingType.equals(""))return connections>0;
        else if(boundingType.equals("-"))return connections>0;
        else if(boundingType.equals("="))return connections-2>=0;
        else if(boundingType.equals("#")) return connections-3>=0;
        else return false;
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

    public String getBoundingType(Node node){
        return neighbours.get(node);
    }
}
