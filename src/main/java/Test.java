import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.GraphUtil;
import model.StringGenerator;
import model.chemGraph.*;
import model.graph.AbstractPQNode;
import model.graph.QNode;
import model.graph.PQTree;
import model.smileParser.SmileParser;

import java.util.*;

public class Test extends Application {

    private final long MEGABYTE = 1024L*1024L;

    public long bytesToMegabyte(long bytes){
        return bytes/MEGABYTE;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {


        //simpleKonstitutionsTest();
        //simpleKonfigurationTest();
        //doubleBoundaryKonstitutionsTest();
        //printNodeTest();
        //parseSmilePQTreeTest();
        //parseSmilePQTreeDoubleTest();

        //testPerm();
        //weinsaeurePQTest();
        //simplesPQTest();
        //weinsaeurePQTest();
        //matchingBracketsTest();
        //RSTestSimple();
        //testRS();
        //testButendisäure();
        //konformTest();
        test();
        Platform.exit();

    }

    public void test(){
        SmileParser smileParser = new SmileParser();
        StringGenerator stringGenerator = new StringGenerator();
        //Molecule m = smileParser.parseSmile("(C(C(H)(Br)(O(H))(C(H)(H)(H))(H)(H)(H)))");
        Molecule m = smileParser.parseSmile("(C(C(Br)(O(H))(C(H)(H)(H)))(H)(H)(H))");
        //Molecule m = smileParser.parseSmile("C(H)(H)(H)(H)");
        m.determineChirality();
        ArrayList<String> test = new ArrayList<>();
        m.getNodes().forEach((s, node) -> {
            if(node instanceof Carbon) {
                node.getNeighbours().forEach(node1 -> {
                    //test.add(stringGenerator.stringGenKonstiStereo((StereoAtom) node, (StereoAtom) node1));
                    System.out.println(stringGenerator.stringGenKonstiStereo((StereoAtom) node, (StereoAtom) node1));
                });
            }
        });

        System.out.println(ChemAlgorithm.konfigurationIso(m));
        System.out.println(ChemAlgorithm.konsitutionIso(m));

        ArrayList<String> s = new ArrayList<>();
        s.add("Br");
        s.add("C");
        s.add("H");
        System.out.println(getCircularMaxString(s));


        //Collections.sort(test);
        //test.forEach(s -> System.out.println(s));
    }

    private String getCircularMaxString(ArrayList<String> strings){
        Set<String> candidates = new HashSet<>();

        //Geht alle Möglichkeiten durch und fügt sie den Kandidaten hinzu.
        for (int i = 0; i < strings.size() ; i++) {
            String candidat ="";
            for (int j = 0; j < strings.size(); j++) {
                candidat+="("+strings.get((j+i)%strings.size())+")";
            }
            candidates.add(candidat);
        }

        //größte Zeichenkette der Kandidaten.
        return Collections.max(candidates);
    }

    public void RSTestSimple(){
        SmileParser smileParser = new SmileParser();
        Molecule m = smileParser.parseSmile("(C(Br)(F)(H)(C(H)(H)(H)))");
        System.out.println("****************");
        m.determineChirality();
        System.out.println("****************");

        m.getChiralAtoms().forEach(node -> {
            System.out.println("test");
            ChemAlgorithm.RSDetermination((StereoAtom) node);
        });
    }

    public void konformTest(){
        SmileParser smileParser = new SmileParser();
        Molecule m = smileParser.parseSmile("(C(Br)(F)(H)(C(Br)(H)(H)))");
        Molecule m2 = smileParser.parseSmile("(C(Br)(F)(H)(C(H)(Br)(H)))");

        Set<String> konfiguration = new HashSet<>();
        Set<String> konstitution = new HashSet<>();
        Set<String> konformation = new HashSet<>();

        konfiguration.add(ChemAlgorithm.konfigurationIso(m));
        konfiguration.add(ChemAlgorithm.konfigurationIso(m2));
        konstitution.add(ChemAlgorithm.konsitutionIso(m));
        konstitution.add(ChemAlgorithm.konsitutionIso(m2));
        konformation.add(ChemAlgorithm.konformationIso(m));
        konformation.add(ChemAlgorithm.konformationIso(m2));

        System.out.println(konfiguration.size());
        System.out.println(konstitution.size());
        System.out.println(konformation.size());
    }

    public void testStringGen(){
        StringGenerator stringGenerator = new StringGenerator();
        Molecule molecule = new Molecule();
        StereoAtom a1 = new Carbon();
        StereoAtom a2 = new Hydrogen();
        StereoAtom a3 = new Hydrogen();
        StereoAtom a4 = new Hydrogen();
        StereoAtom a5 = new Hydrogen();
        molecule.addNode(a1);
        molecule.addNode(a2);
        molecule.addNode(a3);
        molecule.addNode(a4);
        molecule.addNode(a5);
        molecule.tryConnect(a1,a2,"");
        molecule.tryConnect(a1,a3,"");
        molecule.tryConnect(a1,a4,"");
        molecule.tryConnect(a1,a5,"");
        System.out.println(stringGenerator.getKonstiString(a1));
    }

    public void simpleKonstitutionsTest(){
        StringGenerator stringGenerator = new StringGenerator();
        SmileParser smileParser = new SmileParser();
        Molecule molecule1 = smileParser.parseSmile("C(Br)(C(H)(H)(H))(F)(H)");
        Molecule molecule2 = smileParser.parseSmile("C(Br)(F)(C(H)(H)(H))(H)");
        String test = ChemAlgorithm.konsitutionIso(molecule1);
        String test2 = ChemAlgorithm.konsitutionIso(molecule2);
        System.out.println(test);
        System.out.println(test2);
    }
    public void doubleBoundaryKonstitutionsTest(){
        StringGenerator stringGenerator = new StringGenerator();
        SmileParser smileParser = new SmileParser();
        Molecule molecule1 = smileParser.parseSmile("C(=C(H)(H))(F)(H)");


        Molecule molecule2 = smileParser.parseSmile("C(F)(=C(H)(H))(H)");
        String test = ChemAlgorithm.konsitutionIso(molecule1);
        System.out.println("-----------------------------");
        String test2 = ChemAlgorithm.konsitutionIso(molecule2);
        System.out.println(test);
        System.out.println(test2);
    }

    public void simpleKonfigurationTest(){
        StringGenerator stringGenerator = new StringGenerator();
        SmileParser smileParser = new SmileParser();
        Molecule molecule1 = smileParser.parseSmile("C(Br)(C(H)(H)(H))(F)(H)");
        Molecule molecule2 = smileParser.parseSmile("C(Br)(F)(C(H)(H)(H))(H)");
        String test = ChemAlgorithm.konfigurationIso(molecule1);
        String test2 = ChemAlgorithm.konfigurationIso(molecule2);
        System.out.println(test);
        System.out.println(test2);
    }



    public void printNodeTest(){
        QNode p = new QNode("a");
        QNode p2 = new QNode("a2");
        QNode p3 = new QNode("a3");
        QNode p4 = new QNode("a4");
        QNode p5 = new QNode("a5");
        QNode p6 = new QNode("a6");
        QNode p7 = new QNode("a7");
        p.addChild(p2,"");
        p.addChild(p3,"");
        p2.addChild(p4,"");
        p3.addChild(p5,"");
        p5.addChild(p6,"");
        p6.addChild(p7,"");
        GraphUtil.print(p);


    }

    public void parseSmilePQTreeTest(){
        SmileParser smileParser = new SmileParser();
        //AbstractPQNode s = smileParser.parseSmileToPQTree("C(Br)(F)(H)(C(H)(H)(H))");
        PQTree s = smileParser.parseSmileToPQTree("C(C(C(H)(H)(H))(H)(H))(C(H)(H)(H))(H)(C(C(H)(H)(H))(C(H)(H)(H))(H))");
        //ArrayList<String> s = smileGenerator.allPermutation("C(C(C(H)(H)(H))(H)(H))(C(H)(H)(H))(H)(C(C(H)(H)(H))(C(H)(H)(H))(H))");

        s.determineStereocenter();
        PQTree root = smileParser.parseSmileToPQTree("C(C(CCH)(C))(C)");
        GraphUtil.print(s.getRoot());
        //s.getRoot().getAllSmiles().forEach(s1 -> System.out.println("non reduced "+s1));
        s.determineStereocenter();
        AbstractPQNode reducedRoot = s.getRoot().reduce();
        //
        //
        GraphUtil.print(reducedRoot);
        ArrayList<String> res = reducedRoot.getAllSmiles();
        res.forEach(s1 -> System.out.println(s1));
        //HashSet<String> set = new HashSet<>();
        //res.forEach(s1 -> set.add(s1));
        //set.forEach(s1 -> System.out.println("set "+s1));
    }

    private void parseSmilePQTreeDoubleTest(){
        SmileParser smileParser = new SmileParser();
        PQTree s = smileParser.parseSmileToPQTree("(C(H)(H)(=C(H)(H)))");

        GraphUtil.print(s.getRoot());
        s.determineStereocenter();

        GraphUtil.print(s.getRoot());


        AbstractPQNode reducedRoot = s.getRoot().reduce();
        //
        //
        GraphUtil.print(reducedRoot);
        ArrayList<String> res = reducedRoot.getAllSmiles();
        res.forEach(s1 -> System.out.println(s1));
        //HashSet<String> set = new HashSet<>();
        //res.forEach(s1 -> set.add(s1));
        //set.forEach(s1 -> System.out.println("set "+s1));
    }



    public void weinsaeurePQTest(){
        SmileParser smileParser = new SmileParser();
        //String weinsaeure = "(C(=O)(O(H))(C(O(H))(H)(C(H)(O(H))(C(=O)(O(H))))))";
        String weinsaeure = "(C(=O)(O(H))(C(O(H))(H)(C(H)(O(H))(C(=O)(O(H))))))";
        //String weinsaeure = "(C(H)(O(H))(H))";
        //String weinsaeure = "(C(O(H)))";
        PQTree pqTree = smileParser.parseSmileToPQTree(weinsaeure);
        GraphUtil.print(pqTree.getRoot());
        pqTree.determineStereocenter();
        GraphUtil.print(pqTree.getRoot());
        AbstractPQNode reducedRoot = pqTree.getRoot().reduce();
        GraphUtil.print(reducedRoot);
        ArrayList<String> smiles = reducedRoot.getAllSmiles();
        smiles.forEach(s -> System.out.println(s));
        ArrayList<Molecule> molecules = new ArrayList<>();
        smiles.forEach(s -> {
            System.out.println("-------------------------");
            System.out.println(s);
            Molecule molecule = smileParser.parseSmile(s);
            molecules.add(molecule);
            molecule.determineChirality();
            molecule.getChiralAtoms().forEach(node -> ChemAlgorithm.RSDetermination((StereoAtom) node));
        });
        Set<String> konstiStrings = new HashSet<>();
        Set<String> konfiStrings = new HashSet<>();
        Set<String> konforStrings = new HashSet<>();
        molecules.forEach(molecule -> {
            System.out.println(ChemAlgorithm.konsitutionIso(molecule));
            System.out.println("*******************");
            System.out.println(konstiStrings.add(ChemAlgorithm.konsitutionIso(molecule)));
        });
        System.out.println(konstiStrings.size());

        molecules.forEach(molecule -> {
            System.out.println(ChemAlgorithm.konfigurationIso(molecule));
            System.out.println(konfiStrings.add(ChemAlgorithm.konfigurationIso(molecule)));
        });

        Set<String> test = new HashSet<>();
        System.out.println(test.add(ChemAlgorithm.konfigurationIso(molecules.get(0))));
        System.out.println(test.add(ChemAlgorithm.konfigurationIso(molecules.get(3))));
        System.out.println(konstiStrings.size());
        System.out.println(konfiStrings.size());
        molecules.forEach(molecule -> konforStrings.add(ChemAlgorithm.konformationIso(molecule)));
        System.out.println(konforStrings.size());
       // molecules.get(3).getDoubleBounds().forEach(doubleBoundWrapper -> System.out.println(doubleBoundWrapper.getStereoAtomOne().getLabel()+" "+doubleBoundWrapper.getStereoAtomTwo().getLabel()));
        //molecules.get(3).determineChiralityDoubleBound();
        //System.out.println(molecules.get(3).getChiralDoubleBounds().size());


    }

    public void testButendisäure(){
        SmileParser smileParser = new SmileParser();
        //String weinsaeure = "(C(=O)(O(H))(C(O(H))(H)(C(H)(O(H))(C(=O)(O(H))))))";
        String weinsaeure = "(C(=O)(O(H))(C(H)(=C(H)(C(=O)(O(H))))))";
        //String weinsaeure = "(C(H)(O(H))(H))";
        //String weinsaeure = "(C(O(H)))";
        PQTree pqTree = smileParser.parseSmileToPQTree(weinsaeure);
        GraphUtil.print(pqTree.getRoot());
        pqTree.determineStereocenter();
        GraphUtil.print(pqTree.getRoot());
        AbstractPQNode reducedRoot = pqTree.getRoot().reduce();
        GraphUtil.print(reducedRoot);
        ArrayList<String> strings = reducedRoot.getAllSmiles();
        strings.forEach(s -> System.out.println(s));
        Set<String> set = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        Molecule molecule1 = smileParser.parseSmile(strings.get(0));
        Molecule molecule2 = smileParser.parseSmile(strings.get(1));
        set.add(ChemAlgorithm.konsitutionIso(molecule1));
        set.add(ChemAlgorithm.konsitutionIso(molecule2));
        set2.add(ChemAlgorithm.konfigurationIso(molecule1));
        set2.add(ChemAlgorithm.konfigurationIso(molecule2));
        System.out.println(set.size());
        System.out.println(set2.size());
        /*molecule1.determineChiralityDoubleBound();
        molecule1.getChiralDoubleBounds().forEach(doubleBoundWrapper -> System.out.println("test"));
        molecule1.getChiralDoubleBounds().forEach(doubleBoundWrapper -> ChemAlgorithm.cisTrans(doubleBoundWrapper));

        molecule2.determineChiralityDoubleBound();
        molecule2.getChiralDoubleBounds().forEach(doubleBoundWrapper -> System.out.println("test"));
        molecule2.getChiralDoubleBounds().forEach(doubleBoundWrapper -> ChemAlgorithm.cisTrans(doubleBoundWrapper));
        */
    }



    public void testRS(){
        SmileParser smileParser = new SmileParser();
        PQTree pqTree = smileParser.parseSmileToPQTree("(C(H)(Br)(F)(O(H)))");
        pqTree.determineStereocenter();
        AbstractPQNode abstractPQNode = pqTree.getRoot().reduce();
        ArrayList<String> smiles = abstractPQNode.getAllSmiles();
        smiles.forEach(s -> System.out.println(s));
        ArrayList<Molecule> molecules = new ArrayList<>();

        smiles.forEach(s -> molecules.add(smileParser.parseSmile(s)));
        molecules.forEach(molecule -> {
            molecule.determineChirality();
            molecule.getChiralAtoms().forEach(node -> {
                ChemAlgorithm.RSDetermination((StereoAtom) node);
                System.out.println("----------------------");
            });

        });

    }

    public void matchingBracketsTest(){
        SmileParser smileParser = new SmileParser();
        String weinsaeure = "(C(((((()";
        String weinsaeure2 = "(C())))))))))";
        String weinsaeure3 = "(C)";
        smileParser.parseSmileToPQTree(weinsaeure);
        smileParser.parseSmileToPQTree(weinsaeure2);
        smileParser.parseSmileToPQTree(weinsaeure3);

    }



}
