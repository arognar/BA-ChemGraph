import model.GraphFactory;
import model.MoleculeGenerator;
import model.StringGen;
import model.chemGraph.Carbon;
import model.chemGraph.Molecule;
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


        Graph m = new Molecule();
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
        Graph r = new Molecule();
        r.tryConnect(u1,u2);
        r.tryConnect(u2,u3);
        r.tryConnect(u3,u5);
        r.tryConnect(u2,u4);
        r.tryConnect(u4,u7);
        r.tryConnect(u4,u6);






        //System.out.println(n2.getNeighbours().size());

        MoleculeGenerator mGen = new MoleculeGenerator();
        Molecule molecule = mGen.generate(10);
        molecule.printMolecule();
        System.out.println("Candidates");
        molecule.printCandidates();
        StringGen stringGen4 = new StringGen();
        molecule.getNodes().forEach((s, node) -> {
            System.out.println(stringGen4.getSmilesString(node));
        });
    }

}
