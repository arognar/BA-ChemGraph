package model;


import model.chemGraph.ChemAlgorithm;
import model.chemGraph.Molecule;
import model.chemGraph.StereoAtom;
import model.graph.PQTree;
import model.smileParser.SmileParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Liefert die Logik des Programmes.
 * Fuegt die Datenstrukturen und Algorithmen zusammen.
 */
public class Analyzer extends java.util.Observable {
    SmileParser smileParser = new SmileParser();

    public Analyzer() {

    }

    /**
     * Startet die in dem Kapitel Anwendung vorgestellte Analyse eines Molekuels.
     * Unterstuetzt den Ablauf, durch Nachrichten an die View, auch visuell .
     *
     * @param s SMILES String.
     */
    public void analyze(String s) {
        notifyController("Starte Analyse von SMILES ");
        ArrayList<String> smiles = generateStereoIsomere(s);
        if (smiles == null) {
            return;
        }
        notifyController("Es wurden " + smiles.size() + " Stereoisomere generiert:");
        smiles.forEach(s1 -> notifyController(s1));
        ArrayList<Molecule> molecules = new ArrayList<>();
        notifyController("");
        notifyController("Parse SMILES zu Molekuelen");
        notifyController("");
        smiles.forEach(s1 -> molecules.add(smileParser.parseSmile(s1)));
        molecules.forEach(molecule -> chiralAnalysis(molecule));
        notifyController("");
        notifyController("Untersuche Isomorphie");
        notifyController("Untersuche Konstitutionsisomerie");
        Set<String> konstitutionsI = new HashSet<>();
        molecules.forEach(molecule -> {
            konstitutionsI.add(konstitutionsIsomorp(molecule));
        });
        notifyController(konstitutionsI.size() + " gefunden");
        konstitutionsI.forEach(s1 -> notifyController(s1));
        notifyController("");

        notifyController("Untersuche Konfigurationsisomerie");
        Set<String> konfigurationI = new HashSet<>();
        molecules.forEach(molecule -> {
            konfigurationI.add(konfigurationIsomorp(molecule));
        });
        notifyController(konfigurationI.size() + " gefunden");
        konfigurationI.forEach(s1 -> notifyController(s1));


    }


    /**
     * Generiert den PQ-Baum , reduziert ihn und generiert alle Stereoisomere.
     *
     * @param s SMILES String.
     * @return
     */
    private ArrayList<String> generateStereoIsomere(String s) {
        notifyController("Starte Stereoisomer-Generierung");
        notifyController("Fuer SMILES: " + s);
        notifyController("");
        notifyController("Parse Smile-String zu PQ-Baum");
        PQTree pqTree = smileParser.parseSmileToPQTree(s);
        if (pqTree == null) {
            notifyController(smileParser.getErrorMessage());
            return null;
        }
        notifyController("Durchsuche PQ-Baum nach Chiralitaetszentren");
        notifyController(pqTree.determineStereocenter() + " chirale Atome gefunden");
        notifyController("Reduziere PQ-Baum");
        notifyController("");
        pqTree.reduce();

        return pqTree.getRoot().getAllSmiles();

    }

    /**
     * Untersucht die Chiralitaet eines Molekuels.
     * Untersucht Chiralitaetszentren und chirale Doppelbindungen.
     *
     * @param m Molekuel das untersucht wird.
     */
    private void chiralAnalysis(Molecule m) {
        notifyController("Untersuche chiralitaet des Molekuels");
        notifyController("");
        m.determineChirality();
        notifyController(m.getChiralAtoms().size() + " Chiralitaetszentren:");
        m.getChiralAtoms().forEach(node -> notifyController("Drehrichtung: " + ChemAlgorithm.RSDetermination(
                (StereoAtom) node)));
        m.determineChiralityDoubleBond();
        notifyController(m.getChiralDoubleBonds().size() + " chirale Doppelbindungen:");
        m.getChiralDoubleBonds().forEach(doubleBondWrapper -> notifyController("Spezifizierung :" + ChemAlgorithm
                .cisTrans(doubleBondWrapper)));
        notifyController("");
    }

    /**
     * Startet die chirale Analyse.
     *
     * @param s SMILES String.
     */
    public void analyzeChirality(String s) {
        notifyController("Ueberpruefe die Chiralitaet in: " + s);
        Molecule m = smileParser.parseSmile(s);
        if (m == null) {
            notifyController(smileParser.getErrorMessage());
            return;
        }
        chiralAnalysis(m);

    }

    public void getKonstitutionIso(String s) {
        notifyController("String mit Konstitutions-Informationen fuer: " + s);
        Molecule m = smileParser.parseSmile(s);
        if (m == null) {
            notifyController(smileParser.getErrorMessage());
            return;
        }
        notifyController(konstitutionsIsomorp(m));
    }

    public void getKonfigurationIso(String s) {
        notifyController("String mit Konstitutions-Informationen fuer: " + s);
        Molecule m = smileParser.parseSmile(s);
        if (m == null) {
            notifyController(smileParser.getErrorMessage());
            return;
        }
        notifyController(konfigurationIsomorp(m));
    }

    public void getKonformationIso(String s) {
        notifyController("String mit Konstitutions-Informationen fuer: " + s);
        Molecule m = smileParser.parseSmile(s);
        if (m == null) {
            notifyController(smileParser.getErrorMessage());
            return;
        }
        notifyController(konformationIsomorp(m));
    }

    private String konstitutionsIsomorp(Molecule m) {
        return ChemAlgorithm.konsitutionIso(m);
    }

    private String konfigurationIsomorp(Molecule m) {
        return ChemAlgorithm.konfigurationIso(m);
    }

    private String konformationIsomorp(Molecule m) {
        return ChemAlgorithm.konformationIso(m);
    }

    private void notifyController(String s) {
        setChanged();
        notifyObservers(s);
    }
}
