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
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.text.Text;

public class PrimaryController implements Initializable {

    @FXML
    private TextField bacteria;
    @FXML
    private TextField tiempo;
    @FXML
    private TextField tiempoSegundos;
    @FXML
    private TextField potencia;
    @FXML
    private Text gas1;
    @FXML
    private Text gas2;
    @FXML
    private Text con1;
    @FXML
    private Text con2;
    @FXML
    private Text te;
    @FXML
    private Text hum;
    @FXML
    private Text temp;
    @FXML
    private Button botonStart;
   
    private ArrayList<Dispositivo> d = new ArrayList();

    private int tiempoI, tiempoF;
    Thread hilo=null;
    


    public int getTiempo() {
        return 60*Integer.parseInt(this.tiempo.getText());
    }
    public int getTiempoSeg(){
        return Integer.parseInt(this.tiempoSegundos.getText());
    }

    public void setCon1(double i) {
        this.con1.setText(Double.toString(i));
    }

    public void setCon2(double i) {
        this.con2.setText(Double.toString(i));
    }

    public void setHum(float h) {
        this.hum.setText(Float.toString(h));
    }

    public void setTemp(float t) {
        this.temp.setText(Float.toString(t));
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
                    
                    setCon1(d.get(0).getConcentracion());
                    setCon2(d.get(1).getConcentracion());
                    setTemp(d.get(0).getTemp());
                    setHum(d.get(0).getHum());

                    Thread.sleep(700);
                    te.setText(Integer.toString((tiempoFinal - seg)));

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
        
        
        return potencia.getText() + "-" + tiempo.getText()+"'"+tiempoSegundos.getText()+"''" + gas1.getText() + bacteria.getText();
    }

    public String nombreArchivo2() {
        return potencia.getText() + "-" + tiempo.getText()+"'"+tiempoSegundos.getText() +"''"+ gas2.getText() + bacteria.getText();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        Dispositivo.setSensor();

        for (int i = 0; i < Dispositivo.sensoresDetectados(); i++) {
            d.add(new Dispositivo(i));
        }
        if(Dispositivo.sensoresDetectados()==2){
           this.gas1.setText(d.get(0).getTipo());
           this.gas2.setText(d.get(1).getTipo()); 
        }else{
           this.gas1.setText("no detectados");
           this.gas2.setText("no detectados");
        }
        

    }

}
