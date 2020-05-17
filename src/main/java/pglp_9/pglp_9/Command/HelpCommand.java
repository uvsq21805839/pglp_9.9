package pglp_9.pglp_9.Command;

public class HelpCommand implements Command {
    @Override
    public void execute(){
        System.out.println(
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
    }
}
