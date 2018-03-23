package model.smileParser;

import model.chemGraph.Molecule;
import model.chemGraph.StereoAtom;
import model.graph.AbstractPQNode;
import model.graph.PQTree;
import model.graph.PNode;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmileParser {
    private AtomFactory atomFactory;

    public SmileParser(){
        atomFactory= new AtomFactory();
    }

    public Molecule parseSmile(String smileString){
        ArrayList<String> token = tokenize(smileString);
        System.out.println(machtingBrackets(token));
        if(!machtingBrackets(token)) return null;//TODO CONTROLLER

        Molecule molecule = new Molecule();
        Stack<StereoAtom> atomStack = new Stack<>();
        final String[] bound = {"-"};
        final boolean[] branchFlag = {false};

        token.forEach(curToken -> {
            if(curToken.equals("(")) branchFlag[0] = true;
            else if(curToken.equals(")")) atomStack.pop();
            else if(curToken.equals("P")) atomStack.push(atomStack.peek());
            else if(curToken.equals("@")) ;
            else if(curToken.equals("=")) {
                bound[0] = "=";
            }
            else if(curToken.equals("-")) bound[0] = "-"; //todo more bounds
            else {
                StereoAtom curAtom = atomFactory.getAtom(curToken);
                molecule.addNode(curAtom);
                if(!atomStack.empty()) {
                    boolean tryconnect = molecule.tryConnect(curAtom,atomStack.peek(),bound[0]);
                    if(bound[0].equals("=")) molecule.addDoubleBound(atomStack.peek(),curAtom);
                    if(branchFlag[0])branchFlag[0]=false;
                    else atomStack.pop();

                }
                atomStack.push(curAtom);
                bound[0] = "-";
            }

        });
        return molecule;
    }

    public PQTree parseSmileToPQTree(String smileString){
        PQTree pqTree = new PQTree();
        ArrayList<String> token = tokenize(smileString);
        System.out.println(machtingBrackets(token));
        if(!machtingBrackets(token)) return null;//TODO CONTROLLER

        final AbstractPQNode[] root = new AbstractPQNode[1];

        Stack<AbstractPQNode> atomStack = new Stack<>();
        final String[] bound = {"-"};
        final boolean[] branchFlag = {false};
        final boolean[] rootFlag = {true};

        token.forEach(curToken -> {
            if(curToken.equals("(")) branchFlag[0] = true;
            else if(curToken.equals(")")) atomStack.pop();
            else if(curToken.equals("=")) bound[0] = "=";
            else if(curToken.equals("-")) bound[0] = "-"; //todo more bounds
            else {
                PNode curAtom = new PNode(curToken);
                pqTree.add(curAtom);
                if(rootFlag[0]){
                    root[0] = curAtom;
                    pqTree.setRoot(curAtom);
                    rootFlag[0] = false;
                }
                if(!atomStack.empty()) {
                    System.out.println("added");
                    atomStack.peek().addChild(curAtom,bound[0]);
                    curAtom.addNeighbour(curAtom,bound[0]);
                    curAtom.setBounding(bound[0]);
                    if(branchFlag[0])branchFlag[0]=false;
                    else atomStack.pop();

                }
                atomStack.push(curAtom);
                bound[0] = "-";
            }
        });
        return pqTree;
    }


    private ArrayList<String> tokenize(String smileString){
        //todo whitespace trim
        String regex = "([A-Z][a-z]*)|[()=-@#]";
        Pattern pattern = Pattern.compile(regex);
        ArrayList<String> token = new ArrayList<>();
        Matcher matcher = pattern.matcher(smileString);
        while(matcher.find()){
            token.add(matcher.group(0));
        }

        return token;
    }

    private boolean machtingBrackets(ArrayList<String> tokens){
        //TODO CONTROLLER INTERACTION
        int counter = 0;
        for (String s : tokens) {
            if (s.equals("(")) counter++;
            if (s.equals(")")) counter--;
            if (counter < 0) {
                return false;
            }
        }
        if(counter > 0) return false;
        return true;

    }
}
