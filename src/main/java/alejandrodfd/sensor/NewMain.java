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
        
        byte[] med=Config.medir(port.get(1));
        
        for(byte i:med){
            System.out.print(i+",");
        }
        int[] conv=util.conversor(med);
        System.out.println(" ");
        
        for(int i:conv){
            System.out.print(i+",");
        }
        System.out.println(conv[6]*256+conv[7]);
        
        SerialPort p=port.get(1);
        byte[] men={(byte)0xD1};
        byte[] readBuffer=new byte[13];
        p.openPort();
        p.writeBytes(men, men.length);
        p.readBytes(readBuffer, readBuffer.length);
        p.closePort();
        
        for(byte i:readBuffer){
            System.out.print(i+",");
        }
        
        
}
}