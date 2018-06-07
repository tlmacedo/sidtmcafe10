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

    String comandoSql = "";
    TabContatoVO tabContatoVO;

    public TabContatoVO getTabContatoVO(int id) {
        buscaTabContatoVO(id);
        if (tabContatoVO != null)
            addObjetosPesquisa(tabContatoVO);
        return tabContatoVO;
    }

    void buscaTabContatoVO(int id) {
        comandoSql = "SELECT id, descricao, sisCargo_id " +
                "FROM tabContato " +
                "WHERE id = '" + id + "' ";

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

    public void updateTabContatoVO(Connection conn, TabContatoVO contatoVO) throws SQLException {
        comandoSql = "UPDATE tabContato SET ";
        comandoSql += "descricao = '" + contatoVO.getDescricao() + "', ";
        comandoSql += "sisCargo_id = " + contatoVO.getSisCargo_id() + " ";
        comandoSql += "WHERE id = " + contatoVO.getId() + " ";

        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabContatoVO(Connection conn, TabContatoVO contatoVO) throws SQLException {
        comandoSql = "INSERT INTO tabContato ";
        comandoSql += "(descricao, sisCargo_id) ";
        comandoSql += "VALUES (";
        comandoSql += "'" + contatoVO.getDescricao() + "', ";
        comandoSql += +contatoVO.getSisCargo_id() + " ";
        comandoSql += ")";

        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabContatoVO(Connection conn, int id_contatoVO) throws SQLException {
        if (id_contatoVO < 0) id_contatoVO = id_contatoVO * (-1);
        comandoSql = "DELETE FROM tabContato ";
        comandoSql += "WHERE id = " + id_contatoVO + " ";

        getDeleteBancoDados(conn, comandoSql);
    }

}
