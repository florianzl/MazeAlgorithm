
//Import
import basis.*;
import java.awt.*;
import java.util.*;

public class Labgeher extends Fenster implements KnopfLauscher 
{

    //Deklaration
    private Knopf ende, angeber,ladeknopf,opti;
    private Bild bild, bildorg;

    private Geher geher;
    private Optimierer optimierer;
    private Stift stift;

    private BeschriftungsFeld linksLabel, rechtsLabel, glLabel, grLabel, kurzLabel;
    private ZahlenFeld leftSteps, rightSteps, flSteps, frSteps;
    private ZahlenFeld lmax, rmax, flmax, frmax, best;
    // Konstruktor fuer Objekte der Klasse TischKlasse

    private boolean loaded = false;

    private int steps = 0;
    private int stepsMax = 0;

    public String lowest;

    public Labgeher()
    {
        this.initGui();
    }

    public void initGui() 
    {
        this.setzeGroesse(800,600);
        this.setzeTitel("Labgeher");
        ende = new Knopf("Ende",690,550,100,30);
        ende.setzeKnopfLauscher(this);

        angeber = new Knopf("Angeber",10,130,120,30);
        angeber.setzeKnopfLauscher(this);
        opti = new Knopf("OptimalR",10,180,120,30);
        opti.setzeKnopfLauscher(this);
        ladeknopf = new Knopf("Lade Bild",10,80,120,30);
        ladeknopf.setzeKnopfLauscher(this);
        stift=new Stift();
        stift.bewegeAuf(190,190);
        stift.dreheBis(0);
        stift.setzeFarbe(Farbe.GRAU);
        stift.setzeFuellMuster(1);
        Hilfe.warte(1000);
        stift.zeichneRechteck(320,320);
        bild = new Bild();

        linksLabel = new BeschriftungsFeld("Linksgeher",600,100,100,30);
        rechtsLabel = new BeschriftungsFeld("Rechtsgeher",600,200,100,30);
        glLabel = new BeschriftungsFeld("G-L-geher",600,300,100,30);
        grLabel = new BeschriftungsFeld("G-R-geher",600,400,100,30);

        leftSteps = new ZahlenFeld(600,140,60,30);
        rightSteps = new ZahlenFeld(600,240,60,30);
        flSteps = new ZahlenFeld(600,340,60,30);
        frSteps = new ZahlenFeld(600,440,60,30);

        lmax = new ZahlenFeld(670,140,60,30);
        rmax = new ZahlenFeld(670,240,60,30);
        flmax = new ZahlenFeld(670,340,60,30);
        frmax = new ZahlenFeld(670,440,60,30);
        best = new ZahlenFeld(670,500,60,30);

        geher =new Geher(bild);
        optimierer = new Optimierer(); 

        lowest = "Basis Swing is a hurensohn";
    }

    //step forward and counter++
    public void step(){
        geher.vor();
        steps++;
        stepsMax++;
    }

    //step backwards and Green counter--
    public void backstep(){
        geher.zurueck();
        steps--;
    }

