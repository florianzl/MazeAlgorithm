import basis.*;
import java.util.*;

public class Labgeher extends Fenster implements KnopfLauscher {

    //declaration
    private Bild bild, bildorg;
    private Geher geher;
    private Optimierer optimierer;
    private Stift stift;

    private Knopf end, start, load, rightOpt, leftOpt, fLeftOpt, optFR;
    private ZahlenFeld leftSteps, leftMax, leftBest, rightSteps, rightMax, rightBest, fLeftSteps, fLeftMax, fLeftBest, fRightSteps, fRightMax, fRightBest;

    private boolean loaded = false;
    private int steps = 0;
    private int stepsMax = 0;

    //Map.Entry<Integer, Integer> inspired by Laurens H.
    public Map<Integer, Map.Entry<Integer, Integer>> currentWay;


    public Labgeher(){
        //constructor
        this.initGui();
    }


    public void initGui() {

        this.setzeGroesse(800,600);
        this.setzeTitel("Labgeher");

        bild = new Bild();
        geher = new Geher(bild);
        optimierer = new Optimierer();

        end = new Knopf("Ende",690,550,100,30);
        end.setzeKnopfLauscher(this);
        load = new Knopf("Lade Bild",28,190,120,30);
        load.setzeKnopfLauscher(this);
        start = new Knopf("Angeber",28,235,120,30);
        start.setzeKnopfLauscher(this);
        leftOpt = new Knopf("opt. Links", 28, 300, 120, 30);
        leftOpt.setzeKnopfLauscher(this);
        rightOpt = new Knopf("opt. Rechts", 28, 345, 120, 30);
        rightOpt.setzeKnopfLauscher(this);
        fLeftOpt = new Knopf("opt. G-Links", 28, 390, 120, 30);
        fLeftOpt.setzeKnopfLauscher(this);
        optFR = new Knopf("opt. G-Rechts", 28, 435, 120, 30);
        optFR.setzeKnopfLauscher(this);

        leftOpt.setzeBenutzbar(false);
        rightOpt.setzeBenutzbar(false);
        fLeftOpt.setzeBenutzbar(false);
        optFR.setzeBenutzbar(false);

        stift = new Stift();
        stift.bewegeAuf(190,190);
        stift.dreheBis(0);
        stift.setzeFarbe(Farbe.GRAU);
        stift.setzeFuellMuster(1);
        Hilfe.warte(1000);
        stift.zeichneRechteck(320,320);

        BeschriftungsFeld linksLabel = new BeschriftungsFeld("Linksgeher", 619, 100, 100, 30);
        BeschriftungsFeld rechtsLabel = new BeschriftungsFeld("Rechtsgeher", 615, 200, 100, 30);
        BeschriftungsFeld glLabel = new BeschriftungsFeld("G-L-Geher",619,300,100,30);
        BeschriftungsFeld grLabel = new BeschriftungsFeld("G-R-Geher",619,400,100,30);

        leftSteps = new ZahlenFeld(550,140,60,30);
        rightSteps = new ZahlenFeld(550,240,60,30);
        fLeftSteps = new ZahlenFeld(550,340,60,30);
        fRightSteps = new ZahlenFeld(550,440,60,30);
        leftMax = new ZahlenFeld(620,140,60,30);
        rightMax = new ZahlenFeld(620,240,60,30);
        fLeftMax = new ZahlenFeld(620,340,60,30);
        fRightMax = new ZahlenFeld(620,440,60,30);
        rightBest = new ZahlenFeld(690, 240, 60, 30);
        leftBest = new ZahlenFeld(690, 140, 60, 30);
        fLeftBest = new ZahlenFeld(690, 340, 60, 30);
        fRightBest = new ZahlenFeld(690, 440, 60, 30);

        currentWay = new HashMap<>();
    }


    private void mazeLoad() {

        bild.ladeBild("D:\\projects\\IntelliJ_Template\\Mazes\\vickymaze.png");
        bildorg = new Bild();
        bildorg.setBild(bild.getBild().getClone());
        bild.setzePosition(200,200);
        bild.zeige();
        stift.maleAuf(bild);
    }


    public void step() {
        //step forward
        geher.vor();
        steps++;
        stepsMax++;
        //AbstrctMap.SimpleEntry inspired by Laurens H.
        currentWay.put(stepsMax, new AbstractMap.SimpleEntry<>(geher.x, geher.y));
    }


    public void backstep() {
        //step backwards
        geher.zurueck();
        steps--;
    }


