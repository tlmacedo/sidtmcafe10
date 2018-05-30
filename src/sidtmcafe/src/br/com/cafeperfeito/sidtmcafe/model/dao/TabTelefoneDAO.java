package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabTelefoneVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TabTelefoneDAO extends BuscaBancoDados {

    ResultSet rs;

    String comandoSql = "";
    TabTelefoneVO telefoneVO;

    public TabTelefoneVO getTabTelefoneVO(int id) {
        buscaTabTelefoneVO(id);
        if (telefoneVO != null)
            addObjetosPesquisa(telefoneVO);
        return telefoneVO;
    }

    void buscaTabTelefoneVO(int id) {
        comandoSql = "SELECT id, descricao, sisTelefoneOperadora_id " +
                "FROM tabTelefone " +
                "WHERE id = '" + id + "' ";

        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                telefoneVO = new TabTelefoneVO();
                telefoneVO.setId(rs.getInt("id"));
                telefoneVO.setDescricao(rs.getString("descricao"));
                telefoneVO.setSisTelefoneOperadora_id(rs.getInt("sisTelefoneOperadora_id"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    void addObjetosPesquisa(TabTelefoneVO telefoneVO) {
        telefoneVO.setSisTelefoneOperadoraVO(new SisTelefoneOperadoraDAO().getSisTelefoneOperadoraVO(telefoneVO.getSisTelefoneOperadora_id()));
    }

    public void updateTabTelefoneVO(Connection conn, TabTelefoneVO telefoneVO) throws SQLException {
        comandoSql = "UPDATE tabTelefone SET ";
        comandoSql += "descricao = '" + telefoneVO.getDescricao().replaceAll("[\\-/.' \\[\\]]", "") + "', ";
        comandoSql += "sisTelefoneOperadora_id = " + telefoneVO.getSisTelefoneOperadora_id() + " ";
        comandoSql += "WHERE id = '" + telefoneVO.getId() + "' ";

        getInsertBancoDados(conn, comandoSql);

    }

    public int insertTabTelefoneVO(Connection conn, TabTelefoneVO telefoneVO) throws SQLException {
        comandoSql = "INSERT INTO tabTelefone ";
        comandoSql += "(descricao, sisTelefoneOperadora_id) ";
        comandoSql += "VALUES ( ";
        comandoSql += "'" + telefoneVO.getDescricao().replaceAll("[\\-/.' \\[\\]]", "") + "', ";
        comandoSql += telefoneVO.getSisTelefoneOperadora_id();
        comandoSql += ") ";

        return getInsertBancoDados(conn, comandoSql);

    }

    public void deleteTabTelefoneVO(Connection conn, TabTelefoneVO telefoneVO) throws SQLException {
        comandoSql = "DELETE " +
                "FROM tabTelefone " +
                "WHERE id = '" + telefoneVO.getId() + "' ";

        getDeleteBancoDados(conn, comandoSql);
    }

}
