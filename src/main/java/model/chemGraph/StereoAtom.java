package model.chemGraph;

import model.graph.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Node in einem Graphen mit stereochemischen Informationen.
 */
public class StereoAtom extends Node {
    /**
     * Projektion der Atome auf die jeweiligen Listenpositionen.
     */
    public static int[][] bondaryAr = {{-1, 0, 0, 0}, {2, -1, 2, 1}, {1, 1, -1, 2}, {0, 2, 1, -1}};

    /**
     * Alle zyklischen Listen.
     * Blick eines Nachbaratoms auf dieses Atom.
     */
    private ArrayList<ArrayList<Node>> stereoNeighbours = new ArrayList<>();
    /**
     * Spezifische Ordnungszahl fuer die Prioritaet nach CIS.
     */
    private int atomicNumber;


    /**
     * Fuegt ein Atom hinzu und ordnet es und alle vorherigen Nachbaratome in die passende Liste ein.
     *
     * @param node Atom das hinzugefuegt wird.
     * @param c    Art der Bindung.
     */
    public void addNeighbour(Node node, String c) {
        super.addNeighbour(node, c);
        ArrayList<Node> e = new ArrayList();
        //Liste wird mit null-Elementen vorinitialisiert, da hinzufuegen nicht unbedingt chronologisch erfolgt.
        for (int i = 0; i < this.getMaxConnections() - 1; i++) {
            e.add(null);
        }

        stereoNeighbours.add(e);
        //Fuegt das Atom in die Listen und die vorherigen Atome in die Liste des aktuell hinzugefuegten Atoms.
        for (int i = 0; i < super.getNeighbours().size() - 1; i++) {
            stereoNeighbours.get(i).set(bondaryAr[super.getNeighbours().size() - 1][i], node);
            stereoNeighbours.get(super.getNeighbours().size() - 1).set(bondaryAr[i][super.getNeighbours().size() -
                    1], getNeighbours().get(i));
        }
    }

    /**
     * Gibt die Liste zurueck mit dem Blick von "node" auf dieses Atom.
     *
     * @param node Atom von dem auf dieses Atom gesehen wird.
     * @return
     */
    public List<Node> getNeighbourList(Node node) {
        return stereoNeighbours.get(super.getNeighbours().indexOf(node));
    }


    public int getAtomicNumber() {
        return atomicNumber;
    }

    public void setAtomicNumber(int atomicNumber) {
        this.atomicNumber = atomicNumber;
    }

}
