package model.graph;

import java.util.*;

/**
 * Knoten eines Graphen.
 */
public class Node {
    /**
     * Liste aller Nachbarknoten. Knoten und die Art der Bindung zu diesem.
     */
    protected LinkedHashMap<Node,String> neighbours = new LinkedHashMap<>();
    /**
     * ID des Knotens.
     */
    private String id;
    /**
     * Markierung des Knotens.
     */
    private String label;
    /**
     * Anzahl verfügbarer Verbindungen zu diesem Knoten.
     */
    private int connections = 4;
    /**
     * Anzahl maximaler Verbindungen.
     */
    private int maxConnections = 4;


    public Node(){
        id = UUID.randomUUID().toString();
    }



    /**
     * Fügt einen neuen Nachbarn hinzu. Je nach Art der Bindung werden die möglichen Verbindungen reduziert.
     * @param node Der neue Nachbarknoten.
     * @param c Verbindungsart zum neuen Nachbarknoten.
     */
    public void addNeighbour(Node node, String c){
        if(c.equals("")) {
            neighbours.put(node,"-");
        } else {
            neighbours.put(node,c);
        }

        if(c.equals("")) {
            connections--;
        } else if(c.equals("-")) {
            connections--;
        } else if (c.equals("=")) {
            connections-=2;
        }
    }

    /**
     * Prüft, ob dieser Knoten noch mit einer bestimmten Art von Bindung verbunden werden kann.
     * @param boundingType Art der Bindung.
     * @return
     */
    public boolean isConnectable(String boundingType){
        if(boundingType.equals("")) {
            return connections > 0;
        } else if(boundingType.equals("-")) {
            return connections > 0;
        } else if(boundingType.equals("=")) {
            return connections - 2 >= 0;
        } else {
            return false;
        }
    }

    public ArrayList<Node> getNeighbours(){
        return new ArrayList<Node>(neighbours.keySet());
    }

    public LinkedHashMap<Node, String> getNeighbourMap(){
        return neighbours;
    }

    protected void deleteNeighbour(Node node){
        neighbours.remove(node);
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
