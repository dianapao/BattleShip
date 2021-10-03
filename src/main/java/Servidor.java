
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Servidor {
    private static ByteArrayOutputStream baos;
    private static ObjectOutputStream oos;
    private static DatagramSocket cl;
    private static int pto = 1255;
    private static int max=65535;
    //private static InetAddress dir;
    
    /*public static void createConnection(){
        try{
            //dir = InetAddress.getByName("127.0.0.1");
            cl = new DatagramSocket(pto);
            System.out.println("Cliente iniciado en el puerto "+cl.getLocalPort());
        }catch(Exception e){
            e.printStackTrace();
        }
    }*/
    
    public static void sendCoordenadas(DatagramPacket dpRecivido)throws IOException{
        int x_serv = 7;
        int y_serv = 8;
        Coordenadas cord = new Coordenadas(x_serv, y_serv);
        
        baos = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(baos);
        
        oos.writeObject(cord);
        oos.flush();
        
        byte[]tmp = baos.toByteArray();
        DatagramPacket p = new DatagramPacket(tmp,tmp.length,dpRecivido.getAddress(),dpRecivido.getPort());
        cl.send(p);
        
        baos.close();
        oos.close();
    }
    
    public static void main(String[] args) {
        try{
            cl = new DatagramSocket(pto);
            cl.setReuseAddress(true);
            System.out.println("Servidor de datagrama iniciado en el puerto "+cl.getLocalPort());
            
            while(true){
                DatagramPacket p = new DatagramPacket(new byte[max],max);
                cl.receive(p);
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(p.getData()));
                Coordenadas coord = (Coordenadas)ois.readObject();
                int x = coord.getX();
                int y = coord.getY();
                
                System.out.println("Coordenadas recibidas de cliente: "+p.getAddress()+":"+p.getPort());
                System.out.println("in serv from cl X:" + x);
                System.out.println("in serv from cl y:" + y);                
                
                sendCoordenadas(p);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
