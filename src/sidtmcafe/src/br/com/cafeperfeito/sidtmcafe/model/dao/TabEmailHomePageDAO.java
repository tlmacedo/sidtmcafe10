package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEmailHomePageVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;

public class TabEmailHomePageDAO extends ServiceBuscaBancoDados {

    TabEmailHomePageVO tabEmailHomePageVO = null;

    public TabEmailHomePageVO getTabEmailHomePageVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM tabEmailHomePage WHERE id = ? ");
        return tabEmailHomePageVO;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY descricao ");
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
        addNewParametro(new Pair<>("String", emailHomePage.getDescricao()));
        addNewParametro(new Pair<>("int", String.valueOf(emailHomePage.getId())));
        String comandoSql = "UPDATE tabEmailHomePage SET descricao = ? WHERE id = ? ";
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertEmailHomePageVO(Connection conn, TabEmailHomePageVO emailHomePage, int empresa_id, int contato_id) throws SQLException {
        addNewParametro(new Pair<>("String", emailHomePage.getDescricao()));
        addParametro(new Pair<>("boolean", emailHomePage.isIsEmail() ? "true" : "false"));
        String comandoSql = "INSERT INTO tabEmailHomePage (descricao, isEmail) VALUES(?, ?) ";
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
        addNewParametro(new Pair<>("int", String.valueOf(emailHome_id)));
        String comandoSql = "DELETE FROM tabEmailHomePage WHERE id = ? ";
        getDeleteBancoDados(conn, comandoSql);
    }

}
