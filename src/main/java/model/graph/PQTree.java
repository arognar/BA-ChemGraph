package model.graph;

import model.StringGen;
import model.chemGraph.ChemAlgorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PQTree {
    ArrayList<AbstractPQNode> nodes = new ArrayList<>();
    AbstractPQNode root;


    public void reduce(){
        root.reduce();
    }

    //todo maybe chemPQTree  ?
    public void determineStereocenter(){
        System.out.println("DETERMINE STEREOCENTER");
        nodes.forEach(abstractPQNode -> {
            if(abstractPQNode.getLabel().equals("C") && abstractPQNode.getNeighbours().size()==4) {
                abstractPQNode.setChirality(ChemAlgorithm.isChiral(abstractPQNode));
            }
            //chem bestimmen und markieren
        });
    }


    public void add(QNode curAtom) {
        nodes.add(curAtom);
    }

    public void setRoot(AbstractPQNode root) {
        this.root = root;
    }

    public AbstractPQNode getRoot() {
        return root;
    }
}
