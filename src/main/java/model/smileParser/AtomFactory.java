package model.smileParser;

import model.chemGraph.*;

public class AtomFactory {

    public StereoAtom getAtom(String atomSymbol){
        if(atomSymbol.equals("C")) return new Carbon();
        else if(atomSymbol.equals("H")) return new Hydrogen();
        else if(atomSymbol.equals("F")) return new Fluorine();
        else if(atomSymbol.equals("Br")) return new Bromine();
        else if(atomSymbol.equals("O")) return new Oxygen();
        else {
            System.out.println("Could not find "+atomSymbol);
            return null;
        }
    }
}
