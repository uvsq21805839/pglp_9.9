package pglp_9.pglp_9;

import pglp_9.pglp_9.Command.Command;
import pglp_9.pglp_9.Command.DrawingTUI;
import pglp_9.pglp_9.Command.ExitCommand;
import pglp_9.pglp_9.model.Dao_ConnectionBd;

import java.sql.Connection;
import java.sql.SQLClientInfoException;
import java.util.Scanner;


public class DrawingApp
{
    public static void main( String[] args ) throws Exception {
        DrawingApp drawingApp = new DrawingApp();
        drawingApp.run();
    }
    public void run() throws Exception {
        Connection connect = Dao_ConnectionBd.newConnectionDB();
        if (connect == null){
            throw new SQLClientInfoException();
        }
        Dao_ConnectionBd.createTables(connect,false);
        DrawingTUI drawingTUI = new DrawingTUI(connect);
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "PGLP9.9 :\n"+
                        "Commandes : \n \t-\tAfficher(var) pour afficher une figure.\n" +
                        "\t-\tvar = Cercle((x,y),rayon) pour creer un cercle.\n" +
                        "\t-\tvar = Carree((x,y),cote) pour creer un carree.\n"+
                        "\t-\tvar = Triangle((x,y),taille) pour creer un triangle.\n"+
                        "\t-\tvar = Rectangle((x,y),longueur,largeur) pour creer un rectangle.\n"+
                        "\t-\tvar = Grouper(var1,var2,...,varN) pour grouper plusieurs figures.\n"+
                        "\t-\tMove(var,(x,y)) pour deplacer une figure.\n"+
                        "\t-\tSupprimer(var) pour supprimer une figure.\n"+
                        "\t-\tAfficherTout() pour afficher toutes les figures.\n"+
                        "\t-\tHelp ou ? pour afficher aide.\n"+
                        "\t-\tExit ou Quit pour Quitter.\n"
        );

        String input = scanner.nextLine();
        Command command = drawingTUI.nextCommand(input);
        command.execute();
        while (!command.getClass().equals(ExitCommand.class)){
            System.out.println();
            input = scanner.nextLine();
            command = drawingTUI.nextCommand(input);
            command.execute();
        }


    }
}
