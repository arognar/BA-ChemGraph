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
        Set<String> test = new HashSet<>(); //todo naming
        children.forEach(abstractPQNode -> test.add(abstractPQNode.childrenInformation));
        if(getBounding().equals("=")){
            if(test.size()<2) {
                return reductionRuleOne();
            } else {
                return this;
            }

        }
        if(test.size()<3){
            return reductionRuleOne();
        }
        else {
            if(isChiral()){
                System.out.println("enfordeRULE");
                System.out.println("is chiral  " +this.isChiral());
                PNode pNode = new PNode(getLabel());
                pNode.setLabel(getLabel());
                pNode.addChild(children.get(0),"");//BOUNDING
                QNode qNode = new QNode("");
                qNode.setBounding("");
                qNode.children.addAll(children.subList(1,children.size()));
                qNode.setChildrenInformation();
                pNode.addChild(qNode,"");//bounding
                pNode.setChildrenInformation();
                return pNode;

            } else {
                return reductionRuleOne();
            }

        }
    }

    private AbstractPQNode reductionRuleOne(){
        childrenInformation = nodeType+label;
        children.forEach(abstractPQNode -> {
            childrenInformation+=abstractPQNode.childrenInformation;
        });
        PNode pNode = new PNode(getLabel());
        pNode.children.addAll(children);
        pNode.setChildrenInformation();
        return pNode;
    }

    @Override
    public void test() {

    }

    @Override
    public int compareTo(AbstractPQNode o) {
        return childrenInformation.compareTo(o.childrenInformation);
    }
}
