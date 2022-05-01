package alejandrodfd.sensor;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import com.fazecast.jSerialComm.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class PrimaryController implements Initializable {

    @FXML
    private  TextField bacteria;
    @FXML
    private  TextField tiempo;
    @FXML
    private  TextField potencia;
    @FXML
    private  TextField gas1;
    @FXML
    private  TextField gas2;
    @FXML
    private  TextField con1;
    @FXML
    private  TextField con2;
    @FXML
    private  Text te;
    private boolean marcha=true;
    private boolean entrada=false;
    
    private int  tiempoI,tiempoF;
    
    Task aparece = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
           Platform.runLater(new Runnable(){
               @Override
               public void run() {
                   te.setVisible(true);
               }
           }); 
            return null;
        }

    };
    
    
    public static ArrayList<SerialPort> sensores;
    
    
    public  int getTiempo(){
        return Integer.parseInt(this.tiempo.getText());
    }
    public ArrayList<SerialPort> getSenores(){
        return PrimaryController.sensores;
    }
    @FXML
    private void botonDetectar(ActionEvent event){
        ArrayList<SerialPort> s=Config.setSensor();
        ArrayList<String> gas=new ArrayList();
        for(SerialPort i:s){
            gas.add(Config.gas(i));
        }
        
        for(String si:gas){
            int num=gas.indexOf(si);
            if(num==0)this.gas1.setText(gas.get(0));
            if(num==1)this.gas2.setText(gas.get(1));
        }
        PrimaryController.sensores=s;
    }
    public void setCon1(int i){
        this.con1.setText(Integer.toString(i));
    }
    public  void setCon2(int i){
        this.con2.setText(Integer.toString(i));
    }
    @FXML
    private void botonStart(ActionEvent event) throws IOException, InterruptedException {
        new Thread(marc).start();

    }
    
    Task marc=    new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    te.setVisible(true);
                    File f1 = util.nuevoDoc(nombreArchivo1());
                    File f2 = util.nuevoDoc(nombreArchivo2());
                    tiempoI = util.tiempoSegundos();
                    tiempoF = util.tiempoSegundos();
                    int c1;
                    int c2;
                    int seg = tiempoF - tiempoI;
                    while (seg < getTiempo()) {
                        tiempoF = util.tiempoSegundos();
                        seg = tiempoF - tiempoI;
                        c1 = util.ppm(util.conversor(Config.medir(getSenores().get(0))));
                        c2 = util.ppm(util.conversor(Config.medir(getSenores().get(1))));
                        util.guardar(f1, util.salida(c1, seg));
                        util.guardar(f2, util.salida(c2, seg));
                        setCon1(c1);
                        setCon2(c2);
                        Thread.sleep(500);
                        te.setText(Integer.toString((getTiempo()-seg)));

                    }

                    te.setVisible(false);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return null;

            }
        };
        
    
    public  String nombreArchivo1(){
        return potencia.getText()+"-"+tiempo.getText()+gas1.getText()+bacteria.getText();
    }
    public  String nombreArchivo2(){
        return potencia.getText()+"-"+tiempo.getText()+gas2.getText()+bacteria.getText();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        
        
    }
    
    
    
}
