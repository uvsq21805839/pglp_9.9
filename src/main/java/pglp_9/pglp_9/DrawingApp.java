package pglp_9.pglp_9;

import pglp_9.pglp_9.figures.Cercle;
import pglp_9.pglp_9.figures.Rectangle;
import pglp_9.pglp_9.figures.FigureComposite;
import pglp_9.pglp_9.model.Dao_ConnectionBd;
import pglp_9.pglp_9.model.FactoryDao;
import pglp_9.pglp_9.model.data.FigureCompositeDao;
import pglp_9.pglp_9.model.data.RectangleDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DrawingApp
{
    public static void main( String[] args ) throws SQLException {
        Connection connect = Dao_ConnectionBd.newConnectionDB();
        if (connect!= null)
        Dao_ConnectionBd.createTables(connect,true);
        Rectangle r1 = new Rectangle("r1",10,5,0, 2);
        Rectangle r2 = new Rectangle("r2",15,1,1, 3);

        Cercle c1 = new Cercle("c1",15,1,1);
        Cercle c2 = new Cercle("c2",5,1,2);

        FactoryDao factoryDao = new FactoryDao(connect);

        RectangleDao RectangleDao = (RectangleDao) factoryDao.getRectangleDao();
        FigureCompositeDao fig = (FigureCompositeDao) factoryDao.getFigureCompositeDao();

        FigureComposite groupe1 = new FigureComposite("g1");
        groupe1.add(r1);
        groupe1.add(c1);
        groupe1.add(c2);
        RectangleDao.create(r1);
        RectangleDao.create(r2);

        List<Integer> newposition = new ArrayList<Integer>(2);
        newposition.add(15);
        newposition.add(30);
        r1.move(newposition);
        RectangleDao.update(r1);
       fig.create(groupe1);
       FigureComposite g = fig.find("g1");
       if (g == null)
           System.out.println("no found");
       else g.afficher();
       newposition = new ArrayList<>(2);
       newposition.add(100);
       newposition.add(100);
       groupe1.move(newposition);
       fig.update(groupe1);
       RectangleDao.delete(r1);
        g = fig.find("g1");
        if (g == null)
            System.out.println("no found");
        else g.afficher();
      // fig.delete(groupe1);

    }
}
