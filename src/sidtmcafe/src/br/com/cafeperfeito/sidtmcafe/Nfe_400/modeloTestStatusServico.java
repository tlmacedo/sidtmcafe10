package br.com.cafeperfeito.sidtmcafe.Nfe_400;

import br.com.cafeperfeito.sidtmcafe.service.ServiceSocketDinamico;
import br.inf.portalfiscal.nfe_400.xsdPL_009.consStatServ_v400.TConsStatServ;
import br.inf.portalfiscal.www.nfe_400.wsdl.nfeStatusServico4.NfeStatusServico4Stub;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;

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

public class modeloTestStatusServico {


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
        certificado();
        lerXML();
    }

    public static void certificado() {
        try {
            String codigoDoEstado = "13";
            String senhaDoCertificadoDoCliente = "4879";
            //String arquivoCacertsGeradoParaCadaEstado = "/Volumes/150GB-Development/Java/Intellij/sidtmcafe10/src/sidtmcafe/resources/certificado/cacertNfeNfceCte";
            String arquivoCacertsGeradoParaCadaEstado = "/Volumes/150GB-Development/Java/Intellij/sidtmcafe10/src/sidtmcafe/resources/certificado/cacert";


            /**
             * Tipo de Certificados.
             * A3CARTAO = A3 Cartão;
             * A3TOKEN = A3 Token;
             */
            String tipoCertificado = "A3CARTAO";
            //String tipoCertificado = "A3TOKEN";

            /**
             * Informacoes do Certificado Digital.
             */
            String fileCfg = "";
            if ("A3CARTAO".equals(tipoCertificado)) {
                fileCfg = "/Volumes/150GB-Development/Java/Intellij/sidtmcafe10/src/sidtmcafe/resources/certificado/tokenSafeNet5100.cfg";
                //fileCfg = "/certificado/tokenSafeNet5100.cfg";
            } else if ("A3TOKEN".equals(tipoCertificado)) {
                fileCfg = "Token.cfg";
            } else {
                throw new Exception("Tipo de certificado inválido");
            }

            Provider p = Security.getProvider("SunPKCS11");
            p = p.configure(fileCfg);
            Security.addProvider(p);
            KeyStore ks = KeyStore.getInstance("pkcs11", p);
            char[] pin = senhaDoCertificadoDoCliente.toCharArray();
            ks.load(null, pin);

            String alias = "";
            Enumeration<String> aliasesEnum = ks.aliases();
            while (aliasesEnum.hasMoreElements()) {
                alias = (String) aliasesEnum.nextElement();
                if (ks.isKeyEntry(alias)) break;
            }
            X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);
            PrivateKey privateKey = (PrivateKey) ks.getKey(alias, senhaDoCertificadoDoCliente.toCharArray());
            ServiceSocketDinamico serviceSocketDinamico = new ServiceSocketDinamico(certificate, privateKey);
            serviceSocketDinamico.setFileCacerts(arquivoCacertsGeradoParaCadaEstado);

            Protocol protocol = new Protocol("https", serviceSocketDinamico, SSL_PORT);
            Protocol.registerProtocol("https", protocol);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void lerXML() throws Exception {

        TConsStatServ consStatServ = new TConsStatServ();
        consStatServ.setTpAmb("2");
        consStatServ.setCUF("13");
        consStatServ.setVersao("4.00");
        consStatServ.setXServ("STATUS");
        String xml = objectToXml(consStatServ);

        System.out.println(xml);

        OMElement ome = AXIOMUtil.stringToOM(xml);
        NfeStatusServico4Stub.NfeDadosMsg dadosMsg = new NfeStatusServico4Stub.NfeDadosMsg();
        dadosMsg.setExtraElement(ome);

        NfeStatusServico4Stub stub = new NfeStatusServico4Stub("https://homnfe.sefaz.am.gov.br/services2/services/NfeStatusServico4");
        NfeStatusServico4Stub.NfeResultMsg result = stub.nfeStatusServicoNF(dadosMsg);

        System.out.println(result.getExtraElement().toString());


//        File fXmlFile = new File(System.getProperty("user.home") + "/Desktop/35180906981833000248550010000000931027894094-nfe.xml");
//        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//        Document doc = dBuilder.parse(fXmlFile);
//
//        System.out.println("Root do elemento: " + doc.getDocumentElement().getNodeName());
//        NodeList nList = doc.getElementsByTagName("guest");
//
//        System.out.println("----------------------------");
//        for (int temp = 0; temp < nList.getLength(); temp++) {
//            Node nNode = nList.item(temp);
//            //System.out.println("\nElemento corrente :" + nNode.getNodeName());
//            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//                Element eElement = (Element) nNode;
//                System.out.println("Id : " + (temp+1));
//                System.out.println("Primeiro nome.: " + eElement.getElementsByTagName("fname").item(0).getTextContent());
//                System.out.println("Segundo nome..: " + eElement.getElementsByTagName("lname").item(0).getTextContent());
//            }
//        }
    }

//    public static <T> String objectToXml(Object obj) throws JAXBException {
//        JAXBContext context=null;
//        JAXBElement<?> element = null;
//
//        context = JAXBContext.newInstance(TConsStatServ.class);
//        element = new br.inf.portalfiscal.nfe_400.xsdPL_009.consStatServ_v400.ObjectFactory().createConsStatServ((TConsStatServ) obj);
//
//        Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty("jaxb.encoding", "Unicode");
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
//        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
//
//        StringWriter sw = new StringWriter();
//
//    }


    public static <T> String objectToXml(Object obj) throws JAXBException {

        JAXBContext context = null;
        JAXBElement<?> element = null;

        switch (obj.getClass().getSimpleName()) {

            case STATUS:
                context = JAXBContext.newInstance(TConsStatServ.class);
                element = new br.inf.portalfiscal.nfe_400.xsdPL_009.consStatServ_v400.ObjectFactory().createConsStatServ((TConsStatServ) obj);
                break;

//            case ENVIO_NFE:
//                context = JAXBContext.newInstance(TEnviNFe.class);
//                element = new br.inf.portalfiscal.nfe.schema_4.enviNFe.ObjectFactory().createEnviNFe((TEnviNFe) obj);
//                break;
//
//            case RETORNO_ENVIO:
//                context = JAXBContext.newInstance(TRetEnviNFe.class);
//                element = XsdUtil.enviNfe.createTRetEnviNFe((TRetEnviNFe) obj);
//                break;
//
//            case SITUACAO_NFE:
//                context = JAXBContext.newInstance(TConsSitNFe.class);
//                element = new br.inf.portalfiscal.nfe.schema_4.consSitNFe.ObjectFactory().createConsSitNFe((TConsSitNFe) obj);
//                break;
//
//            case DIST_DFE:
//                context = JAXBContext.newInstance(DistDFeInt.class);
//                element = new br.inf.portalfiscal.nfe.schema.distdfeint.ObjectFactory().createDistDFeInt((DistDFeInt) obj);
//                break;
//
//            case TCONSRECINFE:
//                context = JAXBContext.newInstance(TConsReciNFe.class);
//                element = new br.inf.portalfiscal.nfe.schema_4.consReciNFe.ObjectFactory().createConsReciNFe((TConsReciNFe) obj);
//                break;
//
//            case TConsCad:
//                context = JAXBContext.newInstance(TConsCad.class);
//                element = new br.inf.portalfiscal.nfe.schema.consCad.ObjectFactory().createConsCad((TConsCad) obj);
//                break;
//
//            case INUTILIZACAO:
//                context = JAXBContext.newInstance(TInutNFe.class);
//                element = new br.inf.portalfiscal.nfe.schema_4.inutNFe.ObjectFactory().createInutNFe((TInutNFe) obj);
//                break;
//
//            case SITUACAO_NFE_RET:
//                context = JAXBContext.newInstance(TRetConsSitNFe.class);
//                element = new br.inf.portalfiscal.nfe.schema_4.retConsSitNFe.ObjectFactory().createRetConsSitNFe((TRetConsSitNFe) obj);
//                break;
//
//            case TPROCEVENTO:
//                switch (obj.getClass().getName()) {
//                    case TPROCCANCELAR:
//                        context = JAXBContext.newInstance(br.inf.portalfiscal.nfe.schema.envEventoCancNFe.TProcEvento.class);
//                        element = new br.inf.portalfiscal.nfe.schema.envEventoCancNFe.ObjectFactory().createTProcEvento((br.inf.portalfiscal.nfe.schema.envEventoCancNFe.TProcEvento) obj);
//                        break;
//                    case TPROCCCE:
//                        context = JAXBContext.newInstance(br.inf.portalfiscal.nfe.schema.envcce.TProcEvento.class);
//                        element = new br.inf.portalfiscal.nfe.schema.envcce.ObjectFactory().createTProcEvento((br.inf.portalfiscal.nfe.schema.envcce.TProcEvento) obj);
//                        break;
//                    case TPROCEPEC:
//                        context = JAXBContext.newInstance(br.inf.portalfiscal.nfe.schema.envEpec.TProcEvento.class);
//                        element = XsdUtil.epec.createTProcEvento((br.inf.portalfiscal.nfe.schema.envEpec.TProcEvento) obj);
//                        break;
//                }
//
//                break;
//
//            case NFEPROC:
//                context = JAXBContext.newInstance(TNfeProc.class);
//                element = XsdUtil.enviNfe.createTNfeProc((TNfeProc) obj);
//                break;
//
//            case TPROCINUT:
//                context = JAXBContext.newInstance(TProcInutNFe.class);
//                element = XsdUtil.inutNfe.createTProcInutNFe((TProcInutNFe) obj);
//                break;
//
//            case EVENTO:
//                switch (obj.getClass().getName()) {
//                    case CANCELAR:
//                        context = JAXBContext.newInstance(TEnvEvento.class);
//                        element = new br.inf.portalfiscal.nfe.schema.envEventoCancNFe.ObjectFactory().createEnvEvento((TEnvEvento) obj);
//                        break;
//                    case CCE:
//                        context = JAXBContext.newInstance(br.inf.portalfiscal.nfe.schema.envcce.TEnvEvento.class);
//                        element = new br.inf.portalfiscal.nfe.schema.envcce.ObjectFactory().createEnvEvento((br.inf.portalfiscal.nfe.schema.envcce.TEnvEvento) obj);
//                        break;
//                    case EPEC:
//                        context = JAXBContext.newInstance(br.inf.portalfiscal.nfe.schema.envEpec.TEnvEvento.class);
//                        element = new br.inf.portalfiscal.nfe.schema.envEpec.ObjectFactory().createEnvEvento((br.inf.portalfiscal.nfe.schema.envEpec.TEnvEvento) obj);
//                        break;
//                    case MANIFESTAR:
//                        context = JAXBContext.newInstance(br.inf.portalfiscal.nfe.schema.envConfRecebto.TEnvEvento.class);
//                        element = new br.inf.portalfiscal.nfe.schema.envConfRecebto.ObjectFactory().createEnvEvento((br.inf.portalfiscal.nfe.schema.envConfRecebto.TEnvEvento) obj);
//                        break;
//                }
//                break;
//
//            case TProtNFe:
//                switch (obj.getClass().getName()) {
//                    case TProtEnvi:
//                        context = JAXBContext.newInstance(TProtNFe.class);
//                        element = XsdUtil.enviNfe.createTProtNFe((br.inf.portalfiscal.nfe.schema_4.enviNFe.TProtNFe) obj);
//                        break;
//                    case TProtCons:
//                        context = JAXBContext.newInstance(br.inf.portalfiscal.nfe.schema_4.retConsSitNFe.TProtNFe.class);
//                        element = XsdUtil.retConsSitNfe.createTProtNFe((br.inf.portalfiscal.nfe.schema_4.retConsSitNFe.TProtNFe) obj);
//                        break;
//                    case TProtReci:
//                        context = JAXBContext.newInstance(br.inf.portalfiscal.nfe.schema_4.retConsReciNFe.TProtNFe.class);
//                        element = XsdUtil.retConsReciNfe.createTProtNFe((br.inf.portalfiscal.nfe.schema_4.retConsReciNFe.TProtNFe) obj);
//                        break;
//                }
//                break;
//
//            default:
//                throw new NfeException("Objeto não mapeado no XmlUtil:" + obj.getClass().getSimpleName());
        }
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty("jaxb.encoding", "Unicode");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

        StringWriter sw = new StringWriter();

//        if (obj.getClass().getSimpleName().equals(ENVIO_NFE) || obj.getClass().getSimpleName().equals(NFEPROC)) {
//            CDATAContentHandler cdataHandler = new CDATAContentHandler(sw, "utf-8");
//            marshaller.marshal(element, cdataHandler);
//        } else {
        marshaller.marshal(element, sw);
//        }
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(sw.toString());

//        if ((obj.getClass().getSimpleName().equals(TPROCEVENTO))) {
//            return replacesNfe(xml.toString().replaceAll("procEvento", "procEventoNFe"));
//        } else {
        return replacesNfe(xml.toString());
//        }

    }

    public static <T> T xmlToObject(String xml, Class<T> classe) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(classe);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return unmarshaller.unmarshal(new StreamSource(new StringReader(xml)), classe).getValue();
    }

    private static String replacesNfe(String xml) {

        xml = xml.replaceAll("ns2:", "");
        xml = xml.replaceAll("<!\\[CDATA\\[<!\\[CDATA\\[", "<!\\[CDATA\\[");
        xml = xml.replaceAll("\\]\\]>\\]\\]>", "\\]\\]>");
        xml = xml.replaceAll("ns3:", "");
        xml = xml.replaceAll("&lt;", "<");
        xml = xml.replaceAll("&amp;", "&");
        xml = xml.replaceAll("&gt;", ">");
        xml = xml.replaceAll("<Signature>", "<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">");
        xml = xml.replaceAll(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
        xml = xml.replaceAll(" xmlns=\"\" xmlns:ns3=\"http://www.portalfiscal.inf.br/nfe\"", "");

        return xml;

    }
}
