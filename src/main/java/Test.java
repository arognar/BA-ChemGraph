import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.GraphUtil;
import model.SmileGenerator;
import model.chemGraph.Molecule;
import model.graph.AbstractPQNode;
import model.graph.PNode;
import model.smileParser.SmileParser;

import java.lang.management.GarbageCollectorMXBean;
import java.util.*;

import java.util.function.Consumer;

public class Test extends Application {

    private final long MEGABYTE = 1024L*1024L;

    public long bytesToMegabyte(long bytes){
        return bytes/MEGABYTE;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {


        parseSmilePQTreeTest();
        Platform.exit();

    }

    public void test1(){
        SmileParser smileParser = new SmileParser();
        Molecule molecule = smileParser.parseSmile("C(C(CCH)(C))(C)");
        //molecule.printMolecule();

        SmileGenerator smileGenerator = new SmileGenerator();
        long cur = System.currentTimeMillis();
        //ArrayList<String> s = smileGenerator.allPermutation("C(C(C))(C)(C)(H)");
        //ArrayList<String> s = smileGenerator.allPermutation("C(C(C(H)(H)(H))(H)(H))(C(H)(H)(H))(H)(C(C(H)(H)(H))(C(H)(H)(H))(H))");
        ArrayList<String> s = smileGenerator.allPermutation("C(Br)(F)(H)(C(H)(H)(H))");
        System.out.println("C(Br)(F)(H)(C(H)(H)(H))");
        //s.forEach(s1 -> System.out.println(s1));
        System.out.println("LIST SIZE "+ s.size());
        //s.forEach(s1 -> System.out.println(s1));
        Set<String> discString = new HashSet<>();
        s.forEach(s1 -> discString.add(s1));
        System.out.println(" SET SIZE "+discString.size());
        discString.forEach(s1 -> System.out.println(s1));
        ArrayList<Molecule> molecules = new ArrayList<>();
        discString.forEach(s1 -> molecules.add(smileParser.parseSmile(s1)));
        System.out.println("-----------------------------------");
        Set<String> stereoDisc = new HashSet<>();
        molecules.forEach(molecule1 -> {
            stereoDisc.add(molecule1.test2());
        });
        stereoDisc.forEach(s1 -> System.out.println(s1));
        long used = System.currentTimeMillis() - cur;
        System.out.println("time: "+ used);

        Runtime runtime = Runtime.getRuntime();
        long memory = runtime.freeMemory();

        System.out.println("Used memory: "+bytesToMegabyte(runtime.totalMemory()-memory));
        System.out.println("Total memory "+bytesToMegabyte(runtime.totalMemory()));
        System.out.println("Free memory: "+bytesToMegabyte(runtime.freeMemory()));;
    }

    public void printNodeTest(){
        PNode p = new PNode("a");
        PNode p2 = new PNode("a2");
        PNode p3 = new PNode("a3");
        PNode p4 = new PNode("a4");
        PNode p5 = new PNode("a5");
        PNode p6 = new PNode("a6");
        PNode p7 = new PNode("a7");
        p.addChild(p2);
        p.addChild(p3);
        p2.addChild(p4);
        p3.addChild(p5);
        p5.addChild(p6);
        p6.addChild(p7);
        GraphUtil.print(p);

    }

    public void parseSmilePQTreeTest(){
        SmileParser smileParser = new SmileParser();
        AbstractPQNode root = smileParser.parseSmileToPQTree("C(C(CCH)(C))(C)");
        GraphUtil.print(root);
    }

    public ArrayList<String> permutate(ArrayList<ArrayList<String>> lists){
        ArrayList<String> permutatedList = new ArrayList<>();
        int newListIndex = 0;

        for(int i = 0; i < lists.size();i++){
            for(int j = 0; j < lists.get(i).size();j++){
                String s = lists.get(i).get(j);

                for(int k = j+1;k < lists.size()+j;k++){
                    for(int h = 0; h < lists.get(k%lists.size()).size();h++){
                        System.out.println(lists.get(k%lists.size()).get(h));
                        s+=lists.get(k%lists.size()).get(h);
                    }
                }
                permutatedList.add(s);
                newListIndex++;
            }
        }

        return permutatedList;
    }

    public void perm(ArrayList<ArrayList<String>> lists, final Consumer<List<String>> consumer){
        final int[] index_pos = new int[lists.size()];
        final int last_index = lists.size() -1;
        //final Set<String>
        final List<String> permuted = new ArrayList<>(lists.size());

        for(int i = 0; i < lists.size();i++){
            permuted.add(null);
        }

        while (index_pos[last_index] < lists.get(last_index).size()){
            for(int i = 0 ; i < lists.size(); i++){
                permuted.set(i,lists.get(i).get(index_pos[i]));
            }
            consumer.accept(permuted);
        }
        for(int i = 0; i < lists.size();i++){
            ++index_pos[i];
            if(index_pos[i] < lists.get(i).size()){
                break;
            } else if(i < last_index){
                index_pos[0] = 0;
            }
        }
    }

    public String getCircularMaxString(ArrayList<String> strings){
        Set<String> candidates = new HashSet<>();
        for (int i = 0; i < strings.size() ; i++) {
            String candidat ="";
            for (int j = 0; j < strings.size(); j++) {
                candidat+=strings.get((j+i)%strings.size());
            }
            candidates.add(candidat);
        }
        return Collections.max(candidates);
    }

}
