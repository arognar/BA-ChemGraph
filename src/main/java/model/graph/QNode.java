package model.graph;

import java.util.HashSet;
import java.util.Set;

public class QNode extends AbstractPQNode implements IPQNode,IPrintable {
    public QNode(String label) {
        super(label);
        setNodeType("Q");
    }

    @Override
    public AbstractPQNode enforceRules() {
        System.out.println("CHIRALITY "+isChiral()+"  "+getLabel());
        Set<String> test = new HashSet<>(); //todo naming
        children.forEach(abstractPQNode -> {
            System.out.println(abstractPQNode.getChildrenInformation());
            test.add(abstractPQNode.getChildrenInformation());
        });
        System.out.println("------------------------------");
        if(isntRotational()){
            System.out.println("not rotational");
            if(test.size()<2) {
                return reductionRuleQToP();
            } else {
                System.out.println("testtttt");
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
                PNode pNode = new PNode(getLabel());
                pNode.setLabel(getLabel());
                /*for (int i = 0; i < children.size()-2 ; i++) {
                    //pNode.children.add(children.get(i));
                    pNode.addChild(children.get(i),getBoundingType(children.get(i)));
                }*/
                //pNode.addChild(children.get(0),"");//BOUNDING
                pNode.children.addAll(children.subList(0,children.size()-2));
                QNode qNode = new QNode("CHI");
                qNode.setBounding("-");
                /*for (int i = children.size()-2; i < children.size() ; i++) {
                    //qNode.children.add(children.get(i));
                    qNode.addChild(children.get(i),getBoundingType(children.get(i)));
                }*/
                //qNode.children.addAll(children.subList(1,children.size()));//todo bounding
                qNode.children.addAll(children.subList(children.size()-2,children.size()));
                qNode.setChildrenInformation();
                pNode.addChild(qNode,"");//bounding
                pNode.setChildrenInformation();
                return pNode;

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
        PNode pNode = new PNode(getLabel());
        pNode.setBounding(getBounding());
        /*for (int i = 0; i < children.size(); i++) {
            pNode.addChild(children.get(i),getBoundingType(children.get(i)));
        }*/
        pNode.children.addAll(children);
        pNode.setChildrenInformation();
        return pNode;
    }

    @Override
    public void test() {

    }

    @Override
    public int compareTo(AbstractPQNode o) {

        return getChildrenInformation().compareTo(o.getChildrenInformation());
    }

}
