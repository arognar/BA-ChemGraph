package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SmileGenerator {

    public ArrayList<String> allPermutation(String smile){
        String start ="";
        String end = "";
        int bracketFlag=0;
        ArrayList<String> token = new ArrayList<>();
        ArrayList<String> results = new ArrayList<>();
        char smiles[] = smile.toCharArray();
        if(smiles[0]=='('){
            start+="(";
            end+=")";
            smiles= smile.substring(1,smile.length()-1).toCharArray();
            bracketFlag = 1;
        }
        for (int i = 0; i < smiles.length; i++) {
            if (smiles[i] != '(') {
                start += smiles[i];
            } else {
                token = getToken(smile.substring(i, smile.length() - bracketFlag));
                break;
            }
        }
        ArrayList<ArrayList<String>> allStrings = new ArrayList<>();

        if(!token.isEmpty()) {
            token.forEach(s -> {
                allStrings.add(allPermutation(s));
            });
            Set<String> permutations = new HashSet<>();
            getPermutations(0, "", allStrings, permutations);
            for (int i = 0; i < permutations.size(); i++) {
                final String finaleStart = start;
                final String finalEnd = end;
                permutations.forEach(s -> {
                    results.add(finaleStart + s + finalEnd);
                });
            }
        } else {
            results.add(start+end);
        }
        return results;
    }

    private ArrayList<String> getToken(String s){
        char[] sArr = s.toCharArray();
        int level = 0;
        int startOfToken = 0;
        ArrayList<String> token = new ArrayList<>();
        for (int i = 0; i < sArr.length; i++) {
            if(sArr[i] == '(') {
                if(level == 0){
                    startOfToken = i;
                }
                level++;
            }
            if(sArr[i] == ')') {
                level--;
                if(level == 0){
                    token.add(s.substring(startOfToken,i+1));
                }
            }
        }
        return token;
    }

    public void getPermutations(int d,String s,ArrayList<ArrayList<String>> strings,Set<String> results){
        if(d == strings.size()){
            String str[] = s.substring(0,s.length()-1).split(",");
            permutations(str,0,str.length,results);
            return;
        }
        for (int i = 0; i < strings.get(d).size(); i++) {
            getPermutations(d+1,s+strings.get(d).get(i)+",",strings,results);
        }
    }

    private void permutations(String[] strings,int startIndex,int endIndex,Set<String> results){
        if(startIndex == endIndex){
            String s = "";
            for (int i = 0; i < strings.length; i++) {
                s+=strings[i];
            }
            results.add(s);
        } else {
            for (int j = startIndex; j < endIndex; j++) {
                swap(strings,startIndex,j);
                permutations(strings,startIndex+1,endIndex,results);
                swap(strings,startIndex,j);
            }
        }
    }

    private void swap(String[] strings,int i,int x){
        String s = strings[i];
        strings[i] = strings[x];
        strings[x] = s;
    }

}
