/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package alejandrodfd.sensor;

import com.fazecast.jSerialComm.SerialPort;
import java.util.ArrayList;

/**
 *Clase utilizada para la pruebas de puesta en marcha
 * @author aleja
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
       ArrayList<Dispositivo> d=new ArrayList(); 
      Dispositivo.setSensor();

        for (int i = 0; i < Dispositivo.sensoresDetectados(); i++) {
            d.add(new Dispositivo(i));
        }

       System.out.println("decimal:"+d.get(0).getTipo()+d.get(0).getDecimal());
       System.out.println("decimal:"+d.get(1).getTipo()+d.get(1).getDecimal());
       
       
}
}