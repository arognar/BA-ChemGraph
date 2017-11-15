import model.GraphFactory;
import model.StringGen;
import model.chemGraph.Carbon;
import model.chemGraph.Oxygen;
import model.graph.Graph;
import model.graph.Node;

import java.util.HashSet;
import java.util.Set;

public class ChemGra {


    public static void main(String[] args){
        System.out.println("muuuuh");
        GraphFactory graphFactory = new GraphFactory();
        Graph graph = graphFactory.testMolecule();


        Graph m = new Graph();
        Node n1 = new Carbon();
        Node n2 = new Carbon();
        Node n3 = new Carbon();
        Node n4 = new Carbon();
        Node n5 = new Carbon();
        Node n6 = new Carbon();

        Node u1 = new Carbon();
        Node u2 = new Carbon();
        Node u3 = new Carbon();
        Node u4 = new Carbon();
        Node u5 = new Carbon();
        Node u6 = new Carbon();

        Node n7 = new Oxygen();
        Node u7 = new Oxygen();

        m.tryConnect(n1,n2);
        m.tryConnect(n2,n3);
        m.tryConnect(n2,n4);
        m.tryConnect(n3,n5);
        m.tryConnect(n4,n6);
        m.tryConnect(n4,n7);
        Graph r = new Graph();
        r.tryConnect(u1,u2);
        r.tryConnect(u2,u3);
        r.tryConnect(u3,u5);
        r.tryConnect(u2,u4);
        r.tryConnect(u4,u7);
        r.tryConnect(u4,u6);



        StringGen stringGen = new StringGen(n1,n2);
        String str = stringGen.getString();
        System.out.println(str);

        StringGen stringGen1 = new StringGen(u1,u2);
        String str2 = stringGen1.getString();
        System.out.println(str2);

        StringGen stringGen2 = new StringGen(n1,n2);
        String str3 = stringGen2.getString();
        System.out.println(str3);

        StringGen stringGen3 = new StringGen(n1,n2);
        String str4 = stringGen3.getString();
        System.out.println(str4);
        //System.out.println(n2.getNeighbours().size());
    }

}
