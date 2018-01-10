package model;

import model.chemGraph.Hydrogen;
import model.chemGraph.StereoAtom;
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

    public StringGen(){

    }

    //TODO PROTECTED
    public String getString(Node candidate,Node root) {
        System.out.println("CHECK NODE "+root.getId());
        reset();
        this.root = root;
        visitedNodes.add(candidate.getId());
        return stringGeneration2(root);
    }
    public String getString2(){
        return stringGeneration2(root);
    }

    public String getSmilesString(Node n){
        reset();
        return stringGeneration2(n);
    }

    public String getStereoSmiles(StereoAtom node){
        ArrayList<String> strings = new ArrayList<>();
        String[] nextAtoms = {node.getLabel()};
        node.getNeighbours().forEach(node1 -> {
            System.out.println("test");
            strings.add(stringGenStereo(node,(StereoAtom) node1));
        });
        Collections.sort(strings, String.CASE_INSENSITIVE_ORDER);
        strings.forEach(s -> {
            nextAtoms[0] = new StringBuilder().append(nextAtoms[0]).append("(").append((s)).append(")").toString();
        });

        return nextAtoms[0];

    }

    private void reset(){
        visitedNodes = new HashSet<>();
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
        visitedNodes.add(n.getId());
        final String[] nextAtoms = {n.getLabel()};
        List<String> labels = new ArrayList<>();

        n.getNeighbours().forEach(node -> {
            if(visitedNodes.add(node.getId())){

                if(!(node instanceof Hydrogen))labels.add(stringGeneration2(node));
            }
        });

        if(labels.isEmpty()){
//            System.out.println("in stringGen " + n.getLabel());
            return n.getLabel();
        } else {
            Collections.sort(labels, String.CASE_INSENSITIVE_ORDER);
            labels.forEach(node -> {
                nextAtoms[0] = new StringBuilder().append(nextAtoms[0]).append("(").append((node)).append(")").toString();
            });
//            System.out.println("in stringGen "+nextAtoms[0]);
            return nextAtoms[0];
        }
    }

    private String stringGenStereo(StereoAtom from,StereoAtom to){
        //visitedNodes.add(from.getId());
        final String[] nextAtoms = {to.getLabel()};
        List<String> labels = new ArrayList<>();

        to.getNeighbourList(from).forEach(node -> {
            labels.add(stringGenStereo(to,(StereoAtom) node));
        });


        if(labels.isEmpty()){
//            System.out.println("in stringGen " + n.getLabel());
            return to.getLabel();
        } else {
            Collections.sort(labels, String.CASE_INSENSITIVE_ORDER);
            labels.forEach(node -> {
                nextAtoms[0] = new StringBuilder().append(nextAtoms[0]).append("(").append((node)).append(")").toString();
            });
//            System.out.println("in stringGen "+nextAtoms[0]);
            return nextAtoms[0];
        }
    }
}
