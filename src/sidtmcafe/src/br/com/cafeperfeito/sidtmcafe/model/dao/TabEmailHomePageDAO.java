package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEmailHomePageVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TabEmailHomePageDAO extends BuscaBancoDados {

    ResultSet rs;

    String comandoSql = "";
    TabEmailHomePageVO tabEmailHomePageVO;

    public TabEmailHomePageVO getTabEmailHomePageVO(int id) {
        buscaTabEmailHomePageVO(id);
        return tabEmailHomePageVO;
    }

    void buscaTabEmailHomePageVO(int id) {
        comandoSql = String.format("SELECT id, descricao, isEmail FROM tabEmailHomePage WHERE id = %d", id);
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                tabEmailHomePageVO = new TabEmailHomePageVO();
                tabEmailHomePageVO.setId(rs.getInt("id"));
                tabEmailHomePageVO.setDescricao(rs.getString("descricao"));
                tabEmailHomePageVO.setIsEmail(rs.getBoolean("isEmail"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public void updateTabEmailHomePageVO(Connection conn, TabEmailHomePageVO emailHomePage) throws SQLException {
        comandoSql = String.format("UPDATE tabEmailHomePage SET descricao = '%s' WHERE id = %d",
                emailHomePage.getDescricao(), emailHomePage.getId());
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertEmailHomePage(Connection conn, TabEmailHomePageVO emailHomePage, int empresa_id, int contato_id) throws SQLException {
        comandoSql = String.format("INSERT INTO tabEmailHomePage (descricao, isEmail) VALUES('%s', %d)",
                emailHomePage.getDescricao(), emailHomePage.isIsEmail());
        int emailHomePage_id = getInsertBancoDados(conn, comandoSql);
        if (empresa_id > 0)
            new RelEmpresaEmailHomePageDAO().insertRelEmpresaEmailHomePage(conn, empresa_id, emailHomePage_id);
        if (contato_id > 0)
            new RelContatoEmailHomePageDAO().insertRelContatoEmailHomePageVO(conn, contato_id, emailHomePage_id);
        return emailHomePage_id;
    }

    public void deleteEmailHomePage(Connection conn, int emailHome_id, int empresa_id, int contato_id) throws SQLException {
        if (emailHome_id < 0) emailHome_id = emailHome_id * (-1);
        if (empresa_id>0)
        new RelEmpresaEmailHomePageDAO().dedeteRelEmpresaEmailHomePage(conn, emailHome_id, empresa_id);
//        if (contato_id>0)
//            new RelContatoEmailHomePageDAO().deleteRelContatoEmailHomePageVO(conn,);
        comandoSql = "DELETE " +
                "FROM tabEmailHomePage " +
                "WHERE id = " + emailHome_id + " ";

        getDeleteBancoDados(conn, comandoSql);
    }

}
