package br.com.cafeperfeito.sidtmcafe.service;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class ServiceOpenView {
    /*
     * Class verifica se vai aguardar execução de alguma Thread ou não */
    public ServiceOpenView(Stage stage, boolean showAndWait) {
        if (!showAndWait)
            stage.show();
        else
            try {
                stage.initModality(Modality.APPLICATION_MODAL);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                stage.showAndWait();
            }
    }
}
