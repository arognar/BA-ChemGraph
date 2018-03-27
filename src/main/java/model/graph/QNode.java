package model.graph;

/**
 * Implementierung des Q-Knotens.
 */
public class QNode extends AbstractPQNode implements IPrintable {
    public QNode(String label) {
        super(label);
        setNodeType("Q");
    }

    /**
     * Ein Q-Knoten kann nicht reduziert werden.
     * @return Der Q-Knoten selbst.
     */
    @Override
    public AbstractPQNode enforceRules() {
        setChildrenInformation();
        return this;
    }


    /**
     * Implementation von Comparable, damit die Kindknoten sortiert werden können.
     * @param o Knoten mit dem verglichen wird.
     * @return Sortierung anhand der Kindinformatione. Für Strings ist compareTo bereits implementiert.
     */
    @Override
    public int compareTo(AbstractPQNode o) {
        return getChildrenInformation().compareTo(o.getChildrenInformation());
    }
}
