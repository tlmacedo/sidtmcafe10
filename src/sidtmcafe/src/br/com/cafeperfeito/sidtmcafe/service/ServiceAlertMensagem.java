package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.Pair;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ServiceAlertMensagem extends JFrame implements Constants {
    Dialog dialog;
    DialogPane dialogPane;
    int qtdTarefasDialog = 2;
    boolean transparenteDialog = false;
    boolean geraMsgRetornoDialog = false;
    ServiceFormatarDado formatTextField;
    Task<?> taskDialog;

    Random random;
    Image imageDialog;
    ImageView imageViewDialog;

    ProgressBar progressBarDialog = new ProgressBar();
    ProgressIndicator progressIndicatorDialog;

    HBox hBoxDialog;
    VBox vBoxDialog;

    Label lblMensagem, lblTextoMsg;
    JFXTextArea textArea;
    JFXComboBox comboBox;
    JFXTextField textField;
    List list;
    String strContagem, tipMascaraField, txtPreLoader = "";
    Button btnOk, btnApply, btnYes, btnClose, btnFinish, btnNo, btnCancel;

    Timeline tlRegressiva, tlLoop;
    int tempo = 0;
    String pontos = "";
    int rdnImg = 0;

    public String cabecalho, promptText, strIco;
    public String resultCabecalho, resultPromptText;
    public String promptCombo;
    public Exception exceptionErr;

    public ServiceAlertMensagem() {

    }

    public ServiceAlertMensagem(String cabecalho, String promptText, String strIco) {
        this.cabecalho = cabecalho;
        this.promptText = promptText;
        this.strIco = strIco;
    }

    public String getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(String cabecalho) {
        this.cabecalho = cabecalho;
    }

    public String getPromptText() {
        return promptText;
    }

    public void setPromptText(String promptText) {
        this.promptText = promptText;
    }

    public String getStrIco() {
        return strIco;
    }

    public void setStrIco(String strIco) {
        this.strIco = strIco;
    }

    public String getResultCabecalho() {
        return resultCabecalho;
    }

    public void setResultCabecalho(String resultCabecalho) {
        this.resultCabecalho = resultCabecalho;
    }

    public String getResultPromptText() {
        return resultPromptText;
    }

    public void setResultPromptText(String resultPromptText) {
        this.resultPromptText = resultPromptText;
    }

    public String getPromptCombo() {
        return promptCombo;
    }

    public void setPromptCombo(String promptCombo) {
        this.promptCombo = promptCombo;
    }

    public Exception getExceptionErr() {
        return exceptionErr;
    }

    public void setExceptionErr(Exception exceptionErr) {
        this.exceptionErr = exceptionErr;
    }

    void carregaDialog() {
        dialog = new Dialog();
        dialogPane = dialog.getDialogPane();

        dialog.initStyle(StageStyle.TRANSPARENT);
        dialogPane.getScene().setFill(Color.TRANSPARENT);
        dialogPane.getStylesheets().setAll(getClass().getResource(STYLE_SHEETS).toString());

        dialogPane.getButtonTypes().clear();
    }

    void preparaDialogPane() {
        if (transparenteDialog) {
            dialogPane.getStyleClass().add("dialog-pane-transparent");
        } else {
            dialog.setHeaderText(getCabecalho());
            dialog.setContentText(getPromptText());
            if (!getStrIco().equals(""))
                try {
                    dialog.setGraphic(new ImageView(this.getClass().getResource(PATH_ICONE + getStrIco() + (getStrIco().contains(".png") ? "" : ".png")).toString()));
                } catch (Exception ex) {

                }
        }
    }

    VBox preencheDialogBasico() {
        hBoxDialog = new HBox();
        hBoxDialog.setSpacing(7);
        hBoxDialog.setAlignment(Pos.CENTER_LEFT);

        vBoxDialog = new VBox();
        vBoxDialog.setAlignment(Pos.CENTER);

        lblTextoMsg = new Label();
        lblMensagem = new Label();
        lblMensagem.getStyleClass().add("msg");
        if (transparenteDialog) {
            int random = (int) (Math.random() * IMAGE_SPLASH.length);
            imageViewDialog = new ImageView();
            addImagem(IMAGE_SPLASH[random]);
            vBoxDialog.getChildren().add(imageViewDialog);
            vBoxDialog.getChildren().add(lblMensagem);
        } else {
            progressIndicatorDialog = new ProgressIndicator();
            progressIndicatorDialog.progressProperty().bind(taskDialog.progressProperty());
            progressIndicatorDialog.setPrefSize(25, 25);
            hBoxDialog.getChildren().addAll(progressIndicatorDialog, lblMensagem);
            vBoxDialog.getChildren().add(hBoxDialog);
        }


        if (geraMsgRetornoDialog) {
            textArea = new JFXTextArea();
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

        vBoxDialog.getChildren().add(progressBarDialog);

        return vBoxDialog;
    }

    VBox preencheDialogComboBox() {
        vBoxDialog = new VBox();
        vBoxDialog.setAlignment(Pos.CENTER_LEFT);

        comboBox = new JFXComboBox();
        comboBox.getItems().setAll(list);
        comboBox.getSelectionModel().select(0);
        comboBox.setPromptText(getPromptText());

        vBoxDialog.getChildren().add(comboBox);

        return vBoxDialog;
    }

    VBox preencheDialogTextBox() {
        vBoxDialog = new VBox();
        vBoxDialog.setAlignment(Pos.CENTER_LEFT);

        vBoxDialog.getChildren().add(textField);

        return vBoxDialog;
    }

    VBox preencheDialogTextBoxEComboBox() {
        vBoxDialog = new VBox();
        vBoxDialog.setSpacing(9);
        vBoxDialog.setAlignment(Pos.CENTER_LEFT);

        comboBox = new JFXComboBox();
        comboBox.getItems().setAll(list);
        comboBox.setPromptText(getPromptCombo());
        comboBox.getSelectionModel().select(0);

        vBoxDialog.getChildren().addAll(comboBox, textField);

        return vBoxDialog;
    }

    void addImagem(String strImage) {
        imageDialog = new Image(getClass().getResource(strImage).toString());
        imageViewDialog.setImage(imageDialog);
        imageViewDialog.setClip(new Circle(120, 120, 120));
    }

    void contagemRegressiva(final int duracaoTimeOut) {
        lblTextoMsg.textProperty().bind(taskDialog.messageProperty());
        tlRegressiva = new Timeline(new KeyFrame(
                Duration.millis(100),
                ae -> {
                    if (tempo % 10 == 1)
                        if (pontos.length() < 3) {
                            pontos += ".";
                        } else {
                            pontos = "";
                        }
                    tempo++;
                    strContagem = " (" + (duracaoTimeOut - (tempo / 10)) + ")" + pontos;
                    lblMensagem.setText(lblTextoMsg.getText() + strContagem);
                }));
        tlRegressiva.setCycleCount(Animation.INDEFINITE);
        tlRegressiva.play();
    }

    void closeDialog() {
        dialog.setResult(ButtonType.CANCEL);
        dialog.close();
    }

    public void getProgressBar(Task<?> task, boolean transparente, boolean showAndWait, int qtdTarefas) {
        qtdTarefasDialog = qtdTarefas;
        transparenteDialog = transparente;
        taskDialog = task;
        carregaDialog();
        preparaDialogPane();
        if (showAndWait) {
            btnOk = new Button();
            btnOk.setOnAction(event -> {
                closeDialog();
            });

            dialogPane.getButtonTypes().add(ButtonType.OK);
            btnOk = (Button) dialogPane.lookupButton(ButtonType.OK);
            btnOk.setDefaultButton(true);
            btnOk.setDisable(true);
        }

        dialogPane.setContent(preencheDialogBasico());

        contagemRegressiva(30);

        taskDialog.setOnSucceeded(event -> {
            if (!showAndWait) {
                closeDialog();
            } else {
                addImagem(IC_CAFE_PERFEITO_240DP);
                btnOk.setDisable(false);
                if (getResultPromptText() != null) {
                    lblMensagem.setText(getResultPromptText());
                    progressBarDialog.setVisible(false);
                }
            }
            tlRegressiva.stop();
        });

        Thread thread = new Thread(taskDialog);
        thread.setDaemon(true);
        thread.start();

        dialog.showAndWait();
    }

    public void getRetornoAlert_OK() {
        carregaDialog();
        preparaDialogPane();
        dialogPane.getStyleClass().add("dialog_ok");

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        btnOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        btnOk.setDefaultButton(true);
        btnOk.setCancelButton(false);

        btnOk = new Button();
        btnOk.setOnAction(event -> {
            closeDialog();
        });

        dialog.showAndWait();
    }

    public Optional<ButtonType> getRetornoAlert_YES_NO() {
        carregaDialog();
        preparaDialogPane();
        dialogPane.getStyleClass().add("dialog_yes_no");

        dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
        btnYes = (Button) dialog.getDialogPane().lookupButton(ButtonType.YES);
        btnYes.setDefaultButton(true);
        btnYes.setCancelButton(false);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
        btnNo = (Button) dialog.getDialogPane().lookupButton(ButtonType.NO);
        btnNo.setCancelButton(true);
        btnNo.setDefaultButton(false);

        dialog.setResultConverter(new Callback() {
            @Override
            public Object call(Object param) {
                if (param == ButtonType.YES)
                    return ButtonType.YES;
                return ButtonType.NO;
            }
        });
        Optional<ButtonType> result = dialog.showAndWait();
        return result;
    }

    public Optional<Pair<String, Object>> getRetornoAlert_TextFieldEComboBox(List listCombo, String tipMascara, String textoPreLoader) {
        tipMascaraField = tipMascara;
        txtPreLoader = textoPreLoader;
        list = listCombo;
        carregaDialog();
        preparaDialogPane();
        dialogPane.getStyleClass().add("dialog_text_field_e_combo_box");

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        btnOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        btnOk.setDefaultButton(true);
        btnOk.setCancelButton(false);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        btnCancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        btnCancel.setCancelButton(true);
        btnCancel.setDefaultButton(false);

        addTextField();

        dialogPane.setContent(preencheDialogTextBoxEComboBox());

        btnOk.setDisable(true);
        comboBox.getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            btnOk.setDisable((comboBox.getSelectionModel().getSelectedIndex() < 0) || (textField.getText().length() == 0));
        });

        //noinspection Duplicates
        dialogPane.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.ENTER && btnOk.isDisable())
                if (comboBox.isFocused())
                    textField.requestFocus();
                else
                    comboBox.requestFocus();
            if (event.getCode() == KeyCode.F12)
                btnCancel.fire();
        });

        textField.textProperty().addListener((ov, o, n) -> {
            btnOk.setDisable((comboBox.getSelectionModel().getSelectedIndex() < 0) || (textField.getText().length() == 0));
        });

        Platform.runLater(() -> comboBox.requestFocus());

        dialog.setResultConverter(new Callback<ButtonType, Object>() {
            @Override
            public Object call(ButtonType param) {
                if (param.getButtonData().isDefaultButton()) {
                    return new Pair<>(textField.getText(), comboBox.getSelectionModel().getSelectedItem());
                }
                return null;
            }
        });

        Optional<Pair<String, Object>> result = dialog.showAndWait();
        return result;
    }

    public Optional<String> getRetornoAlert_TextField(String tipMascara, String textoPreLoader) {
        tipMascaraField = tipMascara;
        txtPreLoader = textoPreLoader;
        carregaDialog();
        preparaDialogPane();
        dialogPane.getStyleClass().add("dialog_text_box");

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        btnOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        btnOk.setDefaultButton(true);
        btnOk.setCancelButton(false);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        btnCancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        btnCancel.setCancelButton(true);
        btnCancel.setDefaultButton(false);

        addTextField();

        dialogPane.setContent(preencheDialogTextBox());

        Platform.runLater(() -> textField.requestFocus());

        dialog.setResultConverter(new Callback<ButtonType, String>() {
            @Override
            public String call(ButtonType param) {
                if (param.getButtonData().isDefaultButton())
                    return textField.getText();
                return null;
            }
        });

        Optional<String> result = dialog.showAndWait();
        return result;
    }

    public Optional<Object> getRetornoAlert_ComboBox(List listCombo) {
        list = listCombo;
        carregaDialog();
        preparaDialogPane();
        dialogPane.getStyleClass().add("dialog_combo_box");

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        btnOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        btnOk.setDefaultButton(true);
        btnOk.setCancelButton(false);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        btnCancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        btnCancel.setCancelButton(true);
        btnCancel.setDefaultButton(false);

        dialogPane.setContent(preencheDialogComboBox());

        Platform.runLater(() -> comboBox.requestFocus());

        dialog.setResultConverter(new Callback<ButtonType, Object>() {
            @Override
            public Object call(ButtonType param) {
                if (param.getButtonData().isDefaultButton())
                    return comboBox.getSelectionModel().getSelectedItem();
                else
                    return null;
            }
        });

        Optional<Object> result = dialog.showAndWait();
        return result;
    }

    void addTextField() {
        textField = new JFXTextField();
        textField.setPromptText(getPromptText());
        if (txtPreLoader != "")
            if (txtPreLoader.contains("telefone"))
                textField.setText(ServiceFormatarDado.getValorFormatado(txtPreLoader.replace("telefone", ""), "telefone"));
            else if (txtPreLoader.contains("celular"))
                textField.setText(ServiceFormatarDado.getValorFormatado(txtPreLoader.replace("celular", ""), "celular"));
            else
                textField.setText(txtPreLoader);
        formatTextField = new ServiceFormatarDado();
        switch (tipMascaraField.replaceAll("\\d", "").toLowerCase()) {
            case "fone":
            case "telefone":
            case "celular":
                formatTextField.maskField(textField, "telefone9");
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    String value = newValue.replaceAll("\\D", "");
                    if (newValue.length() > 0)
                        formatTextField.setMascara(Integer.parseInt(value.substring(0, 1)) > 7 ? "telefone9" : "telefone8");
                });
                break;
            default:
                formatTextField.maskField(textField, tipMascaraField);
        }
    }

    public void errorException(Exception exceptionErr) {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(getCabecalho());
            alert.setContentText("Erro ocorrido em: " + promptText);

            Exception ex = exceptionErr;

            // Create expandable Exception.
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String exceptionText = sw.toString();

            Label label = new Label("The exception stacktrace was:");

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            // Set expandable Exception into the dialog pane.
            alert.getDialogPane().setExpandableContent(expContent);

            alert.showAndWait();
        } catch (Exception ex) {
            if (ex instanceof IllegalStateException) {
                Platform.runLater(() -> {
                    errorException(exceptionErr);
                });
            } else {
                //ex.printStackTrace();
            }
        }
    }

}
