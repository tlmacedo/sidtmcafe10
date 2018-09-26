package br.com.cafeperfeito.sidtmcafe.teste;

import br.com.cafeperfeito.sidtmcafe.service.ServiceSocketDinamico;
import br.com.cafeperfeito.sidtmcafe.service.ServiceXmlUtil;
import br.inf.portalfiscal.xsd.cte.cte.TCTe;
import br.inf.portalfiscal.xsd.cte.procCTe.CteProc;
import br.inf.portalfiscal.xsd.nfe.consStatServ.TConsStatServ;
import br.inf.portalfiscal.wsdl.nfe.hom.NfeStatusServico4.NfeStatusServico4Stub;
import br.inf.portalfiscal.xsd.nfe.procNFe.TNfeProc;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;

import java.io.File;
import java.io.FileInputStream;
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

    public static void main(String[] args) throws Exception {
        lerXML();
    }

    private static void lerXML() throws Exception {
        FileInputStream arquivo = new FileInputStream(System.getProperty("user.home") + "/Desktop/35180906981833000248550010000000931027894094-nfe.xml");
        TNfeProc tNfeProc = ServiceXmlUtil.xmlToObject(ServiceXmlUtil.leXml(arquivo), TNfeProc.class);
        System.out.println("natOp: [" + tNfeProc.getNFe().getInfNFe().getIde().getNatOp() + "]");
        //CteProc cteProc = ServiceXmlUtil.xmlToObject(ServiceXmlUtil.leXml(diretorio), CteProc.class);
        //System.out.println("natOp: [" + cteProc.getCTe().getInfCte().getIde().getNatOp() + "]");

    }


}
