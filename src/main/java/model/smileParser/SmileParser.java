package model.smileParser;

import model.chemGraph.Molecule;
import model.chemGraph.StereoAtom;
import model.graph.AbstractPQNode;
import model.graph.PQTree;
import model.graph.PNode;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Stellt Funktionen bereit, um aus einer Zeichenkette einen PQ-Baum oder ein Molekül zu generieren.
 */
public class SmileParser {
    /**
     * Fehlernachricht. Kann wenn nötig ausgelesen werden.
     */
    private String errorMessage;
    /**
     * Stellt je nach Atomsymbol Atome bereit.
     */
    private AtomFactory atomFactory;

    public SmileParser(){
        atomFactory= new AtomFactory();
    }

    /**
     * Parst die eingegebene Zeichenkette zu einem Molekül.
     * Die Zeichenkette wird von links nach rechts durchlaufen und es wird je nach Symbol eine andere Regel angewandt.
     *
     * @param smileString Eingegebene Zeichenkette.
     * @return Das geparste Molekül.
     */
    public Molecule parseSmile(String smileString){
        ArrayList<String> token = tokenize(smileString);
        if(token.isEmpty()) {
            errorMessage = "Fehler beim Parsen";
            return null;
        }
        if(!machtingBrackets(token)){
            errorMessage = "Fehler bei der Überprüfung der Klammerung";
            return null;
        }

        final Molecule[] molecule = {new Molecule()};
        Stack<StereoAtom> atomStack = new Stack<>();
        final String[] bound = {"-"};
        final boolean[] branchFlag = {false};

        token.forEach(curToken -> {
            if(curToken.equals("(")) branchFlag[0] = true;
            else if(curToken.equals(")")) {
                try{
                    atomStack.pop();
                } catch(EmptyStackException e){
                    errorMessage = "Fehler beim Parsen. Mögliche Fehler sind: Klammerung fehlerhaft oder falsches Symbol";
                    return;

                }

            }
            //P ist Symbol des PQ-Baum Knotens.
            //Um Konflikte mit der zusätzlichen Klammerung zu vermeiden wird das letzte Atom auf dem Stack noch mal hinzugefügt.
            else if(curToken.equals("P")) {
                atomStack.push(atomStack.peek());
            } else if(curToken.equals("=")) {
                bound[0] = "=";
            } else if(curToken.equals("-")){
                bound[0] = "-";
            } else {
                //Neues Atom wird von der atomFactory generiert.
                StereoAtom curAtom = atomFactory.getAtom(curToken);
                molecule[0].addNode(curAtom);
                if(!atomStack.empty()) {
                    //Wenn bindungstechnisch gültig werden Atome verbunden.
                    boolean tryconnect = molecule[0].tryConnect(curAtom,atomStack.peek(),bound[0]);
                    if(!tryconnect) {
                        molecule[0] = null;
                        errorMessage = "Fehler beim Parsen. Möglicherweise stimmt etwas nicht mit den chemisch erlaubten Bindungen";
                        return;
                    }
                    if(bound[0].equals("=")) {
                        molecule[0].addDoubleBond(atomStack.peek(),curAtom);
                    }
                    if(branchFlag[0]) {
                        branchFlag[0]=false;
                    } else {
                        atomStack.pop();
                    }

                }
                atomStack.push(curAtom);
                bound[0] = "-";
            }

        });
        return molecule[0];
    }

    /**
     * Parst die eingegebene Zeichenkette in einen PQ-Baum.
     * Die Zeichenkette wird von links nach rechts durchlaufen und es wird je nach Symbol eine andere Regel angewandt.
     *
     * @param smileString Eingegebene Zeichenkette.
     * @return Der geparste PQ-Baum.
     */
    public PQTree parseSmileToPQTree(String smileString){
        final PQTree[] pqTree = {new PQTree()};
        ArrayList<String> token = tokenize(smileString);
        if(token.isEmpty()) {
            errorMessage = "Fehler beim parsen";
            return null;
        }
        if(!machtingBrackets(token)){
            errorMessage = "Fehler bei der überprüfung der Klammerung";
            return null;
        }

        final AbstractPQNode[] root = new AbstractPQNode[1];
        Stack<AbstractPQNode> atomStack = new Stack<>();

        final String[] bound = {"-"};
        final boolean[] branchFlag = {false};
        final boolean[] rootFlag = {true};

        token.forEach(curToken -> {
            //neue Verzweigung.
            if(curToken.equals("(")) {
                branchFlag[0] = true;
            }
            //Verzweigung abgeschlossen. Letzte Atom auf dem Stack wurde abgearbeitet.
            else if(curToken.equals(")")) {
                try{
                    atomStack.pop();
                } catch(EmptyStackException e){
                    errorMessage = "Fehler beim Parsen. Mögliche Fehler sind Klammerung oder falsches Symbol";
                    pqTree[0] = null;
                    return;

                }

            }
            //Setzt die angegebenen Bindungen.
            else if(curToken.equals("=")) {
                bound[0] = "=";
            } else if(curToken.equals("-")) {
                bound[0] = "-";
            } else {
                //Neuer Knoten wird angelegt
                PNode curAtom = new PNode(curToken);
                pqTree[0].add(curAtom);
                //Wurzel wird gesetzt, falls noch nicht vorhanden.
                if(rootFlag[0]){
                    root[0] = curAtom;
                    pqTree[0].setRoot(curAtom);
                    rootFlag[0] = false;
                }
                //Wenn der Stack nicht leer ist wird der aktuelle Knoten mit dem auf dem Stapel verbunden.
                //Seine Eigenschaften werden gesetzt.
                if(!atomStack.empty()) {
                    atomStack.peek().addChild(curAtom,bound[0]);
                    curAtom.addNeighbour(curAtom,bound[0]);
                    curAtom.setBonding(bound[0]);
                    if(branchFlag[0])branchFlag[0]=false;
                    else atomStack.pop();

                }
                atomStack.push(curAtom);
                //Standardbindung wird gesetzt.
                bound[0] = "-";
            }
        });
        return pqTree[0];
    }


    /**
     * Nutzt einen regulären Ausdruck um die gültigen Zeichen zu extrahieren.
     * @param smileString Der eingegebene String.
     * @return Liste der gültigen Zeichen.
     */
    private ArrayList<String> tokenize(String smileString){
        String regex = "([A-Z][a-z]*)|[()=-]";
        Pattern pattern = Pattern.compile(regex);
        ArrayList<String> token = new ArrayList<>();
        Matcher matcher = pattern.matcher(smileString);
        while(matcher.find()){
            token.add(matcher.group(0));
        }
        return token;
    }

    /**
     * Überprüft ob die Klammerung balanciert ist.
     * Für jede öffnende Klammer wird der Zähler erhöht.
     * Für jede schließende Klammer wird der Zähler verringert.
     * Wird der Zähler negativ treten zuviele schließende Klammern auf.
     * Am Ende muss der Zähler 0 sein, ansonsten sind es zuviele öffnende Klammern.
     * @param tokens Liste an zu überprüfenden Zeichen.
     * @return Klammerung passt und nicht.
     */
    private boolean machtingBrackets(ArrayList<String> tokens){
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

    public String getErrorMessage(){
        return errorMessage;
    }
}
