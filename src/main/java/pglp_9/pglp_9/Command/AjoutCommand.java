package pglp_9.pglp_9.Command;

import pglp_9.pglp_9.figures.*;
import pglp_9.pglp_9.figures.interfaces.Figures;
import pglp_9.pglp_9.model.FactoryDao;
import pglp_9.pglp_9.model.data.*;

import java.sql.Connection;

public class AjoutCommand implements Command {
    private Connection connection;
    private Figures figures;
        AjoutCommand(Connection _conn, Figures _fig){
            this.connection = _conn;
            this.figures = _fig;
        }
    @Override
    public void execute() {
        FactoryDao factoryDao = new FactoryDao(connection);
        Figures toAdd = null;
        if (figures.getClass() == Cercle.class) {
            CercleDao cercleDao = (CercleDao)
                    factoryDao.getCercleDao();
            toAdd = cercleDao.create((Cercle) figures);
        } else if (figures.getClass() == Carree.class) {
            CarreeDao carreeDao = (CarreeDao)
                    factoryDao.getCarreeDao();
            toAdd = carreeDao.create((Carree) figures);
        }
         else if (figures.getClass() == Triangle.class) {
            TriangleDao triangleDao = (TriangleDao)
                    factoryDao.getTriangleDao();
            toAdd = triangleDao.create((Triangle) figures);
        }else if (figures.getClass() == Rectangle.class) {
            RectangleDao rectangleDao = (RectangleDao)
                    factoryDao.getRectangleDao();
            toAdd = rectangleDao.create((Rectangle) figures);
        } else if (figures.getClass() == FigureComposite.class) {
            FigureCompositeDao figureCompositeDao = (FigureCompositeDao)
                    factoryDao.getFigureCompositeDao();
            toAdd = figureCompositeDao.create((FigureComposite) figures);
        }
        if (toAdd == null) {
            System.err.println("Cette varriable existe déjà.");
        }
    }
}
