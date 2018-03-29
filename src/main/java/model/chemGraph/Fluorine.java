package model.chemGraph;

/**
 * Implementation des Fluor-Atoms mit seinen spezifischen Eigenschaften.
 */
public class Fluorine extends StereoAtom {
    public Fluorine() {
        setLabel("F");
        setConnections(1);
        setAtomicNumber(9);
    }
}
