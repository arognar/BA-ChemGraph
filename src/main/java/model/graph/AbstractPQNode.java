package model.graph;

import java.util.*;

/**
 * Generischer Knoten im PQ-Baum
 */
public abstract class AbstractPQNode extends Node implements IPrintable,Comparable<AbstractPQNode> {
    /**
     * Liste aller Kindknoten.
     */
    private ArrayList<AbstractPQNode> children;

    /**
     * Art der Bindung. Standardbindung ist die Einzelbindung.
     */
    private String bonding ="-";
    /**
     * Informationen über alle Kinder.
     */
    private String childrenInformation="";
    /**
     * Markierung des Knotens. Art des Atoms.
     */
    private String label="";
    /**
     * Typ des Knoten. P oder Q .
     */
    private String nodeType="";
    /**
     * Fasst, ob der Knoten als chiral erkannt wurde.
     */
    private boolean chirality = false;

    public AbstractPQNode(String label){
        super();
        setLabel(label);
        setChildrenInformation(label);
        children = new ArrayList<>();
    }

    public void addChild(AbstractPQNode child,String bonding){
        addNeighbour(child,bonding);
        children.add(child);
    }

    @Override
    public void print(String prefix, boolean isTail) {
        //System.out.println(prefix + (isTail ? "└── " : "├── ") + nodeType+label);
        System.out.println(prefix + (isTail ? "└── " : "├── ") + nodeType+(isChiral()?"#":"")+ bonding +getLabel()+" is NODETYPE  "+nodeType);
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1)
                    .print(prefix + (isTail ?"    " : "│   "), true);
        }
    }

    /**
     * Reduziert den PQ-Baum.
     * Ausgehend von dem Knoten an dem reduce ausgeführt wurde.
     * @return
     */
    public AbstractPQNode reduce() {

        //Keine weiteren Kinder. Keine Reduktion möglich.
        if(children.isEmpty()){
            String childrenInformations = nodeType+getLabel();
            setChildrenInformation(childrenInformations);
            return this;
        }
        else {
            //Reduktion aller Kinder und dieses Knotens.
            AbstractPQNode afterRules;
            ArrayList<AbstractPQNode> reducedChildren = new ArrayList<>();
            children.forEach(abstractPQNode -> {
                AbstractPQNode redNode = abstractPQNode.reduce();
                reducedChildren.add(redNode);
            });
            children = reducedChildren;
            Collections.sort(children);

            //Reduktion dieses Knotens je nach Typ des Knotens.
            afterRules = enforceRules();

            return afterRules;
        }
    }

    /**
     * Setzt die Informationen der Kinder in sortierter Reihung.
     */
    public void setChildrenInformation(){
        Collections.sort(children);
        final String[] childrenInformation = {getNodeType() + getLabel()};
        children.forEach(abstractPQNode -> {
            childrenInformation[0] +=abstractPQNode.getChildrenInformation();
        });
        setChildrenInformation(childrenInformation[0]);
    }





    /**
     * Setzt die Informationen eines Baums zu einer Liste an gültigen SMILES-String zusammen.
     * @return Liste aller generierter SMILES-Strings.
     */
    public ArrayList<String> getAllSmiles(){
        ArrayList<String> smiles = new ArrayList<>();
        //Hat keine Kinder.
        if(children.isEmpty()) {
            smiles.add("("+ getBonding()+getLabel()+")");
            return smiles;
        } else {
            //Wenn Kinder vorhanden.
            //Informationen der Kinder werden je nach Art des Knoten Permutiert und mit dem Karthesischen Produkt zu einer List
            //aller gültigen Strings zusammengefügt.
            ArrayList<ArrayList<String>> childSMILES = new ArrayList<>();
            //SMILES der Kinder werden generiert.
            children.forEach(abstractPQNode -> childSMILES.add(abstractPQNode.getAllSmiles()));
            //Alle Möglichkeiten werden in abhängigkeit der Art des Knotens generiert.
            Set<String> results = new HashSet<>();
            if(this instanceof PNode){
                getPermutationsP(0,"",childSMILES,results);
            }
            if(this instanceof QNode){
                getPermutations(0,"",childSMILES,results);
            }


             //alle SMILES der Kinder werden um die Informationen dieses Knoten, sowie der Klammern der Verzweigung ergänzt.
            ArrayList<String> end = new ArrayList<>(results);
            for (int i = 0; i < end.size(); i++) {
                end.set(i,"("+ getBonding()+getLabel()+end.get(i)+")");
            }

            return end;
        }
    }


    /**
     * Berechnet das karthesische Produkt einer Liste von Stringlisten sowie die Permutationen.
     * @param d Index der aktuellen Liste.
     * @param s String des aktuellen karthesischen Produkts.
     * @param strings Eingabe der Listen.
     * @param results Alle Resultate.
     * @see
     */
    public void getPermutationsP(int d, String s, ArrayList<ArrayList<String>> strings, Set<String> results){
        if(d == strings.size()){
            String str[] = s.substring(0,s.length()-1).split(",");
            //Permutiert die karthesischen Produkte, um alle Möglichkeiten an einem P-Knoten zu generieren.
            permutations(str,0,str.length,results);
            return;
        }
        for (int i = 0; i < strings.get(d).size(); i++) {
            getPermutationsP(d+1,s+strings.get(d).get(i)+",",strings,results);
        }
    }

    /**
     * Berechnet das karthesische Produkt einer Liste von Stringlisten.
     * @param d Index der aktuellen Liste.
     * @param s String des aktuellen karthesischen Produkts.
     * @param strings Eingabe der Listen.
     * @param results Alle Resultate.
     * @see
     */
    private void getPermutations(int d,String s,ArrayList<ArrayList<String>> strings,Set<String> results){
        if(d == strings.size()){
            results.add(s);
            return;
        }
        for (int i = 0; i < strings.get(d).size(); i++) {
            getPermutations(d+1,s+strings.get(d).get(i),strings,results);
        }
    }

    /**
     * Berechnet alle Permutationen eines StringArray nach dem Algorithmus von Heap.
     * @param strings Eingabe der Strings.
     * @param startIndex
     * @param endIndex
     * @param results Menge aller Permutationen.
     * @see
     */
    private void permutations(String[] strings,int startIndex,int endIndex,Set<String> results){
        if(startIndex == endIndex){
            String s = "";
            for (int i = 0; i < strings.length; i++) {
                s+=strings[i];
            }
            results.add(s);
        } else {
            for (int j = startIndex; j < endIndex; j++) {
                swap(strings,startIndex,j);
                permutations(strings,startIndex+1,endIndex,results);
                swap(strings,startIndex,j);
            }
        }
    }

    /**
     * Hilfsfunktion für den Algorithmus von Heap. Tauscht Elemente in einem Array.
     * @param strings Eingabe des Arrays.
     * @param i Zu tauschender Index.
     * @param x Zu tauschender Index.
     * @see
     * @see
     */
    private void swap(String[] strings,int i,int x){
        String s = strings[i];
        strings[i] = strings[x];
        strings[x] = s;
    }

    public void setBonding(String bonding) {
        this.bonding = bonding;
    }

    public String getBonding() {
        return bonding;
    }

    public void setChirality(boolean chirality) {
        this.chirality = chirality;
    }

    public boolean isChiral() {
        return chirality;
    }

    public boolean isntRotational(){
        return(bonding.equals("="));
    }

    public void setChildrenInformation(String childrenInformation) {
        this.childrenInformation = childrenInformation;
    }

    public String getChildrenInformation() {
        return childrenInformation;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public abstract AbstractPQNode enforceRules();

    public String getNodeType() {
        return nodeType;
    }

    public ArrayList<AbstractPQNode> getChildren() {
        return children;
    }
}
