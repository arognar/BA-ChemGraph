package model.graph;

public class QNode extends AbstractPQNode implements IPQNode,IPrintable {
    public QNode(String label) {
        super(label);
        nodeType = "Q";
    }

    @Override
    public void test() {

    }

}
