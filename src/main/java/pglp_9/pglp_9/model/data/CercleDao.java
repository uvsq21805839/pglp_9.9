package pglp_9.pglp_9.model.data;

import pglp_9.pglp_9.figures.Cercle;
import pglp_9.pglp_9.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CercleDao extends DAO<Cercle> {
    public CercleDao(final Connection c){
        this.connect = c;
    }

    @Override
    public Cercle find(String variable) {
        Cercle cercle = null;
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT * FROM Cercle WHERE variable = ?");
            prepare.setString(1, variable);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                try {
                    cercle =  new Cercle(
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
        return cercle;
    }

    @Override
    public ArrayList<Cercle> findAll() {
        ArrayList<Cercle> cercles = new ArrayList<Cercle>();
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT VARIABLE FROM Cercle");
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                cercles.add(find(result.getString("variable")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Cercle>();
        }
        return cercles;
    }

    @Override
    public Cercle create(Cercle figures) {
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "INSERT INTO Figures (variable) VALUES (?)");
            prepare.setString(1, figures.getVariable());
            int result = prepare.executeUpdate();
            prepare = connect.prepareStatement(
                    "INSERT INTO Cercle (variable,rayon,position_x,position_y) VALUES (?,?,?,?)");
            prepare.setString(1, figures.getVariable());
            prepare.setDouble(2, figures.getRayon());
            prepare.setInt(3, figures.getPosition().get(0));
            prepare.setInt(4, figures.getPosition().get(0));
            result = prepare.executeUpdate();
            assert result == 1;
            System.out.println("Cercle added to db");
        } catch (SQLException e) {
            return null;
        }
        return figures;
    }

    @Override
    public Cercle update(Cercle figures) {
        Cercle toEdit = this.find(figures.getVariable());
        if (toEdit != null) {
            try {
                PreparedStatement prepare = connect.prepareStatement(
                        "UPDATE Cercle " +
                                "SET RAYON = ?," +
                                "POSITION_X = ?," +
                                " POSITION_Y = ? WHERE VARIABLE = ?");
                prepare.setDouble(1,figures.getRayon());
                prepare.setInt(2, figures.getPosition().get(0));
                prepare.setInt(3, figures.getPosition().get(1));
                prepare.setString(4, figures.getVariable());
                int result = prepare.executeUpdate();
                if (result==1)
                    System.out.println("Cercle deplacé");
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
    public void delete(Cercle figures) {
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "DELETE FROM Cercle WHERE VARIABLE = ?");
            prepare.setString(1, figures.getVariable());
            int result = prepare.executeUpdate();
            prepare = connect.prepareStatement(
                    "DELETE FROM Figures WHERE VARIABLE = ?");
            prepare.setString(1, figures.getVariable());
            result = prepare.executeUpdate();
            if (result== 1)
            System.out.println("Cercle supprimé");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
