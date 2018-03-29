package model.chemGraph;

import model.StringGenerator;
import model.graph.Node;

import java.util.*;


/**
 * Stellt Algorithmen bereit, die für die chemischen Analyse noetig sind.
 */
public class ChemAlgorithm {

    /**
     * Berechnet den eindeutigen String mit Konstitutionsinformationen für das gesamten Molekuel.
     * Fuegt die für jedes Kohlenstoff-Atom generierten Baum-Isomorphismus-Strings geordnet zusammen.
     *
     * @param molecule Molekuel das betrachtet wird.
     * @return Eindeutigen String für den Konstitutions-Isomorphismus.
     */
    public static String konsitutionIso(Molecule molecule) {
        StringGenerator stringGenerator = new StringGenerator();
        ArrayList<String> atomStringList = new ArrayList<>();
        molecule.getNodes().forEach((s, node) -> {
            if (node instanceof Carbon) {
                String label = node.getLabel();
                String atomString = stringGenerator.getKonstiString((StereoAtom) node);

                atomStringList.add(new StringBuilder().append(label).append("[").append((atomString)).append("]")
                        .toString());
            }
        });

        Collections.sort(atomStringList, String.CASE_INSENSITIVE_ORDER);
        final String[] isoString = {""};
        atomStringList.forEach(s -> {
            isoString[0] = new StringBuilder().append(isoString[0]).append(s).toString();
        });

        return isoString[0];

    }

    /**
     * Berechnet den eindeutigen String mit Konfigurationsinformationen für das gesamte Molekuel.
     * Fuegt die für jedes Kohlenstoff-Atom generierten Baum-Isomorphismus-Strings geordnet zusammen.
     *
     * @param molecule Molekuel das betrachtet wird.
     * @return Eindeutigen String für den Konfigurations-Isomorphismus.
     */
    public static String konfigurationIso(Molecule molecule) {
        StringGenerator stringGenerator = new StringGenerator();
        ArrayList<String> atomStringList = new ArrayList<>();
        molecule.getNodes().forEach((s, node) -> {
            if (node instanceof Carbon) {
                String label = node.getLabel();
                String atomString = stringGenerator.getKonfiString((StereoAtom) node);

                atomStringList.add(new StringBuilder().append(label).append("[").append((atomString)).append("]")
                        .toString());
            }
        });

        Collections.sort(atomStringList, String.CASE_INSENSITIVE_ORDER);
        final String[] isoString = {""};
        atomStringList.forEach(s -> {
            isoString[0] = new StringBuilder().append(isoString[0]).append(s).toString();
        });
        return isoString[0];
    }


    /**
     * Berechnet den eindeutigen String mit Konformationsinformationen für das gesamte Molekuel.
     * Fuegt die für jedes Kohlenstoff-Atom generierten Baum-Isomorphismus-Strings geordnet zusammen.
     *
     * @param molecule Molekuel das betrachtet wird.
     * @return Eindeutigen String für den Konformations-Isomorphismus.
     */
    public static String konformationIso(Molecule molecule) {
        StringGenerator stringGenerator = new StringGenerator();
        ArrayList<String> atomStringList = new ArrayList<>();
        molecule.getNodes().forEach((s, node) -> {
            if (node instanceof Carbon) {
                String label = node.getLabel();
                String atomString = stringGenerator.getKonformString((StereoAtom) node);

                atomStringList.add(new StringBuilder().append(label).append("[").append((atomString)).append("]")
                        .toString());
            }
        });

        Collections.sort(atomStringList, String.CASE_INSENSITIVE_ORDER);
        final String[] isoString = {""};
        atomStringList.forEach(s -> {
            isoString[0] = new StringBuilder().append(isoString[0]).append(s).toString();
        });

        return isoString[0];

    }

    /**
     * Prueft ob ein Knoten Chiralitaetszentrum ist.
     *
     * @param node Knoten der Geprueft wird. Knoten eines PQ-Baumes oder Atom.
     * @return Ob Chiralitaetszentrum oder nicht.
     */
    public static boolean isChiral(Node node) {
        StringGenerator stringGenerator = new StringGenerator();
        //Fuegt für jede anliegende Atomgruppe des Knoten den String des Baum-Isomorphismus dem Set hinzu.
        Set<String> isoSet = new HashSet<>();
        node.getNeighbours().forEach(node1 -> {
            isoSet.add(stringGenerator.getStringNonStereo(node, node1));
        });
        //Groeße des Sets muss 4 sein. Nur dann sind alle Nachbarn verschieden.
        if (isoSet.size() == 4) return true;
        return false;
    }


