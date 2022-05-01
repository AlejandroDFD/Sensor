/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alejandrodfd.sensor;
import com.fazecast.jSerialComm.*;
import java.util.ArrayList;

/**
 *
 * @author aleja
 */
public  class Config {
    
    final private static byte[] gasHumTem={(byte)0xFF,0x00,(byte)0x87,0x00,0x00,0x00,0x00,0x00,0x79};
    
    public static String gas(SerialPort p){
        byte[] men={(byte)0xD1};
        byte[] readBuffer=new byte[13];
        p.openPort();
        p.writeBytes(men, men.length);
        p.readBytes(readBuffer, readBuffer.length);
        p.closePort();
        if(readBuffer[0]==33)return "NO2";
        else if(readBuffer[0]==35)return "O3";
        else return "gas no encontrado";
        
    }
    
    /**
     * Genera un ArrayListo con lso SerialPort de los sensores
     * @return ArrayList con los SerialPort
     */
    public static ArrayList<SerialPort> setSensor(){
        ArrayList <SerialPort> s=new ArrayList();
        for(SerialPort se:SerialPort.getCommPorts()){
            if(se.getDescriptivePortName().contains("Silicon")) s.add(se);
        }
        System.out.println(s.size());
        for(SerialPort se:s){
            se.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 1000);
        }
        return s;
    }
    public static byte[] medir(SerialPort p){
        byte[] readB=new byte[13];
        p.openPort();
        p.writeBytes(gasHumTem, gasHumTem.length);
        p.readBytes(readB, readB.length);
        return readB;
    }
}
