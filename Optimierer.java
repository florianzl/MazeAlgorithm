import basis.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Optimierer
{    
    //private geher Geher;
    
    private String lowest;

    public BeschriftungsFeld bestLabel;
    
    private Map<Integer, int[]> keyPoints = new HashMap<Integer, int[]>();
    
    public Optimierer(){
       
        bestLabel = new BeschriftungsFeld("bester",600,500,100,30);
        bestLabel = new BeschriftungsFeld("Starten um schnellsten Geher zu finden!",190,70,320,30);
        bestLabel.setzeSchriftGroesse(12);
    }
    
    public void best(int lmax, int rmax, int flmax, int frmax){
        System.out.println("best startet"); 
        int low;
        boolean equal = false; //to know when all the methods are equal to each other
        if(lmax < rmax) {
            low = lmax;
            lowest = "Linksgeher";
        }
        //when all the methods are equal to each other
        else if(lmax == rmax){
            low = lmax;
            equal = true;
        }
        else {
            low = rmax;
            lowest = "Rechtsgeher";
        }
        if(flmax < low){
            low = flmax;
            lowest = "G-R-geher";
        }
        if(frmax < low){
            low = frmax;
            lowest = "G-L-geher";
        }

        if(equal == false)bestLabel.setzeText(lowest + " war mit " + low + " Schritten am schnellsten!");
        else bestLabel.setzeText("Alle Geher waren gleich schnell!"); 
    }
    
    private void woIsTheBest(){
        if (lowest == "Linksgeher");{
            optimizeLeft();
        }
        if (lowest == "rechtsgeher"){
            //optimizeRight();
        }
        if (lowest == "G-L-geher"){
            //optimizeFLeft();
        }
        if (lowest == "G-R-geher"){
            //optimizeFRight();
        }
    }
    
    /*public void iAmTheBest(String way){
        if(way  == "left") geher.links;
        else if(way == "right") geher.rechts;
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
    
    /*public void optimizeLeft(){
        labgeher.left();
    }*/
}
