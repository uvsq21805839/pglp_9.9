package pglp_9.pglp_9.Command;

import pglp_9.pglp_9.figures.*;
import pglp_9.pglp_9.figures.interfaces.Figures;
import pglp_9.pglp_9.model.FactoryDao;
import pglp_9.pglp_9.model.data.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MoveCommand implements Command {
    private final Connection connection;
    private final String variable;
    private final List<Integer> position;
    MoveCommand(Connection _conn, String _var,int _x,int _y){
        this.connection = _conn;
        this.variable = _var;
        this.position = new ArrayList<>(2);
        this.position.add(_x);
        this.position.add(_y);
    }
    @Override
    public void execute() {
        FactoryDao factoryDao = new FactoryDao(connection);
        Figures toMove = FigureCompositeDao.figureExist(connection,variable);
        boolean toEdit = toMove != null;
        if (toEdit){
            CercleDao cercleDao = (CercleDao) factoryDao.getCercleDao();
            CarreeDao carreeDao = (CarreeDao) factoryDao.getCarreeDao();
            RectangleDao rectangleDao = (RectangleDao) factoryDao.getRectangleDao();
            TriangleDao triangleDao = (TriangleDao) factoryDao.getTriangleDao();
            FigureCompositeDao figureCompositeDao = (FigureCompositeDao) factoryDao.getFigureCompositeDao();
            toMove.move(position);
            if (toMove.getClass().equals(Cercle.class))
                cercleDao.update((Cercle)toMove);
            else if (toMove.getClass().equals(Carree.class))
                carreeDao.update((Carree) toMove);
            else if (toMove.getClass().equals(Triangle.class))
                triangleDao.update((Triangle) toMove);
            else if (toMove.getClass().equals(Rectangle.class))
                rectangleDao.update((Rectangle) toMove);
            else
                figureCompositeDao.update((FigureComposite) toMove);
        }else {
            System.err.println("Erreur: La figure "+variable+" est introuvable dans la bd");
        }
    }
}
