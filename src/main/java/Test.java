import javafx.application.Application;
import javafx.stage.Stage;
import model.chemGraph.Molecule;
import model.smileParser.SmileParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SmileParser smileParser = new SmileParser();
        Molecule molecule = smileParser.parseSmile("C(C(CCH)(C))(C)");
        molecule.printMolecule();

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
}
