
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;


//implements Runnable
public class Cliente {
    private static int[][] board = null;
    private int pto = 1255;
    private int max = 65535;
    private int x,y;
    private DatagramPacket peco;
    
    InetAddress dir;
    DatagramSocket cl;
    ByteArrayOutputStream baos;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    
    public Cliente(){
        this.max = 65535;
    }
    
    public void createConecction(){
        System.out.println("function createConecction");
        try{
            dir = InetAddress.getByName("127.0.0.2");
            cl = new DatagramSocket();
            System.out.println("Cliente iniciado en el puerto "+cl.getLocalPort());
        }catch(Exception e){
            System.out.println("Error connection");
            e.printStackTrace();
        }
    }
    
    public void createStreams(){
        try{
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void closeStreams() throws IOException{
        oos.close();
        baos.close();
    }
    
    public void sendPacket(Object o)throws IOException{
        createStreams();
        oos.flush();
        oos.writeObject(o);
        
        byte[]tmp = baos.toByteArray();
        DatagramPacket p = new DatagramPacket(tmp,tmp.length,dir,pto);
        cl.send(p);   
    }
    
    public Object recivePacket() throws Exception{
        DatagramPacket p = new DatagramPacket(new byte[max],max);
        cl.receive(p);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(p.getData()));
        return ois.readObject();
    }
    
    public void Negociar(String nombre){
        try{
            sendPacket(nombre);
            Object aux = recivePacket();
            while(true){
                if(aux instanceof String){
                    if(((String)aux).equals("OK")){
                        System.out.println("Negociacion exitosa");
                        closeStreams();
                        return;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        
        
    }
    
    public void sendServerAdivinaCoordenadas() throws IOException, ClassNotFoundException{
        
        /*try{
            Coordenadas cord = new Coordenadas(getCoordenadaX(), getCoordenadaY());
            System.out.println("in Cliente X: " + getCoordenadaX());
            System.out.println("in Cliente Y: " + getCoordenadaY());
            sendPacket(cord);
            //Object aux = recivePacket();
        }catch (Exception e){
            e.printStackTrace();
        }*/
        createStreams();
        Coordenadas cord = new Coordenadas(getCoordenadaX(), getCoordenadaY());
        System.out.println("Cliente X: " + getCoordenadaX());
        System.out.println("Cliente Y: " + getCoordenadaY());
        oos.writeObject(cord);
        oos.flush();
        byte[]tmp = baos.toByteArray();
        DatagramPacket p = new DatagramPacket(tmp,tmp.length,dir,pto);
        cl.send(p);        
        closeStreams();
        
        getRespuestaServidor();
        
        //closeStreams();
    }
    
    public void setAdivinaCoordenadas(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int getCoordenadaX(){
        return x;
    }
    
    public int getCoordenadaY(){
        return y;
    }
    
    public void getRespuestaServidor() throws IOException, ClassNotFoundException{
        byte[]tmp = baos.toByteArray();
        DatagramPacket peco = new DatagramPacket(new byte[tmp.length],tmp.length);
        cl.receive(peco);
        ois = new ObjectInputStream(new ByteArrayInputStream(peco.getData()));
        Coordenadas coord = (Coordenadas)ois.readObject();
        int x = coord.getX();
        int y = coord.getY();
        System.out.println("Coordenadas recibidas de servidor X: " + x);
        System.out.println("Coordenadas recibidas de servidor Y: " + y);
        
        ois.close();
     //   cl.close();
    }
    
}
