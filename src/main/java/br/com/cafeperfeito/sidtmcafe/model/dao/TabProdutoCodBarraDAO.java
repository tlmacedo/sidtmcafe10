package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoCodBarraVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import br.com.cafeperfeito.sidtmcafe.service.ServiceImage;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabProdutoCodBarraDAO extends ServiceBuscaBancoDados {

    TabProdutoCodBarraVO codBarraVO = null;
    List<TabProdutoCodBarraVO> codBarraVOList = null;

    public TabProdutoCodBarraVO getTabProdutoCodBarraVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM tabProdutoCodBarra WHERE id = ? ");
        return codBarraVO;
    }

    public TabProdutoCodBarraVO getTabProdutoCodBarraVO(String barCode) {
        addNewParametro(new Pair<>("String", barCode));
        getResultSet("SELECT * FROM tabProdutoCodBarra WHERE codBarra = ? ");
        return codBarraVO;
    }

    public List<TabProdutoCodBarraVO> getTabProdutoCodBarraVOList(int produto_id) {
        codBarraVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(produto_id)));
        getResultSet("SELECT * FROM tabProdutoCodBarra WHERE produto_id = ? ");
        return codBarraVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                codBarraVO = new TabProdutoCodBarraVO();
                codBarraVO.setId(rs.getInt("id"));
                codBarraVO.setCodBarra(rs.getString("codBarra"));
                codBarraVO.setImgCodBarra(ServiceImage.getImageFromInputStream(rs.getBinaryStream("imgCodBarra")));
                if (codBarraVOList != null) codBarraVOList.add(codBarraVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public void updateTabProdutoCodBarraVO(Connection conn, TabProdutoCodBarraVO codBarra) throws SQLException {
        String comandoSql = "UPDATE tabProdutoCodBarra SET " +
                "codBarra = ?, " +
                "imgCodBarra = ? " +
                "WHERE id = ? ";
        addNewParametro(new Pair<>("String", codBarra.getCodBarra()));
        image[0] = ServiceImage.getInputStreamFromImage(codBarra.getImgCodBarra());
        addParametro(new Pair<>("blob0", "image"));
        addParametro(new Pair<>("int", String.valueOf(codBarra.getId())));
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabProdutoCodBarraVO(Connection conn, TabProdutoCodBarraVO codBarra, int produto_id) throws SQLException {
        String comandoSql = "INSERT INTO tabProdutoCodBarra " +
                "(codBarra, " +
                "imgCodBarra) " +
                "VALUES(" +
                "?, ?) ";
        addNewParametro(new Pair<>("String", codBarra.getCodBarra()));
        image[0] = ServiceImage.getInputStreamFromImage(codBarra.getImgCodBarra());
        addParametro(new Pair<>("blob0", "image"));
        int produto_CodBarra_id = getInsertBancoDados(conn, comandoSql);
        if (produto_id > 0)
            new RelProduto_CodBarraDAO().insertRelProduto_CodBarraVO(conn, produto_id, produto_CodBarra_id);
        return produto_CodBarra_id;
    }

    public void deleteTabProdutoCodBarraVO(Connection conn, int codBarra_id, int produto_id) throws SQLException {
        if (codBarra_id < 0) codBarra_id = codBarra_id * (-1);
        if (produto_id > 0)
            new RelProduto_CodBarraDAO().deleteRelProduto_CodBarraVO(conn, produto_id, codBarra_id);
        String comandoSql = "DELETE FROM tabProdutoCodBarra WHERE id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(codBarra_id)));
        getDeleteBancoDados(conn, comandoSql);
    }
}
