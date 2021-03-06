package model;

import model.chemGraph.StereoAtom;
import model.graph.Node;

import java.util.*;

/**
 * Generiert Zeichenketten, um eine Aussage ueber die Isomorphie von chemischen Graphen und Baeume treffen zu koennen.
 */
public class StringGenerator {
    /**
     * Menge an bereits besuchten Knoten. Wird benutzt, falls keine eindeutige Richtung wie durch die Stereoatome
     * festgelegt ist.
     * Mehrmaliges besuchen von Knoten wird verhindert.
     */
    private Set<String> visitedNodes = new HashSet<String>();


    public StringGenerator() {
    }

    /**
     * Generiert eindeutigen Isomorphie-String fuer den Chiralitaets-Check.
     * Fuer Knoten ohne Stereoinformationen.
     *
     * @param candidate Kandidat fuer die Chiralitaet.
     * @param root      Wurzel des Baumes fuer den man den String generiert.
     * @return IsomorphieString.
     */
    public String getStringNonStereo(Node candidate, Node root) {
        reset();
        visitedNodes.add(candidate.getId());
        return generateStringNonStereo(root);
    }

    /**
     * Berechnet den String fuer die KonfigurationsIsomorphie von einem Atom.
     *
     * @param node Atom das betrachet wird.
     * @return eindeutige Konfigurations-Isomorphie-Zeichenkette.
     */
    public String getKonfiString(StereoAtom node) {
        ArrayList<String> childStrings = new ArrayList<>();
        String[] isoString = {node.getLabel()};

        //Berechne fuer jeden Nachbarn den Baum-Isomorphismus mit Konfigurations-Informationen.
        node.getNeighbours().forEach(node1 -> {
            childStrings.add(stringGenKonfiStereo(node, (StereoAtom) node1));
        });

        //Fuegt Informationen in geordneter Reihung zusammen.
        Collections.sort(childStrings, String.CASE_INSENSITIVE_ORDER);
        childStrings.forEach(s -> {
            isoString[0] = new StringBuilder().append(isoString[0]).append("(").append((s)).append(")").toString();
        });

        return isoString[0];

    }

    /**
     * Berechnet den String fuer die Konstitutions-Isomorphie von einem Atom.
     *
     * @param node Atom das betrachet wird.
     * @return eindeutige Konstitutions-Isomorphie-Zeichenkette.
     */
    public String getKonstiString(StereoAtom node) {
        ArrayList<String> childStrings = new ArrayList<>();
        String[] isoString = {node.getLabel()};

        //Berechne fuer jeden Nachbarn den Baum-Isomorphismus mit Konstitutions-Informationen.
        node.getNeighbours().forEach(node1 -> {
            childStrings.add(stringGenKonstiStereo(node, (StereoAtom) node1));
        });

        //Fuegt Informationen in geordneter Reihung zusammen.
        Collections.sort(childStrings, String.CASE_INSENSITIVE_ORDER);
        childStrings.forEach(s -> {
            isoString[0] = new StringBuilder().append(isoString[0]).append("(").append((s)).append(")").toString();
        });

        return isoString[0];

    }

    /**
     * Berechnet den String fuer die Konformations-Isomorphie von einem Atom.
     *
     * @param node Atom das betrachet wird.
     * @return eindeutige Konstitutions-Isomorphie-Zeichenkette.
     */
    public String getKonformString(StereoAtom node) {
        ArrayList<String> childStrings = new ArrayList<>();
        String[] isoString = {node.getLabel()};

        //Berechne fuer jeden Nachbarn den Baum-Isomorphismus mit Konstitutions-Informationen.
        node.getNeighbours().forEach(node1 -> {
            childStrings.add(stringGenKonformStereo(node, (StereoAtom) node1));
        });

        //Fuege Informationen in geordneter Reihung zusammen.
        Collections.sort(childStrings, String.CASE_INSENSITIVE_ORDER);
        childStrings.forEach(s -> {
            isoString[0] = new StringBuilder().append(isoString[0]).append("(").append((s)).append(")").toString();
        });

        return isoString[0];

    }

