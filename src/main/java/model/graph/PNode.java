package model.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Der P-Knoten Typ des PQ-Baums.
 */
public class PNode extends AbstractPQNode {
    public PNode(String label) {
        super(label);
        setNodeType("P");
    }

    /**
     * Reduktion eines P-Knotens.
     *
     * @return Gibt den reduzierten Knoten zurueck. Typ kann sich geaendert haben.
     */
    @Override
    public AbstractPQNode enforceRules() {
        //Einzigartige Informationen der Kinder.
        Set<String> distinctChildren = new HashSet<>();
        getChildren().forEach(abstractPQNode -> {
            distinctChildren.add(abstractPQNode.getChildrenInformation());
        });

        //Regeln fuer eine starre Bindung.
        if (isntRotational()) {
            if (distinctChildren.size() < 2) {
                return reductionRulePToQ();
            } else {
                return this;
            }

        }


        if (distinctChildren.size() < 3) {
            return reductionRulePToQ();
        } else {
            if (isChiral()) {
                QNode qNode = new QNode(getLabel());
                qNode.setLabel(getLabel());
                qNode.setChirality(true);

                qNode.getChildren().addAll(getChildren().subList(0, getChildren().size() - 2));
                PNode pNode = new PNode("P");
                pNode.setBonding("-");


                pNode.getChildren().addAll(getChildren().subList(getChildren().size() - 2, getChildren().size()));
                pNode.setChildrenInformation();
                qNode.addChild(pNode, "-");
                qNode.setChildrenInformation();
                return qNode;

            } else {
                return reductionRulePToQ();
            }

        }
    }

    /**
     * Reduktion eines P-Knotens zu einem Q-Knoten.
     *
     * @return Generierter Q-Knoten.
     */
    private AbstractPQNode reductionRulePToQ() {
        QNode qNode = new QNode(getLabel());
        qNode.setBonding(getBonding());
        qNode.getChildren().addAll(getChildren());
        qNode.setChildrenInformation();
        return qNode;
    }


    /**
     * Implementation von Comparable, damit die Kindknoten sortiert werden koennen.
     *
     * @param o Knoten mit dem verglichen wird.
     * @return Sortierung anhand der Kindinformationen. Fuer Strings ist compareTo bereits implementiert.
     */
    @Override
    public int compareTo(AbstractPQNode o) {
        return getChildrenInformation().compareTo(o.getChildrenInformation());
    }

}
