package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEmailHomePageVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TabEmailHomePageDAO extends BuscaBancoDados {

    ResultSet rs;
    TabEmailHomePageVO tabEmailHomePageVO;

    public TabEmailHomePageVO getTabEmailHomePageVO(int id) {
        getResultSet(String.format("SELECT * FROM tabEmailHomePage WHERE id = %d ORDER BY id", id));
        return tabEmailHomePageVO;
    }

    void getResultSet(String comandoSql) {
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
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public void updateTabEmailHomePageVO(Connection conn, TabEmailHomePageVO emailHomePage) throws SQLException {
        String comandoSql = String.format("UPDATE tabEmailHomePage SET descricao = '%s' WHERE id = %d",
                emailHomePage.getDescricao(), emailHomePage.getId());
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertEmailHomePageVO(Connection conn, TabEmailHomePageVO emailHomePage, int empresa_id, int contato_id) throws SQLException {
        String comandoSql = String.format("INSERT INTO tabEmailHomePage (descricao, isEmail) VALUES('%s', %b)",
                emailHomePage.getDescricao(), emailHomePage.isIsEmail());
        int emailHomePage_id = getInsertBancoDados(conn, comandoSql);
        if (empresa_id > 0)
            new RelEmpresaEmailHomePageDAO().insertRelEmpresaEmailHomePage(conn, empresa_id, emailHomePage_id);
        if (contato_id > 0)
            new RelContatoEmailHomePageDAO().insertRelContatoEmailHomePageVO(conn, contato_id, emailHomePage_id);
        return emailHomePage_id;
    }

    public void deleteEmailHomePageVO(Connection conn, int emailHome_id, int empresa_id, int contato_id) throws SQLException {
        if (emailHome_id < 0) emailHome_id = emailHome_id * (-1);
        if (empresa_id > 0)
            new RelEmpresaEmailHomePageDAO().dedeteRelEmpresaEmailHomePage(conn, empresa_id, emailHome_id);
        if (contato_id > 0)
            new RelContatoEmailHomePageDAO().deleteRelContatoEmailHomePageVO(conn, contato_id, emailHome_id);
        String comandoSql = String.format("DELETE FROM tabEmailHomePage WHERE id = %d", emailHome_id);
        getDeleteBancoDados(conn, comandoSql);
    }

}