    /**
     * Setzt die besuchten Knoten zurueck.
     */
    private void reset() {
        visitedNodes = new HashSet<>();
    }


    /**
     * Berechnet den String fuer den Baum-Isomorphismus ohne Stereoinformationen.
     *
     * @param n Wurzelknoten.
     * @return Eindeutige Zeichenkette fuer einen markierten Baum ohne Ordnung.
     */
    private String generateStringNonStereo(Node n) {
        visitedNodes.add(n.getId());
        final String[] isoString = {n.getLabel()};
        List<String> childStrings = new ArrayList<>();

        n.getNeighbours().forEach(node -> {
            if (visitedNodes.add(node.getId())) {
                childStrings.add(n.getBoundingType(node) + generateStringNonStereo(node));
            }
        });

        //Knoten hat keine Kinder.
        if (childStrings.isEmpty()) {
            return n.getLabel();
        } else {
            //Strings der Kinder werden geordnet zusammengefuegt.
            Collections.sort(childStrings, String.CASE_INSENSITIVE_ORDER);
            childStrings.forEach(node -> {
                isoString[0] = new StringBuilder().append(isoString[0]).append("(").append((node)).append(")")
                        .toString();
            });
            return isoString[0];
        }
    }

    /**
     * Generiert den eindeutigen Baum-Isomorphie-String mit Konstitutions-Informationen.
     *
     * @param from Atom von dem man kommt.
     * @param to   Atom auf das man blickt.
     * @return Eindeutige Zeichenkette fuer die Isomorphie von Baeumen mit Konstitutions-Informationen.
     */
    public String stringGenKonstiStereo(StereoAtom from, StereoAtom to) {
        final String[] isoString = {to.getLabel()};
        List<String> childStrings = new ArrayList<>();

        //Generiert die Strings der Kindatome.
        //Null, falls leere Stellen bei Doppelbindungen in der Liste.
        to.getNeighbourList(from).forEach(node -> {
            if (node != null) childStrings.add(to.getBoundingType(node) + stringGenKonstiStereo(to, (StereoAtom) node));
        });

        //hat keine Kinder.
        if (childStrings.isEmpty()) {
            return to.getLabel();
        } else {

            //Generierten Strings der Kinder in Reihung bringen und zusammenfuegen.
            Collections.sort(childStrings, String.CASE_INSENSITIVE_ORDER);
            childStrings.forEach(node -> {
                isoString[0] = new StringBuilder().append(isoString[0]).append("(").append((node)).append(")")
                        .toString();
            });
            return isoString[0];
        }
    }

    /**
     * Generiert den eindeutigen Baum-Isomorphie-String mit Konformations-Informationen.
     *
     * @param from Atom von dem man kommt.
     * @param to   Atom auf das man blickt.
     * @return Eindeutige Zeichenkette fuer die Isomorphie von Baeumen mit Konformations-Informationen.
     */
    public String stringGenKonformStereo(StereoAtom from, StereoAtom to) {
        final String[] isoString = {to.getLabel()};
        ArrayList<String> childStrings = new ArrayList<>();

        //Generiert die Strings der Kindatome.
        //Null, falls leere Stellen bei Doppelbindungen in der Liste.
        to.getNeighbourList(from).forEach(node -> {
            if (node != null) childStrings.add(to.getBoundingType(node) + stringGenKonstiStereo(to, (StereoAtom) node));
        });

        //Hat keine Kinder.
        if (childStrings.isEmpty()) {
            return to.getLabel();
        } else {

            //Generierten Strings der Kinder in Reihung bringen und zusammenfuegen.
            isoString[0] = new StringBuilder().append(isoString[0]).append(getSimpleList(childStrings)).toString();
            return isoString[0];
        }
    }

