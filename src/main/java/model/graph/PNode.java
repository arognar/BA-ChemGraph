package model.graph;

import java.util.ArrayList;

public class PNode extends AbstractPQNode implements IPrintable {
    public PNode(String label) {
        super(label);
        nodeType = "P";
    }

    @Override
    public AbstractPQNode enforceRules() {
        setChildrenInformation();
        return this;
    }


    @Override
    public int compareTo(AbstractPQNode o) {
        return getChildrenInformation().compareTo(o.getChildrenInformation());
    }
}