    public int left()
    {
        geher.links(); //priority no.1: left
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) 
        {
            step();
            if (left() == 2) return(2);
            backstep();
        }
        geher.rechts(); //priority no.2: forwards
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) 
        {
            step();
            if (left() == 2) return(2);
            backstep();
        }
        geher.rechts(); //priority no.3: right
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) 
        {
            step();
            if (left() == 2) return(2);
            backstep();
        }
        geher.links(); //now he's again looking forward
        return(0);
    }

    public int right()
    {
        geher.rechts(); //priority no.1: right
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) 
        {
            step();
            if (right() == 2) return(2);
            backstep();
        }
        geher.links(); //priority no.2: forwards
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) 
        {
            step();
            if (right() == 2) return(2);
            backstep();
        }
        geher.links(); //priority no.3: left
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) 
        {
            step();
            if (right() == 2) return(2);
            backstep();
        }
        geher.rechts(); //now he's again looking forward
        return(0);
    }

    public int fLeft()
    {
        //priority no.1: forwards
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) 
        {
            step();
            if (fLeft()==2) return(2);
            backstep();
        }
        geher.links(); //priority no.2: left
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) 
        {
            step();
            if (fLeft()==2) return(2);
            backstep();
        }
        geher.rechts();
        geher.rechts(); //priority no.3: right
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) 
        {
            step();
            if (fLeft()==2) return(2);
            backstep();
        }
        geher.links(); //now he's again looking forward 
        return(0);
    }

    public int fRight()
    {
        //priority no.1: forward
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) 
        {
            step();
            if (fRight() == 2) return(2);
            backstep();
        }
        geher.rechts(); // priority no.2: right
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) 
        {
            step();
            if (fRight() == 2) return(2);
            backstep();
        }
        geher.links();
        geher.links(); //priority no.3: left
        if (geher.pruefe()==2) return(2);
        if (geher.pruefe()==0) 
        {
            step();
            if (fRight() == 2) return(2);
            backstep();
        }
        geher.rechts(); //now he's looking foward again
        return(0);
    }

    //resets the settings of the game
    public void reset(){
        steps = 0;
        stepsMax = 0;
        Hilfe.warte(2000);
        bild.setBild(bildorg.getBild().getClone());
        bild.setzePosition(200,200);
        bild.zeige();
        geher.start(65,245);
    }

    //solves the Lab using the left method
    public void solveLeft(){
        left();
        if(left() == 0){
            System.out.println("Ziel NICHT gefunden"); 
            leftSteps.setzeZahl(0); 
            lmax.setzeZahl(stepsMax);
        }
        if(left() == 2) {
            System.out.println("Ziel gefunden");
            leftSteps.setzeZahl(steps);
            lmax.setzeZahl(stepsMax);
        }
    }

    //solves the Lab using the right method
    public void solveRight(){
        right();
        if(right() == 0){
            System.out.println("Ziel NICHT gefunden"); 
            rightSteps.setzeZahl(0); 
            rmax.setzeZahl(stepsMax);
        }
        if(right() == 2) {
            System.out.println("Ziel gefunden");
            rightSteps.setzeZahl(steps);
            rmax.setzeZahl(stepsMax);
        }
    }

    //solves the Lab using the forward-left method
    public void solveGLeft() {
        fLeft();
        if(fLeft() == 0){
            System.out.println("Ziel NICHT gefunden"); 
            flSteps.setzeZahl(0); 
            flmax.setzeZahl(stepsMax);
        }
        if(fLeft() == 2) {
            System.out.println("Ziel gefunden");
            flSteps.setzeZahl(steps);
            flmax.setzeZahl(stepsMax);
        }
    }

    //solves the Lab using the forward-right method
    public void solveFRight() {
        fRight();
        if(fRight() == 0){
            System.out.println("Ziel NICHT gefunden"); 
            frSteps.setzeZahl(0); 
            frmax.setzeZahl(stepsMax);
        }

        if(fRight() == 2) {
            System.out.println("Ziel gefunden");
            frSteps.setzeZahl(steps);
            frmax.setzeZahl(stepsMax);
        }
    }

    //starts all the methods to solve the lab successively
    public void solveLab(){
        optimierer.bestLabel.setzeText("schnellster Geher wird ermittelt...");
        steps = 0;
        stepsMax = 0;
        solveLeft();
        reset();
        solveRight();
        reset();
        solveGLeft();
        reset();
        solveFRight();
    }

    private void imageLoad(){
        bild.ladeBild("F:\\Lab_Solver\\Mazes\\maze6.png");
        bildorg = new Bild();
        bildorg.setBild(bild.getBild().getClone());
        bild.setzePosition(200,200);
        bild.zeige();
        stift.maleAuf(bild);
    }

    @Override
    public void bearbeiteKnopfDruck(Knopf k)
    {
        if (k ==ende)
        {
            this.gibFrei();
        } 

        else if (k ==angeber)
        {
            if (loaded){
                solveLab();
                optimierer.best(lmax.ganzZahl(), rmax.ganzZahl(), flmax.ganzZahl(), frmax.ganzZahl());
                //optimierer.iAmTheBest();
            }
        }
        else if (k == opti){
            //TODO
        }
        else if (k ==ladeknopf)
        {
            imageLoad();
            loaded = true;
            geher.start(65,245);
            System.out.println("Maze vorhanden");
        }
    }
}