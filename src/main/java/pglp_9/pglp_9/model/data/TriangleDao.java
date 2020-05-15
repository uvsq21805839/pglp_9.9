package pglp_9.pglp_9.model.data;

import pglp_9.pglp_9.figures.Triangle;
import pglp_9.pglp_9.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TriangleDao extends DAO<Triangle> {
    public TriangleDao(final Connection c){
        this.connect = c;
    }

    @Override
    public Triangle find(String variable) {
        Triangle triangle = null;
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT * FROM Triangle WHERE variable = ?");
            prepare.setString(1, variable);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                try {
                    triangle =  new Triangle(
                            result.getString("variable"),
                            result.getDouble("rayon"),
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
        return triangle;
    }

    @Override
    public ArrayList<Triangle> findAll() {
        ArrayList<Triangle> triangles = new ArrayList<Triangle>();
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT VARIABLE FROM Triangle");
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                triangles.add(find(result.getString("variable")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Triangle>();
        }
        return triangles;
    }

    @Override
    public Triangle create(Triangle figures) {
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "INSERT INTO Figures (variable) VALUES (?)");
            prepare.setString(1, figures.getVariable());
            int result = prepare.executeUpdate();
            prepare = connect.prepareStatement(
                    "INSERT INTO Triangle (variable,taille,position_x,position_y) VALUES (?,?,?,?)");
            prepare.setString(1, figures.getVariable());
            prepare.setDouble(2, figures.getTaille());
            prepare.setInt(3, figures.getPosition().get(0));
            prepare.setInt(4, figures.getPosition().get(1));
            result = prepare.executeUpdate();
            assert result == 1;
            System.out.println("Triangle "+figures.getVariable()+" added to db");
        } catch (SQLException e) {
            return null;
        }
        return figures;
    }

    @Override
    public Triangle update(Triangle figures) {
        Triangle toEdit = this.find(figures.getVariable());
        if (toEdit != null) {
            try {
                PreparedStatement prepare = connect.prepareStatement(
                        "UPDATE Triangle " +
                                "SET TAILLE = ?," +
                                "POSITION_X = ?," +
                                " POSITION_Y = ? WHERE VARIABLE = ?");
                prepare.setDouble(1,figures.getTaille());
                prepare.setInt(2, figures.getPosition().get(0));
                prepare.setInt(3, figures.getPosition().get(1));
                prepare.setString(4, figures.getVariable());
                int result = prepare.executeUpdate();
                if (result==1)
                    System.out.println("Triangle "+figures.getVariable()+" deplacé");
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
    public void delete(Triangle figures) {
        try {
            FigureCompositeDao.deleteFromGroupe(connect,figures.getVariable());
            PreparedStatement prepare = connect.prepareStatement(
                    "DELETE FROM Triangle WHERE VARIABLE = ?");
            prepare.setString(1, figures.getVariable());
            int result = prepare.executeUpdate();
            prepare = connect.prepareStatement(
                    "DELETE FROM Figures WHERE VARIABLE = ?");
            prepare.setString(1, figures.getVariable());
            result = prepare.executeUpdate();
            if (result== 1)
            System.out.println("Triangle "+figures.getVariable()+" supprimé");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
