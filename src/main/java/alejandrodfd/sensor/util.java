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
    
    public static void guardar(File f,String s) throws IOException{
        PrintWriter pw=new PrintWriter(new FileWriter(f,true));
        pw.println(s);
        pw.close();
    }
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
    public static int ppm(int[] b){
        return b[2]*256+b[3];
    }
    public static String salida(int i,int seg){
        return seg+";"+i;
    }
    public static File nuevoDoc(String nombre) throws IOException{
        Calendar fecha = Calendar.getInstance();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        String nom=año+"-"+ mes+"-"+dia;
        if(!(new File(nom).isDirectory()))util.crearCarpeta();  
        
        File f=new File(nom+"\\"+nombre+".csv");
        PrintWriter pw=new PrintWriter(new FileWriter(f));
        pw.println("tiempo(s);concentracion");
        pw.close();
        return f;
    }
    
    /**
     * Obtiene los segundos actuales
     * @return devulve un int con los segundos actuales
     */
    public static int tiempoSegundos(){
        int salida;
        long nss=1000000000;
        salida=(int)(System.nanoTime()/nss);
        return salida;
    }
}
