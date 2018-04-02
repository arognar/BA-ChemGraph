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
 * Stellt Funktionen bereit, um aus einer Zeichenkette einen PQ-Baum oder ein Molekuel zu generieren.
 */
public class SmileParser {
    /**
     * Fehlernachricht. Kann, wenn noetig, ausgelesen werden.
     */
    private String errorMessage;
    /**
     * Stellt je nach Atomsymbol Atome bereit.
     */
    private AtomFactory atomFactory;

    public SmileParser() {
        atomFactory = new AtomFactory();
    }

    /**
     * Parst die eingegebene Zeichenkette zu einem Molekuel.
     * Die Zeichenkette wird von links nach rechts durchlaufen und es wird je nach Symbol eine andere Regel angewandt.
     *
     * @param smileString Eingegebene Zeichenkette.
     * @return Das geparste Molekuel.
     */
    public Molecule parseSmile(String smileString) {
        ArrayList<String> token = tokenize(smileString);
        if (token.isEmpty()) {
            errorMessage = "Fehler beim Parsen";
            return null;
        }
        if (!machtingBrackets(token)) {
            errorMessage = "Fehler bei der Ueberpruefung der Klammerung";
            return null;
        }

        final Molecule[] molecule = {new Molecule()};
        Stack<StereoAtom> atomStack = new Stack<>();
        final String[] bound = {"-"};
        final boolean[] branchFlag = {false};

        token.forEach(curToken -> {
            if (curToken.equals("(")) branchFlag[0] = true;
            else if (curToken.equals(")")) {
                try {
                    atomStack.pop();
                } catch (EmptyStackException e) {
                    errorMessage = "Fehler beim Parsen. Moegliche Fehler sind: Klammerung fehlerhaft oder falsches " +
                            "Symbol";
                    return;

                }

            }
            //P ist Symbol des PQ-Baum Knotens.
            //Um Konflikte mit der zusaetzlichen Klammerung zu vermeiden wird das letzte Atom auf dem Stack noch mal
            // hinzugefuegt.
            else if (curToken.equals("P")) {
                atomStack.push(atomStack.peek());
            } else if (curToken.equals("=")) {
                bound[0] = "=";
            } else if (curToken.equals("-")) {
                bound[0] = "-";
            } else {
                //Neues Atom wird von der atomFactory generiert.
                StereoAtom curAtom = atomFactory.getAtom(curToken);
                molecule[0].addNode(curAtom);
                if (!atomStack.empty()) {
                    //Wenn bindungstechnisch gueltig, werden Atome verbunden.
                    boolean tryconnect = molecule[0].tryConnect(curAtom, atomStack.peek(), bound[0]);
                    if (!tryconnect) {
                        molecule[0] = null;
                        errorMessage = "Fehler beim Parsen. Moeglicherweise stimmt etwas nicht mit den chemisch " +
                                "erlaubten Bindungen";
                        return;
                    }
                    if (bound[0].equals("=")) {
                        molecule[0].addDoubleBond(atomStack.peek(), curAtom);
                    }
                    if (branchFlag[0]) {
                        branchFlag[0] = false;
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
    public PQTree parseSmileToPQTree(String smileString) {
        final PQTree[] pqTree = {new PQTree()};
        ArrayList<String> token = tokenize(smileString);
        if (token.isEmpty()) {
            errorMessage = "Fehler beim parsen";
            return null;
        }
        if (!machtingBrackets(token)) {
            errorMessage = "Fehler bei der Ueberpruefung der Klammerung";
            return null;
        }

        final AbstractPQNode[] root = new AbstractPQNode[1];
        Stack<AbstractPQNode> atomStack = new Stack<>();

        final String[] bound = {"-"};
        final boolean[] branchFlag = {false};
        final boolean[] rootFlag = {true};

        token.forEach(curToken -> {
            //Neue Verzweigung.
            if (curToken.equals("(")) {
                branchFlag[0] = true;
            }
            //Verzweigung abgeschlossen. Letzte Atom auf dem Stack wurde abgearbeitet.
            else if (curToken.equals(")")) {
                try {
                    atomStack.pop();
                } catch (EmptyStackException e) {
                    errorMessage = "Fehler beim Parsen. Moegliche Fehler sind die Klammerung oder falsches Symbol";
                    pqTree[0] = null;
                    return;

                }

            }
            //Setzt die angegebenen Bindungen.
            else if (curToken.equals("=")) {
                bound[0] = "=";
            } else if (curToken.equals("-")) {
                bound[0] = "-";
            } else {
                //Neuer Knoten wird angelegt
                PNode curAtom = new PNode(curToken);
                pqTree[0].add(curAtom);
                //Wurzel wird gesetzt, falls noch nicht vorhanden.
                if (rootFlag[0]) {
                    root[0] = curAtom;
                    pqTree[0].setRoot(curAtom);
                    rootFlag[0] = false;
                }
                //Wenn der Stack nicht leer ist wird der aktuelle Knoten mit dem auf dem Stapel verbunden.
                //Seine Eigenschaften werden gesetzt.
                if (!atomStack.empty()) {
                    atomStack.peek().addChild(curAtom, bound[0]);
                    curAtom.addNeighbour(curAtom, bound[0]);
                    curAtom.setBonding(bound[0]);
                    if (branchFlag[0]) branchFlag[0] = false;
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
     * Nutzt einen regulaeren Ausdruck um die gueltigen Zeichen zu extrahieren.
     *
     * @param smileString Der eingegebene String.
     * @return Liste der gueltigen Zeichen.
     */
    private ArrayList<String> tokenize(String smileString) {
        String regex = "([A-Z][a-z]*)|[()=-]";
        Pattern pattern = Pattern.compile(regex);
        ArrayList<String> token = new ArrayList<>();
        Matcher matcher = pattern.matcher(smileString);
        while (matcher.find()) {
            token.add(matcher.group(0));
        }
        return token;
    }

    /**
     * Ueberprueft ob die Klammerung balanciert ist.
     * Fuer jede oeffnende Klammer wird der Zaehler erhoeht.
     * Fuer jede schliessende Klammer wird der Zaehler verringert.
     * Wird der Zaehler negativ treten zuviele schliessende Klammern auf.
     * Am Ende muss der Zaehler 0 sein, ansonsten sind es zuviele oeffnende Klammern.
     *
     * @param tokens Liste an zu ueberpruefenden Zeichen.
     * @return Klammerung passt und nicht.
     */
    private boolean machtingBrackets(ArrayList<String> tokens) {
        int counter = 0;
        for (String s : tokens) {
            if (s.equals("(")) counter++;
            if (s.equals(")")) counter--;
            if (counter < 0) {
                return false;
            }
        }
        if (counter > 0) return false;
        return true;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
