package alejandrodfd.sensor;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class PrimaryController implements Initializable {

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
    private TextField bacteria;
    @FXML
    private TextField tiempo;
    @FXML
    private TextField tiempoSegundos;
    @FXML
    private TextField potencia;
    
    @FXML
    private Text te;
    
    @FXML
    private Button botonStart;
    @FXML
    
   
    private ArrayList<Dispositivo> d = new ArrayList();
    

    private int tiempoI, tiempoF;
    Thread hilo=null;
    @FXML
    private TextField muestreo;
    


    public int getTiempo() {
        return 60*Integer.parseInt(this.tiempo.getText());
    }
    public long getMuestreo() {
        return 1000*Long.parseLong(this.muestreo.getText());
    }
    public int getTiempoSeg(){
        return Integer.parseInt(this.tiempoSegundos.getText());
    }



    @FXML
    private void botonStart(ActionEvent event) throws IOException, InterruptedException {
       if(hilo==null||!hilo.isAlive()){
           hilo=new Thread(marc);
           hilo.start();
           botonStart.setText("Cerrar");
       }else{
           System.exit(0);
       }
       
    }

    Task marc = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            try {
                
                te.setVisible(true);
                File f1 = util.nuevoDoc(nombreArchivo1());
                File f2 = util.nuevoDoc(nombreArchivo2());
                tiempoI = util.tiempoSegundos();
                tiempoF = util.tiempoSegundos();

                double c1;
                double c2;
                float temp, hum;

                int seg = tiempoF - tiempoI;
                int tiempoFinal=getTiempo()+getTiempoSeg();
                
               
                while (seg < tiempoFinal) {
                    tiempoF = util.tiempoSegundos();
                    seg = tiempoF - tiempoI;

                    //mediciones
                    d.get(0).medir();
                    d.get(1).medir();

                    //guardar los datos
                    util.guardar(f1, util.salida(d.get(0).getConcentracion(), seg, d.get(0).getTemp(), d.get(0).getHum()));
                    util.guardar(f2, util.salida(d.get(1).getConcentracion(), seg, d.get(1).getTemp(), d.get(1).getHum()));
                    
                    tabla.refresh();
                    

                    te.setText(Integer.toString((tiempoFinal - seg)));
                    Thread.sleep(getMuestreo());

                }

                te.setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            System.exit(0);
            
            return null;

        }
        
        
    };

    public String nombreArchivo1() {
        
        
        return potencia.getText() + "-" + tiempo.getText()+"'"+tiempoSegundos.getText()+"''" + d.get(0).getTipo() + bacteria.getText();
    }

    public String nombreArchivo2() {
        return potencia.getText() + "-" + tiempo.getText()+"'"+tiempoSegundos.getText() +"''"+ d.get(1).getTipo()  + bacteria.getText();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        Dispositivo.setSensor();

        for (int i = 0; i < Dispositivo.sensoresDetectados(); i++) {
            d.add(new Dispositivo(i));
            
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
