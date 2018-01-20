package model.smileParser;

import model.chemGraph.Atom;
import model.chemGraph.Molecule;
import model.chemGraph.StereoAtom;

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
            else if(curToken.equals("=")) bound[0] = "=";
            else if(curToken.equals("-")) bound[0] = "-"; //todo more bounds
            else {
                StereoAtom curAtom = atomFactory.getAtom(curToken);
                molecule.addNode(curAtom);
                if(!atomStack.empty()) {
                    molecule.tryConnect(curAtom,atomStack.peek());
                    if(branchFlag[0])branchFlag[0]=false;
                    else atomStack.pop();

                }
                atomStack.push(curAtom);
            }
        });
        return molecule;
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
