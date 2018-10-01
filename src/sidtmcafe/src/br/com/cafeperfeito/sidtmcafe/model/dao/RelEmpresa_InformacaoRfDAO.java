package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEmpresa_InformacaoRfVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEmpresa_InformacaoRfDAO extends ServiceBuscaBancoDados {

    RelEmpresa_InformacaoRfVO relEmpresa_informacaoRfVO = null;
    List<RelEmpresa_InformacaoRfVO> relEmpresa_informacaoRfVOList = null;

    public RelEmpresa_InformacaoRfVO getRelEmpresa_informacaoRfVO(int empresa_id, int informacaoRf_id) {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(informacaoRf_id)));
        getResultSet("SELECT * FROM relEmpresa_InformacaoRF WHERE tabEmpresa_id = ? AND tabInformacaoReceitaFederal_id = ?");
        return relEmpresa_informacaoRfVO;
    }

    public List<RelEmpresa_InformacaoRfVO> getRelEmpresa_informacaoRfVOList(int empresa_id) {
        relEmpresa_informacaoRfVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        getResultSet("SELECT * FROM relEmpresa_informacaoRF WHERE tabEmpresa_id = ? ");
        return relEmpresa_informacaoRfVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabEmpresa_id, tabInformacaoReceitaFederal_id");
        try {
            while (rs.next()) {
                relEmpresa_informacaoRfVO = new RelEmpresa_InformacaoRfVO();
                relEmpresa_informacaoRfVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresa_informacaoRfVO.setTabInformacaoReceitaFederal_id(rs.getInt("tabInformacaoReceitaFederal_id"));
                if (relEmpresa_informacaoRfVOList != null) relEmpresa_informacaoRfVOList.add(relEmpresa_informacaoRfVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelEmpresa_informacaoRfVO(Connection conn, int empresa_id, int informacaoRf_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(informacaoRf_id)));
        String comandoSql = "INSERT INTO relEmpresa_InformacaoRF (tabEmpresa_id, tabInformacaoReceitaFederal_id) VALUES(?, ?) ";
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelEmpresa_informacaoRfVO(Connection conn, int empresa_id, int informacaoRf_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        String comandoSql = "DELETE FROM relEmpresa_InformacaoRF WHERE tabEmpresa_id = ? ";
        if (informacaoRf_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(informacaoRf_id)));
            comandoSql += "AND tabInformacaoReceitaFederal_id = ? ";
        }
        getDeleteBancoDados(conn, comandoSql);
    }
}
