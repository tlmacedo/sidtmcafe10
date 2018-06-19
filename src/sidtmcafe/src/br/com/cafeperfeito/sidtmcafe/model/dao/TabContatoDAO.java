package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabContatoDAO extends BuscaBancoDados {

    ResultSet rs;
    TabContatoVO tabContatoVO;

    public TabContatoVO getTabContatoVO(int id, boolean getDetalheContato) {
        getResultSet(String.format("SELECT * FROM tabContato WHERE id = %d ORDER BY descricao", id));
        if (getDetalheContato)
            addObjetosPesquisa(tabContatoVO);
        return tabContatoVO;
    }

    void getResultSet(String comandoSql) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                tabContatoVO = new TabContatoVO();
                tabContatoVO.setId(rs.getInt("id"));
                tabContatoVO.setDescricao(rs.getString("descricao"));
                tabContatoVO.setSisCargo_id(rs.getInt("sisCargo_id"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    void addObjetosPesquisa(TabContatoVO contatoVO) {
        contatoVO.setSisCargoVO(new SisCargoDAO().getSisCargoVO(contatoVO.getSisCargo_id()));

        List<RelContatoEmailHomePageVO> relContatoEmailHomePageVOList = new RelContatoEmailHomePageDAO().getRelContatoEmailHomePageVOList(contatoVO.getId());
        List<TabEmailHomePageVO> tabEmailHomePageVOList = new ArrayList<>();
        if (relContatoEmailHomePageVOList != null)
            for (int i = 0; i < relContatoEmailHomePageVOList.size(); i++)
                tabEmailHomePageVOList.add(new TabEmailHomePageDAO().getTabEmailHomePageVO(relContatoEmailHomePageVOList.get(i).getTabEmailHomePage_id()));
        contatoVO.setTabEmailHomePageVOList(tabEmailHomePageVOList);

        List<RelContatoTelefoneVO> relContatoTelefoneVOList = new RelContatoTelefoneDAO().getRelContatoTelefoneVOList(contatoVO.getId());
        List<TabTelefoneVO> tabTelefoneVOList = new ArrayList<>();
        if (relContatoEmailHomePageVOList != null)
            for (int i = 0; i < relContatoTelefoneVOList.size(); i++)
                tabTelefoneVOList.add(new TabTelefoneDAO().getTabTelefoneVO(relContatoTelefoneVOList.get(i).getTabTelefone_id()));
        contatoVO.setTabTelefoneVOList(tabTelefoneVOList);

    }

    public void updateTabContatoVO(Connection conn, TabContatoVO contato) throws SQLException {
        String comandoSql = String.format("UPDATE tabContato SET descricao = '%s', sisCargo_id = %d WHERE id = %d",
                contato.getDescricao(), contato.getSisCargo_id(), contato.getId());
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabContatoVO(Connection conn, TabContatoVO contato, int empresa_id) throws SQLException {
        String comandoSql = String.format("INSERT INTO tabContato (descricao, sisCargo_id) VALUES('%s', %d)",
                contato.getDescricao(), contato.getSisCargo_id());
        int contato_id = getInsertBancoDados(conn, comandoSql);
        new RelEmpresaContatoDAO().insertRelEmpresaContatoVO(conn, empresa_id, contato_id);
        return contato_id;
    }

    public void deleteTabContatoVO(Connection conn, int contato_id, int empresa_id) throws SQLException {
        if (contato_id < 0) contato_id = contato_id * (-1);
        new RelEmpresaContatoDAO().deleteRelEmpresaContatoVO(conn, empresa_id, contato_id);
        String comandoSql = String.format("DELETE FROM tabContato WHERE id = %d", contato_id);
        getDeleteBancoDados(conn, comandoSql);
    }

}
