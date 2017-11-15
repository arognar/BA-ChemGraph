package model.chemGraph;

import model.StringGen;
import model.graph.Graph;
import model.graph.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Molecule extends Graph {
    private Set<Node> candidates = new HashSet<>();

    @Override
    public boolean tryConnect(Node node1, Node node2) {
        if(super.tryConnect(node1,node2)) {
            if((((Atom)node1).getNumberHydrogen() + ((Atom)node1).getConnections()) <= 1) candidates.add(node1);
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
        super.getNodes().forEach((s, node) -> {

        });
        System.out.println("PRINTING:");
        super.getNodes().forEach((s, node) -> {
            System.out.println(s+": H:"+((Atom)node).getNumberHydrogen()+"  "+node.getConnections());
            node.getNeighbours().forEach(node1 -> System.out.println("    "+node1.getId()+" "+node1.getLabel()));
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
}
