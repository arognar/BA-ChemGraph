package model.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class QNode extends AbstractPQNode implements IPQNode,IPrintable {
    public QNode(String label) {
        super(label);
        nodeType = "Q";
    }

    @Override
    public AbstractPQNode enforceRules() {
        Set<String> test = new HashSet<>();
        children.forEach(abstractPQNode -> test.add(abstractPQNode.childrenInformation));
        if(test.size()<3){
            childrenInformation = nodeType+label;
            children.forEach(abstractPQNode -> {
                childrenInformation+=abstractPQNode.childrenInformation;
            });
            PNode pNode = new PNode(label);
            pNode.children.addAll(children);
            pNode.setChildrenInformation();
            return pNode;
        }
        else {
            System.out.println("enfordeRULE");
            PNode pNode = new PNode(label);
            pNode.addChild(children.get(0));
            QNode qNode = new QNode("");
            qNode.children.addAll(children.subList(1,children.size()));
            qNode.setChildrenInformation();
            pNode.addChild(qNode);
            pNode.setChildrenInformation();
            return pNode;
        }
    }

    @Override
    public void test() {

    }

    @Override
    public int compareTo(AbstractPQNode o) {
        return childrenInformation.compareTo(o.childrenInformation);
    }
}
