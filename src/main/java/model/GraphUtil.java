package model;

import model.graph.IPrintable;

public class GraphUtil {

    public static int[][] boundaryAr = {{-1,0,0,0},{2,-1,2,1},{1,1,-1,2},{0,2,1,-1}};

    public static void print(IPrintable s){
        s.print("",false);
    }
}
