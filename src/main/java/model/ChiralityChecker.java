package model;

import model.graph.Node;
import model.graph.RootedTree;

import java.util.ArrayList;
import java.util.List;

public class ChiralityChecker {
    private Node candidate;
    private List<RootedTree> adjacentAtoms = new ArrayList<RootedTree>();
    public ChiralityChecker(){}

    public boolean checkAtom(Node candidate){
        this.candidate = candidate;
        return true;
    }







}
