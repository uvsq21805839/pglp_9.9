package pglp_9.pglp_9.model;

import pglp_9.pglp_9.figures.*;
import pglp_9.pglp_9.model.data.*;

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
    public DAO<FigureComposite> getFigureCompositeDao(){ return new FigureCompositeDao(connect);
    }
}
