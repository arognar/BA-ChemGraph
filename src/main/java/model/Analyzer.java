package model;


import model.chemGraph.ChemAlgorithm;
import model.chemGraph.Molecule;
import model.chemGraph.StereoAtom;
import model.graph.PQTree;
import model.smileParser.SmileParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Analyzer extends java.util.Observable {
    StringGenerator stringGenerator;
    SmileParser smileParser = new SmileParser();
    public Analyzer(){

    }

    public void analyze(String s){
        notifyController("Starte Analyse von SMILES ");
        ArrayList<String> smiles = generateStereoIsomere(s);
        if(smiles==null) {
            return;
        }
        notifyController("Es wurden "+smiles.size()+" Stereoisomere generiert:");
        smiles.forEach(s1 -> notifyController(s1));
        ArrayList<Molecule> molecules = new ArrayList<>();
        notifyController("");
        notifyController("Parse SMILES zu Molekülen");
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
        notifyController(konstitutionsI.size()+" gefunden");
        konstitutionsI.forEach(s1 -> notifyController(s1));
        notifyController("");

        notifyController("Untersuche Konfigurationsisomerie");
        Set<String> konfigurationI = new HashSet<>();
        molecules.forEach(molecule -> {
            konfigurationI.add(konfigurationIsomorp(molecule));
        });
        notifyController(konfigurationI.size()+" gefunden");
        konfigurationI.forEach(s1 -> notifyController(s1));




    }

    public ArrayList<String> generateStereoIsomere(String s){
        notifyController("Starte Stereoisomer-Generierung");
        notifyController("Für SMILES: "+s);
        notifyController("");
        notifyController("Parse Smile-String zu PQ-Baum");
        PQTree pqTree = smileParser.parseSmileToPQTree(s);
        if(pqTree==null) {
            notifyController(smileParser.getErrorMessage());
            return null;
        }
        notifyController("Durchsuche PQ-Baum nach Chiralitätszentren");
        notifyController(pqTree.determineStereocenter()+" chirale Atome gefunden");
        notifyController("Reduziere PQ-Baum");
        notifyController("");
        pqTree.reduce();

        return pqTree.getRoot().getAllSmiles();

    }

    private void chiralAnalysis(Molecule m){
        notifyController("Untersuche chiralität des Moleküls");
        notifyController("");
        m.determineChirality();
        notifyController(m.getChiralAtoms().size()+" chirale Atome:");
        m.getChiralAtoms().forEach(node -> notifyController("Drehrichtung: "+ ChemAlgorithm.RSDetermination((StereoAtom) node)));
        m.determineChiralityDoubleBound();
        notifyController(m.getChiralDoubleBonds().size()+" chirale Doppelbindungen:");
        m.getChiralDoubleBonds().forEach(doubleBondWrapper -> notifyController("Spezifizierung :"+ ChemAlgorithm.cisTrans(doubleBondWrapper)));
        notifyController("");
    }

    public void analyzeChirality(String s){
        notifyController("Überprüfe die Chiralität in: "+s);
        Molecule m = smileParser.parseSmile(s);
        if(m==null) {
            notifyController(smileParser.getErrorMessage());
            return;
        }
        chiralAnalysis(m);

    }

    private String konstitutionsIsomorp(Molecule m){
        return ChemAlgorithm.konsitutionIso(m);
    }

    private String konfigurationIsomorp(Molecule m){
        return ChemAlgorithm.konfigurationIso(m);
    }

    private void notifyController(String s){
        setChanged();
        notifyObservers(s);
    }
}
