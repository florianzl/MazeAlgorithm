import basis.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Optimierer {

    private Bild bild;
    private Stift stift;
    private int coordsCounter = 0;
    private int[] coords = {65, 245};
    public BeschriftungsFeld bestLabel;
    public Map<Integer, Map.Entry<Integer, Integer>> leftWay = new HashMap<>();
    public Map<Integer, Map.Entry<Integer, Integer>> rightWay = new HashMap<>();
    public Map<Integer, Map.Entry<Integer, Integer>> fLeftWay = new HashMap<>();
    public Map<Integer, Map.Entry<Integer, Integer>> fRightWay = new HashMap<>();


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


    private void right(int start) {
        //this method looks if on the right side of the current pos is a green field
        int xStart = leftWay.get(start).getKey();
        //yPos is equal to yStart so it's the same
        int yPos = leftWay.get(start).getValue();
        int xPos = xStart;

        //it stops if hitting a black field
        while (!bild.farbeVon(xPos, yPos).equals(Color.BLACK)){

            //if hitting a Green field add the pos to coords
            if (bild.farbeVon(xPos, yPos).equals(Color.GREEN)){
                coords[coordsCounter] = xPos;
                coordsCounter++;
                coords[coordsCounter] = yPos;
                coordsCounter++;
            }
            xPos = xPos + 10;
        }

    }

    private void left(int start) {
        //this method looks if on the right side of the current pos is a green field
        int xStart = leftWay.get(start).getKey();
        //yPos is equal to yStart so it's the same
        int yPos = leftWay.get(start).getValue();
        int xPos = xStart;

        //it stops if hitting a black field
        while (!bild.farbeVon(xPos, yPos).equals(Color.BLACK)) {

            //if hitting a Green field add the pos to coords
            if (bild.farbeVon(xPos, yPos).equals(Color.GREEN)) {
                coords[coordsCounter] = xPos;
                coordsCounter++;
                coords[coordsCounter] = yPos;
                coordsCounter++;
            }
            xPos = xPos - 10;
        }
    }

    private void up(int start) {
        //this method looks if on the right side of the current pos is a green field
        int yStart = leftWay.get(start).getValue();
        //xPos is equal to yStart so it's the same
        int xPos = leftWay.get(start).getKey();
        int yPos = yStart;

        //it stops if hitting a black field
        while (!bild.farbeVon(xPos, yPos).equals(Color.BLACK)){

            //if hitting a Green field add the pos to coords
            if (bild.farbeVon(xPos, yPos).equals(Color.GREEN)){
                coords[coordsCounter] = xPos;
                coordsCounter++;
                coords[coordsCounter] = yPos;
                coordsCounter++;
            }
            yPos = yPos - 10;
        }
    }

    private void down(int start) {
        //this method looks if on the right side of the current pos is a green field
        int yStart = leftWay.get(start).getValue();
        //yPos is equal to yStart so it's the same
        int xPos = leftWay.get(start).getKey();
        int yPos = yStart;

        //it stops if hitting a black field
        while (!bild.farbeVon(xPos, yPos).equals(Color.BLACK)) {

            //if hitting a Green field add the pos to coords
            if (bild.farbeVon(xPos, yPos).equals(Color.GREEN)){
                coords[coordsCounter] = xPos;
                coordsCounter++;
                coords[coordsCounter] = yPos;
                coordsCounter++;
            }
            yPos = yPos + 10;
        }
    }


    public int keyCompare(int key1) {
        //todo fixing this method
        //checks if the current pos or the next green field in coords array has a higher step number
        int key2 = 0;

        //
        do {
            //gets the key of the pos of green fields
            for (Map.Entry<Integer, Map.Entry<Integer, Integer>> map : leftWay.entrySet()) {

                if (map.getValue().getKey() == coords[0] && map.getValue().getValue() == coords[1]){

                    key2 = map.getKey();
                }
            }

            //removes the first two indices of coords
            List<Integer> arrayList = IntStream.of(coords).boxed().collect(Collectors.toList());
            System.out.println(coords.length);
            arrayList.remove(1);
            arrayList.remove(0);
            coords = arrayList.stream().mapToInt(Integer::intValue).toArray();
            System.out.println("coords length: " + coords.length);

        }while (coords.length >= 2);

        if (key1 < key2) {
            return key2;
        }
        else return key1;
    }

    private void bewege(int x1, int x2, int y1, int y2){
        stift = new Stift();
        stift.maleAuf(bild);
        int tempX = 0;
        int tempY = 0;
        if (x1 < x2) tempX = x2 - x1;
        else tempX = x1 - x2;
        if (y1 < y2) tempY = y2 - y1;
        else tempY = y1 - y2;

        int distance = tempX + tempY;

        for (int i = 0; i < distance / 10; i++){
            stift.dreheInRichtung(x2, y2);
            stift.setzeFarbe(Color.PINK);
            stift.hoch();
            stift.bewegeUm(10);
            stift.runter();
            stift.setzeFuellMuster(1);
            stift.zeichneKreis(4);
            stift.hoch();
            Hilfe.warte(10);
        }
    }


    public void leftOptimize() {
        //geher.start(65, 245);
        bild = new Bild();
        bild.ladeBild("D:\\projects\\IntelliJ_Template\\Mazes\\vickymaze.png");
        bild.setzePosition(200,200);
        /*
        int startKey = keyCompare(1);
        right(startKey);
        left(startKey);
        up(startKey);
        down(startKey);
        bewege(leftWay.get(startKey).getKey(), leftWay.get(keyCompare(startKey)).getKey(), leftWay.get(startKey).getValue(), leftWay.get(keyCompare(startKey)).getValue());
        bewege(65, 65, 235, 225);
        */
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
