package model.graph;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Node {
    private Map<Node,String> neighbours = new HashMap<Node, String>();
    private String id;
    private String label;
    private int connections = 12;

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
    }

    protected void deleteNeighbour(Node node){
        neighbours.remove(node);
    }

    protected boolean isConnectable(){
        return neighbours.size() < connections;
    }

    public ArrayList<Node> getNeighbours(){
        return new ArrayList<Node>(neighbours.keySet());
    }

    //TODO maybe not needed
    public Node getAsRoot(Node preRoot){
        Node node = new Node();
        neighbours.forEach((node1, s) -> {
            if(node1!=preRoot)node.addNeighbour(node1,s);
        });
        node.setLabel(this.getLabel());
        return node;
    }


}
