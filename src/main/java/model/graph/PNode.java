package model.graph;

public class PNode extends AbstractPQNode implements IPQNode {
    public PNode(String label) {
        super(label);
        nodeType = "P";
    }

    @Override
    public void test() {

    }


}