    /**
     * Spezifiziert Drehung nach links oder rechts.
     *
     * @param atom Chiralitaetszentrum.
     * @return R oder S.
     */
    public static String RSDetermination(StereoAtom atom) {
        StringGenerator stringGenerator = new StringGenerator();
        ArrayList<String> isoStrings = new ArrayList<>();
        //Ordnet den isoString dem jeweiligen Node zu.
        Map<String, Node> stringNodeMap = new HashMap<>();
        //Ordnet einem Knoten den isoString zu.
        Map<Node, String> nodeStringMap = new HashMap<>();
        //Berechnet für jeden Nachbarn des Zentrum den String mit Prioritaetsinformationen.
        atom.getNeighbours().forEach(node -> {
            String smileNumber = stringGenerator.stringGenStereoAtomicNumber(atom, (StereoAtom) node);
            isoStrings.add(smileNumber);
            stringNodeMap.put(smileNumber, node);
            nodeStringMap.put(node, smileNumber);
        });

        //Knoten mit der kleinsten Prioritaet wird bestimmt.
        Collections.sort(isoStrings);
        //Liste mit Blick des prioritaetsmaeßig kleinsten Atoms auf das Zentrum.
        List<Node> nodesList = atom.getNeighbourList(stringNodeMap.get(isoStrings.get(0)));

        //Sucht den Eintrag des Atoms mit der groeßten Prioritaet und vergleicht die links und rechts liegenden
        // Nachbarn.
        for (int i = 0; i < nodesList.size(); i++) {
            if (nodesList.get(i) == stringNodeMap.get(isoStrings.get(3))) {
                if (nodeStringMap.get(nodesList.get((i + 1) % nodesList.size())).compareTo(nodeStringMap.get
                        (nodesList.get(Math.floorMod(i - 1, nodesList.size())))) < 0)
                    return "R";
                else return "S";
            }
        }
        return "Error: Keine Aussage Moeglich";
    }

    /**
     * Untersucht ob eine Doppelbindung chiral ist.
     *
     * @param doubleBoundWrapper Beide Atome einer Doppelbindung.
     * @return Ob die Doppelbindung chiral ist oder nicht.
     */
    public static boolean isChiralDoubleBound(DoubleBondWrapper doubleBoundWrapper) {
        StringGenerator stringGenerator = new StringGenerator();
        StereoAtom first = doubleBoundWrapper.getStereoAtomOne();
        StereoAtom second = doubleBoundWrapper.getStereoAtomTwo();

        //Anzahl Nachbarn muss jeweils 3 sein.
        if ((first.getNeighbours().size() < 3) || second.getNeighbours().size() < 3) return false;

        //Generiere die Baum-Isomorphie Strings für die Nachbarn des ersten Atoms.
        Set<String> neighboursFirst = new HashSet<>();
        first.getNeighbourList(second).forEach(node -> {
            if (node != null) neighboursFirst.add(stringGenerator.stringGenKonstiStereo(first, (StereoAtom) node));
        });

        //Atome sind nicht unterschiedlich.
        if (neighboursFirst.size() < 2) return false;

        //Generiere die Baum-Isomorphie Strings für die Nachbarn des zweiten Atoms.
        Set<String> neighboursSecond = new HashSet<>();
        second.getNeighbourList(first).forEach(node -> {
            if (node != null) neighboursSecond.add(stringGenerator.stringGenKonstiStereo(second, (StereoAtom) node));
        });
        //Atome sind nicht unterschiedlich.
        if (neighboursSecond.size() < 2) return false;

        return true;
    }

    /**
     * Prueft eine chirale Doppelbindung auf E oder Z .
     *
     * @param doubleBoundWrapper Beinhaltet die beiden Atome einer Doppelbindung.
     * @return E oder Z. Je nach Art der Bindung.
     */
    public static String cisTrans(DoubleBondWrapper doubleBoundWrapper) {
        StringGenerator stringGenerator = new StringGenerator();
        StereoAtom first = doubleBoundWrapper.getStereoAtomOne();
        StereoAtom second = doubleBoundWrapper.getStereoAtomTwo();
        ArrayList<String> firstNeighboursPrio = new ArrayList<>();
        ArrayList<String> secondNeighboursPrio = new ArrayList<>();

        //Generiert Strings mit Prioritaetsinformationen.
        first.getNeighbourList(second).forEach(node -> {
            if (node != null)
                firstNeighboursPrio.add(stringGenerator.stringGenStereoAtomicNumber(first, (StereoAtom) node));
        });
        second.getNeighbourList(first).forEach(node -> {
            if (node != null)
                secondNeighboursPrio.add(stringGenerator.stringGenStereoAtomicNumber(second, (StereoAtom) node));
        });

        //Ueberprueft ob die Nachbarn gleich geordnet sind.
        if (firstNeighboursPrio.get(0).compareTo(firstNeighboursPrio.get(1)) < 0 && secondNeighboursPrio.get(0)
                .compareTo(secondNeighboursPrio.get(1)) < 0) {
            return "Z";
        }
        if (firstNeighboursPrio.get(0).compareTo(firstNeighboursPrio.get(1)) > 0 && secondNeighboursPrio.get(0)
                .compareTo(secondNeighboursPrio.get(1)) > 0) {
            return "Z";
        }

        //Wenn sie unterschiedlich geordnet sind, ist es E spezifiziert.
        return "E";
    }
}
