package pglp_9.pglp_9;

import pglp_9.pglp_9.figures.Cercle;
import pglp_9.pglp_9.figures.FigureComposite;
import pglp_9.pglp_9.figures.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Cercle c1 = new Cercle("c1",10,0,0);
        Cercle c2 = new Cercle("c2",10,0,1);
        Rectangle r1 = new Rectangle("r1",10,5,0,0);
        Rectangle r2 = new Rectangle("r2",8,4,1,1);
        FigureComposite cercleComposite = new FigureComposite();
        cercleComposite.add(c1);
        cercleComposite.add(c2);
        FigureComposite rectangleCoposite = new FigureComposite();
        rectangleCoposite.add(r1);
        rectangleCoposite.add(r2);
        rectangleCoposite.afficher();
        List<Integer> newposition = new ArrayList<Integer>(2);
        newposition.add(15);
        newposition.add(30);
        rectangleCoposite.move(newposition);
        rectangleCoposite.afficher();
        cercleComposite.afficher();
    }
}
