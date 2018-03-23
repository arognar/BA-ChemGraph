package model.chemGraph;


public class DoubleBoundWrapper {
    private StereoAtom stereoAtomOne;
    private StereoAtom stereoAtomTwo;

    public DoubleBoundWrapper(StereoAtom stereoAtomOne,StereoAtom stereoAtomTwo){
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
