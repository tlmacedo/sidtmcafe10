package br.com.tlmacedo.cafeperfeito.interfaces.jdbc;

public interface SqlDDL {

    void create(String query);

    void alter(String query);

    void drop(String query);
}
