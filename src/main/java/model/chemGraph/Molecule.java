package model.chemGraph;

import model.graph.Graph;
import model.graph.Node;

import java.util.*;

/**
 * Repraesentiert den chemischen Graphen.
 */
public class Molecule extends Graph {

    /**
     * Liste aller Chiralitaetszentren.
     */
    private ArrayList<Node> chiralAtoms = new ArrayList<>();
    /**
     * Liste aller Doppelbindungen.
     */
    private ArrayList<DoubleBondWrapper> doubleBonds = new ArrayList<>();
    /**
     * Liste aller chiralen Doppelbindungen.
     */
    private ArrayList<DoubleBondWrapper> chiralDoubleBonds = new ArrayList<>();


    /**
     * Untersucht für jedes Kohlenstoffatom die Chiralitaet und fügt die Chiralitaetszentren der Liste aller
     * Chiralitaetszentren hinzu.
     */
    public void determineChirality() {
        getNodes().forEach((s, node) -> {
            if (node instanceof Carbon) {
                if (ChemAlgorithm.isChiral(node)) {
                    chiralAtoms.add(node);
                }
            }
        });
    }

    /**
     * Untersucht die Chiralitaet jeder Doppelbindung und fuegt diese bei Treffer der Liste aller chiralen
     * Doppelbindungen hinzu.
     */
    public void determineChiralityDoubleBound() {
        getDoubleBonds().forEach(doubleBoundWrapper -> {
            if (ChemAlgorithm.isChiralDoubleBound(doubleBoundWrapper)) {
                chiralDoubleBonds.add(doubleBoundWrapper);
            }
        });
    }


    public ArrayList<Node> getChiralAtoms() {
        return chiralAtoms;
    }

    public void addDoubleBond(StereoAtom stereoAtomOne, StereoAtom stereoAtomTwo) {
        doubleBonds.add(new DoubleBondWrapper(stereoAtomOne, stereoAtomTwo));
    }

    public ArrayList<DoubleBondWrapper> getDoubleBonds() {
        return doubleBonds;
    }

    public ArrayList<DoubleBondWrapper> getChiralDoubleBonds() {
        return chiralDoubleBonds;
    }
}
