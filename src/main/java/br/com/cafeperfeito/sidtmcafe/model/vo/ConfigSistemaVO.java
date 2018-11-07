package br.com.cafeperfeito.sidtmcafe.model.vo;

import br.com.cafeperfeito.sidtmcafe.xsd.configSistema.informacaoBasica.TInformacaoBasica;

import java.util.Locale;

public class ConfigSistemaVO {

    public int idLoja;
    public String tituloLoja;
    public String copyright;
    public String theme;
    public int ddd;
    public int portaSSL;
    public Locale myLocale;

    public ConfigSistemaVO() {
    }

    public ConfigSistemaVO(TInformacaoBasica inf) {
        this.idLoja = inf.getIdLoja();
        this.tituloLoja = inf.getTitulo();
        this.copyright = inf.getCopyright();
        this.theme = inf.getTheme();
        this.ddd = inf.getDdd();
        this.portaSSL = inf.getPortaSSl();
        this.myLocale = new Locale(inf.getMyLocale().substring(0, 2), inf.getMyLocale().substring(3));
//        this.myLocale = inf
    }

    public int getIdLoja() {
        return idLoja;
    }

    public void setIdLoja(int idLoja) {
        this.idLoja = idLoja;
    }

    public String getTituloLoja() {
        return tituloLoja;
    }

    public void setTituloLoja(String tituloLoja) {
        this.tituloLoja = tituloLoja;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) {
        this.ddd = ddd;
    }

    public int getPortaSSL() {
        return portaSSL;
    }

    public void setPortaSSL(int portaSSL) {
        this.portaSSL = portaSSL;
    }

    public Locale getMyLocale() {
        return myLocale;
    }

    public void setMyLocale(Locale myLocale) {
        this.myLocale = myLocale;
    }
}