    /**
     * Generiert den String, um die Prioritaeten fuer CIP zu bestimmen.
     * Nutzung der Ordnungzahl statt Atomsymbol.
     *
     * @param from Atom von dem man kommt.
     * @param to   Atom auf das man blickt.
     * @return Eindeutige Zeichenkette mit Prioritaetsinformationen.
     */
    public String stringGenStereoAtomicNumber(StereoAtom from, StereoAtom to) {
        //Ordnungzahl wird zu ASCII transformiert und in einen verwertbaren Bereich geschoben.
        char numbertoChar = (char) (to.getAtomicNumber() + 33);
        String label = "" + numbertoChar;
        final String[] isoString = {"" + label};
        List<String> childStrings = new ArrayList<>();

        to.getNeighbourList(from).forEach(node -> {
            if (node != null) childStrings.add(stringGenStereoAtomicNumber(to, (StereoAtom) node));
        });

        //Hat keine Kinder.
        if (childStrings.isEmpty()) {
            return label;
        } else {
            //Generierte Strings der Kinder werden sortiert und zusammengefuegt.
            Collections.sort(childStrings, String.CASE_INSENSITIVE_ORDER);
            childStrings.forEach(node -> {
                isoString[0] = new StringBuilder().append(isoString[0]).append("(").append((node)).append(")")
                        .toString();

            });
            return isoString[0];
        }
    }

    /**
     * Generiert den eindeutigen Baum-Isomorphie-String mit Konfigurations-Informationen.
     *
     * @param from Atom von dem man kommt.
     * @param to   Atom auf das man blickt.
     * @return Eindeutige Zeichenkette fuer die Isomorphie von Baeumen mit Konfigurations-Informationen.
     */
    public String stringGenKonfiStereo(StereoAtom from, StereoAtom to) {
        final String[] isoString = {to.getLabel()};
        ArrayList<String> childStrings = new ArrayList<>();

        //Generiert die eindeutigen Strings der Kinder.
        to.getNeighbourList(from).forEach(node -> {
            if (node != null) childStrings.add(to.getBoundingType(node) + stringGenKonfiStereo(to, (StereoAtom) node));
        });


        //Hat keine Kinder.
        if (childStrings.isEmpty()) {
            return to.getLabel();
        } else {
            //Falls Doppelbindung wird die Ordnung ihrer Liste beibehalten.
            //Falls Einfachbindung wird die groesste Zeichenkette in der zirkularen Liste generiert.
            if (from.getBoundingType(to).equals("=")) {
                isoString[0] = new StringBuilder().append(isoString[0]).append(getSimpleList(childStrings)).toString();
            } else {
                isoString[0] = new StringBuilder().append(isoString[0]).append(getCircularMaxString(childStrings))
                        .toString();
            }
            return isoString[0];
        }
    }


    /**
     * Generiert die groesste Zeichenkette der Elemente in einer zirkulare Liste.
     *
     * @param strings Liste der Strings.
     * @return groesst moegliche Zeichenkette in der zirkularen Liste.
     */
    private String getCircularMaxString(ArrayList<String> strings) {
        Set<String> candidates = new HashSet<>();

        //Geht alle Moeglichkeiten durch und fuegt sie den Kandidaten hinzu.
        for (int i = 0; i < strings.size(); i++) {
            String candidat = "";
            for (int j = 0; j < strings.size(); j++) {
                candidat += "(" + strings.get((j + i) % strings.size()) + ")";
            }
            candidates.add(candidat);
        }

        //groesste Zeichenkette der Kandidaten.
        return Collections.max(candidates);
    }

    /**
     * Fuegt die Strings, ihrer Ordnung in der Liste nach, zusammen.
     *
     * @param strings Liste der Strings.
     * @return Zusammengefuegter Strings.
     */
    private String getSimpleList(ArrayList<String> strings) {
        String candidat = "";
        for (int i = 0; i < strings.size(); i++) {
            candidat += "(" + strings.get(i) + ")";
        }
        return candidat;
    }

}
