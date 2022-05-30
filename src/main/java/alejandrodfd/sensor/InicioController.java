/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package alejandrodfd.sensor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author aleja
 */
public class InicioController implements Initializable {

    
    @FXML
    private Button boton2Sen;
    @FXML
    private Button botonTodo;
    
    @FXML
    private void boton2SenAction(ActionEvent event) throws IOException, InterruptedException {
    
       App.setRoot("primary");
    }
    @FXML
    private void botonTodoAction(ActionEvent event) throws IOException, InterruptedException {
    
       App.setRoot("second");
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
