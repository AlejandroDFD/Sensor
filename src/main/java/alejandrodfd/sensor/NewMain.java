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
        
        
        Dispositivo.setSensor();
       Dispositivo d1=new Dispositivo(0);
       
       int[] m=d1.medir();
       int tempH=m[8];
       int tempL=m[9];
       
       int t=tempH*(int)Math.pow(2, 8);
       
       System.out.println(t|tempL);
       System.out.println(t);
       System.out.println(tempL);
       
       
       
  
       
       
        
}
}