import basis.*;
import java.util.*;

public class SolveMaze extends Fenster implements KnopfLauscher {


    //declaration
    private Bild img, imgOrg;
    private Pointer pointer;
    private Optimize optimize;
    private Stift pen;

    private Knopf end, start, load, rightOpt, leftOpt, forwardLeftOpt, optFR;
    private ZahlenFeld leftSteps, leftMax, leftBest;
    private ZahlenFeld rightSteps, rightMax, rightBest;
    private ZahlenFeld forwardLeftSteps, forwardLeftMax, forwardLeftBest;
    private ZahlenFeld forwardRightSteps, forwardRightMax, forwardRightBest;

    private boolean loaded = false;
    private int steps = 0;
    public int stepsMax = 0;
    public int counterBest = 0;

    public Map<Integer, Map.Entry<Integer, Integer>> way;

    public static SolveMaze instance;

    public SolveMaze(){
        //constructor
        this.initGui();
        this.instance = this;
    }


    public void initGui() {

        this.setzeGroesse(800,600);
        this.setzeTitel("MazeAlgorithm");

        img = new Bild();
        pointer = new Pointer(img);
        optimize = new Optimize();

        end = new Knopf("close",690,550,100,30);
        end.setzeKnopfLauscher(this);
        load = new Knopf("load image",28,190,120,30);
        load.setzeKnopfLauscher(this);
        start = new Knopf("start",28,235,120,30);
        start.setzeKnopfLauscher(this);
        leftOpt = new Knopf("opt. Links", 28, 300, 120, 30);
        leftOpt.setzeKnopfLauscher(this);
        rightOpt = new Knopf("opt. Rechts", 28, 345, 120, 30);
        rightOpt.setzeKnopfLauscher(this);
        forwardLeftOpt = new Knopf("opt. G-Links", 28, 390, 120, 30);
        forwardLeftOpt.setzeKnopfLauscher(this);
        optFR = new Knopf("opt. G-Rechts", 28, 435, 120, 30);
        optFR.setzeKnopfLauscher(this);

        leftOpt.setzeBenutzbar(false);
        rightOpt.setzeBenutzbar(false);
        forwardLeftOpt.setzeBenutzbar(false);
        optFR.setzeBenutzbar(false);

        pen = new Stift();
        pen.bewegeAuf(190,190);
        pen.dreheBis(0);
        pen.setzeFarbe(Farbe.GRAU);
        pen.setzeFuellMuster(1);
        Hilfe.warte(1000);
        pen.zeichneRechteck(320,320);

        BeschriftungsFeld leftLabel = new BeschriftungsFeld("Linksgeher", 619, 100, 100, 30);
        BeschriftungsFeld rightLabel = new BeschriftungsFeld("Rechtsgeher", 615, 200, 100, 30);
        BeschriftungsFeld fLeftLabel = new BeschriftungsFeld("G-L-Geher",619,300,100,30);
        BeschriftungsFeld fRightLabel= new BeschriftungsFeld("G-R-Geher",619,400,100,30);

        leftSteps = new ZahlenFeld(550,140,60,30);
        rightSteps = new ZahlenFeld(550,240,60,30);
        forwardLeftSteps = new ZahlenFeld(550,340,60,30);
        forwardRightSteps = new ZahlenFeld(550,440,60,30);
        leftMax = new ZahlenFeld(620,140,60,30);
        rightMax = new ZahlenFeld(620,240,60,30);
        forwardLeftMax = new ZahlenFeld(620,340,60,30);
        forwardRightMax = new ZahlenFeld(620,440,60,30);
        rightBest = new ZahlenFeld(690, 240, 60, 30);
        leftBest = new ZahlenFeld(690, 140, 60, 30);
        forwardLeftBest = new ZahlenFeld(690, 340, 60, 30);
        forwardRightBest = new ZahlenFeld(690, 440, 60, 30);

        way = new HashMap<>();
    }


    private void loadMaze() {

        img.ladeBild();
        imgOrg = new Bild();
        imgOrg.setBild(img.getBild().getClone());
        img.setzePosition(200,200);
        img.zeige();
        pen.maleAuf(img);
    }


    public void step() {
        //step forward
        pointer.forwards();
        steps++;
        stepsMax++;
        //AbstractMap.SimpleEntry inspired by Laurens H.
        way.put(stepsMax, new AbstractMap.SimpleEntry<>(pointer.x, pointer.y));
    }


    public void backstep() {
        //step backwards
        pointer.backwards();
        steps--;
        stepsMax++;
        //AbstractMap.SimpleEntry inspired by Laurens H.
        way.put(stepsMax, new AbstractMap.SimpleEntry<>(pointer.x, pointer.y));
    }


    public int left() {
        //step left
        pointer.turnLeft(); //priority no.1: left
        if (pointer.check()==2) return(2);
        if (pointer.check()==0) {
            step();
            if (left() == 2) return(2);
            backstep();
        }
        pointer.turnRight(); //priority no.2: forwards
        if (pointer.check()==2) return(2);
        if (pointer.check()==0) {
            step();
            if (left() == 2) return(2);
            backstep();
        }
        pointer.turnRight(); //priority no.3: right
        if (pointer.check()==2) return(2);
        if (pointer.check()==0) {
            step();
            if (left() == 2) return(2);
            backstep();
        }
        pointer.turnLeft(); //now he's again looking forward
        return(0);
    }


