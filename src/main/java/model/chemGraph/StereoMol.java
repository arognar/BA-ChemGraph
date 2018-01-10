package model.chemGraph;

import model.graph.Node;

public class StereoMol extends Node {
    private int numberHydrogen;

    void setNumberHydrogen(int numberHydrogen) {
        numberHydrogen = numberHydrogen;
    }

    public int getNumberHydrogen() {
        return numberHydrogen;
    }

    public void addHydrogen(){
        numberHydrogen++;
        super.setConnections(getConnections()-1);

    }
}
