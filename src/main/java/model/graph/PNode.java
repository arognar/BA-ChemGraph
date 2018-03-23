package model.graph;

import java.util.HashSet;
import java.util.Set;

public class PNode extends AbstractPQNode implements IPQNode,IPrintable {
    public PNode(String label) {
        super(label);
        setNodeType("P");
    }

    @Override
    public AbstractPQNode enforceRules() {
        Set<String> test = new HashSet<>(); //todo naming
        children.forEach(abstractPQNode -> {
            test.add(abstractPQNode.getChildrenInformation());
        });
        if(isntRotational()){
            System.out.println("not rotational");
            if(test.size()<2) {
                return reductionRuleQToP();
            } else {
                return this;
            }

        }
        if(test.size()<3){
            System.out.println(" < 3");
            return reductionRuleQToP();
        }
        else {
            if(isChiral()){
                System.out.println("enfordeRULE");
                System.out.println("is chiral  " +this.isChiral());
                QNode qNode = new QNode(getLabel());
                qNode.setLabel(getLabel());
                qNode.setChirality(true);
                System.out.println(qNode.isChiral());
                /*for (int i = 0; i < children.size()-2 ; i++) {
                    //pNode.children.add(children.get(i));
                    pNode.addChild(children.get(i),getBoundingType(children.get(i)));
                }*/
                //pNode.addChild(children.get(0),"");//BOUNDING
                qNode.children.addAll(children.subList(0,children.size()-2));
                PNode pNode = new PNode("P");
                pNode.setBounding("-");
                /*for (int i = children.size()-2; i < children.size() ; i++) {
                    //qNode.children.add(children.get(i));
                    qNode.addChild(children.get(i),getBoundingType(children.get(i)));
                }*/
                //qNode.children.addAll(children.subList(1,children.size()));//todo bounding
                pNode.children.addAll(children.subList(children.size()-2,children.size()));
                pNode.setChildrenInformation();
                qNode.addChild(pNode,"");//bounding
                qNode.setChildrenInformation();
                return qNode;

            } else {
                System.out.println("last ");
                return reductionRuleQToP();
            }

        }
    }

    private AbstractPQNode reductionRuleQToP(){
        childrenInformation = nodeType+getBounding()+getLabel();
        children.forEach(abstractPQNode -> {
            childrenInformation+=abstractPQNode.childrenInformation;
        });
        QNode qNode = new QNode(getLabel());
        qNode.setBounding(getBounding());
        /*for (int i = 0; i < children.size(); i++) {
            pNode.addChild(children.get(i),getBoundingType(children.get(i)));
        }*/
        qNode.children.addAll(children);
        qNode.setChildrenInformation();
        return qNode;
    }

    @Override
    public void test() {

    }

    @Override
    public int compareTo(AbstractPQNode o) {

        return getChildrenInformation().compareTo(o.getChildrenInformation());
    }

}
