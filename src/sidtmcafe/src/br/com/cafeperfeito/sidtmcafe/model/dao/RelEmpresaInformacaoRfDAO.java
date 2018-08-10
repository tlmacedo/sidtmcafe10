package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEmpresaInformacaoRfVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEmpresaInformacaoRfDAO extends BuscaBancoDados {

    ResultSet rs;
    RelEmpresaInformacaoRfVO relEmpresaInformacaoRfVO;
    List<RelEmpresaInformacaoRfVO> relEmpresaInformacaoRfVOList;
    boolean returnList = false;

    public RelEmpresaInformacaoRfVO getRelEmpresaInformacaoRfVO(int empresa_id, int informacaoRf_id) {
        getResultSet(String.format("SELECT * FROM relEmpresaInformacaoRF WHERE tabEmpresa_id = %d " +
                "AND tabInformacaoReceitaFederal_id = %d ORDER BY tabEmpresa_id, tabInformacaoReceitaFederal_id", empresa_id, informacaoRf_id), false);
        return relEmpresaInformacaoRfVO;
    }

    public List<RelEmpresaInformacaoRfVO> getRelEmpresaInformacaoRfVOList(int empresa_id) {
        relEmpresaInformacaoRfVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM relEmpresaInformacaoRF WHERE tabEmpresa_id = %d " +
                "ORDER BY tabEmpresa_id, tabInformacaoReceitaFederal_id", empresa_id), true);
        return relEmpresaInformacaoRfVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                relEmpresaInformacaoRfVO = new RelEmpresaInformacaoRfVO();
                relEmpresaInformacaoRfVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresaInformacaoRfVO.setTabInformacaoReceitaFederal_id(rs.getInt("tabInformacaoReceitaFederal_id"));
                if (returnList) relEmpresaInformacaoRfVOList.add(relEmpresaInformacaoRfVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelEmpresaInformacaoRfVO(Connection conn, int empresa_id, int informacaoRf_id) throws SQLException {
        String comandoSql = String.format("INSERT INTO relEmpresaInformacaoRF (tabEmpresa_id, tabInformacaoReceitaFederal_id) " +
                "VALUES(%d, %d)", empresa_id, informacaoRf_id);
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelEmpresaInformacaoRfVO(Connection conn, int empresa_id, int informacaoRf_id) throws SQLException {
        String comandoSql = String.format("DELETE FROM relEmpresaInformacaoRF WHERE tabEmpresa_id = %d ", empresa_id);
        if (informacaoRf_id > 0)
            comandoSql += String.format("AND tabInformacaoReceitaFederal_id = %d", informacaoRf_id);
        getDeleteBancoDados(conn, comandoSql);
    }
}
