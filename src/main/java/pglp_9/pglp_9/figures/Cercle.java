package pglp_9.pglp_9.figures;

import pglp_9.pglp_9.figures.interfaces.Figures;

import java.util.ArrayList;
import java.util.List;

public class Cercle implements Figures {
    private String variable;
    private List<Integer> position;
    private double rayon;
    public Cercle(String _var,double _rayon, int _x, int _y){
        this.variable = _var;
        this.rayon = _rayon;
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
                " = Cercle" +
                "(centre=(" + position.get(0) + "," + position.get(1) + ")" +
                ",rayon=" + this.rayon + ")";
        System.out.println(stringBuilder);
    }

    public void move(List<Integer> _position) {
        position = _position;
    }
}
