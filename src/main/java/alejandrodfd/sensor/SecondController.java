/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package alejandrodfd.sensor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author aleja
 */
public class SecondController implements Initializable {
    
    @FXML
    TableView<Dispositivo> tabla;    
    @FXML
    TableColumn<Dispositivo,String> tipo;
    @FXML
    TableColumn<Dispositivo,Double> cConc;
    @FXML
    TableColumn<Dispositivo,Float> cTemp;
    @FXML
    TableColumn<Dispositivo,Float> cHum;
    
    @FXML
    Button bMedir;
    @FXML
    TextField nombreExp;
    @FXML
    LineChart<Integer,Double> graph;
    
    
    private Thread hilo;
    private boolean bucle=true;
    private int tiempoI,tiempoF;
    
    private ArrayList<File> f=new ArrayList();
    private ArrayList<Dispositivo> d = new ArrayList();
    private ArrayList<LineChart.Series> series= new ArrayList();
    
    private void iniciarDocs() throws IOException{
        int i=0;
        for(Dispositivo disp:d){
            f.add(util.nuevoDoc(nombreExp.getText()+disp.getTipo()+"-"+i));
            i++;
        }
    }
    
    Task marc = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            iniciarDocs();
            tiempoF=util.tiempoSegundos();
            tiempoI=util.tiempoSegundos();
            int segundos=tiempoF-tiempoI;
            while(bucle){
                tiempoF=util.tiempoSegundos();
                segundos=tiempoF-tiempoI;
                for(Dispositivo Disp:d){
                    Disp.medir();
                    util.guardar(f.get(d.indexOf(Disp)), util.salida(Disp.getConcentracion(), segundos, Disp.getTemp(), Disp.getHum()));
                    series.get(d.indexOf(Disp)).getData().add(new LineChart.Data(Integer.toString(segundos),Disp.getConcentracion()));
                
                }
                tabla.refresh();         
                               
        
                
                
                
                Thread.sleep(500);
                
            }
            
            
            
            return null;

        }
        
        
    };
    
    
    @FXML
    private void botonMedir(ActionEvent event) throws IOException, InterruptedException {
       if(hilo==null||!hilo.isAlive()){
           bucle=true;
           hilo=new Thread(marc);
           hilo.start();
           bMedir.setText("Cerrar");
       }else{
           bucle=false;
           System.exit(0);
       }
       
    }
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Dispositivo.setSensor();

        for (int i = 0; i < Dispositivo.sensoresDetectados(); i++) {
            d.add(new Dispositivo(i));
            series.add(new LineChart.Series());
            series.get(i).setName(d.get(i).getTipo());
            graph.getData().add(series.get(i));
        }
        
        tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        cConc.setCellValueFactory(new PropertyValueFactory<>("concentracion"));
        cTemp.setCellValueFactory(new PropertyValueFactory<>("temp"));
        cHum.setCellValueFactory(new PropertyValueFactory<>("hum"));
        tabla.getItems().addAll(d);
        
        for(Dispositivo Disp:d){
            Disp.medir();
        }
        
        
        
    }    
    
}
