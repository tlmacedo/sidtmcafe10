package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.FiscalFreteTomadorServicoVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEntradaCteVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabEntradaCteDAO extends ServiceBuscaBancoDados {

    TabEntradaCteVO tabEntradaCteVO = null;
    List<TabEntradaCteVO> tabEntradaCteVOList = null;

    public TabEntradaCteVO getTabEntradaCteVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM tabEntradaCte WHERE id = ? ");
        if (tabEntradaCteVO != null)
            addObjetosPesquisa(tabEntradaCteVO);
        return tabEntradaCteVO;
    }

    public TabEntradaCteVO getTabEntradaCteVO(String num_chaveCte) {
        String comandoSql = "SELECT * FROM tabEntradaCte ";
        addNewParametro(new Pair<>("String", num_chaveCte));
        if (num_chaveCte.length() == 44)
            getResultSet(comandoSql + "WHERE chaveCte = ? ");
        else
            getResultSet(comandoSql + "WHERE numeroCte = ? ");
        if (tabEntradaCteVO != null)
            addObjetosPesquisa(tabEntradaCteVO);
        return tabEntradaCteVO;
    }

    public List<TabEntradaCteVO> getTabEntradaCteVOList() {
        tabEntradaCteVOList = new ArrayList<>();
        getResultSet("SELECT * FROM tabEntradaCte ");
        if (tabEntradaCteVO != null)
            for (TabEntradaCteVO entradaCte : tabEntradaCteVOList)
                addObjetosPesquisa(tabEntradaCteVO);
        return tabEntradaCteVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                tabEntradaCteVO = new TabEntradaCteVO();
                tabEntradaCteVO.setId(rs.getInt("id"));
                tabEntradaCteVO.setChaveCte(rs.getString("chaveCte"));
                tabEntradaCteVO.setTomadorServico_id(rs.getInt("tomadorServico_id"));
                tabEntradaCteVO.setNumeroCte(rs.getInt("numeroCte"));
                tabEntradaCteVO.setSerieCte(rs.getInt("serieCte"));
                tabEntradaCteVO.setModeloCte_id(rs.getInt("modeloCte_id"));
                tabEntradaCteVO.setSituacaoTributaria_id(rs.getInt("situacaoTributaria_id"));
                tabEntradaCteVO.setTransportadora_id(rs.getInt("transportadora_id"));
                tabEntradaCteVO.setDataEmissaoCte(rs.getTimestamp("dataEmissaoCte"));
                tabEntradaCteVO.setVlrCte(rs.getBigDecimal("vlrCte"));
                tabEntradaCteVO.setQtdVolume(rs.getInt("qtdVolume"));
                tabEntradaCteVO.setPesoBruto(rs.getBigDecimal("pesoBruto"));
                tabEntradaCteVO.setVlrFreteBruto(rs.getBigDecimal("vlrFreteBruto"));
                tabEntradaCteVO.setVlrImpostoFrete(rs.getBigDecimal("vlrImpostoFrete"));
                if (tabEntradaCteVOList != null) tabEntradaCteVOList.add(tabEntradaCteVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    void addObjetosPesquisa(TabEntradaCteVO entradaCte) {
        entradaCte.setTomadorServicoVO(new FiscalFreteTomadorServicoDAO().getFiscalFreteTomadorServicoVO(entradaCte.getTomadorServico_id()));
        entradaCte.setModeloCteVO(new FiscalModeloNfeCteDAO().getFiscalModeloNfeCteVO(entradaCte.getModeloCte_id()));
        entradaCte.setSituacaoTributariaVO(new FiscalFreteSituacaoTributariaDAO().getFiscalFreteSituacaoTributariaVO(entradaCte.getSituacaoTributaria_id()));
        entradaCte.setTransportadoraVO(new TabEmpresaDAO().getTabEmpresaVO(entradaCte.getTransportadora_id()));
    }

    public void updateTabEntradaCteVO(Connection conn, TabEntradaCteVO entradaCteVO) throws SQLException {
        String comandoSql = "UPDATE tabEntradaCte SET " +
                "chaveCte = ?, " +
                "tomadorServico_id = ?, " +
                "numeroCte = ?, " +
                "serieCte = ?, " +
                "modeloNfeCte_id = ?, " +
                "situacaoTributaria_id = ?, " +
                "transportadora_id = ?, " +
                "dataEmissaoCte = ?, " +
                "vlrCte = ?, " +
                "qtdVolume = ?, " +
                "pesoBruto = ?, " +
                "vlrFreteBruto = ?, " +
                "vlrImpostoFrete = ? " +
                "WHERE id = ? ";
        addNewParametro(new Pair<>("String", entradaCteVO.getChaveCte()));
        addParametro(new Pair<>("int", String.valueOf(entradaCteVO.getTomadorServico_id())));
        addParametro(new Pair<>("int", String.valueOf(entradaCteVO.getNumeroCte())));
        addParametro(new Pair<>("int", String.valueOf(entradaCteVO.getSerieCte())));
        addParametro(new Pair<>("int", String.valueOf(entradaCteVO.getModeloCte_id())));
        addParametro(new Pair<>("int", String.valueOf(entradaCteVO.getSituacaoTributaria_id())));
        addParametro(new Pair<>("int", String.valueOf(entradaCteVO.getTransportadora_id())));
        addParametro(new Pair<>("timestamp", String.valueOf(entradaCteVO.getDataEmissaoCte())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaCteVO.getVlrCte())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaCteVO.getQtdVolume())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaCteVO.getPesoBruto())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaCteVO.getVlrFreteBruto())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaCteVO.getVlrImpostoFrete())));
        addParametro(new Pair<>("int", String.valueOf(entradaCteVO.getId())));
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabEntradaCteVO(Connection conn, TabEntradaCteVO entradaCteVO, int nfe_id) throws SQLException {
        String comandoSql = "INSERT INTO tabEntradaCte (" +
                "chaveCte, " +
                "tomadorServico_id, " +
                "numeroCte, " +
                "serieCte, " +
                "modeloCte_id, " +
                "situacaoTributaria_id, " +
                "transportadora_id, " +
                "dataEmissaoCte, " +
                "vlrCte, " +
                "qtdVolume, " +
                "pesoBruto, " +
                "vlrFreteBruto, " +
                "vlrImpostoFrete " +
                ") VALUES (" +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?) ";
        addNewParametro(new Pair<>("String", entradaCteVO.getChaveCte().replaceAll("\\D", "")));
        addParametro(new Pair<>("int", String.valueOf(entradaCteVO.getTomadorServico_id())));
        addParametro(new Pair<>("int", String.valueOf(entradaCteVO.getNumeroCte())));
        addParametro(new Pair<>("int", String.valueOf(entradaCteVO.getSerieCte())));
        addParametro(new Pair<>("int", String.valueOf(entradaCteVO.getModeloCte_id())));
        addParametro(new Pair<>("int", String.valueOf(entradaCteVO.getSituacaoTributaria_id())));
        addParametro(new Pair<>("int", String.valueOf(entradaCteVO.getTransportadora_id())));
        addParametro(new Pair<>("timestamp", String.valueOf(entradaCteVO.getDataEmissaoCte())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaCteVO.getVlrCte())));
        addParametro(new Pair<>("int", String.valueOf(entradaCteVO.getQtdVolume())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaCteVO.getPesoBruto())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaCteVO.getVlrFreteBruto())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaCteVO.getVlrImpostoFrete())));
        int cte_id = getInsertBancoDados(conn, comandoSql);
        new RelEntradaNfe_EntradaCteDAO().insertRelEntradaNfe_EntradaCteVO(conn, nfe_id, cte_id);
        return cte_id;
    }

    public void deleteTabEntradaCteVO(Connection conn, int entradaCte_id) throws SQLException {
        if (entradaCte_id < 0) entradaCte_id = entradaCte_id * (-1);
        String comandoSql = "DELETE FROM tabEntradaCte WHERE id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(entradaCte_id)));
        getDeleteBancoDados(conn, comandoSql);
    }
}
