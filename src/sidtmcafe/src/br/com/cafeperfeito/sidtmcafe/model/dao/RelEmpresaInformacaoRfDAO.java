package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEmpresaInformacaoRfVO;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEmpresaInformacaoRfDAO extends BuscaBancoDados {

    RelEmpresaInformacaoRfVO relEmpresaInformacaoRfVO = null;
    List<RelEmpresaInformacaoRfVO> relEmpresaInformacaoRfVOList = null;

    public RelEmpresaInformacaoRfVO getRelEmpresaInformacaoRfVO(int empresa_id, int informacaoRf_id) {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(informacaoRf_id)));
        getResultSet("SELECT * FROM relEmpresaInformacaoRF WHERE tabEmpresa_id = ? AND tabInformacaoReceitaFederal_id = ?");
        return relEmpresaInformacaoRfVO;
    }

    public List<RelEmpresaInformacaoRfVO> getRelEmpresaInformacaoRfVOList(int empresa_id) {
        relEmpresaInformacaoRfVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        getResultSet("SELECT * FROM relEmpresaInformacaoRF WHERE tabEmpresa_id = ? ");
        return relEmpresaInformacaoRfVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabEmpresa_id, tabInformacaoReceitaFederal_id");
        try {
            while (rs.next()) {
                relEmpresaInformacaoRfVO = new RelEmpresaInformacaoRfVO();
                relEmpresaInformacaoRfVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresaInformacaoRfVO.setTabInformacaoReceitaFederal_id(rs.getInt("tabInformacaoReceitaFederal_id"));
                if (relEmpresaInformacaoRfVOList != null) relEmpresaInformacaoRfVOList.add(relEmpresaInformacaoRfVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelEmpresaInformacaoRfVO(Connection conn, int empresa_id, int informacaoRf_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(informacaoRf_id)));
        String comandoSql = "INSERT INTO relEmpresaInformacaoRF (tabEmpresa_id, tabInformacaoReceitaFederal_id) VALUES(?, ?) ";
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelEmpresaInformacaoRfVO(Connection conn, int empresa_id, int informacaoRf_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        String comandoSql = "DELETE FROM relEmpresaInformacaoRF WHERE tabEmpresa_id = ? ";
        if (informacaoRf_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(informacaoRf_id)));
            comandoSql += "AND tabInformacaoReceitaFederal_id = ? ";
        }
        getDeleteBancoDados(conn, comandoSql);
    }
}
