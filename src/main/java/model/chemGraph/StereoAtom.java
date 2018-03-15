package model.chemGraph;

import model.GraphUtil;
import model.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class StereoAtom extends Node{
    private ArrayList<ArrayList<Node>> stereoNeighbours = new ArrayList<>();


    @Override
    public void addNeighbour(Node node, String c) {
        super.addNeighbour(node, c);
        ArrayList<Node> e = new ArrayList();
        for(int i = 0; i < this.getMaxConnections()-1; i++) {
            e.add(null);
        }
        stereoNeighbours.add(e);
        for(int i = 0; i < super.getNeighbours().size()-1;i++){
            stereoNeighbours.get(i).set(GraphUtil.boundaryAr[super.getNeighbours().size()-1][i],node);
            stereoNeighbours.get(super.getNeighbours().size()-1).set(GraphUtil.boundaryAr[i][super.getNeighbours().size()-1],getNeighbours().get(i));
        }
    }

    public void print(){
        System.out.println("Atom "+this.getLabel()+" with ID"+this.getId()+"------------");
        System.out.println("Neighbours:" +super.getNeighbours().size());
        for(int i = 0; i < stereoNeighbours.size(); i++){
            System.out.println(super.getNeighbours().get(i).getId()+"--------------------");
            stereoNeighbours.get(i).forEach(node -> {
                if(node != null)System.out.print("AtomSymbol"+node.getLabel()+" ID:"+node.getId()+" ");
                //else System.out.print("NULL ");
            });
            System.out.println();
        }
        System.out.println("----- --------- --------- ---------- -------------- -----------");
        System.out.println(this.getLabel());
        stereoNeighbours.forEach(nodes -> {
            nodes.forEach(node -> {
                if(node!=null)System.out.print(node.getLabel());
            });
            System.out.println();
        });
        System.out.println("----- --------- --------- ---------- -------------- -----------");

    }

    public List<Node> getNeighbourList(Node node){

        //return stereoNeighbours.get(super.getNeighbours().indexOf(node)).subList(0,getNeighbours().size()-1);
        return stereoNeighbours.get(super.getNeighbours().indexOf(node));
    }

}
