import java.awt.*;

public class Pointer {

    Stift pen;
    Bild img;
    private Direction direction;
    public int x, y;

    public Pointer(Bild b) {
        //Constructor
        img = b;
        pen = new Stift();
        pen.maleAuf(img);
    }

    public void start(int x, int y) {

        pen.hoch();
        pen.bewegeAuf(x,y);
        pen.dreheBis(90);
        direction = Richtung.UP;
        this.x = x;
        this.y = y;
    }


    public void go(int z,int farbe) {

        if (farbe == 0) pen.setzeFarbe(Color.WHITE);
        if (farbe == 1) pen.setzeFarbe(Color.RED);
        if (farbe == 2) pen.setzeFarbe(Color.YELLOW);
        if (farbe == 3) pen.setzeFarbe(Color.RED);
        if (farbe == 4) pen.setzeFarbe(Color.GREEN);
        if (farbe == 5) pen.setzeFarbe(Color.RED);
        if ((farbe == 4) && (z<0)) pen.setzeFarbe(Color.RED);

        pen.hoch();
        if (z>0) pen.bewegeUm(z);
        pen.runter();
        pen.setzeFuellMuster(1);
        pen.zeichneKreis(4);
        pen.hoch();
        if (z<0) pen.bewegeUm(z);
        Hilfe.warte(1);
    }

    private void updateDirection(int temp) {

        if (temp == 90){
            if (direction == Richtung.UP) direction = Richtung.LEFT;
            else if (direction == Richtung.LEFT) direction = Richtung.DOWN;
            else if (direction == Richtung.DOWN) direction = Richtung.RIGHT;
            else if (direction == Richtung.RIGHT) direction = Richtung.UP;
        }
        else if (temp == -90){
            if (direction == Richtung.UP) direction = Richtung.RIGHT;
            else if (direction == Richtung.LEFT) direction = Richtung.UP;
            else if (direction == Richtung.DOWN) direction = Richtung.LEFT;
            else if (direction == Richtung.RIGHT) direction = Richtung.DOWN;
        }
    }

    private void updateCoordinates(int i) {

        if (direction == Richtung.UP){
            y = y - i;
        }
        else if (direction == Richtung.DOWN){
            y = y + i;
        }
        else if (direction == Richtung.RIGHT){
            x = x + i;
        }
        else if (direction == Richtung.LEFT) {
            x = x - i;
        }
    }

    public void forwards() {

        gehe(10,4);
        updateCoords(10);
    }


    public void backwards() {

        gehe(-10,4);
        updateCoords(-10);
    }


    public void turn(int r) {

        pen.dreheUm(r);
    }


    public void turnRight() {

        drehe(-90);
        updateRichtung(-90);
    }


    public void turnLeft() {

        drehe(90);
        updateRichtung(90);
    }


    public int check() {

        Color c;
        pen.hoch();
        pen.bewegeUm(10);
        c= img.farbeVon((int) pen.hPosition(),(int) pen.vPosition());
        pen.bewegeUm(-10);

        if (c.equals(Color.WHITE))    return(0);
        if (c.equals(Color.BLACK))    return(1);
        if (c.equals(Color.YELLOW))   return(2);
        if (c.equals(Color.BLUE))     return(3);
        if (c.equals(Color.GREEN))    return(4);
        if (c.equals(Color.RED))      return(5);

        return(-1);
    }
}
