package model;

import model.graph.Node;

import java.util.*;
import java.util.stream.Collectors;

//TODO benennen
public class StringGen {
    private Set<String> visitedNodes = new HashSet<String>();
    private Node root;
//TODO protected
    public StringGen(Node candidate,Node root){
        this.root = root;
        visitedNodes.add(candidate.getId());
    }

    //TODO PROTECTED
    public String getString(){
        return stringGeneration(root);
    }
    public String getString2(){
        return stringGeneration2(root);
    }


    //TODO first get then sort
    private String stringGeneration(Node n){

        //System.out.println(visitedNodes.size());
        boolean last = true;
        visitedNodes.add(n.getId());
        final String[] nextAtoms = {n.getLabel()};
        //System.out.println(n.getId()+"    lll");
        //System.out.println(root.getNeighbours().size());
        //List<String> labels = root.getNeighbours().stream().filter(node -> !visitedNodes.add(node)).map(node1 -> node1.getLabel()+stringGeneration(node1)).collect(Collectors.toList());
        List<Node> labels = new ArrayList<>();
        //System.out.println("LABEL SIZE"+ labels.size());
        n.getNeighbours().forEach(node -> {

            if(!visitedNodes.contains(node.getId())){
                visitedNodes.add(node.getId());
                //System.out.println("in neighbours"+ node.getId());
                labels.add(node);
            }

        });
        //System.out.println(n.getLabel());



        if(labels.isEmpty()){
            //System.out.println("empty");
            return n.getLabel();
        }

        else {
            Collections.sort(labels, Comparator.comparing(Node::getLabel));
            labels.forEach(node -> {
                //stringGeneration(node);
                nextAtoms[0] = new StringBuilder().append(nextAtoms[0]).append("[").append(stringGeneration(node)).append("]").toString();


            });
            //System.out.println(nextAtoms[0] +"   NEXT ATOMS");
            //Collections.sort(labels,String.CASE_INSENSITIVE_ORDER);
            //labels.forEach((String s) -> nextAtoms.concat(s));
            return nextAtoms[0];
        }
    }

    private String stringGeneration2(Node n){

        boolean last = true;
        visitedNodes.add(n.getId());
        final String[] nextAtoms = {n.getLabel()};
        List<String> labels = new ArrayList<>();
        n.getNeighbours().forEach(node -> {
            if(visitedNodes.add(node.getId())){
                labels.add(stringGeneration2(node));
            }
        });

        if(labels.isEmpty()){
            return n.getLabel();
        }

        else {
            Collections.sort(labels, String.CASE_INSENSITIVE_ORDER);
            labels.forEach(node -> {
                nextAtoms[0] = new StringBuilder().append(nextAtoms[0]).append("[").append((node)).append("]").toString();


            });
            return nextAtoms[0];
        }
    }
}