    public int left() {
        //left method
        geher.links(); //priority no.1: left
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) {
            step();
            if (left() == 2) return(2);
            backstep();
        }
        geher.rechts(); //priority no.2: forwards
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) {
            step();
            if (left() == 2) return(2);
            backstep();
        }
        geher.rechts(); //priority no.3: right
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) {
            step();
            if (left() == 2) return(2);
            backstep();
        }
        geher.links(); //now he's again looking forward
        return(0);
    }


    public int right() {
        //right method
        geher.rechts(); //priority no.1: right
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) {
            step();
            if (right() == 2) return(2);
            backstep();
        }
        geher.links(); //priority no.2: forwards
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) {
            step();
            if (right() == 2) return(2);
            backstep();
        }
        geher.links(); //priority no.3: left
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) {
            step();
            if (right() == 2) return(2);
            backstep();
        }
        geher.rechts(); //now he's again looking forward
        return(0);
    }


    public int fLeft() {
        //forward-left method
        //priority no.1: forwards
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) {
            step();
            if (fLeft()==2) return(2);
            backstep();
        }
        geher.links(); //priority no.2: left
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) {
            step();
            if (fLeft()==2) return(2);
            backstep();
        }
        geher.rechts();
        geher.rechts(); //priority no.3: right
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) {
            step();
            if (fLeft()==2) return(2);
            backstep();
        }
        geher.links(); //now he's again looking forward 
        return(0);
    }


    public int fRight() {
        //forward-right method
        //priority no.1: forward
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) {
            step();
            if (fRight() == 2) return(2);
            backstep();
        }
        geher.rechts(); // priority no.2: right
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) {
            step();
            if (fRight() == 2) return(2);
            backstep();
        }
        geher.links();
        geher.links(); //priority no.3: left
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) {
            step();
            if (fRight() == 2) return(2);
            backstep();
        }
        geher.rechts(); //now he's looking foward again
        return(0);
    }


    public void reset() {
        //resets the maze
        steps = 0;
        stepsMax = 0;
        Hilfe.warte(2000);
        bild.setBild(bildorg.getBild().getClone());
        bild.setzePosition(200,200);
        bild.zeige();
        geher.start(65,245);
    }


    public void solveLeft() {
        //solves the Lab using the left method
        left();
        if(left() == 0) {
            System.out.println("Ziel NICHT gefunden");
            leftSteps.setzeZahl(0);
            leftMax.setzeZahl(stepsMax);
        }
        if(left() == 2) {
            System.out.println("Ziel gefunden");
            leftSteps.setzeZahl(steps);
            leftMax.setzeZahl(stepsMax);
        }
    }


    public void solveRight() {
        //solves the Lab using the right method
        right();
        if(right() == 0) {
            System.out.println("Ziel NICHT gefunden");
            rightSteps.setzeZahl(0);
            rightMax.setzeZahl(stepsMax);
        }
        if(right() == 2) {
            System.out.println("Ziel gefunden");
            rightSteps.setzeZahl(steps);
            rightMax.setzeZahl(stepsMax);
        }
    }


    public void solveFLeft() {
        //solves the Lab using the forward-left method
        fLeft();
        if(fLeft() == 0){
            System.out.println("Ziel NICHT gefunden");
            fLeftSteps.setzeZahl(0);
            fLeftMax.setzeZahl(stepsMax);
        }
        if(fLeft() == 2) {
            System.out.println("Ziel gefunden");
            fLeftSteps.setzeZahl(steps);
            fLeftMax.setzeZahl(stepsMax);
        }
    }


    public void solveFRight() {
        //solves the Lab using the forward-right method
        fRight();
        if(fRight() == 0) {
            System.out.println("Ziel NICHT gefunden");
            fRightSteps.setzeZahl(0);
            fRightMax.setzeZahl(stepsMax);
        }
        if(fRight() == 2) {
            System.out.println("Ziel gefunden");
            fRightSteps.setzeZahl(steps);
            fRightMax.setzeZahl(stepsMax);
        }
    }


    public void solveLab() {
        //starts all the methods to solve the lab successively
        optimierer.bestLabel.setzeText("schnellster Geher wird ermittelt...");
        steps = 0;
        stepsMax = 0;
        solveLeft();
        optimierer.leftWay.putAll(currentWay);
        reset();
        solveRight();
        optimierer.rightWay.putAll(currentWay);
        reset();
        solveFLeft();
        optimierer.fLeftWay.putAll(currentWay);
        reset();
        solveFRight();
        enableOpt();
        optimierer.fRightWay.putAll(currentWay);

        /*
        System.out.println(optimierer.leftWay);
        System.out.println(optimierer.rightWay);
        System.out.println(optimierer.fLeftWay);
        System.out.println(optimierer.fRightWay);
        */
    }


    private void enableOpt() {
        //enables the optimize buttons
        leftOpt.setzeBenutzbar(true);
        rightOpt.setzeBenutzbar(true);
        fLeftOpt.setzeBenutzbar(true);
        optFR.setzeBenutzbar(true);
    }


    @Override
    public void bearbeiteKnopfDruck(Knopf k) {
        //listener of the buttons
        if (k ==end) {
            this.gibFrei();
        }

        else if (k == start) {
            if (loaded) {
                solveLab();
                optimierer.best(leftMax.ganzZahl(), rightMax.ganzZahl(), fLeftMax.ganzZahl(), fRightMax.ganzZahl());
            }
        }

        else if (k == load) {
            mazeLoad();
            loaded = true;
            geher.start(65,245);
            System.out.println("Maze vorhanden");
        }

        else if (k == leftOpt) {
            optimierer.leftOptimize();
        }

        /*
        else if (k == rightOpt) {
            //NOTHING
        }
        else if (k == fLeftOpt) {
            //NOTHING
        }
        else if (k == optFR) {
            //NOTHING
        }
        */

    }
}