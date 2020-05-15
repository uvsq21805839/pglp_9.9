package pglp_9.pglp_9.model.data;

import pglp_9.pglp_9.figures.*;
import pglp_9.pglp_9.figures.interfaces.Figures;
import pglp_9.pglp_9.model.DAO;
import pglp_9.pglp_9.model.FactoryDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class FigureCompositeDao extends DAO<FigureComposite> {
    public FigureCompositeDao(final Connection _connection){
        this.connect = _connection;
    }
    @Override
    public FigureComposite find(String variable) {
        FigureComposite result = null;
        try {
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "SELECT VARIABLE FROM FIGURECOMPOSITE where VARIABLE = ? "
            );
            preparedStatement.setString(1,variable);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                try {
                    result = new FigureComposite(resultSet.getString(1));
                    ArrayList<Figures> resChilds = getGroupChilds(variable);
                    FigureComposite finalResult = result;
                    resChilds.forEach(finalResult::add);
                    result = finalResult;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    private ArrayList<Figures> getGroupChilds(String variable) {
        ArrayList<Figures> childs = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "SELECT VAR_FIG FROM GROUPE where VAR_GROUP = ? "
            );
            preparedStatement.setString(1,variable);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Figures figures;
                FactoryDao factoryDao = new FactoryDao(connect);
                CercleDao cercleDao = (CercleDao) factoryDao.getCercleDao();
                CarreeDao carreeDao = (CarreeDao) factoryDao.getCarreeDao();
                TriangleDao triangleDao = (TriangleDao) factoryDao.getTriangleDao();
                RectangleDao rectangleDao = (RectangleDao) factoryDao.getRectangleDao();
                FigureCompositeDao figureCompositeDao = (FigureCompositeDao) factoryDao.getFigureCompositeDao();
                figures = cercleDao.find(resultSet.getString(1));
                if (figures == null)
                    figures = carreeDao.find(resultSet.getString(1));
                if (figures == null)
                    figures = triangleDao.find(resultSet.getString(1));
                if (figures == null)
                    figures = rectangleDao.find(resultSet.getString(1));
                if (figures == null)
                    figures = figureCompositeDao.find(resultSet.getString(1));
                childs.add(figures);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return childs;
    }

    @Override
    public ArrayList<FigureComposite> findAll() {
        ArrayList<FigureComposite> figureComposites = new ArrayList<>();
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT VARIABLE FROM FIGURECOMPOSITE");
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                figureComposites.add(find(result.getString(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return figureComposites;
    }

    @Override
    public FigureComposite create(FigureComposite figures) {
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "INSERT INTO FIGURES (VARIABLE) VALUES (?)"
            );
            prepare.setString(1, figures.getVariable());
            prepare.executeUpdate();
            prepare = connect.prepareStatement(
                    "INSERT INTO FigureComposite (VARIABLE)"
                            + "VALUES (?)");
            prepare.setString(1, figures.getVariable());
            int result = prepare.executeUpdate();
            assert result == 1;
            Iterator<Figures> it = figures.iterator();
            FactoryDao factoryDao = new FactoryDao(connect);
            while (it.hasNext()) {
                Figures fig = it.next();
                switch (fig.getClass().getName()){
                    case "pglp_9.pglp_9.figures.Cercle":
                        Cercle cercle = (Cercle) fig;
                        CercleDao cercleDao = (CercleDao) factoryDao.getCercleDao();
                        Cercle oldCercle =cercleDao.find(cercle.getVariable());
                        if (oldCercle == null)
                            cercleDao.create(cercle);
                        else {
                            if (!oldCercle.getPosition().get(0).equals(cercle.getPosition().get(0))
                            || !oldCercle.getPosition().get(1).equals(cercle.getPosition().get(1)))
                                cercleDao.update(cercle);
                        }
                        break;
                    case "pglp_9.pglp_9.figures.Carree":
                        Carree carree = (Carree) fig;
                        CarreeDao carreeDao = (CarreeDao) factoryDao.getCarreeDao();
                        if (carreeDao.find(carree.getVariable()) == null)
                            carreeDao.create(carree);
                        Carree oldCarree =carreeDao.find(carree.getVariable());
                        if (oldCarree == null)
                            carreeDao.create(carree);
                        else {
                            if (!oldCarree.getPosition().get(0).equals(carree.getPosition().get(0))
                                    || !oldCarree.getPosition().get(1).equals(carree.getPosition().get(1)))
                                carreeDao.update(carree);
                        }
                        break;
                    case "pglp_9.pglp_9.figures.Triangle":
                        Triangle triangle = (Triangle) fig;
                        TriangleDao triangleDao = (TriangleDao) factoryDao.getTriangleDao();
                        Triangle oldTriangle =triangleDao.find(triangle.getVariable());
                        if (oldTriangle == null)
                            triangleDao.create(triangle);
                        else {
                            if (!oldTriangle.getPosition().get(0).equals(triangle.getPosition().get(0))
                                    || !oldTriangle.getPosition().get(1).equals(triangle.getPosition().get(1)))
                                triangleDao.update(triangle);
                        }
                        break;
                    case "pglp_9.pglp_9.figures.Rectangle":
                        Rectangle rectangle = (Rectangle) fig;
                        RectangleDao rectangleDao = (RectangleDao) factoryDao.getRectangleDao();
                        Rectangle oldRectangle =rectangleDao.find(rectangle.getVariable());
                        if (oldRectangle == null)
                            rectangleDao.create(rectangle);
                        else {
                            if (!oldRectangle.getPosition().get(0).equals(rectangle.getPosition().get(0))
                                    || !oldRectangle.getPosition().get(1).equals(rectangle.getPosition().get(1)))
                                rectangleDao.update(rectangle);
                        }
                        break;
                    default:
                        FigureComposite figureComposite = (FigureComposite) fig;
                        create(figureComposite);
                }
                if (!groupeExist(figures.getVariable(),fig.getVariable())){
                    prepare = connect.prepareStatement(
                            "INSERT INTO GROUPE (var_group, var_fig) VALUES (?,?)"
                    );
                    prepare.setString(1,figures.getVariable());
                    prepare.setString(2,fig.getVariable());
                    prepare.executeUpdate();
                }
            }
            System.out.println("Groupe "+figures.getVariable()+" créé avec success");
        } catch (SQLException e) {
            return null;
        }
        return figures;
    }

    private boolean groupeExist(String variable, String variable1) {
        boolean response = false;
        try {
          PreparedStatement preparedStatement = connect.prepareStatement(
                  "SELECT COUNT(id) FROM GROUPE where VAR_GROUP = ? and VAR_FIG = ?"
          );
          preparedStatement.setString(1,variable);
            preparedStatement.setString(2,variable1);
          ResultSet resultSet = preparedStatement.executeQuery();
          if (resultSet.next())
              response = resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return response;
    }

    @Override
    public FigureComposite update(FigureComposite newFigComposite) {
        FigureComposite toEdit = this.find(newFigComposite.getVariable());
        if (toEdit != null) {
            delete(toEdit);
            if (create(newFigComposite) == null){
                delete(newFigComposite);
                create(toEdit);
                return toEdit;
            }
            System.out.println("Groupe "+newFigComposite.getVariable() +" déplacé");
        } else {
            return null;
        }
        return newFigComposite;
    }

    @Override
    public void delete(FigureComposite figures) {
        try {
            deleteGroupe(figures.getVariable());
            PreparedStatement prepare = connect.prepareStatement(
                    "DELETE FROM FIGURECOMPOSITE WHERE VARIABLE = ?");
            prepare.setString(1, figures.getVariable());
            prepare.executeUpdate();
            prepare = connect.prepareStatement(
                    "DELETE FROM FIGURES WHERE VARIABLE = ?");
            prepare.setString(1, figures.getVariable());
           int result = prepare.executeUpdate();
            assert result == 1;
            System.out.println("Groupe "+figures.getVariable()+" supprimé avec success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteGroupe(String variable) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "DELETE FROM GROUPE where VAR_GROUP = ?"
            );
            preparedStatement.setString(1,variable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFromGroupe(Connection _con, String variable) {
        try {
            PreparedStatement preparedStatement = _con.prepareStatement(
                    "DELETE FROM GROUPE where VAR_FIG = ?"
            );
            preparedStatement.setString(1,variable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
