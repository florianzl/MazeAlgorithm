import basis.*;

import java.awt.*;
import java.util.*;


public class Optimierer {

    private Bild bild, bildorg, bild1;
    private Stift stift, stift1;
    private int coordsCounter;
    private int[] coords = new int[6];
    public BeschriftungsFeld bestLabel;
    public Map<Integer, Map.Entry<Integer, Integer>> leftWay = new HashMap<>();
    public Map<Integer, Map.Entry<Integer, Integer>> rightWay = new HashMap<>();
    public Map<Integer, Map.Entry<Integer, Integer>> fLeftWay = new HashMap<>();
    public Map<Integer, Map.Entry<Integer, Integer>> fRightWay = new HashMap<>();


    public Optimierer() {
        coordsCounter = 0;
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


    private int keyCompare(int key1, int x2, int y2){
        System.out.println("in KeyCompare");
        int key2 = 0;
        System.out.println("x2 = " + x2 + " y2 = " + y2);
        for (Map.Entry<Integer, Map.Entry<Integer, Integer>> map : leftWay.entrySet()) {

            if (map.getValue().getKey() == x2 && map.getValue().getValue() == y2){

                key2 = map.getKey();
                System.out.println("key2 = " + key2);
            }
        }
        if (key1 < key2) return key2;
        return key1;
    }

    /*
    private void move(int x1, int x2, int y1, int y2){
        int tempX = 0;
        int tempY = 0;

        if (x1 < x2) tempX = x2 - x1;
        else tempX = x1 - x2;
        if (y1 < y2) tempY = y2 - y1;
        else tempY = y1 - y2;
        int distance = tempX + tempY;
        System.out.println(distance);

        for (int i = 0; i < distance / 10; i++){
            stift1.dreheInRichtung(x2, y2);
            stift1.setzeFarbe(Color.PINK);
            stift1.hoch();
            stift1.bewegeUm(10);
            stift1.runter();
            stift1.setzeFuellMuster(1);
            stift1.zeichneKreis(4);
            stift1.hoch();
            Hilfe.warte(10);
        }
    }
    */

    private int right(int i) {
        int xPos = leftWay.get(i).getKey() + 10;
        int yPos = leftWay.get(i).getValue();
        System.out.println("xPos = " + xPos);

        while (!bild1.farbeVon(xPos, yPos).equals(Color.BLACK)){

            if (bild1.farbeVon(xPos, yPos).equals(Color.PINK)){
                System.out.println("GOT PINK! right");
                return keyCompare(i, xPos, yPos);
            }
            xPos = xPos + 10;
        }
        return i;
    }

    private int left(int i) {
        int xPos = leftWay.get(i).getKey();
        int yPos = leftWay.get(i).getValue();

        while (!bild1.farbeVon(xPos, yPos).equals(Color.BLACK)){
            if (bild1.farbeVon(xPos, yPos).equals(Color.PINK)){
                return keyCompare(i, xPos, yPos);
            }
            else{
                xPos = xPos - 10;
            }
        }
        return i;
    }

    private int up(int i) {
        int xPos = leftWay.get(i).getKey();
        int yPos = leftWay.get(i).getValue();

        while (!bild1.farbeVon(xPos, yPos).equals(Color.BLACK)){
            if (bild1.farbeVon(xPos, yPos).equals(Color.PINK)){
                return keyCompare(i, xPos, yPos);
            }
            else{
                yPos = yPos - 10;
            }
        }
        return i;
    }

    private int down(int i) {
        int xPos = leftWay.get(i).getKey();
        int yPos = leftWay.get(i).getValue();

        while (!bild1.farbeVon(xPos, yPos).equals(Color.BLACK)){
            if (bild1.farbeVon(xPos, yPos).equals(Color.PINK)){
                return keyCompare(i, xPos, yPos);
            }
            else{
                yPos = yPos + 10;
            }
        }
        return i;
    }

    public void leftOptimize(Stift stift, Bild bild) {
        String richtung = "";
        int i = 1;
        stift1 = stift;
        bild1 = bild;
        stift1.maleAuf(bild1);
        stift1.bewegeAuf(65, 245);
        stift1.dreheBis(90);
        stift1.setzeFarbe(Color.PINK);
        stift1.setzeFuellMuster(1);
        stift1.hoch();

        while (i <= leftWay.size()) {

            if(i < leftWay.size()) {
                if (leftWay.get(i + 1).getKey() > leftWay.get(i).getKey()) richtung = "rechts";
                else if (leftWay.get(i + 1).getKey() < leftWay.get(i).getKey()) richtung = "links";
                else if (leftWay.get(i + 1).getValue() < leftWay.get(i).getValue()) richtung = "oben";
                else if (leftWay.get(i + 1).getValue() > leftWay.get(i).getValue()) richtung = "unten";
            }

            System.out.println(richtung);
            System.out.println("i = " + i);
            if (richtung == "rechts"){
                if (left(i) > right(i) && left(i) > up(i) && left(i) > down(i)){
                    stift1.bewegeAuf(leftWay.get(left(i)).getKey(), leftWay.get(left(i)).getValue());
                }
                else if (up(i) > right(i) && up(i) > left(i) && left(i) > down(i)){
                    stift1.bewegeAuf(leftWay.get(up(i)).getKey(), leftWay.get(up(i)).getValue());
                }
                else if (down(i) > right(i) && left(i) > up(i) && left(i) > left(i)){
                    stift1.bewegeAuf(leftWay.get(down(i)).getKey(), leftWay.get(down(i)).getValue());
                }
                else stift1.bewegeAuf(leftWay.get(i).getKey(), leftWay.get(i).getValue());
            }
            if (richtung == "links"){

                if(right(i) > left(i) && right(i) > up(i) && right(i) > down(i)){
                    stift1.bewegeAuf(leftWay.get(right(i)).getKey(), leftWay.get(right(i)).getValue());
                }
                else if (up(i) > right(i) && up(i) > left(i) && left(i) > down(i)){
                    stift1.bewegeAuf(leftWay.get(up(i)).getKey(), leftWay.get(up(i)).getValue());
                }
                else if (down(i) > right(i) && left(i) > up(i) && left(i) > left(i)){
                    stift1.bewegeAuf(leftWay.get(down(i)).getKey(), leftWay.get(down(i)).getValue());
                }
                else stift1.bewegeAuf(leftWay.get(i).getKey(), leftWay.get(i).getValue());
            }
            if (richtung == "oben"){
                if(right(i) > left(i) && right(i) > up(i) && right(i) > down(i)){
                    System.out.println("right ist am größten");
                    stift1.bewegeAuf(leftWay.get(right(i)).getKey(), leftWay.get(right(i)).getValue());
                }
                else if (left(i) > right(i) && left(i) > up(i) && left(i) > down(i)){
                    System.out.println("left ist am größten");
                    stift1.bewegeAuf(leftWay.get(left(i)).getKey(), leftWay.get(left(i)).getValue());
                }
                else if (down(i) > right(i) && left(i) > up(i) && left(i) > left(i)){
                    System.out.println("down ist am größten");
                    stift1.bewegeAuf(leftWay.get(down(i)).getKey(), leftWay.get(down(i)).getValue());
                }
                else stift1.bewegeAuf(leftWay.get(i).getKey(), leftWay.get(i).getValue());
                System.out.println("right = " + right(i));
                System.out.println("left = " + left(i));
                System.out.println("up = " + up(i));
                System.out.println("down = " + down(i));

            }
            if (richtung == "unten"){

                if(right(i) > left(i) && right(i) > up(i) && right(i) > down(i)){
                    stift1.bewegeAuf(leftWay.get(right(i)).getKey(), leftWay.get(right(i)).getValue());
                }
                else if (left(i) > right(i) && left(i) > up(i) && left(i) > down(i)){
                    stift1.bewegeAuf(leftWay.get(left(i)).getKey(), leftWay.get(left(i)).getValue());
                }
                else if (up(i) > right(i) && up(i) > left(i) && left(i) > down(i)){
                    stift1.bewegeAuf(leftWay.get(up(i)).getKey(), leftWay.get(up(i)).getValue());
                }
                else stift1.bewegeAuf(leftWay.get(i).getKey(), leftWay.get(i).getValue());
            }
            System.out.println("=========================");
            //stift1.bewegeAuf(leftWay.get(i).getKey(), leftWay.get(i).getValue());
            stift1.runter();
            stift1.zeichneKreis(4);
            stift1.hoch();
            Hilfe.warte(10);
            i++;
        }
    }

/*
    private void rightOptimize() {
        //NOTHING
    }


    private void forwardLeftOptimize() {
        //NOTHING
    }


    private void forwardRightOtimize() {
        //NOTHING
    }
*/
}
