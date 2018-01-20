package model.smileParser;

import model.chemGraph.Carbon;
import model.chemGraph.Hydrogen;
import model.chemGraph.StereoAtom;

public class AtomFactory {

    public StereoAtom getAtom(String atomSymbol){
        if(atomSymbol.equals("C")) return new Carbon();
        else if(atomSymbol.equals("H")) return new Hydrogen();
        else {
            System.out.println("Could not find "+atomSymbol);
            return null;
        }
    }
}
