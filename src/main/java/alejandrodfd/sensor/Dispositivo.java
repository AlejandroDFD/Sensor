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
public class Dispositivo {

    final private static byte[] gasHumTem = {(byte) 0xFF, 0x00, (byte) 0x87, 0x00, 0x00, 0x00, 0x00, 0x00, 0x79};
    private static ArrayList<SerialPort> sensores;
    
    private byte[] configuracion;
    
    private SerialPort puerto;
    private String tipo;
    private int decimal;
    private int[] medir;
    
    private double concentracion;
    private float temp,hum;

    public Dispositivo(int index) {
        this.puerto=sensores.get(index);
        this.setConfiguracion(puerto);
        this.setDecimal();
        this.setTipo();    
        
    }
    
    
    
    /**
     * Set de la posición decimal 
     */
    private void setDecimal() {
        this.decimal = (configuracion[4]|(configuracion[5]<<1)|(configuracion[6]<<2)|(configuracion[7]<<3))/100;
    }
    /**
     * Obtener la posición decimal de la medición
     * @return 
     */
    public int getDecimal() {
        return decimal;
    }
    
    
    
    

    /**
     * Conseguir el SerialPort por el index
     *
     * @param index
     * @return
     */
    public SerialPort getSenores(int index) {
        return Dispositivo.sensores.get(index);
    }

    /**
     * Genera un ArrayListo con los SerialPort de los sensores
     *
     * @return ArrayList con los SerialPort
     */
    public static void setSensor() {
        ArrayList<SerialPort> s = new ArrayList();
        for (SerialPort se : SerialPort.getCommPorts()) {
            if (se.getDescriptivePortName().contains("Silicon")) {
                s.add(se);
            }
        }
        System.out.println(s.size());
        for (SerialPort se : s) {
            se.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 1000);
        }
        Dispositivo.sensores = s;
    }
    /**
     * Set del tipo de gas medido
     */
    private void setTipo() {
        byte[] men = {(byte) 0xD1};
        byte[] readBuffer = new byte[13];
        this.puerto.openPort();
        this.puerto.writeBytes(men, men.length);
        this.puerto.readBytes(readBuffer, readBuffer.length);
        this.puerto.closePort();
        if (readBuffer[0] == 33) {
            this.tipo = "NO2";
        } else if (readBuffer[0] == 35) {
            this.tipo = "O3";
        } else {
            this.tipo = "gas no encontrado";
        }

    }
    /**
     * 
     * @return Retorna String del tipo de gas medido
     */
    public String getTipo(){
        return this.tipo;
    }
    /**
     * set del Puerto del dispositivo
     * @param puerto 
     */
    private void setPuerto(SerialPort puerto) {
        this.puerto = puerto;
    }
    /**
     * get del puerto del dispositivo
     * 
     * @return 
     */
    public SerialPort getPuerto() {
        return puerto;
    }
    /**
     * seto de la configuarción del sipositivo con el SerialPort
     * @param p 
     */
    private void setConfiguracion(SerialPort p) {
        byte[] men = {(byte) 0xD1};
        byte[] readBuffer = new byte[9];
        p.openPort();
        p.writeBytes(men, men.length);
        p.readBytes(readBuffer, readBuffer.length);
        p.closePort();
        this.configuracion=readBuffer;
    }
    
    public byte[] getConfiguracion() {
        return configuracion;
    }
    
    
    
    /**
     * Realizar una medición por metodo pregunta/respuesta
     * @return 
     */
    public void medir() {
        byte[] readB = new byte[13];
        this.puerto.openPort();
        this.puerto.writeBytes(gasHumTem, gasHumTem.length);
        this.puerto.readBytes(readB, readB.length);
        this.medir= util.conversor(readB);
        this.temperatura();
        this.humedad();
        this.ppm();
    }
    
    /**
     * Cantidad de sensores detectados.
     * @return 
     */
    public static int sensoresDetectados(){
        return Dispositivo.sensores.size();
    }
    
    /**
     * Devuelve la concentración en ppm de una medición para un dispositivo.
     * 
     */
    private void ppm(){
        
        this.concentracion= (this.medir[6]*256+this.medir[7])/Math.pow(10, (double)this.getDecimal());
    }
    public double getConcentracion(){
        return this.concentracion;
    }
    
    /**
     * obtención de la temperatura
     
     * @return 
     */
    private void temperatura(){
        this.temp= (float)((int)((this.medir[8]<<8)|this.medir[9]))/100;
    }
    
    public float getTemp(){
        return this.temp;
    }
    /**
     * obtención de la humedad 
     * 
     */
    private void humedad(){
        this.hum= (float)((int)((this.medir[10]<<8)|this.medir[11]))/100;
    }
    public float getHum(){
        return this.hum;
    }
}
