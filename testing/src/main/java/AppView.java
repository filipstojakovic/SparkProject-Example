import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;

public class AppView extends Application
{
    @FXML
    private Label mutationChanceLabel;
    @FXML
    private TextField mutationChanceField;
    @FXML
    private ComboBox<String> mutationComboBox;
    ObservableList<String> mutationWay = FXCollections
                                        .observableArrayList("duplicate","duplicate with mutation chance", "four time combination");

    public static void main(String[] args)
    {
        launch(args);
    }

    @FXML
    private void initialize()
    {
        mutationComboBox.setValue("duplicate");
        mutationComboBox.setItems(mutationWay);

    }

    @FXML
    void mutationComboBoxAction(ActionEvent event)
    {
        if("duplicate with mutation chance".equals(mutationComboBox.getValue()))
        {
            mutationChanceField.setVisible(true);
            mutationChanceLabel.setVisible(true);
        }else
        {
            mutationChanceField.setVisible(false);
            mutationChanceLabel.setVisible(false);
        }

    }


    @FXML
    @Override
    public void start(Stage primaryStage)
    {
        String sceneFile = "fxml/AppVeiw.fxml";
        Parent root = null;
        URL url = null;
        try
        {
            url = getClass().getResource(sceneFile);
            root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            primaryStage.setTitle("Generic Algotithm");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception ex)
        {
            System.out.println("Exception on FXMLLoader.load()");
            System.out.println("  * url: " + url);
            System.out.println("  * " + ex);
        }

    }


}
