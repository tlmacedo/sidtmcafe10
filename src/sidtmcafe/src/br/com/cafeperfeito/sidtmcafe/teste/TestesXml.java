package br.com.cafeperfeito.sidtmcafe.teste;

import br.com.cafeperfeito.sidtmcafe.service.ServiceSocketDinamico;
import br.com.cafeperfeito.sidtmcafe.service.ServiceXmlUtil;
import br.inf.portalfiscal.cte.PL_CTE_300_NT2018_002.cte_v300.TCTe;
import br.inf.portalfiscal.cte.PL_CTE_300_NT2018_002.procCTe_v300.CteProc;
import br.inf.portalfiscal.nfe_400.xsdPL_009.consStatServ_v400.TConsStatServ;
import br.inf.portalfiscal.www.nfe_400.wsdl.nfeStatusServico4.NfeStatusServico4Stub;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;

import java.io.File;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.X509Certificate;

import org.apache.commons.httpclient.protocol.Protocol;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Enumeration;

public class TestesXml {


    private static final String STATUS = "TConsStatServ";
    private static final String SITUACAO_NFE = "TConsSitNFe";
    private static final String ENVIO_NFE = "TEnviNFe";
    private static final String DIST_DFE = "DistDFeInt";
    private static final String INUTILIZACAO = "TInutNFe";
    private static final String NFEPROC = "TNfeProc";
    private static final String EVENTO = "TEnvEvento";
    private static final String TPROCEVENTO = "TProcEvento";
    private static final String TCONSRECINFE = "TConsReciNFe";
    private static final String TConsCad = "TConsCad";
    private static final String TPROCINUT = "TProcInutNFe";
    private static final String RETORNO_ENVIO = "TRetEnviNFe";
    private static final String SITUACAO_NFE_RET = "TRetConsSitNFe";

    private static final String TPROCCANCELAR = "br.inf.portalfiscal.nfe.schema.envEventoCancNFe.TProcEvento";
    private static final String TPROCCCE = "br.inf.portalfiscal.nfe.schema.envcce.TProcEvento";
    private static final String TPROCEPEC = "br.inf.portalfiscal.nfe.schema.envEpec.TProcEvento";

    private static final String TProtNFe = "TProtNFe";
    private static final String TProtEnvi = "br.inf.portalfiscal.nfe.schema_4.enviNFe.TProtNFe";
    private static final String TProtCons = "br.inf.portalfiscal.nfe.schema_4.retConsSitNFe.TProtNFe";
    private static final String TProtReci = "br.inf.portalfiscal.nfe.schema_4.retConsReciNFe.TProtNFe";

    private static final String CANCELAR = "br.inf.portalfiscal.nfe.schema.envEventoCancNFe.TEnvEvento";
    private static final String CCE = "br.inf.portalfiscal.nfe.schema.envcce.TEnvEvento";
    private static final String EPEC = "br.inf.portalfiscal.nfe.schema.envEpec.TEnvEvento";
    private static final String MANIFESTAR = "br.inf.portalfiscal.nfe.schema.envConfRecebto.TEnvEvento";

    private static final int SSL_PORT = 443;

    public static void main(String[] args) throws Exception {
        lerXML();
    }

    private static void lerXML() throws Exception {
        String diretorio = System.getProperty("user.home") + "/Desktop/35180906186733000220570010005423021009457382-cte.xml";
        CteProc cteProc = ServiceXmlUtil.xmlToObject(ServiceXmlUtil.leXml(diretorio), CteProc.class);

        System.out.println("natOp: [" + cteProc.getCTe().getInfCte().getIde().getNatOp() + "]");

    }


}
