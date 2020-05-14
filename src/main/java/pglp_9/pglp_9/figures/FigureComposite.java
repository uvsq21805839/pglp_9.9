package pglp_9.pglp_9.figures;

import pglp_9.pglp_9.figures.interfaces.Figures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FigureComposite implements Figures, Iterable<Figures>  {
    private String variable;
    private List<Figures> childs = new ArrayList<Figures>();

    public FigureComposite(String _var){
        this.variable = _var;
    }

    public void add(Figures _childs){
        childs.add(_childs);
    }

    public void remove(Figures _childs){
        childs.remove(_childs);
    }

    public void afficher() {
        for (Figures item:childs
             ) {
            item.afficher();
        }
    }

    public void move(List<Integer> _positions) {
        for (Figures item:childs
        ) {
            item.move(_positions);  }
    }

    @Override
    public Iterator<Figures> iterator() {
        return childs.iterator();
    }
}
