package model.chemGraph;

/**
 * Implementation des Wasserstoff-Atoms mit seinen spezifischen Eigenschaften.
 */
public class Hydrogen extends StereoAtom {
    public Hydrogen() {
        setLabel("H");
        setConnections(1);
        setAtomicNumber(1);
    }
}
