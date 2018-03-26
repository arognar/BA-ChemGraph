package model.chemGraph;

/**
 * Speichert die beiden Atome einer Doppelbindung.
 */
public class DoubleBondWrapper {
    private StereoAtom stereoAtomOne;
    private StereoAtom stereoAtomTwo;

    public DoubleBondWrapper(StereoAtom stereoAtomOne, StereoAtom stereoAtomTwo){
        this.stereoAtomOne = stereoAtomOne;
        this.stereoAtomTwo = stereoAtomTwo;
    }

    public StereoAtom getStereoAtomOne() {
        return stereoAtomOne;
    }

    public StereoAtom getStereoAtomTwo() {
        return stereoAtomTwo;
    }
}
