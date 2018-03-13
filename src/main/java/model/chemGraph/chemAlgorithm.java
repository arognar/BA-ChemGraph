package model.chemGraph;

import model.StringGen;

import java.util.ArrayList;
import java.util.Collections;

public class chemAlgorithm {

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
}
