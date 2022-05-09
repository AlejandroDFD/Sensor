/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alejandrodfd.sensor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 *
 * @author aleja
 */
public class util {
    
    /**
     * guarda una cadena de caracteres en un archivo
     * @param f
     * @param s
     * @throws IOException 
     */
    public static void guardar(File f,String s) throws IOException{
        PrintWriter pw=new PrintWriter(new FileWriter(f,true));
        pw.println(s);
        pw.close();
    }
    
    /**
     *Genera una carpeta con la fecha actual como nombre
     */
    private static void crearCarpeta(){
        Calendar fecha = Calendar.getInstance();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        String nombre=año+"-"+ mes+"-"+dia;
        new File(nombre).mkdir();
    }
    public static int[] conversor(byte[] b){
        int[] i=new int[b.length];
        int c=0;
        for(byte by:b){
            if(by<0)i[c]=by+256;
            else i[c]=by;
            c++;
        }
        return i;
    }
    
    /**
     * Devuelve la concentración en ppm de una medición para un dispositivo.
     * @param medicion medición
     * @param d dispositivo
     * @return 
     */
    public static double ppm(int[] medicion,Dispositivo d){
        return (medicion[6]*256+medicion[7])/Math.pow(10, (double)d.getDecimal());
    }
    public static float temperatura(int[] medicion,Dispositivo d){
        return ((medicion[8]*(int)Math.pow(2, 8))|medicion[9])/100;
    }
    
    public static float humedad(int[] medicion,Dispositivo d){
        return ((medicion[10]*(int)Math.pow(2, 8))|medicion[11])/100;
    }
    
    /**
     * Adapta la cadena de caracteres para una salida CSV
     * @param i medicioón ppm
     * @param seg segundos del experimento
     * @return String
     */
    public static String salida(double i,int seg,float temp, float hum){
        return seg+";"+i+";"+temp+";"+hum;
    }
    
    /**
     * Creación de un documento con la cabecera de datos.
     * @param nombre
     * @return
     * @throws IOException 
     */
    public static File nuevoDoc(String nombre) throws IOException{
        Calendar fecha = Calendar.getInstance();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        String nom=año+"-"+ mes+"-"+dia;
        if(!(new File(nom).isDirectory()))util.crearCarpeta();  
        
        File f=new File(nom+"\\"+nombre+".csv");
        PrintWriter pw=new PrintWriter(new FileWriter(f));
        pw.println("Tiempo(s);Concentracion (ppm);Temperatura;Humedad");
        pw.close();
        return f;
    }
    
    /**
     * Obtiene los segundos actuales
     * @return devuelve un int con los segundos actuales
     */
    public static int tiempoSegundos(){
        int salida;
        long nss=1000000000;
        salida=(int)(System.nanoTime()/nss);
        return salida;
    }
}
