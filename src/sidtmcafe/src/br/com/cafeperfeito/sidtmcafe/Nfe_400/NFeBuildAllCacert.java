package br.com.cafeperfeito.sidtmcafe.Nfe_400;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import static br.com.cafeperfeito.sidtmcafe.interfaces.Constants.CACERT;
import static br.com.cafeperfeito.sidtmcafe.interfaces.Constants.PORTA;

public class NFeBuildAllCacert {

    private static final int TIMEOUT_WS = 30;
    private static String cacert;

    public static void main(String[] args) {
        List<String> lista = new ArrayList<>();
        gerarCacert(lista);
    }

    private static void gerarCacert(List<String> listaEnderecos) {
        cacert = CACERT;
        try {
            // Se não For informado Nenhuma LIsta, carrega a padrão
            if (listaEnderecos == null || listaEnderecos.isEmpty()) {
                listaEnderecos = listaPadraoWebService();
            }

            char[] senha = "changeit".toCharArray();
            File arquivoCacert = new File(cacert);

            if (arquivoCacert.isFile()) {
                arquivoCacert.delete();
            }

            if (!arquivoCacert.isFile()) {
                File dir = new File(System.getProperty("java.home") + File.separatorChar + "lib" + File.separatorChar + "security");
                arquivoCacert = new File(dir, "cacerts");
            }

            info("| Carregando KeyStore " + arquivoCacert + "...");
            InputStream in = new FileInputStream(arquivoCacert);
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(in, senha);
            in.close();

            listaEnderecos.forEach(endereco -> {
                get(endereco, ks);
            });

            OutputStream out = new FileOutputStream(cacert);
            ks.store(out, senha);
            out.close();

            info("| Arquivo gerado com sucesso em " + cacert);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void get(String host, KeyStore ks) {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ks);
            X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
            SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
            context.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory factory = context.getSocketFactory();

            info("| Abrindo conexão com " + host + ":443...");
            SSLSocket socket = (SSLSocket) factory.createSocket(host, PORTA);
            socket.setSoTimeout(TIMEOUT_WS * 1000);
            try {
                info("| Iniciando SSL handshake...");
                socket.startHandshake();
                socket.close();
                info("| Sem erros, certificado adicionado");
            } catch (SSLHandshakeException e) {
                /**
                 * PKIX path building failed:
                 * sun.security.provider.certpath.SunCertPathBuilderException:
                 * Não tratado, pois sempre ocorre essa exception quando o
                 * cacert nao esta gerado.
                 */
            } catch (SSLException e) {
                error("| " + e.toString());
            }

            X509Certificate[] chain = tm.chain;
            if (chain == null) {
                info("| Não pode obter cadeia de certificados");
            } else {
                info("| Servidor enviou " + chain.length + " certificado(s):");
                MessageDigest sha1 = MessageDigest.getInstance("SHA1");
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                for (int i = 0; i < chain.length; i++) {
                    X509Certificate cert = chain[i];
                    sha1.update(cert.getEncoded());
                    md5.update(cert.getEncoded());

                    String alias = host + "-" + (i);
                    ks.setCertificateEntry(alias, cert);
                    info("| Adicionou certificado para o keystore '" + cacert + "' usando alias '" + alias + "'");
                }
            }
        } catch (NoSuchAlgorithmException | KeyStoreException | CertificateEncodingException | KeyManagementException | IOException e) {
            error("| " + e.toString());
        }
    }

    private static class SavingTrustManager implements X509TrustManager {
        private final X509TrustManager tm;
        private X509Certificate[] chain;

        SavingTrustManager(X509TrustManager tm) {
            this.tm = tm;
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            throw new UnsupportedOperationException();
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            this.chain = chain;
            this.tm.checkServerTrusted(chain, authType);
        }
    }

    private static void info(String log) {
        System.out.println("INFO: " + log);
    }

    private static void error(String log) {
        System.out.println("ERROR*************: " + log);
    }

