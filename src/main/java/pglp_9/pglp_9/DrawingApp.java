package pglp_9.pglp_9;

import pglp_9.pglp_9.figures.Rectangle;
import pglp_9.pglp_9.figures.FigureComposite;
import pglp_9.pglp_9.figures.Rectangle;
import pglp_9.pglp_9.model.Dao_ConnectionBd;
import pglp_9.pglp_9.model.FactoryDao;
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
        FactoryDao factoryDao = new FactoryDao(connect);
        RectangleDao RectangleDao = (RectangleDao) factoryDao.getRectangleDao();
        RectangleDao.create(r1);
        RectangleDao.create(r2);
        List<Integer> newposition = new ArrayList<Integer>(2);
        newposition.add(15);
        newposition.add(30);
        r1.move(newposition);
        RectangleDao.update(r1);
        RectangleDao.findAll().forEach(Rectangle::afficher);
        RectangleDao.delete(r1);
        RectangleDao.findAll().forEach(Rectangle::afficher);
    }
}
