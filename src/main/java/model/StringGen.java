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
            //System.out.println("test");
            //System.out.println(node1.getLabel());
            strings.add(stringGenStereoStereoOrder(node,(StereoAtom) node1));
        });
        Collections.sort(strings, String.CASE_INSENSITIVE_ORDER);
        strings.forEach(s -> {
            nextAtoms[0] = new StringBuilder().append(nextAtoms[0]).append("(").append((s)).append(")").toString();
        });

        return nextAtoms[0];

    }

    //ISOMORPHIE
    public String getStereoSmiles2(StereoAtom node){
        ArrayList<String> strings = new ArrayList<>();
        String[] nextAtoms = {node.getLabel()};
        node.getNeighbours().forEach(node1 -> {
            //System.out.println("test");

            strings.add(node.getBoundingType(node1)+stringGenStereo(node,(StereoAtom) node1));
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
                labels.add(stringGeneration2(node));
                //if(!(node instanceof Hydrogen))labels.add(stringGeneration2(node));
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

    public String stringGenStereo(StereoAtom from,StereoAtom to){
        //visitedNodes.add(from.getId());
        final String[] nextAtoms = {from.getBoundingType(to)+to.getLabel()};
        List<String> labels = new ArrayList<>();

        to.getNeighbourList(from).forEach(node -> {
            if(node!=null)labels.add(to.getBoundingType(node)+stringGenStereo(to,(StereoAtom) node));
        });


        if(labels.isEmpty()){
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

    //for RS
    public String stringGenStereoAtomicNumber(StereoAtom from,StereoAtom to){
        //System.out.println("FROM "+from.getLabel()+" "+to.getLabel());
        //visitedNodes.add(from.getId());
        char numbertoChar = (char)(to.getAtomicNumber()+33);
        String label = ""+numbertoChar;
        final String[] nextAtoms = {""+label};
        List<String> labels = new ArrayList<>();

        to.getNeighbourList(from).forEach(node ->{
            if(node!=null)labels.add(stringGenStereoAtomicNumber(to,(StereoAtom) node));//todo boundingtype
        });


        if(labels.isEmpty()){
//            System.out.println("in stringGen " + n.getLabel());

            return label;
        } else {
            Collections.sort(labels, String.CASE_INSENSITIVE_ORDER);
            labels.forEach(node -> {
                nextAtoms[0] = new StringBuilder().append(nextAtoms[0]).append((node)).toString();
            });
//            System.out.println("in stringGen "+nextAtoms[0]);
            return nextAtoms[0];
        }
    }
    public String stringGenStereoStereoOrder(StereoAtom from,StereoAtom to){
        //visitedNodes.add(from.getId());
        //System.out.println("FROM "+from.getLabel());
        //System.out.println(to==null);
        //System.out.println(to.getLabel());
        //System.out.println("FROM "+from.getLabel());
        final String[] nextAtoms = {to.getLabel()};
        ArrayList<String> labels = new ArrayList<>();

        to.getNeighbourList(from).forEach(node -> {

            if(node!=null)labels.add(to.getBoundingType(node)+stringGenStereoStereoOrder(to,(StereoAtom) node));
        });


        if(labels.isEmpty()){
            //System.out.println(to.getLabel());
//            System.out.println("in stringGen " + n.getLabel());
            return to.getLabel();
        } else {
            if(from.getBoundingType(to).equals("=")) {
                nextAtoms[0]= new StringBuilder().append(nextAtoms[0]).append(getSimpleList(labels)).toString();
            } else {
                nextAtoms[0]= new StringBuilder().append(nextAtoms[0]).append(getCircularMaxString(labels)).toString();
            }


//            System.out.println("in stringGen "+nextAtoms[0]);
            return nextAtoms[0];
        }
    }


    public String getCircularMaxString(ArrayList<String> strings){
        Set<String> candidates = new HashSet<>();
        for (int i = 0; i < strings.size() ; i++) {
            String candidat ="";
            for (int j = 0; j < strings.size(); j++) {
                candidat+="("+strings.get((j+i)%strings.size())+")";
            }
            candidates.add(candidat);
        }
        return Collections.max(candidates);
    }

    public String getSimpleList(ArrayList<String> strings){
        String candidat ="";
        for (int i = 0; i < strings.size() ; i++) {
            candidat+="("+strings.get(i)+")";
        }
        return candidat;
    }

}
