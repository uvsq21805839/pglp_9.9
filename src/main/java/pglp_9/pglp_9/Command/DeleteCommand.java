package pglp_9.pglp_9.Command;

import pglp_9.pglp_9.figures.*;
import pglp_9.pglp_9.figures.interfaces.Figures;
import pglp_9.pglp_9.model.FactoryDao;
import pglp_9.pglp_9.model.data.*;

import java.sql.Connection;

public class DeleteCommand implements Command {
    private final Connection connection;
    private final String variable;
    DeleteCommand(Connection _conn, String _var){
        this.connection = _conn;
        this.variable = _var;
    }
    @Override
    public void execute() {
        FactoryDao factoryDao = new FactoryDao(connection);
        Figures toDelete = FigureCompositeDao.figureExist(connection,variable);
        boolean isDelete = toDelete != null;
        if (isDelete){
            CercleDao cercleDao = (CercleDao) factoryDao.getCercleDao();
            CarreeDao carreeDao = (CarreeDao) factoryDao.getCarreeDao();
            RectangleDao rectangleDao = (RectangleDao) factoryDao.getRectangleDao();
            TriangleDao triangleDao = (TriangleDao) factoryDao.getTriangleDao();
            FigureCompositeDao figureCompositeDao = (FigureCompositeDao) factoryDao.getFigureCompositeDao();
            if (toDelete.getClass().equals(Cercle.class))
                cercleDao.delete((Cercle)toDelete);
            else if (toDelete.getClass().equals(Carree.class))
                carreeDao.delete((Carree) toDelete);
            else if (toDelete.getClass().equals(Triangle.class))
                triangleDao.delete((Triangle) toDelete);
            else if (toDelete.getClass().equals(Rectangle.class))
                rectangleDao.delete((Rectangle) toDelete);
            else
                figureCompositeDao.delete((FigureComposite) toDelete);
        }else {
            System.err.println("Erreur: La figure "+variable+" est introuvable dans la bd");
        }
    }
}
