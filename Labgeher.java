import basis.*;
import java.util.*;

public class Labgeher extends Fenster implements KnopfLauscher {

    //declaration
    private Bild bild, bildorg;
    private Geher geher;
    private Optimierer optimierer;
    private Stift stift;

    private Knopf end, angeber,ladeknopf, optR, optL, optFL, optFR;
    private ZahlenFeld lSteps, lMax, lBest, rSteps, rMax, rBest, fLSteps, fLMax, fLBest, fRSteps , fRMax, fRBest;

    private boolean loaded = false;
    private int steps = 0;
    private int stepsMax = 0;

    public Map<Integer, Map.Entry<Integer, Integer>> way;


    //constructor
    public Labgeher(){
        this.initGui();
    }


    public void initGui() {

        bild = new Bild();
        geher = new Geher(bild);
        optimierer = new Optimierer();

        this.setzeGroesse(800,600);
        this.setzeTitel("Labgeher");

        end = new Knopf("Ende",690,550,100,30);
        end.setzeKnopfLauscher(this);
        ladeknopf = new Knopf("Lade Bild",28,190,120,30);
        ladeknopf.setzeKnopfLauscher(this);
        angeber = new Knopf("Angeber",28,235,120,30);
        angeber.setzeKnopfLauscher(this);
        optL = new Knopf("opt. Links", 28, 300, 120, 30);
        optL.setzeKnopfLauscher(this);
        optR = new Knopf("opt. Rechts", 28, 345, 120, 30);
        optR.setzeKnopfLauscher(this);
        optFL = new Knopf("opt. G-Links", 28, 390, 120, 30);
        optFL.setzeKnopfLauscher(this);
        optFR = new Knopf("opt. G-Rechts", 28, 435, 120, 30);
        optFR.setzeKnopfLauscher(this);

        optL.setzeBenutzbar(false);
        optR.setzeBenutzbar(false);
        optFL.setzeBenutzbar(false);
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
        lSteps = new ZahlenFeld(550,140,60,30);
        rSteps = new ZahlenFeld(550,240,60,30);
        fLSteps = new ZahlenFeld(550,340,60,30);
        fRSteps = new ZahlenFeld(550,440,60,30);
        lMax = new ZahlenFeld(620,140,60,30);
        rMax = new ZahlenFeld(620,240,60,30);
        fLMax = new ZahlenFeld(620,340,60,30);
        fRMax = new ZahlenFeld(620,440,60,30);
        rBest = new ZahlenFeld(690, 240, 60, 30);
        lBest = new ZahlenFeld(690, 140, 60, 30);
        fLBest = new ZahlenFeld(690, 340, 60, 30);
        fRBest = new ZahlenFeld(690, 440, 60, 30);

        way = new HashMap<>();
    }


    private void mazeLoad() {

        bild.ladeBild("D:\\projects\\IntelliJ_Template\\Mazes\\vickymaze.png");
        bildorg = new Bild();
        bildorg.setBild(bild.getBild().getClone());
        bild.setzePosition(200,200);
        bild.zeige();
        stift.maleAuf(bild);
    }


    //step forward
    public void step() {

        geher.vor();
        steps++;
        stepsMax++;
        //coords.put(geher.x, geher.y);
        way.put(stepsMax, new AbstractMap.SimpleEntry<>(geher.x, geher.y));
    }


    //step backwards
    public void backstep() {

        geher.zurueck();
        steps--;
    }


    //left method
    public int left() {
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


    //right method
    public int right() {

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


    //forward-left method
    public int fLeft() {

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


    //forward-right method
    public int fRight() {

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


    //resets the settings of the game
    public void reset() {

        steps = 0;
        stepsMax = 0;
        Hilfe.warte(2000);
        bild.setBild(bildorg.getBild().getClone());
        bild.setzePosition(200,200);
        bild.zeige();
        geher.start(65,245);
    }


    //solves the Lab using the left method
    public void solveLeft() {

        left();
        if(left() == 0) {
            System.out.println("Ziel NICHT gefunden");
            lSteps.setzeZahl(0);
            lMax.setzeZahl(stepsMax);
        }
        if(left() == 2) {
            System.out.println("Ziel gefunden");
            lSteps.setzeZahl(steps);
            lMax.setzeZahl(stepsMax);
        }
    }


    //solves the Lab using the right method
    public void solveRight() {

        right();
        if(right() == 0) {
            System.out.println("Ziel NICHT gefunden");
            rSteps.setzeZahl(0);
            rMax.setzeZahl(stepsMax);
        }
        if(right() == 2) {
            System.out.println("Ziel gefunden");
            rSteps.setzeZahl(steps);
            rMax.setzeZahl(stepsMax);
        }
    }


    //solves the Lab using the forward-left method
    public void solveFLeft() {

        fLeft();
        if(fLeft() == 0){
            System.out.println("Ziel NICHT gefunden");
            fLSteps.setzeZahl(0);
            fLMax.setzeZahl(stepsMax);
        }
        if(fLeft() == 2) {
            System.out.println("Ziel gefunden");
            fLSteps.setzeZahl(steps);
            fLMax.setzeZahl(stepsMax);
        }
    }


    //solves the Lab using the forward-right method
    public void solveFRight() {

        fRight();
        if(fRight() == 0) {
            System.out.println("Ziel NICHT gefunden");
            fRSteps.setzeZahl(0);
            fRMax.setzeZahl(stepsMax);
        }
        if(fRight() == 2) {
            System.out.println("Ziel gefunden");
            fRSteps.setzeZahl(steps);
            fRMax.setzeZahl(stepsMax);
        }
    }


    //starts all the methods to solve the lab successively
    public void solveLab() {

        optimierer.bestLabel.setzeText("schnellster Geher wird ermittelt...");
        steps = 0;
        stepsMax = 0;
        solveLeft();
        optimierer.leftWay.putAll(way);
        reset();
        solveRight();
        optimierer.rightWay.putAll(way);
        reset();
        solveFLeft();
        optimierer.fLeftWay.putAll(way);
        reset();
        solveFRight();
        enableOpt();
        optimierer.fRightWay.putAll(way);
        System.out.println(optimierer.leftWay);
        System.out.println(optimierer.rightWay);
        System.out.println(optimierer.fLeftWay);
        System.out.println(optimierer.fRightWay);
        optimierer.lCompare(1, optimierer.leftWay.get(10).getKey(), optimierer.leftWay.get(10).getValue());
    }


    //enables the optimize buttons
    private void enableOpt() {
        optL.setzeBenutzbar(true);
        optR.setzeBenutzbar(true);
        optFL.setzeBenutzbar(true);
        optFR.setzeBenutzbar(true);
    }


    //listener of the buttons
    @Override
    public void bearbeiteKnopfDruck(Knopf k) {

        if (k ==end) {
            this.gibFrei();
        }

        else if (k ==angeber) {
            if (loaded) {
                solveLab();
                optimierer.best(lMax.ganzZahl(), rMax.ganzZahl(), fLMax.ganzZahl(), fRMax.ganzZahl());
            }
            else System.out.println("Bitte Wähle erst ein Maze aus bevor du das Programm startest");
        }
        else if (k ==ladeknopf) {
            mazeLoad();
            loaded = true;
            geher.start(65,245);
            System.out.println("Maze vorhanden");
        }
        else if (k == optL) {
            //NOTHING
        }
        else if (k == optR) {
            //NOTHING
        }
        else if (k == optFL) {
            //NOTHING
        }
        else if (k == optFR) {
            //NOTHING
        }
    }
}