    public int right() {
        //step right
        pointer.turnRight(); //priority no.1: right
        if (pointer.check()==2) return(2);
        if (pointer.check()==0) {
            step();
            if (right() == 2) return(2);
            backstep();
        }
        pointer.turnLeft(); //priority no.2: forwards
        if (pointer.check()==2) return(2);
        if (pointer.check()==0) {
            step();
            if (right() == 2) return(2);
            backstep();
        }
        pointer.turnLeft(); //priority no.3: left
        if (pointer.check()==2) return(2);
        if (pointer.check()==0) {
            step();
            if (right() == 2) return(2);
            backstep();
        }
        pointer.turnRight(); //now he's again looking forward
        return(0);
    }


    public int fLeft() {
        //step forward, then left
        //priority no.1: forwards
        if (pointer.check()==2) return(2);
        if (pointer.check()==0) {
            step();
            if (fLeft()==2) return(2);
            backstep();
        }
        pointer.turnLeft(); //priority no.2: left
        if (pointer.check()==2) return(2);
        if (pointer.check()==0) {
            step();
            if (fLeft()==2) return(2);
            backstep();
        }
        pointer.turnRight();
        pointer.turnRight(); //priority no.3: right
        if (pointer.check()==2) return(2);
        if (pointer.check()==0) {
            step();
            if (fLeft()==2) return(2);
            backstep();
        }
        pointer.turnLeft(); //now he's again looking forward
        return(0);
    }


    public int fRight() {
        //step forward, then right
        //priority no.1: forward
        if (pointer.check()==2) return(2);
        if (pointer.check()==0) {
            step();
            if (fRight() == 2) return(2);
            backstep();
        }
        pointer.turnRight(); // priority no.2: right
        if (pointer.check()==2) return(2);
        if (pointer.check()==0) {
            step();
            if (fRight() == 2) return(2);
            backstep();
        }
        pointer.turnLeft();
        pointer.turnLeft(); //priority no.3: left
        if (pointer.check()==2) return(2);
        if (pointer.check()==0) {
            step();
            if (fRight() == 2) return(2);
            backstep();
        }
        pointer.turnRight(); //now he's looking foward again
        return(0);
    }


    public void reset() {
        //resets the maze
        steps = 0;
        stepsMax = 0;
        counterBest = 0;
        Hilfe.warte(250);
        img.setBild(imgOrg.getBild().getClone());
        img.setzePosition(200,200);
        img.zeige();
        pointer.start(65,245);
        way.clear();
    }


    public void solveLeft() {
        //solves the Maze using the left method
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
        //solves the Maze using the right method
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


    public void solveForwardLeft() {
        //solves the Maze using the forward-left method
        fLeft();
        if(fLeft() == 0){
            System.out.println("Ziel NICHT gefunden");
            forwardLeftSteps.setzeZahl(0);
            forwardLeftMax.setzeZahl(stepsMax);
        }
        if(fLeft() == 2) {
            System.out.println("Ziel gefunden");
            forwardLeftSteps.setzeZahl(steps);
            forwardLeftMax.setzeZahl(stepsMax);
        }
    }


    public void solveForwardRight() {
        //solves the Maze using the forward-right method
        fRight();
        if(fRight() == 0) {
            System.out.println("Ziel NICHT gefunden");
            forwardRightSteps.setzeZahl(0);
            forwardRightMax.setzeZahl(stepsMax);
        }
        if(fRight() == 2) {
            System.out.println("Ziel gefunden");
            forwardRightSteps.setzeZahl(steps);
            forwardRightMax.setzeZahl(stepsMax);
        }
    }


    public void solveLab() {
        //runs all the methods to solve the Maze successively and find out which is the fastest way
        optimize.bestLabel.setzeText("schnellster Geher wird ermittelt...");
        steps = 0;
        stepsMax = 0;
        solveLeft();
        reset();
        solveRight();
        reset();
        solveForwardLeft();
        reset();
        solveForwardRight();
        enableOpt();
    }


    private void enableOpt() {
        //enables the optimize buttons
        leftOpt.setzeBenutzbar(true);
        rightOpt.setzeBenutzbar(true);
        forwardLeftOpt.setzeBenutzbar(true);
        optFR.setzeBenutzbar(true);
    }


    @Override
    public void bearbeiteKnopfDruck(Knopf button) {
        //listener of the buttons
        if (button == end) {
            this.gibFrei();
        }

        else if (button == start) {
            if (loaded) {
                solveLab();
                optimize.best(leftMax.ganzZahl(), rightMax.ganzZahl(), forwardLeftMax.ganzZahl(), forwardRightMax.ganzZahl());
            }
        }

        else if (button == load) {
            loadMaze();
            loaded = true;
            pointer.start(65,245);
            System.out.println("Maze vorhanden");
        }

        else if (button == leftOpt) {
            reset();
            left();
            Hilfe.warte(500);
            optimize.optimize(pen, img);
            leftBest.setzeZahl(counterBest);
        }

        else if (button == rightOpt) {
            reset();
            right();
            Hilfe.warte(500);
            optimize.optimize(pen, img);
            rightBest.setzeZahl(counterBest);
        }
        else if (button == forwardLeftOpt) {
            reset();
            fLeft();
            Hilfe.warte(500);
            optimize.optimize(pen, img);
            forwardLeftBest.setzeZahl(counterBest);
        }
        else if (button == optFR) {
            reset();
            fRight();
            Hilfe.warte(500);
            optimize.optimize(pen, img);
            forwardRightBest.setzeZahl(counterBest);
        }


    }
}
