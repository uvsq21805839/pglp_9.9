package pglp_9.pglp_9.Command;


import pglp_9.pglp_9.figures.interfaces.Figures;
import pglp_9.pglp_9.model.data.*;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class AfficherCommand implements Command {
    private final Connection connection;
    private final ArrayList<String> variables;
    AfficherCommand(Connection _conn, ArrayList<String> _vars){
        this.connection = _conn;
        this.variables = _vars;
    }
    @Override
    public void execute() {
        Figures toShow;
        for (String value:variables){
            toShow = FigureCompositeDao.figureExist(connection,value);
            if (toShow !=null)
                toShow.afficher();
            else System.err.println("la figure "+value+" est introuvable.");
        }
    }
}
