package pglp_9.pglp_9.figures;

import pglp_9.pglp_9.figures.interfaces.Figures;

import java.util.ArrayList;
import java.util.List;

public class FigureComposite implements Figures  {
    private List<Figures> childs = new ArrayList<Figures>();
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

}
