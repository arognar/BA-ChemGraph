package model.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractPQNode implements IPrintable,Comparable<AbstractPQNode> {
    ArrayList<AbstractPQNode> children;
    String childrenInformation;
    String label;
    String nodeType;

    public AbstractPQNode(String label){
        this.label = label;
        childrenInformation = label;
        children = new ArrayList<>();
    }

    public void addChild(AbstractPQNode child){
        children.add(child);
    }

    @Override
    public void print(String prefix, boolean isTail) {
        //System.out.println(prefix + (isTail ? "└── " : "├── ") + nodeType+label);
        System.out.println(prefix + (isTail ? "└── " : "├── ") + nodeType+label+" is NODETYPE  "+nodeType);
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
            childrenInformation = nodeType+label;
            return this;
        }
        else {
            AbstractPQNode afterRules;
            ArrayList<AbstractPQNode> reducedChildren = new ArrayList<>();
            children.forEach(abstractPQNode -> reducedChildren.add(abstractPQNode.reduce()));
            children = reducedChildren;
            Collections.sort(children);
            afterRules = enforceRules();

            return afterRules;
        }
    }

    public void setChildrenInformation(){
        Collections.sort(children);
        childrenInformation = nodeType+label;
        children.forEach(abstractPQNode -> {
            childrenInformation+=abstractPQNode.childrenInformation;
        });
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
            smiles.add("("+label+")"); //todo bindungen
            return smiles;
        } else {
            ArrayList<ArrayList<String>> child = new ArrayList<>();
            children.forEach(abstractPQNode -> child.add(abstractPQNode.getAllSmiles()));
            Set<String> results = new HashSet<>();
            if(this instanceof QNode){
                getPermutationsQ(0,"",child,results);
            }
            if(this instanceof PNode){
                getPermutations(0,"",child,results);
            }

            ArrayList<String> end = new ArrayList<>(results);
            for (int i = 0; i < end.size(); i++) {
                end.set(i,"("+label+end.get(i)+")");
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

    public abstract AbstractPQNode enforceRules();
}
