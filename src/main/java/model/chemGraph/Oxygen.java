package model.chemGraph;

/**
 * Implementation des Sauerstoff-Atoms mit seinen spezifischen Eigenschaften.
 */
public class Oxygen extends StereoAtom {
    
    public Oxygen(){
        setLabel("O");
        setConnections(2);
        setAtomicNumber(8);

    }
}
