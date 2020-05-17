package pglp_9.pglp_9.Command;


import pglp_9.pglp_9.figures.*;
import pglp_9.pglp_9.figures.interfaces.Figures;
import pglp_9.pglp_9.model.FactoryDao;
import pglp_9.pglp_9.model.data.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class AfficherToutCommand implements Command {
    private final Connection connection;
    AfficherToutCommand(Connection _conn){
        this.connection = _conn;
    }
    @Override
    public void execute() {
        FactoryDao factoryDao = new FactoryDao(connection);
        CercleDao cercleDao = (CercleDao) factoryDao.getCercleDao();
        CarreeDao carreeDao = (CarreeDao) factoryDao.getCarreeDao();
        TriangleDao triangleDao = (TriangleDao) factoryDao.getTriangleDao();
        RectangleDao rectangleDao = (RectangleDao) factoryDao.getRectangleDao();
        ArrayList<Cercle> cercles =cercleDao.findAll();
        ArrayList<Carree> carrees =carreeDao.findAll();
        ArrayList<Triangle> triangles =triangleDao.findAll();
        ArrayList<Rectangle> rectangles =rectangleDao.findAll();
        List<Figures> figuresList = new ArrayList<>(cercles);
        figuresList.addAll(carrees);
        figuresList.addAll(triangles);
        figuresList.addAll(rectangles);

        if (figuresList.isEmpty()){
            System.err.println("Aucune Figure trouvé");
        }else {
            for (Figures value:figuresList){
                if (!value.getClass().equals(FigureComposite.class))
                    value.afficher();
            }
        }


    }
}
