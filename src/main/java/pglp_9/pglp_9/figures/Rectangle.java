package pglp_9.pglp_9.figures;

import pglp_9.pglp_9.figures.interfaces.Figures;

import java.util.ArrayList;
import java.util.List;

public class Rectangle implements Figures {
    private String variable;
    private List<Integer> position;
    private int longueur;
    private int largeur;
    public Rectangle(String _var,int _long, int _larg, int _x, int _y){
        this.variable = _var;
        this.longueur = _long;
        this.largeur = _larg;
        position = new ArrayList<Integer>(2);
        position.add(_x);
        position.add(_y);
    }
    public String getVariable() {
        return variable;
    }

    public List<Integer> getPosition() {
        return position;
    }

    public void afficher() {
        String stringBuilder = this.variable +
                " = Rectangle" +
                "(centre=(x: " + position.get(0) + ", y: " + position.get(1) + ")" +
                ", longueur= " + this.longueur + ", largeur= " + this.largeur +")";
        System.out.println(stringBuilder);
    }

    public void move(List<Integer> _position) {
        position = _position;
    }
}
