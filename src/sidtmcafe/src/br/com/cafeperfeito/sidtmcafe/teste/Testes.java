package br.com.cafeperfeito.sidtmcafe.teste;

import com.jfoenix.controls.JFXTextArea;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.com.cafeperfeito.sidtmcafe.interfaces.Constants.IMAGE_SPLASH;
import static br.com.cafeperfeito.sidtmcafe.interfaces.Constants.STYLE_SHEETS;

public class Testes extends Application {
    static Dialog dialog;
    static DialogPane dialogPane;
    private HBox hBoxDialog;
    private Task<?> taskDialog;
    private VBox vBoxDialog;
    Label lblMensagem, lblTextoMsg;
    private ProgressBar progressBarDialog = new ProgressBar();
    private ImageView imageViewDialog;
    private ProgressIndicator progressIndicatorDialog;
    int qtdTarefasDialog = 10;
    private Image imageDialog;

    @Override
    public void start(Stage primaryStage) throws Exception {
        taskDialog = new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < qtdTarefasDialog; i++) {
                    Thread.sleep(500);
                    updateProgress(i, 10);
                    updateMessage("Tarefa: " + i);
                }
                updateProgress(qtdTarefasDialog, qtdTarefasDialog);
                return null;
            }
        };

        dialog = new Dialog();
        dialogPane = dialog.getDialogPane();

        dialog.initStyle(StageStyle.TRANSPARENT);
        dialogPane.getScene().setFill(Color.TRANSPARENT);
        dialogPane.getStylesheets().setAll(getClass().getResource(STYLE_SHEETS).toString());
        dialogPane.getStyleClass().add("dialog-pane-transparent");

        dialogPane.setContent(preencheDialogBasico());

        taskDialog.setOnSucceeded(event -> {
            closeDialog();
        });

        Thread thread = new Thread(taskDialog);
        thread.start();

        dialog.showAndWait();

    }

    void closeDialog() {
        dialog.setResult(ButtonType.CANCEL);
        dialog.close();
    }

    VBox preencheDialogBasico() {
        hBoxDialog = new HBox();
        hBoxDialog.setSpacing(7);
        hBoxDialog.setAlignment(Pos.CENTER_LEFT);

        vBoxDialog = new VBox();
        vBoxDialog.setSpacing(15);
        vBoxDialog.setAlignment(Pos.CENTER);

        lblTextoMsg = new Label();
        lblMensagem = new Label();
        lblMensagem.getStyleClass().add("msg");
        boolean transparenteDialog = false;
        if (transparenteDialog) {
            int random = (int) (Math.random() * IMAGE_SPLASH.length);
            imageViewDialog = new ImageView();
            addImagem(IMAGE_SPLASH[random]);
            vBoxDialog.getChildren().add(imageViewDialog);
            vBoxDialog.getChildren().add(lblTextoMsg);
            vBoxDialog.getChildren().add(progressBarDialog);
            vBoxDialog.getChildren().add(lblMensagem);
        } else {
            progressIndicatorDialog = new ProgressIndicator();
            progressIndicatorDialog.progressProperty().bind(taskDialog.progressProperty());
            progressIndicatorDialog.setPrefSize(25, 25);
            hBoxDialog.getChildren().addAll(progressIndicatorDialog, lblMensagem);
            vBoxDialog.getChildren().add(hBoxDialog);
        }


        boolean geraMsgRetornoDialog = false;
        if (geraMsgRetornoDialog) {
            JFXTextArea textArea = new JFXTextArea();
            textArea.setWrapText(true);
            textArea.setEditable(false);
            vBoxDialog.getChildren().add(textArea);
            progressBarDialog.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        } else {
            if (qtdTarefasDialog > 1) {
                progressBarDialog.progressProperty().bind(taskDialog.progressProperty());
            } else {
                progressBarDialog.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            }
        }

        vBoxDialog.getChildren().add(progressBarDialog = new ProgressBar());

        return vBoxDialog;
    }

    void addImagem(String strImage) {
        imageDialog = new Image(getClass().getResource(strImage).toString());
        imageViewDialog.setImage(imageDialog);
        imageViewDialog.setClip(new Circle(120, 120, 120));
    }


