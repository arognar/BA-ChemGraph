package model.graph;

import java.util.*;

/**
 * Generelle implementation eines Graphen.
 */
public class Graph {

    /**
     * Alle Knoten und ihre IDs.
     */
    private Map<String, Node> nodes = new HashMap<String, Node>();

    public Graph() {
    }

    /**
     * Fuegt einen Knoten hinzu.
     *
     * @param node Knoten der hinzugefuegt werden soll.
     * @return Ob Knoten schon in der Liste ist oder nicht.
     */
    public boolean addNode(Node node) {
        if (nodes.containsKey(node.getId())) {
            return false;
        } else {
            nodes.put(node.getId(), node);
            return true;
        }
    }

    /**
     * Versucht zwei Knoten in abhaengigkeit der Bindungsart zu Verbindungen.
     *
     * @param node1
     * @param node2
     * @param boundingType Art der Bindung. Einzel- Doppelbindung.
     * @return Ob die Knoten verbunden werden koennen.
     */
    public boolean tryConnect(Node node1, Node node2, String boundingType) {
        if (node1.isConnectable(boundingType) && node2.isConnectable(boundingType)) {
            nodes.put(node1.getId(), node1);
            nodes.put(node2.getId(), node2);
            node1.addNeighbour(node2, boundingType);
            node2.addNeighbour(node1, boundingType);
            return true;
        } else {
            return false;
        }
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }


}
