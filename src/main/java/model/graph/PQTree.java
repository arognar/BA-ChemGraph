package model.graph;

import model.chemGraph.ChemAlgorithm;

import java.util.ArrayList;

/**
 * Fasst die Informationen eines PQ-Baums.
 */
public class PQTree {
    /**
     * Liste aller Knoten des Baums.
     */
    private ArrayList<AbstractPQNode> nodes = new ArrayList<>();
    /**
     * Wurzel des Baums.
     */
    private AbstractPQNode root;


    /**
     * Startet die Reduktion des Baums an der Wurzel.
     */
    public void reduce() {
        root = root.reduce();
    }


    /**
     * Bestimmt die chiralen Knoten in dem Baum.
     *
     * @return Anzahl der chiralen Knoten.
     */
    public int determineStereocenter() {
        final int[] numberOfChiralAtoms = {0};

        nodes.forEach(abstractPQNode -> {
            //Knoten kann nur chiral sein, wenn er als Kohlenstoff markiert wurde und 4 Nachbarn besitzt.
            if (abstractPQNode.getLabel().equals("C") && abstractPQNode.getNeighbours().size() == 4) {
                abstractPQNode.setChirality(ChemAlgorithm.isChiral(abstractPQNode));
                if (abstractPQNode.isChiral()) {
                    numberOfChiralAtoms[0]++;
                }
            }
        });
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
