package pglp_9.pglp_9.Command;

import pglp_9.pglp_9.figures.*;
import pglp_9.pglp_9.figures.interfaces.Figures;
import pglp_9.pglp_9.model.FactoryDao;
import pglp_9.pglp_9.model.data.*;

import java.sql.Connection;

public class NotFoundCommand implements Command {
    @Override
    public void execute(){
        System.err.println("SyntaxError: Commande non valide, verifier les parentheses.");
    }
}
