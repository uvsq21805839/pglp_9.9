package pglp_9.pglp_9.figures;

import pglp_9.pglp_9.figures.interfaces.Figures;

import java.util.ArrayList;
import java.util.List;

public class Carree implements Figures {
    private String variable;
    private List<Integer> position;
    private int cote;
    public Carree(String _var,int _ct, int _x, int _y){
        this.variable = _var;
        this.cote = _ct;
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
                " = Carree" +
                "(centre=(x: " + position.get(0) + ", y: " + position.get(1) + ")" +
                ",cote=" + this.cote + ")";
        System.out.println(stringBuilder);
    }

    public void move(List<Integer> _position) {
        position = _position;
    }
}
