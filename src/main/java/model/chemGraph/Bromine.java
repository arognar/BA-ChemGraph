package model.chemGraph;

/**
 * Implementation des Brom-Atoms mit seinen spezifischen Eigenschaften.
 */
public class Bromine extends StereoAtom {
    public Bromine() {
        setLabel("Br");
        setConnections(1);
        setAtomicNumber(35);
    }
}
