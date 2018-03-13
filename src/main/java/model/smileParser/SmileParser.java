package model.smileParser;

import model.chemGraph.Atom;
import model.chemGraph.Molecule;
import model.chemGraph.StereoAtom;
import model.graph.AbstractPQNode;
import model.graph.QNode;

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
        Molecule molecule = new Molecule();
        Stack<StereoAtom> atomStack = new Stack<>();
        final String[] bound = {"-"};
        final boolean[] branchFlag = {false};

        token.forEach(curToken -> {
            if(curToken.equals("(")) branchFlag[0] = true;
            else if(curToken.equals(")")) atomStack.pop();
            else if(curToken.equals("=")) {
                bound[0] = "=";
            }
            else if(curToken.equals("-")) bound[0] = "-"; //todo more bounds
            else {
                StereoAtom curAtom = atomFactory.getAtom(curToken);
                molecule.addNode(curAtom);
                if(!atomStack.empty()) {
                    boolean tryconnect = molecule.tryConnect(curAtom,atomStack.peek(),bound[0]);
                    if(branchFlag[0])branchFlag[0]=false;
                    else atomStack.pop();

                }
                atomStack.push(curAtom);
                bound[0] = "-";
            }

        });
        return molecule;
    }

    public AbstractPQNode parseSmileToPQTree(String smileString){
        ArrayList<String> token = tokenize(smileString);
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
                QNode curAtom = new QNode(curToken);
                if(rootFlag[0]){
                    root[0] = curAtom;
                    rootFlag[0] = false;
                }
                if(!atomStack.empty()) {
                    System.out.println("added");
                    atomStack.peek().addChild(curAtom);
                    if(branchFlag[0])branchFlag[0]=false;
                    else atomStack.pop();

                }
                atomStack.push(curAtom);
            }
        });
        return root[0];
    }


    private ArrayList<String> tokenize(String smileString){
        //todo whitespace trim
        String regex = "([A-Z][a-z]*)|[()=-]";
        Pattern pattern = Pattern.compile(regex);
        ArrayList<String> token = new ArrayList<>();
        Matcher matcher = pattern.matcher(smileString);
        while(matcher.find()){
            token.add(matcher.group(0));
        }

        return token;
    }
}
