package model.chemGraph;

/**
 * Implementation des Kohlenstoff-Atoms mit seinen spezifischen Eigenschaften.
 */
public class Carbon extends StereoAtom {
    public Carbon(){
        setLabel("C");
        setAtomicNumber(6);
        setConnections(4);
    }
}
