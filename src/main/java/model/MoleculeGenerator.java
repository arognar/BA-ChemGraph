package model;

import model.chemGraph.*;
import model.graph.Graph;
import model.graph.Node;

import java.util.*;

public class MoleculeGenerator {
    private int numberOfAtoms = 0;
    private int maxAtoms;

    public MoleculeGenerator(){

    }

    /*public Molecule generate(int maxAtoms){
        numberOfAtoms = 0;
        this.maxAtoms = maxAtoms;
        Random ran = new Random(System.currentTimeMillis());
        Molecule molecule = new Molecule();
        Atom startAtom = new Carbon();
        molecule.addNode(startAtom);
        Atom currentAtom;
        Stack<Atom> atoms = new Stack<>();
        atoms.push(startAtom);

        numberOfAtoms++;
        while(!atoms.empty()) {
            currentAtom = atoms.pop();
            if(ran.nextInt(10) < 3) {
                currentAtom.addHydrogen();
                //System.out.println("Added H to: " + currentAtom.getId());
                if(currentAtom.isConnectable()) atoms.push(currentAtom);
            } else {
                numberOfAtoms++;
                Atom nextAtom = new Carbon();
                molecule.addNode(nextAtom);
                molecule.tryConnect(currentAtom,nextAtom);
                //System.out.println("Added C to: " + currentAtom.getId());
                atoms.push(nextAtom);
                if(currentAtom.isConnectable())atoms.push(currentAtom);

            }
            if(numberOfAtoms >= maxAtoms) break;
        }

        //Fill with H
        molecule.getNodes().forEach((s, node) -> {
            while(node.isConnectable()){
                //System.out.println("ADDED H   "+s);
                ((Atom)node).addHydrogen();
            }
        });
        return molecule;
    }*/

    public Molecule generate2(int maxAtoms){
        numberOfAtoms = 0;
        this.maxAtoms = maxAtoms;
        Random ran = new Random(System.currentTimeMillis());
        Molecule molecule = new Molecule();
        StereoAtom startAtom = new Carbon();
        molecule.addNode(startAtom);
        StereoAtom currentAtom;
        Stack<StereoAtom> atoms = new Stack<>();
        atoms.push(startAtom);

        numberOfAtoms++;
        while(!atoms.empty()) {
            currentAtom = atoms.pop();
            if(ran.nextInt(10) < 3) {
                StereoAtom nextAtom = new Hydrogen();
                molecule.addNode(nextAtom);
                molecule.tryConnect(currentAtom,nextAtom,"-");
                //System.out.println("Added H to: " + currentAtom.getId());
                if(currentAtom.isConnectable("")) atoms.push(currentAtom);
            } else {
                numberOfAtoms++;
                StereoAtom nextAtom = new Carbon();
                molecule.addNode(nextAtom);
                molecule.tryConnect(currentAtom,nextAtom,"");
                //System.out.println("Added C to: " + currentAtom.getId());
                atoms.push(nextAtom);
                if(currentAtom.isConnectable(""))atoms.push(currentAtom);

            }
            if(numberOfAtoms >= maxAtoms) break;
        }

        //Fill with H
        /*Iterator i = molecule.getNodes().values().iterator();
        i.forEachRemaining(o -> {
            Node  node = (Node) o;
            while(node.isConnectable()){
                //System.out.println("ADDED H   "+s);
                StereoAtom h = new Hydrogen();
                molecule.tryConnect(node,h);
            }
        });

        /*final Map<String, Node> nodes = molecule.getNodes();
        nodes.forEach((s, node) -> {
            while(node.isConnectable()){
                //System.out.println("ADDED H   "+s);
                StereoAtom h = new Hydrogen();
                molecule.tryConnect(node,h);
            }
        });*/
        return molecule;
    }
}
