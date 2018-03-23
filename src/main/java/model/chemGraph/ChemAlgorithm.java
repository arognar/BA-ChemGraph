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
                //System.out.println("CarbonFound");
                String n = node.getLabel();
                String c = stringGen.getStereoSmiles2((StereoAtom) node);
                //System.out.println(c);
                test.add(new StringBuilder().append(n).append("[").append((c)).append("]").toString());
            }
        });

        Collections.sort(test, String.CASE_INSENSITIVE_ORDER);
        final String[] m = {""};
        test.forEach(s -> {
            m[0] = new StringBuilder().append(m[0]).append(s).toString();
        });
        //System.out.println(m[0]);
        return m[0];

    }

    public static String konfigurationIso(Molecule molecule){
        StringGen stringGen = new StringGen();
        ArrayList<String> test = new ArrayList<>();
        molecule.getNodes().forEach((s, node) -> {
            if(node instanceof Carbon){
                String n = node.getLabel();
                String c = stringGen.getStereoSmiles((StereoAtom) node);
                //System.out.println(c);
                test.add(new StringBuilder().append(n).append("[").append((c)).append("]").toString());
            }
        });

        Collections.sort(test, String.CASE_INSENSITIVE_ORDER);
        final String[] m = {""};
        test.forEach(s -> {
            m[0] = new StringBuilder().append(m[0]).append(s).toString();
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
        Map<Node,String> nodeStringMap = new HashMap<>();
        atom.getNeighbours().forEach(node -> {
            String smileNumber = stringGen.stringGenStereoAtomicNumber(atom,(StereoAtom)node);
            test.add(smileNumber);
            stringNodeMap.put(smileNumber,node);
            nodeStringMap.put(node,smileNumber);
        });
        Collections.sort(test);
        List<Node> nodesList = atom.getNeighbourList(stringNodeMap.get(test.get(0)));
        for (int i = 0; i < nodesList.size(); i++) {
            if(nodesList.get(i) == stringNodeMap.get(test.get(3))) {
                if(nodeStringMap.get(nodesList.get((i+1)%nodesList.size())).compareTo(nodeStringMap.get(nodesList.get(Math.floorMod(i-1,nodesList.size())))) < 0)
                    System.out.println("R");
                else System.out.println("S");
            }
        }
        return "dummy";
    }

    public static boolean isChiralDoubleBound(DoubleBoundWrapper doubleBoundWrapper){
        StringGen stringGen = new StringGen();
        StereoAtom first = doubleBoundWrapper.getStereoAtomOne();
        StereoAtom second = doubleBoundWrapper.getStereoAtomTwo();
        if((first.getNeighbours().size()<3) || second.getNeighbours().size()<3) return false;

        Set<String> neighboursFirst = new HashSet<>();
        first.getNeighbourList(second).forEach(node -> {
            if(node!=null)neighboursFirst.add(stringGen.stringGenStereo(first,(StereoAtom)node));
        });
        if(neighboursFirst.size()<2) return false;

        Set<String> neighboursSecond = new HashSet<>();
        second.getNeighbourList(first).forEach(node -> {
            if(node!=null)neighboursSecond.add(stringGen.stringGenStereo(second,(StereoAtom)node));
        });
        if(neighboursSecond.size()<2) return false;

        return true;
    }

    public static String cisTrans(DoubleBoundWrapper doubleBoundWrapper){
        StringGen stringGen = new StringGen();
        StereoAtom first = doubleBoundWrapper.getStereoAtomOne();
        StereoAtom second = doubleBoundWrapper.getStereoAtomTwo();
        ArrayList<String> firstNeighboursPrio = new ArrayList<>();
        ArrayList<String> secondNeighboursPrio = new ArrayList<>();
        first.getNeighbourList(second).forEach(node -> {
            if(node!=null) firstNeighboursPrio.add(stringGen.stringGenStereoAtomicNumber(first,(StereoAtom) node));
        });
        second.getNeighbourList(first).forEach(node -> {
            if(node!=null) secondNeighboursPrio.add(stringGen.stringGenStereoAtomicNumber(second,(StereoAtom) node));
        });

        firstNeighboursPrio.forEach(s -> System.out.println(s));
        secondNeighboursPrio.forEach(s -> System.out.println(s));
        if(firstNeighboursPrio.get(0).compareTo(firstNeighboursPrio.get(1))<0 && secondNeighboursPrio.get(0).compareTo(secondNeighboursPrio.get(1))<0) {
            System.out.println("cis Z");
            return "Z";
        }
        if(firstNeighboursPrio.get(0).compareTo(firstNeighboursPrio.get(1))>0 && secondNeighboursPrio.get(0).compareTo(secondNeighboursPrio.get(1))>0) {
            System.out.println("cis Z");
            return "Z";
        }

        System.out.println("trans");

        return "dummy";
    }
}
