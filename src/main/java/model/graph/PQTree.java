package model.graph;

import model.chemGraph.ChemAlgorithm;

import java.util.ArrayList;

public class PQTree {
    ArrayList<AbstractPQNode> nodes = new ArrayList<>();
    AbstractPQNode root;


    //todo muh
    public void reduce(){
        root = root.reduce();
    }

    //todo maybe chemPQTree  ?
    public int determineStereocenter(){
        final int[] numberOfChiralAtoms = {0};
        nodes.forEach(abstractPQNode -> {
            if(abstractPQNode.getLabel().equals("C") && abstractPQNode.getNeighbours().size()==4) {
                abstractPQNode.setChirality(ChemAlgorithm.isChiral(abstractPQNode));
                if(abstractPQNode.isChiral()) {
                    System.out.println("CHIRAL -----------------------------");
                    numberOfChiralAtoms[0]++;
                }
            }
        });
        System.out.println(numberOfChiralAtoms[0]);
        return numberOfChiralAtoms[0];
    }


    public void add(PNode curAtom) {
        nodes.add(curAtom);
    }

    public void setRoot(AbstractPQNode root) {
        this.root = root;
    }

    public AbstractPQNode getRoot() {
        return root;
    }
}
