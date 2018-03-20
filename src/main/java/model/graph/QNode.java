package model.graph;

import java.util.ArrayList;

public class QNode extends AbstractPQNode implements IPrintable {
    public QNode(String label) {
        super(label);
        nodeType = "Q";
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
