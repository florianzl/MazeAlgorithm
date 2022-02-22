import basis.*;
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
        direction = Direction.UP;
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
            if (direction == Direction.UP) direction = Direction.LEFT;
            else if (direction == Direction.LEFT) direction = Direction.DOWN;
            else if (direction == Direction.DOWN) direction = Direction.RIGHT;
            else if (direction == Direction.RIGHT) direction = Direction.UP;
        }
        else if (temp == -90){
            if (direction == Direction.UP) direction = Direction.RIGHT;
            else if (direction == Direction.LEFT) direction = Direction.UP;
            else if (direction == Direction.DOWN) direction = Direction.LEFT;
            else if (direction == Direction.RIGHT) direction = Direction.DOWN;
        }
    }

    private void updateCoordinates(int i) {

        if (direction == Direction.UP){
            y = y - i;
        }
        else if (direction == Direction.DOWN){
            y = y + i;
        }
        else if (direction == Direction.RIGHT){
            x = x + i;
        }
        else if (direction == Direction.LEFT) {
            x = x - i;
        }
    }

    public void forwards() {

        go(10,4);
        updateCoordinates(10);
    }


    public void backwards() {

        go(-10,4);
        updateCoordinates(-10);
    }


    public void turn(int r) {

        pen.dreheUm(r);
    }


    public void turnRight() {

        turn(-90);
        updateDirection(-90);
    }


    public void turnLeft() {

        turn(90);
        updateDirection(90);
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
