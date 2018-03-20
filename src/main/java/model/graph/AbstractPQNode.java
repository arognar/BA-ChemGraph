package model.graph;

import java.util.*;

public abstract class AbstractPQNode extends Node implements IPrintable,Comparable<AbstractPQNode> {
    ArrayList<AbstractPQNode> children;
    Map<AbstractPQNode,String> children2;

    String bounding="-";
    String childrenInformation="";
    String label="";
    String nodeType="";
    boolean chirality = false;//TODO markierung smile

    public AbstractPQNode(String label){
        super();
        setLabel(label);
        setChildrenInformation(label);
        children = new ArrayList<>();
        children2 = new HashMap<>();
    }

    public void addChild(AbstractPQNode child,String bounding){
        addNeighbour(child,bounding);
        children.add(child);
        children2.put(child,bounding);
    }

    @Override
    public void print(String prefix, boolean isTail) {
        //System.out.println(prefix + (isTail ? "└── " : "├── ") + nodeType+label);
        System.out.println(prefix + (isTail ? "└── " : "├── ") + nodeType+(isChiral()?"#":"")+bounding+getLabel()+" is NODETYPE  "+nodeType);
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1)
                    .print(prefix + (isTail ?"    " : "│   "), true);
        }
    }

    public AbstractPQNode reduce() {

        if(children.isEmpty()){
            String childrenInformations = nodeType+getLabel();
            setChildrenInformation(childrenInformations);
            return this;
        }
        else {
            AbstractPQNode afterRules;
            ArrayList<AbstractPQNode> reducedChildren = new ArrayList<>();
            Map<AbstractPQNode,String> reducedChildren2 = new HashMap<>();
            children.forEach(abstractPQNode -> {

                AbstractPQNode redNode = abstractPQNode.reduce();
                reducedChildren.add(redNode);
                //reducedChildren2.put(redNode,redNode.getBounding());
                //neighbours.put(redNode,redNode.getBounding());
            });
            children = reducedChildren;
            //children.forEach(abstractPQNode -> System.out.println(abstractPQNode.getLabel()));


            //children2 = reducedChildren2;
            Collections.sort(children);
            //this.neighbours = neighbours;//change
            afterRules = enforceRules();

            return afterRules;
        }
    }

    public void setChildrenInformation(){
        Collections.sort(children);
        final String[] childrenInformation = {getNodeType() + getLabel()};
        children.forEach(abstractPQNode -> {
            childrenInformation[0] +=abstractPQNode.getChildrenInformation();
        });
        setChildrenInformation(childrenInformation[0]);
    }

    private void getPermutations(int d,String s,ArrayList<ArrayList<String>> strings,Set<String> results){//todo set ?
        if(d == strings.size()){
            results.add(s);
            return;
        }
        for (int i = 0; i < strings.get(d).size(); i++) {
            getPermutations(d+1,s+strings.get(d).get(i),strings,results);
        }
    }

    public ArrayList<String> getAllSmiles(){
        ArrayList<String> smiles = new ArrayList<>();
        if(children.isEmpty()) {
            smiles.add("("+getBounding()+getLabel()+")"); //todo bindungen
            return smiles;
        } else {
            ArrayList<ArrayList<String>> child = new ArrayList<>();
            children.forEach(abstractPQNode -> child.add(abstractPQNode.getAllSmiles()));
            Set<String> results = new HashSet<>();
            if(this instanceof PNode){
                getPermutationsQ(0,"",child,results);
            }
            if(this instanceof QNode){
                getPermutations(0,"",child,results);
            }

            ArrayList<String> end = new ArrayList<>(results);
            for (int i = 0; i < end.size(); i++) {
                end.set(i,"("+getBounding()+getLabel()+end.get(i)+")");
            }

            return end;
        }
    }



    public void getPermutationsQ(int d,String s,ArrayList<ArrayList<String>> strings,Set<String> results){
        if(d == strings.size()){
            String str[] = s.substring(0,s.length()-1).split(",");
            permutations(str,0,str.length,results);
            return;
        }
        for (int i = 0; i < strings.get(d).size(); i++) {
            getPermutationsQ(d+1,s+strings.get(d).get(i)+",",strings,results);
        }
    }

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

    private void swap(String[] strings,int i,int x){
        String s = strings[i];
        strings[i] = strings[x];
        strings[x] = s;
    }

    public void setBounding(String bounding) {
        this.bounding = bounding;
    }

    public String getBounding() {
        return bounding;
    }

    public void setChirality(boolean chirality) {
        this.chirality = chirality;
    }

    public boolean isChiral() {
        return chirality;
    }

    public boolean isntRotational(){
        return(bounding.equals("="));//TODO dreifachbindung
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
}
