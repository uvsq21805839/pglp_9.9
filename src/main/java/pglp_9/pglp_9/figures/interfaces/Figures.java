package pglp_9.pglp_9.figures.interfaces;

import java.util.List;

public interface Figures {
    String getVariable();
    void afficher();
    void move(List<Integer> _positions);
}
