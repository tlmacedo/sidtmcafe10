package br.com.sidtmcafe.interfaces;

public interface BancoDados extends SqlDCL, SqlDML, SqlDDL {

    void abrirConexao();

    void fecharConexao();
}
