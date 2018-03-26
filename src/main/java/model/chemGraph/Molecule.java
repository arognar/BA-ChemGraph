package model.chemGraph;

import model.graph.Graph;
import model.graph.Node;

import java.util.*;

/**
 * Repräsentiert den chemischen Graphen.
 */
public class Molecule extends Graph {

    /**
     * Liste aller chiraler Atome.
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


    public void addDoubleBond(StereoAtom stereoAtomOne,StereoAtom stereoAtomTwo){
        doubleBonds.add(new DoubleBondWrapper(stereoAtomOne,stereoAtomTwo));
    }

    /**
     * Untersucht für jedes Kohlenstoffatom die Chiralität und fügt dieser der Liste aller chiralen Atome hinzu.
     */
    public void determineChirality(){
        getNodes().forEach((s, node) -> {
            if(node instanceof Carbon) {
                if(ChemAlgorithm.isChiral(node)){
                    chiralAtoms.add(node);
                }
            }
        });
    }

    /**
     * Untersucht die Chiralität jeder Doppelbindung und fügt diese der Liste aller chiralen Doppelbindungen hinzu.
     */
    public void determineChiralityDoubleBound(){
        getDoubleBonds().forEach(doubleBoundWrapper -> {
            if(ChemAlgorithm.isChiralDoubleBound(doubleBoundWrapper)){
                chiralDoubleBonds.add(doubleBoundWrapper);
            }
        });
    }

    public ArrayList<Node> getChiralAtoms() {
        return chiralAtoms;
    }

    public ArrayList<DoubleBondWrapper> getDoubleBonds() {
        return doubleBonds;
    }

    public ArrayList<DoubleBondWrapper> getChiralDoubleBonds() {
        return chiralDoubleBonds;
    }
}
