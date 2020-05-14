package pglp_9.pglp_9.model;

import java.sql.*;

public abstract class  Dao_ConnectionBd {
    /**
     * Création de la base de données
     * @return Connection
     * @throws SQLException
     */
    public static Connection newConnectionDB() throws SQLException {
        try {
            return DriverManager.getConnection(
                    "jdbc:derby:FiguresDb;create=true;update=true");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Création de toutes les tables de la base de données.
     * @param connect Le connecteur a la base
     * @throws SQLException
     */
    public static void createTables(final Connection connect,Boolean forced )
            throws SQLException {
        DatabaseMetaData databaseMetadata = connect.getMetaData();
        String drop_figure = "DROP TABLE Figures";
        String figure =
                "CREATE TABLE Figures (" +
                        "variable varchar(25)," +
                        "PRIMARY KEY (variable)" +
                        ")";

        String drop_cercle = "DROP TABLE Cercle";
        String cercle = "CREATE TABLE Cercle (" +
                "variable varchar(25)," +
                "position_x int," +
                "position_y int," +
                "Rayon double," +
                "PRIMARY KEY (variable)," +
                "FOREIGN KEY (variable) REFERENCES Figures (variable)" +
                ")";

        String drop_carree = "DROP TABLE Carree";
        String carree = "CREATE TABLE Carree (" +
                "variable varchar(25)," +
                "position_x int," +
                "position_y int," +
                "cote int," +
                "PRIMARY KEY (variable)," +
                "FOREIGN KEY (variable) REFERENCES Figures (variable)" +
                ")";

        String drop_triangle = "DROP TABLE Triangle";
        String triangle = "CREATE TABLE Triangle (" +
                "variable varchar(25)," +
                "position_x int," +
                "position_y int," +
                "taille double," +
                "PRIMARY KEY (variable)," +
                "FOREIGN KEY (variable) REFERENCES Figures (variable)" +
                ")";

        String drop_rectangle = "DROP TABLE Rectangle";
        String rectangle = "CREATE TABLE Rectangle (" +
                "variable varchar(25)," +
                "position_x int," +
                "position_y int," +
                "longueur int," +
                "largeur int," +
                "PRIMARY KEY (variable)," +
                "FOREIGN KEY (variable) REFERENCES Figures (variable)" +
                ")";

        String drop_groupe = "DROP TABLE Groupe";
        String groupe = "CREATE TABLE Groupe (" +
                "variable varchar(25),"+
                "PRIMARY KEY (variable),"+
                "FOREIGN KEY (variable) REFERENCES Figures (variable)"+
                ")";

        String drop_figureComposite = "DROP TABLE figureComposite";
        String figureComposite = "CREATE TABLE figureComposite ("+
                "variable varchar(25),"+
                "PRIMARY KEY (variable),"+
                "FOREIGN KEY (variable) REFERENCES Groupe (variable),"+
                "FOREIGN KEY (variable) REFERENCES Figures (variable)"+
                ")";

        Statement s = connect.createStatement();
        ResultSet resultSet = databaseMetadata.getTables(null,"APP","FIGURES",null);
        if (forced) {
            if (resultSet.next()){
                //cercle
                resultSet = databaseMetadata.getTables(null,"APP","CERCLE",null);
                if (resultSet.next())
                    s.execute(drop_cercle);
                //carre
                resultSet = databaseMetadata.getTables(null,"APP","CARREE",null);
                if (resultSet.next())
                    s.execute(drop_carree);
                //rectangle
                resultSet = databaseMetadata.getTables(null,"APP","RECTANGLE",null);
                if (resultSet.next())
                    s.execute(drop_rectangle);
                //triangle
                resultSet = databaseMetadata.getTables(null,"APP","TRIANGLE",null);
                if (resultSet.next())
                    s.execute(drop_triangle);
                //groupeComposite
                resultSet = databaseMetadata.getTables(null,"APP","FIGURECOMPOSITE",null);
                if (resultSet.next())
                    s.execute(drop_figureComposite);
                //groupe
                resultSet = databaseMetadata.getTables(null,"APP","GROUPE",null);
                if (resultSet.next())
                    s.execute(drop_groupe);
                s.execute(drop_figure);
            }
            s.execute(figure);
            s.execute(cercle);
            s.execute(carree);
            s.execute(rectangle);
            s.execute(triangle);
            s.execute(groupe);
            s.execute(figureComposite);
        }else {
            if (!resultSet.next())
                s.execute(figure);
            resultSet = databaseMetadata.getTables(null,"APP","CERCLE",null);
            if (!resultSet.next())
                s.execute(cercle);
            //carree
            resultSet = databaseMetadata.getTables(null,"APP","CARREE",null);
            if (!resultSet.next())
                s.execute(carree);
            //triangle
            resultSet = databaseMetadata.getTables(null,"APP","TRIANGLE",null);
            if (!resultSet.next())
                s.execute(triangle);
            //rectangle
            resultSet = databaseMetadata.getTables(null,"APP","RECTANGLE",null);
            if (!resultSet.next())
                s.execute(rectangle);
            //groupe
            resultSet = databaseMetadata.getTables(null,"APP","GROUPE",null);
            if (!resultSet.next())
                s.execute(groupe);
            //figureComposite
            resultSet = databaseMetadata.getTables(null,"APP","FIGURECOMPOSITE",null);
            if (!resultSet.next())
                s.execute(figureComposite);
        }
    }
}
