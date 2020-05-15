package pglp_9.pglp_9.model.data;

import pglp_9.pglp_9.figures.Rectangle;
import pglp_9.pglp_9.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RectangleDao extends DAO<Rectangle> {
    public RectangleDao(final Connection c){
        this.connect = c;
    }

    @Override
    public Rectangle find(String variable) {
        Rectangle rectangle = null;
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT * FROM Rectangle WHERE variable = ?");
            prepare.setString(1, variable);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                try {
                    rectangle =  new Rectangle(
                            result.getString("variable"),
                            result.getInt("longueur"),
                            result.getInt("largeur"),
                            result.getInt("position_x"),
                            result.getInt("position_y")
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return rectangle;
    }

    @Override
    public ArrayList<Rectangle> findAll() {
        ArrayList<Rectangle> rectangles = new ArrayList<>();
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT VARIABLE FROM Rectangle");
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                rectangles.add(find(result.getString("variable")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return rectangles;
    }

    @Override
    public Rectangle create(Rectangle figures) {
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "INSERT INTO Figures (variable) VALUES (?)");
            prepare.setString(1, figures.getVariable());
            prepare.executeUpdate();
            prepare = connect.prepareStatement(
                    "INSERT INTO Rectangle (variable,longueur,largeur,position_x,position_y) VALUES (?,?,?,?,?)");
            prepare.setString(1, figures.getVariable());
            prepare.setInt(2, figures.getLongueur());
            prepare.setInt(3, figures.getLargeur());
            prepare.setInt(4, figures.getPosition().get(0));
            prepare.setInt(5, figures.getPosition().get(0));
            int result = prepare.executeUpdate();
            assert result == 1;
            System.out.println("Rectangle "+figures.getVariable()+" added to db");
        } catch (SQLException e) {
            return null;
        }
        return figures;
    }

    @Override
    public Rectangle update(Rectangle figures) {
        Rectangle toEdit = this.find(figures.getVariable());
        if (toEdit != null) {
            try {
                PreparedStatement prepare = connect.prepareStatement(
                        "UPDATE Rectangle " +
                                "SET LONGUEUR = ?," +
                                "LARGEUR = ?," +
                                "POSITION_X = ?," +
                                " POSITION_Y = ? WHERE VARIABLE = ?");
                prepare.setDouble(1,figures.getLongueur());
                prepare.setDouble(2,figures.getLargeur());
                prepare.setInt(3, figures.getPosition().get(0));
                prepare.setInt(4, figures.getPosition().get(1));
                prepare.setString(5, figures.getVariable());
                int result = prepare.executeUpdate();
                if (result==1)
                    System.out.println("Rectangle "+figures.getVariable()+" deplacé");
            } catch (SQLException e) {
                e.printStackTrace();
                return toEdit;
            }
        } else {
            return null;
        }
        return figures;
    }

    @Override
    public void delete(Rectangle figures) {
        try {
            FigureCompositeDao.deleteFromGroupe(connect,figures.getVariable());
            PreparedStatement prepare = connect.prepareStatement(
                    "DELETE FROM Rectangle WHERE VARIABLE = ?");
            prepare.setString(1, figures.getVariable());
            prepare.executeUpdate();
            prepare = connect.prepareStatement(
                    "DELETE FROM Figures WHERE VARIABLE = ?");
            prepare.setString(1, figures.getVariable());
            int result = prepare.executeUpdate();
            if (result== 1)
            System.out.println("Rectangle "+figures.getVariable()+" supprimé");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