//    public static void main(String... args) {
//        System.out.println("cnpj: [" + new ServiceFormatarDado().gerarMascara("cnpj", 0, "#") + "]");
//        System.out.println("cpf: [" + new ServiceFormatarDado().gerarMascara("cpf", 0, "#") + "]");
//        System.out.println("barcode: [" + new ServiceFormatarDado().gerarMascara("barcode", 0, "#") + "]");
//        System.out.println("moeda: [" + new ServiceFormatarDado().gerarMascara("moeda3", 0, "#") + "]");
//        System.out.println("cep: [" + new ServiceFormatarDado().gerarMascara("cep", 0, "#") + "]");
//        System.out.println("ncm: [" + new ServiceFormatarDado().gerarMascara("ncm", 0, "#") + "]");
//        System.out.println("cest: [" + new ServiceFormatarDado().gerarMascara("cest", 0, "#") + "]");
//        System.out.println("nfechave: [" + new ServiceFormatarDado().gerarMascara("nfechave", 0, "#") + "]");
//        System.out.println("nfenumero: [" + new ServiceFormatarDado().gerarMascara("nfenumero", 0, "#") + "]");
//        System.out.println("nfedocorigem: [" + new ServiceFormatarDado().gerarMascara("nfedocorigem", 0, "#") + "]");
//        System.out.println("telefone8: [" + new ServiceFormatarDado().gerarMascara("telefone", 8, "#") + "]");
//        System.out.println("telefone9: [" + new ServiceFormatarDado().gerarMascara("telefone", 9, "#") + "]");
//        System.out.println("ie(ac): [" + new ServiceFormatarDado().gerarMascara("ieac", 0, "#") + "]");
//        System.out.println("ie(al): [" + new ServiceFormatarDado().gerarMascara("ieal", 0, "#") + "]");
//        System.out.println("ie(am): [" + new ServiceFormatarDado().gerarMascara("ieam", 0, "#") + "]");
//        System.out.println("ie(ap): [" + new ServiceFormatarDado().gerarMascara("ieap", 0, "#") + "]");
//        System.out.println("ie(ba): [" + new ServiceFormatarDado().gerarMascara("ieba", 0, "#") + "]");
//        System.out.println("ie(ce): [" + new ServiceFormatarDado().gerarMascara("iece", 0, "#") + "]");
//        System.out.println("ie(df): [" + new ServiceFormatarDado().gerarMascara("iedf", 0, "#") + "]");
//        System.out.println("ie(es): [" + new ServiceFormatarDado().gerarMascara("iees", 0, "#") + "]");
//        System.out.println("ie(go): [" + new ServiceFormatarDado().gerarMascara("iego", 0, "#") + "]");
//        System.out.println("ie(ma): [" + new ServiceFormatarDado().gerarMascara("iema", 0, "#") + "]");
//        System.out.println("ie(mg): [" + new ServiceFormatarDado().gerarMascara("iemg", 0, "#") + "]");
//        System.out.println("ie(ms): [" + new ServiceFormatarDado().gerarMascara("iems", 0, "#") + "]");
//        System.out.println("ie(mt): [" + new ServiceFormatarDado().gerarMascara("iemt", 0, "#") + "]");
//        System.out.println("ie(pa): [" + new ServiceFormatarDado().gerarMascara("iepa", 0, "#") + "]");
//        System.out.println("ie(pb): [" + new ServiceFormatarDado().gerarMascara("iepb", 0, "#") + "]");
//        System.out.println("ie(pe): [" + new ServiceFormatarDado().gerarMascara("iepe", 0, "#") + "]");
//        System.out.println("ie(pi): [" + new ServiceFormatarDado().gerarMascara("iepi", 0, "#") + "]");
//        System.out.println("ie(pr): [" + new ServiceFormatarDado().gerarMascara("iepr", 0, "#") + "]");
//        System.out.println("ie(rj): [" + new ServiceFormatarDado().gerarMascara("ierj", 0, "#") + "]");
//        System.out.println("ie(rn): [" + new ServiceFormatarDado().gerarMascara("iern", 0, "#") + "]");
//        System.out.println("ie(ro): [" + new ServiceFormatarDado().gerarMascara("iero", 0, "#") + "]");
//        System.out.println("ie(rr): [" + new ServiceFormatarDado().gerarMascara("ierr", 0, "#") + "]");
//        System.out.println("ie(rs): [" + new ServiceFormatarDado().gerarMascara("iers", 0, "#") + "]");
//        System.out.println("ie(sc): [" + new ServiceFormatarDado().gerarMascara("iesc", 0, "#") + "]");
//        System.out.println("ie(se): [" + new ServiceFormatarDado().gerarMascara("iese", 0, "#") + "]");
//        System.out.println("ie(sp): [" + new ServiceFormatarDado().gerarMascara("iesp", 0, "#") + "]");
//        System.out.println("ie(to): [" + new ServiceFormatarDado().gerarMascara("ieto", 0, "#") + "]");
//
//        System.out.println("cnpj(08009246000136): [" + new ServiceFormatarDado().getValorFormatado("08009246000136", "cnpj") + "]");
//        System.out.println("cpf(52309550230): [" + new ServiceFormatarDado().getValorFormatado("52309550230", "cpf") + "]");
//        System.out.println("barcode(7896423421255): [" + new ServiceFormatarDado().getValorFormatado("7896423421255", "barcode") + "]");
//        System.out.println("moeda(29902): [" + new ServiceFormatarDado().getValorFormatado("29902", "moeda3") + "]");
//        System.out.println("cep(69067360): [" + new ServiceFormatarDado().getValorFormatado("69067360", "cep") + "]");
//        System.out.println("ncm(09012100): [" + new ServiceFormatarDado().getValorFormatado("09012100", "ncm") + "]");
//        System.out.println("cest(1234567): [" + new ServiceFormatarDado().getValorFormatado("1234567", "cest") + "]");
//        System.out.println("nfechave(35180406981833000248550010000000471027609712): [" + new ServiceFormatarDado().getValorFormatado("35180406981833000248550010000000471027609712", "nfechave") + "]");
//        System.out.println("nfenumero(47): [" + new ServiceFormatarDado().getValorFormatado("47", "nfenumero")+"]");
//        System.out.println("nfedocorigem(041812441652): [" + new ServiceFormatarDado().getValorFormatado("041812441652","nfedocorigem") + "]");
//        System.out.println("telefone8(38776148): [" + new ServiceFormatarDado().getValorFormatado("38776148","telefone") + "]");
//        System.out.println("telefone9(981686148): [" + new ServiceFormatarDado().getValorFormatado("981686148","telefone") + "]");

//        System.out.println("ie(): [" + new ServiceFormatarDado().gerarMascara("ie", 0, "#") + "]");


//        System.out.print("digite um valor para conversão: ");
//        System.out.println("retorno: [" + getMoeda(new Scanner(System.in).nextLine(), 2) + "]");
//    }

    static String getMoeda(String valor, int casaDecimal) {
        boolean sinal = valor.contains("-");
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(valor.replaceAll("(\\D)", ""));
        String value = "";
        while (m.find())
            value = m.group();
        for (int i = value.length(); i < (casaDecimal + 1); i++)
            value = "0" + value;

        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 18) + "})$", "$1.$2");
        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 15) + "})$", "$1.$2");
        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 12) + "})$", "$1.$2");
        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 9) + "})$", "$1.$2");
        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 6) + "})$", "$1.$2");
        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 3) + "})$", "$1.$2");
        if (casaDecimal > 0)
            value = value.replaceAll("(\\d{1})(\\d{" + casaDecimal + "})$", "$1,$2");

        if (sinal) value = "-" + value;
        return value;
    }

}
