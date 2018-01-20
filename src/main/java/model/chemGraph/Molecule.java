package model.chemGraph;

import model.StringGen;
import model.graph.Graph;
import model.graph.Node;

import java.util.*;

public class Molecule extends Graph {
    private Set<Node> candidates = new HashSet<>();

    @Override
    public boolean tryConnect(Node node1, Node node2) {
        if(super.tryConnect(node1,node2)) {
            if(((node1).getConnections()) <= 1) candidates.add(node1);
            else candidates.remove(node1);
            return true;
        } else {
            return false;
        }
    }

    public void printCandidates(){
        candidates.forEach(node -> {
            if(((Atom)node).getNumberHydrogen()<=1) System.out.println(node.getId());
        });
    }

    public void printMolecule(){
        System.out.println("Printing: ");
        super.getNodes().forEach((s, node) -> {
            ((StereoAtom)node).print();
        });

    }

    public int numberOfChiralityAtoms(){

        final int[] numerChiral = {0};
        candidates.forEach(candidate -> {
            System.out.println("NEW CHECK FOR CHIRALITY------------------------------------");
            Set<String> cCheck = new HashSet<>();
            StringGen stringGen = new StringGen();
            final boolean[] chiral = {true};
            candidate.getNeighbours().forEach(node1 -> {
                //System.out.println("in candidate "+stringGen.getString(candidate, node1));
                if(!cCheck.add(stringGen.getString(candidate,node1))) chiral[0] =false;
            });
            if(chiral[0]) {
                System.out.println("FOUND CHIRAL ATOM");
                numerChiral[0]++;
            }
        });
        return numerChiral[0];
    }

    public String test(){
        StringGen stringGen = new StringGen();
        ArrayList<String> test = new ArrayList<>();
        getNodes().forEach((s, node) -> {
            if(node instanceof Carbon){
                String n = node.getLabel();
                String c = stringGen.getSmilesString(node);
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
