import basis.*;
import java.awt.*;

/*
 * Noten für das Projekt:
 * dieses Programm abgeben -> 5
 * Rechtsgeher richtig hinbekommen -> 4
 * Mehrere Geher mit Länge -> 3
 * Mehrere Geher mit Länge und Vergleich, welches der kürzeste Geher war -> 2
 * Mehrere Geher und den kürzesten (oder alle) Wege noch mal optimieren -> 1
 *
 *
 */


public class Geher {

    Stift stift;
    Bild bild;
    private Richtung richtung;
    public int x, y;


    public Geher(Bild b) {
        //Constructor
        bild = b;
        stift = new Stift();
        stift.maleAuf(bild);
    }


    public void start(int x, int y) {

        stift.hoch();
        stift.bewegeAuf(x,y);
        stift.dreheBis(90);
        richtung = Richtung.UP;
        this.x = x;
        this.y = y;
    }


    public void gehe(int z,int farbe) {

        if (farbe == 0) stift.setzeFarbe(Color.WHITE);
        if (farbe == 1) stift.setzeFarbe(Color.RED);
        if (farbe == 2) stift.setzeFarbe(Color.YELLOW);
        if (farbe == 3) stift.setzeFarbe(Color.RED);
        if (farbe == 4) stift.setzeFarbe(Color.GREEN);
        if (farbe == 5) stift.setzeFarbe(Color.RED);
        if ((farbe == 4) && (z<0)) stift.setzeFarbe(Color.RED);

        stift.hoch();
        if (z>0) stift.bewegeUm(z);
        stift.runter();
        stift.setzeFuellMuster(1);
        stift.zeichneKreis(4);
        stift.hoch();
        if (z<0) stift.bewegeUm(z);
        Hilfe.warte(10);
    }


    private void updateRichtung(int temp) {

        if (temp == 90){
            if (richtung == Richtung.UP) richtung = Richtung.LEFT;
            else if (richtung == Richtung.LEFT) richtung = Richtung.DOWN;
            else if (richtung == Richtung.DOWN) richtung = Richtung.RIGHT;
            else if (richtung == Richtung.RIGHT) richtung = Richtung.UP;
        }
        else if (temp == -90){
            if (richtung == Richtung.UP) richtung = Richtung.RIGHT;
            else if (richtung == Richtung.LEFT) richtung = Richtung.UP;
            else if (richtung == Richtung.DOWN) richtung = Richtung.LEFT;
            else if (richtung == Richtung.RIGHT) richtung = Richtung.DOWN;
        }
    }


    private void updateCoords() {

        if (richtung == Richtung.UP){
            y = y - 10;
        }
        else if (richtung == Richtung.DOWN){
            y = y + 10;
        }
        else if (richtung == Richtung.RIGHT){
            x = x + 10;
        }
        else if (richtung == Richtung.LEFT) {
            x = x - 10;
        }
    }


    public void vor() {

        gehe(10,4);
        updateCoords();
    }


    public void zurueck() {

        gehe(-10,4);
        updateCoords();
    }


    public void drehe(int r) {

        stift.dreheUm(r);
    }


    public void rechts() {

        drehe(-90);
        updateRichtung(-90);
    }


    public void links() {

        drehe(+90);
        updateRichtung(90);
    }


    public int pruefe() {

        Color c;
        stift.hoch();
        stift.bewegeUm(10);
        c= bild.farbeVon((int) stift.hPosition(),(int) stift.vPosition());
        stift.bewegeUm(-10);

        if (c.equals(Color.WHITE))    return(0);
        if (c.equals(Color.BLACK))    return(1);
        if (c.equals(Color.YELLOW))   return(2);
        if (c.equals(Color.BLUE))     return(3);
        if (c.equals(Color.GREEN))    return(4);
        if (c.equals(Color.RED))      return(5);

        return(-1);
    }


}
