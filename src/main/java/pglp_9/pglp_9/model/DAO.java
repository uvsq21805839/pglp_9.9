package pglp_9.pglp_9.model;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class DAO<T> {

    protected Connection connect;

    /**
     * recherche T dans bd.
     * @param variable Le nom de l'objet
     * @return T  la figure
     */
    public abstract T find(String variable);

    public abstract ArrayList<T> findAll();

    public abstract T create(T figures);

    public abstract T update(T figures);

    public abstract void delete(T figures);
}
