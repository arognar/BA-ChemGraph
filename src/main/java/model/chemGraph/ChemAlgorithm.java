package model.chemGraph;

import model.StringGen;
import model.graph.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
}
