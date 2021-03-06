package pglp_9.pglp_9.model.data;

import pglp_9.pglp_9.figures.Carree;
import pglp_9.pglp_9.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarreeDao extends DAO<Carree> {
    public CarreeDao(final Connection c){
        this.connect = c;
    }

    @Override
    public Carree find(String variable) {
        Carree carree = null;
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT * FROM Carree WHERE variable = ?");
            prepare.setString(1, variable);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                try {
                    carree =  new Carree(
                            result.getString("variable"),
                            result.getDouble("cote"),
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
        return carree;
    }

    @Override
    public ArrayList<Carree> findAll() {
        ArrayList<Carree> carrees = new ArrayList<Carree>();
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT VARIABLE FROM Carree");
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                carrees.add(find(result.getString("variable")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Carree>();
        }
        return carrees;
    }

    @Override
    public Carree create(Carree figures) {
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "INSERT INTO Figures (variable) VALUES (?)");
            prepare.setString(1, figures.getVariable());
            int result = prepare.executeUpdate();
            prepare = connect.prepareStatement(
                    "INSERT INTO Carree (variable,COTE,position_x,position_y) VALUES (?,?,?,?)");
            prepare.setString(1, figures.getVariable());
            prepare.setDouble(2, figures.getCote());
            prepare.setInt(3, figures.getPosition().get(0));
            prepare.setInt(4, figures.getPosition().get(0));
            result = prepare.executeUpdate();
            assert result == 1;
            System.out.println("Carree "+figures.getVariable()+" added to db");
        } catch (SQLException e) {
            return null;
        }
        return figures;
    }

    @Override
    public Carree update(Carree figures) {
        Carree toEdit = this.find(figures.getVariable());
        if (toEdit != null) {
            try {
                PreparedStatement prepare = connect.prepareStatement(
                        "UPDATE Carree " +
                                "SET COTE = ?," +
                                "POSITION_X = ?," +
                                " POSITION_Y = ? WHERE VARIABLE = ?");
                prepare.setDouble(1,figures.getCote());
                prepare.setInt(2, figures.getPosition().get(0));
                prepare.setInt(3, figures.getPosition().get(1));
                prepare.setString(4, figures.getVariable());
                int result = prepare.executeUpdate();
                if (result==1)
                    System.out.println("Carree "+figures.getVariable()+" deplacé");
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
    public void delete(Carree figures) {
        try {
            FigureCompositeDao.deleteFromGroupe(connect,figures.getVariable());
            PreparedStatement prepare = connect.prepareStatement(
                    "DELETE FROM Carree WHERE VARIABLE = ?");
            prepare.setString(1, figures.getVariable());
            int result = prepare.executeUpdate();
            prepare = connect.prepareStatement(
                    "DELETE FROM Figures WHERE VARIABLE = ?");
            prepare.setString(1, figures.getVariable());
            result = prepare.executeUpdate();
            if (result== 1)
            System.out.println("Carree "+figures.getVariable()+" supprimé");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
