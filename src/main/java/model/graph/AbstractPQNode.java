package model.graph;

import java.util.ArrayList;

public class AbstractPQNode implements IPrintable {
    ArrayList<AbstractPQNode> children;
    ArrayList<String> childrenInformation;
    String label;
    String nodeType;

    public AbstractPQNode(String label){
        this.label = label;
        children = new ArrayList<>();
    }

    public void addChild(AbstractPQNode child){
        children.add(child);
    }

    @Override
    public void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + nodeType+label);
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1)
                    .print(prefix + (isTail ?"    " : "│   "), true);
        }
    }
}
