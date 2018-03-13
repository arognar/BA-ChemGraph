package model.graph;

import java.util.*;

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
    public boolean tryConnect(Node node1,Node node2,String boundingType){
        if(node1.isConnectable(boundingType) && node2.isConnectable(boundingType)) {
            nodes.put(node1.getId(),node1);
            nodes.put(node2.getId(),node2);
            node1.addNeighbour(node2,boundingType);
            node2.addNeighbour(node1,boundingType);
            return true;
        } else {
            return false;
        }
    }

    public Map<String,Node> getNodes(){
        return nodes;
    }

    public void printGraph(){
        System.out.println("PRINTING:");
        nodes.forEach((s, node) -> {
            System.out.println(s+":");
            node.getNeighbours().forEach(node1 -> System.out.println("    "+node1.getId()+" "+node1.getLabel()));
        });
    }



}
