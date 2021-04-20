import basis.*;
import java.awt.*;
import java.util.*;


public class Optimierer {

    private Bild bild;
    private Stift stift;
    int x;
    int y;
    public BeschriftungsFeld bestLabel;


    public Optimierer() {

        bestLabel = new BeschriftungsFeld("Starten um schnellsten Geher zu finden!",190,130,320,30);
        bestLabel.setzeSchriftGroesse(12);
    }

    public void best(int leftmax, int rightmax, int fLeftMax, int fRightMax) {
        //compares the 4 methods and finds out which is the best
        String lowest = "leftmax";
        int low = leftmax;
        boolean equal = false; //if all methods have the same number of steps

        if(leftmax < rightmax) {
            low = leftmax;
            lowest = "Linksgeher";
        }
        else if (leftmax > rightmax) {
            low = rightmax;
            lowest = "Rechtsgeher";
        }
        if(fLeftMax < low) {
            low = fLeftMax;
            lowest = "G-R-geher";
        }
        else if(fRightMax < low) {
            low = fRightMax;
            lowest = "G-L-geher";
        }

        if (!equal) bestLabel.setzeText(lowest + " war mit " + low + " Schritten am schnellsten!");
        else bestLabel.setzeText("Alle Geher waren gleich schnell!");
    }

    private int getStep(int x, int y){
        int key = 0;
        for (Map.Entry<Integer, Map.Entry<Integer, Integer>> map : Labgeher.instance.way.entrySet()) {

            if (map.getValue().getKey() == x && map.getValue().getValue() == y){

                key = map.getKey();
            }
        }
        return key;
    }

    private int keyCompare(int key1, int x2, int y2){
        int key2 = getStep(x2, y2);

        if (key2 > key1) {;
            return key2;
        }
        return key1;
    }


    private void move(int x1, int y1, int x2, int y2){

        if (x2 > x1) {
            stift.bewegeAuf(x1 + 10, y1);
            x = x1 + 10;
        }
        if (x2 < x1) {
            stift.bewegeAuf(x1 - 10, y1);
            x = x1 - 10;
        }
        if (y2 > y1) {
            stift.bewegeAuf(x1, y1 + 10);
            y = y1 + 10;
        }
        if (y2 < y1) {
            stift.bewegeAuf(x1, y1 - 10);
            y = y1 - 10;
        }

        stift.runter();
        stift.zeichneKreis(4);
        stift.hoch();
        Hilfe.warte(10);

        Labgeher.instance.counterBest++;


    }


    private int checkX(int xPos, int yPos){
        int best = 0;

        for (int j = xPos; j < 300; j+= 10){

            if (bild.farbeVon(j, yPos).equals(Color.BLACK)) break;
            if (bild.farbeVon(j, yPos).equals(Color.GREEN))  best = keyCompare(best, j, yPos);
        }
        for (int j = xPos; j > 0; j-= 10){

            if (bild.farbeVon(j, yPos).equals(Color.BLACK)) break;
            if (bild.farbeVon(j, yPos).equals(Color.GREEN))  best = keyCompare(best, j, yPos);
        }
        return best;
    }

    private int checkY(int xPos, int yPos){
        int best = 0;

        for (int j = yPos; j < 300; j+= 10){

            if (bild.farbeVon(xPos, j).equals(Color.BLACK)) break;
            if (bild.farbeVon(xPos, j).equals(Color.GREEN))  best = keyCompare(best, xPos, j);
        }
        for (int j = yPos; j > 0; j-= 10){

            if (bild.farbeVon(xPos, j).equals(Color.BLACK)) break;
            if (bild.farbeVon(xPos, j).equals(Color.GREEN))  best = keyCompare(best, xPos, j);
        }
        return best;
    }


    public void optimize(Stift stift, Bild bild) {
        x = 65;
        y = 245;
        this.stift = stift;
        this.bild = bild;
        this.stift.maleAuf(this.bild);
        this.stift.bewegeAuf(65, 245);
        this.stift.dreheBis(90);
        this.stift.setzeFarbe(Color.PINK);
        this.stift.setzeFuellMuster(1);
        this.stift.hoch();

        while (!checkLastStep(x, y)) {

            int bestX = checkX(x, y);
            int bestY = checkY(x, y);

            if (bestX > bestY) move(x, y, Labgeher.instance.way.get(bestX).getKey(), Labgeher.instance.way.get(bestX).getValue());
            else move(x, y, Labgeher.instance.way.get(bestY).getKey(), Labgeher.instance.way.get(bestY).getValue());
        }
    }


    private boolean checkLastStep(int x, int y){
        System.out.println(getStep(x, y));
        System.out.println(Labgeher.instance.stepsMax);
        return getStep(x, y) == Labgeher.instance.stepsMax;
    }

}