    private static List<String> listaPadraoWebService() {
        List<String> listaWebServices = new ArrayList<>();

        listaWebServices.add("homnfe.sefaz.am.gov.br");
        listaWebServices.add("hnfe.sefaz.ba.gov.br");
        listaWebServices.add("nfeh.sefaz.ce.gov.br");
        listaWebServices.add("homolog.sefaz.go.gov.br");
        listaWebServices.add("hnfe.fazenda.mg.gov.br");
        listaWebServices.add("hom.nfe.sefaz.ms.gov.br");
        listaWebServices.add("homologacao.sefaz.mt.gov.br");
        listaWebServices.add("nfehomolog.sefaz.pe.gov.br");
        listaWebServices.add("homologacao.nfe.sefa.pr.gov.br");
        listaWebServices.add("nfe-homologacao.sefazrs.rs.gov.br");
        listaWebServices.add("homologacao.nfe.fazenda.sp.gov.br");
        listaWebServices.add("hom.sefazvirtual.fazenda.gov.br");
        listaWebServices.add("nfe-homologacao.svrs.rs.gov.br");
        listaWebServices.add("hom.svc.fazenda.gov.br");
        listaWebServices.add("nfe-homologacao.svrs.rs.gov.br");
        listaWebServices.add("hom.nfe.fazenda.gov.br");

        listaWebServices.add("nfe.sefaz.am.gov.br");
        listaWebServices.add("nfe.sefaz.ba.gov.br");
        listaWebServices.add("nfe.sefaz.ce.gov.br");
        listaWebServices.add("nfe.sefaz.go.gov.br");
        listaWebServices.add("nfe.fazenda.mg.gov.br");
        listaWebServices.add("nfe.sefaz.ms.gov.br");
        listaWebServices.add("nfe.sefaz.mt.gov.br");
        listaWebServices.add("nfe.sefaz.pe.gov.br");
        listaWebServices.add("nfe.sefa.pr.gov.br");
        listaWebServices.add("nfe.sefazrs.rs.gov.br");
        listaWebServices.add("cad.sefazrs.rs.gov.br");
        listaWebServices.add("nfe.fazenda.sp.gov.br");
        listaWebServices.add("www.sefazvirtual.fazenda.gov.br");
        listaWebServices.add("nfe.svrs.rs.gov.br");
        listaWebServices.add("www.svc.fazenda.gov.br");
        listaWebServices.add("nfe.svrs.rs.gov.br");
        listaWebServices.add("www.nfe.fazenda.gov.br");

        // CTE PRODUCAO
        listaWebServices.add("cte.sefaz.mt.gov.br");
        listaWebServices.add("producao.cte.ms.gov.br");
        listaWebServices.add("nfe.sefaz.ms.gov.br");
        listaWebServices.add("cte.fazenda.mg.gov.br");
        listaWebServices.add("cte.fazenda.pr.gov.br");
        listaWebServices.add("cte.svrs.rs.gov.br");
        listaWebServices.add("nfe.fazenda.sp.gov.br");
        listaWebServices.add("cte.svrs.rs.gov.br");
        listaWebServices.add("nfe.fazenda.sp.gov.br");
        listaWebServices.add("www1.cte.fazenda.gov.br");

        // CTE HOMOLOGACAO
        listaWebServices.add("homologacao.sefaz.mt.gov.br");
        listaWebServices.add("homologacao.cte.ms.gov.br");
        listaWebServices.add("hom.nfe.sefaz.ms.gov.br");
        listaWebServices.add("hcte.fazenda.mg.gov.br");
        listaWebServices.add("homologacao.cte.fazenda.pr.gov.br");
        listaWebServices.add("cte-homologacao.svrs.rs.gov.br");
        listaWebServices.add("homologacao.nfe.fazenda.sp.gov.br");
        listaWebServices.add("cte-homologacao.svrs.rs.gov.br");
        listaWebServices.add("homologacao.nfe.fazenda.sp.gov.br");
        listaWebServices.add("hom1.cte.fazenda.gov.br");

        //MDFE HOMOLOGACAO
        listaWebServices.add("mdfe-homologacao.svrs.rs.gov.br");

        //MDFE PRODUCAO
        listaWebServices.add("mdfe.svrs.rs.gov.br");

        //eSOCIAL Homologação
        listaWebServices.add("webservices.producaorestrita.esocial.gov.br");

        //eSOCIAL Produção
        listaWebServices.add("webservices.consulta.esocial.gov.br");
        listaWebServices.add("webservices.envio.esocial.gov.br");


        //EFD-REINF Homologação
        listaWebServices.add("preprodefdreinf.receita.fazenda.gov.br");

        //EFD-REINF Produção

        return listaWebServices;
    }

}
