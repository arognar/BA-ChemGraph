import model.StringGen;
import model.chemGraph.StereoAtom;
import model.graph.Graph;

public class ChemGra {


    public static void main(String[] args){
        /*System.out.println("muuuuh");
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
        r.tryConnect(u4,u6);*/






        //System.out.println(n2.getNeighbours().size());

        /*MoleculeGenerator mGen = new MoleculeGenerator();
        Molecule molecule = mGen.generate(10);
        molecule.printMolecule();
        System.out.println("Candidates");
        molecule.printCandidates();
        StringGen stringGen4 = new StringGen();
        molecule.getNodes().forEach((s, node) -> {
            System.out.println(stringGen4.getSmilesString(node));
        });*/

        Graph graph1 = new Graph();
        StereoAtom s1 = new StereoAtom();
        s1.setLabel("1");
        StereoAtom s2 = new StereoAtom();
        s2.setId("A");
        s2.setLabel("A");
        StereoAtom s3 = new StereoAtom();
        s3.setId("B");
        s3.setLabel("B");
        StereoAtom s4 = new StereoAtom();
        s4.setId("C");
        s4.setLabel("C");
        StereoAtom s5 = new StereoAtom();
        s5.setId("D");
        s5.setLabel("D");
        graph1.addNode(s1);
        graph1.addNode(s2);
        graph1.addNode(s3);
        graph1.addNode(s4);
        graph1.addNode(s5);
        graph1.tryConnect(s1,s2,"");
        graph1.tryConnect(s1,s3,"");
        graph1.tryConnect(s1,s4,"");
        graph1.tryConnect(s1,s5,"");
        s1.print();
        StringGen stringGen = new StringGen();
        System.out.println(stringGen.getStereoSmiles(s1));
        System.out.println(stringGen.getSmilesString(s1));
    }

}
