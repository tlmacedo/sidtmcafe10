package br.com.cafeperfeito.sidtmcafe.interfaces;

public interface BancoDados extends SqlDCL, SqlDML, SqlDDL {

    void abrirConexao();

    void fecharConexao();
}
