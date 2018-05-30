package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEnderecoVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

//import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class TabEnderecoDAO extends BuscaBancoDados {

    ResultSet rs;

    String comandoSql = "";
    TabEnderecoVO tabEnderecoVO;

    public TabEnderecoVO getTabEnderecoVO(int id) {
        buscaTabEnderecoVO(id);
        if (tabEnderecoVO != null)
            addObjetosPesquisa(tabEnderecoVO);
        return tabEnderecoVO;
    }

    void buscaTabEnderecoVO(int id) {
        comandoSql = "SELECT id, sisTipoEndereco_id, cep, logradouro, numero, complemento, bairro, " +
                "sisMunicipio_id, pontoReferencia " +
                "FROM TabEndereco " +
                "WHERE id = '" + id + "' ";

        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                tabEnderecoVO = new TabEnderecoVO();
                tabEnderecoVO.setId(rs.getInt("id"));
                tabEnderecoVO.setSisTipoEndereco_id(rs.getInt("sisTipoEndereco_id"));
                tabEnderecoVO.setCep(rs.getString("cep"));
                tabEnderecoVO.setLogradouro(rs.getString("logradouro"));
                tabEnderecoVO.setNumero(rs.getString("numero"));
                tabEnderecoVO.setComplemento(rs.getString("complemento"));
                tabEnderecoVO.setBairro(rs.getString("bairro"));
                tabEnderecoVO.setSisMunicipio_id(rs.getInt("sisMunicipio_id"));
                tabEnderecoVO.setPontoReferencia(rs.getString("pontoReferencia"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    void addObjetosPesquisa(TabEnderecoVO endereco) {
        endereco.setSisMunicipioVO(new SisMunicipioDAO().getSisMunicipioVO(endereco.getSisMunicipio_id()));
        endereco.setSisTipoEnderecoVO(new SisTipoEnderecoDAO().getSisTipoEnderecoVO(endereco.getSisTipoEndereco_id()));
    }

    public void updateTabEnderecoVO(Connection conn, TabEnderecoVO enderecoVO) throws SQLException {
        comandoSql = "UPDATE tabEndereco SET ";
        comandoSql += "sisTipoEndereco_id = " + enderecoVO.getSisTipoEndereco_id() + ", ";
        comandoSql += "cep = '" + enderecoVO.getCep() + "', ";
        comandoSql += "logradouro = '" + enderecoVO.getLogradouro() + "', ";
        comandoSql += "numero = '" + enderecoVO.getNumero() + "', ";
        comandoSql += "complemento = '" + enderecoVO.getComplemento() + "', ";
        comandoSql += "bairro = '" + enderecoVO.getBairro() + "', ";
        comandoSql += "sisMunicipio_id = " + enderecoVO.getSisMunicipio_id() + ", ";
        comandoSql += "pontoReferencia = '" + enderecoVO.getPontoReferencia() + "' ";
        comandoSql += "WHERE id = " + enderecoVO.getId();

        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabEnderecoVO(Connection conn, TabEnderecoVO enderecoVO) throws SQLException {
        comandoSql = "INSERT INTO tabEndereco ";
        comandoSql += "(sisTipoEndereco_id, cep, logradouro, numero, complemento, bairro, sisMunicipio_id, pontoReferencia) ";
        comandoSql += "VALUES(";
        comandoSql += enderecoVO.getSisTipoEndereco_id() + ", ";
        comandoSql += "'" + enderecoVO.getCep().replaceAll("[\\-/. \\[\\]]", "") + "', ";
        comandoSql += "'" + enderecoVO.getLogradouro() + "', ";
        comandoSql += "'" + enderecoVO.getNumero() + "', ";
        comandoSql += "'" + enderecoVO.getComplemento() + "', ";
        comandoSql += "'" + enderecoVO.getBairro() + "', ";
        comandoSql += enderecoVO.getSisMunicipio_id() + ", ";
        comandoSql += "'" + enderecoVO.getPontoReferencia() + "'";
        comandoSql += ") ";

        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabEnderecoVO(Connection conn, TabEnderecoVO enderecoVO) throws SQLException {
        int idEndereco = enderecoVO.getId();
        if (idEndereco < 0) idEndereco = idEndereco * (-1);
        comandoSql = "DELETE " +
                "FROM tabEndereco " +
                "WHERE id = " + idEndereco + " ";

        getDeleteBancoDados(conn, comandoSql);
    }

}
