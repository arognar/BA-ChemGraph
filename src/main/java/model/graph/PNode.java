package model.graph;

import java.util.ArrayList;

public class PNode extends AbstractPQNode implements IPQNode {
    public PNode(String label) {
        super(label);
        nodeType = "P";
    }

    @Override
    public AbstractPQNode enforceRules() {
        return null;
    }

    @Override
    public void test() {

    }


    @Override
    public int compareTo(AbstractPQNode o) {
        return childrenInformation.compareTo(o.childrenInformation);
    }
}
