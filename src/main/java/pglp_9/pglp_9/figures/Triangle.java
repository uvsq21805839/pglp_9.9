package pglp_9.pglp_9.figures;

import pglp_9.pglp_9.figures.interfaces.Figures;

import java.util.ArrayList;
import java.util.List;

public class Triangle implements Figures {
    private String variable;
    private List<Integer> position;
    private double taille;
    public Triangle(String _var,double _tail, int _x, int _y){
        this.variable = _var;
        this.taille = _tail;
        position = new ArrayList<Integer>(2);
        position.add(_x);
        position.add(_y);
    }

    public double getTaille() {
        return taille;
    }

    public String getVariable() {
        return variable;
    }

    public List<Integer> getPosition() {
        return position;
    }

    public void afficher() {
        String stringBuilder = this.variable +
                " = Triangle" +
                "(centre=(x: " + position.get(0) + ", y: " + position.get(1) + ")" +
                ", Taille 3 cotes = " + this.taille +")";
        System.out.println(stringBuilder);
    }

    public void move(List<Integer> _positions) {
        position = _positions;
    }
}
