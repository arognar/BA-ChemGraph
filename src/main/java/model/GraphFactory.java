package model;

import model.chemGraph.Carbon;
import model.chemGraph.Oxygen;
import model.graph.Graph;
import model.graph.Node;

public class GraphFactory {

    public Graph testGraph1(){
        Graph testGraph = new Graph();
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();
        Node n4 = new Node();
        testGraph.addNode(n1);
        testGraph.addNode(n2);
        testGraph.addNode(n3);
        testGraph.tryConnect(n1,n2,"");
        testGraph.tryConnect(n2,n3,"");
        return testGraph;
    }

    public Graph testMolecule(){
        Graph m = new Graph();
        Node n1 = new Carbon();
        Node n2 = new Carbon();
        Node n3 = new Carbon();
        Node n4 = new Carbon();
        Node n5 = new Carbon();
        Node n6 = new Carbon();

        Node n7 = new Oxygen();

        m.tryConnect(n1,n2,"");
        m.tryConnect(n2,n3,"");
        m.tryConnect(n2,n4,"");
        m.tryConnect(n3,n5,"");
        m.tryConnect(n4,n6,"");
        m.tryConnect(n4,n7,"");

        return m;

    }
}
