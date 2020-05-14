package pglp_9.pglp_9.model;

import pglp_9.pglp_9.figures.Carree;
import pglp_9.pglp_9.figures.Cercle;
import pglp_9.pglp_9.figures.Rectangle;
import pglp_9.pglp_9.figures.Triangle;
import pglp_9.pglp_9.model.data.CarreeDao;
import pglp_9.pglp_9.model.data.CercleDao;
import pglp_9.pglp_9.model.data.RectangleDao;
import pglp_9.pglp_9.model.data.TriangleDao;

import java.sql.Connection;

public class FactoryDao {
    private Connection connect;

    public FactoryDao(final Connection _connect) {
        this.connect = _connect;
    }

    public DAO<Cercle> getCercleDao() {
        return new CercleDao(connect);
    }
    public DAO<Rectangle> getRectangleDao() {
        return new RectangleDao(connect);
    }
    public DAO<Triangle> getTriangleDao() {return new TriangleDao(connect);    }
    public DAO<Carree> getCarreeDao() {
        return new CarreeDao(connect);
    }
}
