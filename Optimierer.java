import basis.*;
import java.util.*;


public class Optimierer {

    private Geher geher;

    private String lowest;
    public BeschriftungsFeld bestLabel;
    public Map<Integer, Map.Entry<Integer, Integer>> leftWay = new HashMap<>();
    public Map<Integer, Map.Entry<Integer, Integer>> rightWay = new HashMap<>();
    public Map<Integer, Map.Entry<Integer, Integer>> fLeftWay = new HashMap<>();
    public Map<Integer, Map.Entry<Integer, Integer>> fRightWay = new HashMap<>();



    public Optimierer() {

        bestLabel = new BeschriftungsFeld("Starten um schnellsten Geher zu finden!",190,130,320,30);
        bestLabel.setzeSchriftGroesse(12);
    }


    //compares the 4 methods and finds out which is the best
    public void best(int lmax, int rmax, int flmax, int frmax) {

        System.out.println("best startet");
        int low;
        boolean equal = false; //to know when all the methods are equal to each other

        if(lmax < rmax) {
            low = lmax;
            lowest = "Linksgeher";
        }
        //when all the methods are equal to each other
        else if(lmax == rmax) {
            low = lmax;
            equal = true;
        }
        else {
            low = rmax;
            lowest = "Rechtsgeher";
        }
        if(flmax < low) {
            low = flmax;
            lowest = "G-R-geher";
        }
        if(frmax < low) {
            low = frmax;
            lowest = "G-L-geher";
        }

        if (!equal) bestLabel.setzeText(lowest + " war mit " + low + " Schritten am schnellsten!");
        else bestLabel.setzeText("Alle Geher waren gleich schnell!");
    }


    //optimizes the best method
    private void whoIsTheBest(){
        if (lowest == "Linksgeher");{
            lOptimize();
        }
        if (lowest == "rechtsgeher"){
            rOptimize();
        }
        if (lowest == "G-L-geher"){
            fLOptimize();
        }
        if (lowest == "G-R-geher"){
            fROtimize();
        }
    }


    public void lCompare(int key1, int x2, int y2) {

        int key2 = 0;
        for (Map.Entry<Integer, Map.Entry<Integer, Integer>> map : leftWay.entrySet()) {

            if (map.getValue().getKey() == x2 && map.getValue().getValue() == y2){

                key2 = map.getKey();
            }
        }

        if (key1 < key2) {

            //changes coords to green
        }
    }

    public void lOptimize() {

        //NOTHING
    }


    private void rOptimize() {

        //NOTHING
    }


    private void fLOptimize() {

        //NOTHING
    }


    private void fROtimize() {

        //NOTHING
    }

}
