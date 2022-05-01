/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package alejandrodfd.sensor;

import com.fazecast.jSerialComm.SerialPort;
import java.util.ArrayList;

/**
 *
 * @author aleja
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        ArrayList<SerialPort> port=Config.setSensor();
        ArrayList<String> gas= new ArrayList();
        for(SerialPort i:port){
            gas.add(Config.gas(i));
        }
        for(String i:gas){
            System.out.println(i);
        }
        
}
}