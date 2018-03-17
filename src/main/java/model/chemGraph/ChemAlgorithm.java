package model.chemGraph;

import model.StringGen;
import model.graph.Node;

import java.util.*;

public class ChemAlgorithm {

    public static String konsitutionIso(Molecule molecule){
        StringGen stringGen = new StringGen();
        ArrayList<String> test = new ArrayList<>();
        molecule.getNodes().forEach((s, node) -> {
            if(node instanceof Carbon){
                System.out.println("CarbonFound");
                String n = node.getLabel();
                String c = stringGen.getStereoSmiles2((StereoAtom) node);
                test.add(new StringBuilder().append(n).append("[").append((c)).append("]").toString());
            }
        });

        Collections.sort(test, String.CASE_INSENSITIVE_ORDER);
        final String[] m = {""};
        test.forEach(s -> {
            m[0] = new StringBuilder().append(s).toString();
        });
        return m[0];

    }

    public static String konfigurationIso(Molecule molecule){
        StringGen stringGen = new StringGen();
        ArrayList<String> test = new ArrayList<>();
        molecule.getNodes().forEach((s, node) -> {
            if(node instanceof Carbon){
                String n = node.getLabel();
                String c = stringGen.getStereoSmiles((StereoAtom) node);
                test.add(new StringBuilder().append(n).append("[").append((c)).append("]").toString());
            }
        });

        Collections.sort(test, String.CASE_INSENSITIVE_ORDER);
        final String[] m = {""};
        test.forEach(s -> {
            m[0] = new StringBuilder().append(s).toString();
        });
        return m[0];

    }

    public static boolean isChiral(Node node){
        StringGen stringGen = new StringGen();
        Set<String> TODO = new HashSet<>();
        node.getNeighbours().forEach(node1 -> {
            TODO.add(stringGen.getString(node,node1));

        });
        if (TODO.size()==4) return true;
        return false;

    }



    public static String RSDetermination(StereoAtom atom){


        StringGen stringGen = new StringGen();
        ArrayList<String> test = new ArrayList<>();
        Map<String,Node> stringNodeMap = new HashMap<>();
        atom.getNeighbours().forEach(node -> {
            String smileNumber = stringGen.stringGenStereoAtomicNumber(atom,(StereoAtom)node);
            test.add(smileNumber);
            stringNodeMap.put(smileNumber,node);
        });
        System.out.println("-----------------");
        Collections.sort(test);
        test.forEach(s -> System.out.println(s+"  "+stringNodeMap.get(s).getLabel()));

        atom.getNeighbourList(stringNodeMap.get(test.get(0))).forEach(node -> System.out.println(node.getLabel()));
        System.out.println("-----------------");
        return "dummy";
    }
}
