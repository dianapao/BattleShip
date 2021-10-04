
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
    private static int ptoCl;
    private static InetAddress dir;
    private static int max=65535;
    private static String nombreJugador;
    private static int[][] tablero = new int[10][10];
    private static Barco[] barcos = new Barco[7];
    private static int barcosDisponibles;
    private static int orientacion=0;
    
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
        int x_serv = rand(0,9);
        int y_serv = rand(0,9);
        Coordenadas cord = new Coordenadas(x_serv, y_serv);
        sendPacket(cord); 
        System.out.println("Enviando coordenadas al azar al cliente ");
    }
    
    public static void sendPacket(Object o)throws IOException{
        baos = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(baos);
        
        oos.flush();
        oos.writeObject(o);
        
        byte[]tmp = baos.toByteArray();
        DatagramPacket p = new DatagramPacket(tmp,tmp.length,dir,ptoCl);
        cl.send(p);   
        
        baos.close();
        oos.close();
    }
    public static void Inicializar(){
        barcos[0]=new Barco(4);
        barcos[1]=new Barco(3);
        barcos[2]=new Barco(3);
        barcos[3]=new Barco(2);
        barcos[4]=new Barco(2);
        barcos[5]=new Barco(2);
        barcos[6]=new Barco(5);
        
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                tablero[i][j]=-1;
            }
        }
        
        for(int i=0;i<barcos.length;i++){
            colocarBarco(barcos[i],i);
        }
    }
    public static void colocarBarco(Barco b,int id){
        
        while(true){
            int x= rand(0,9),y=rand(0,9);
            int intentos=0;
            for(orientacion = rand(0,3);intentos<4;orientacion = (orientacion+1)%4){
               if(comprobar(x,y,b)){
                  colocar(x,y,b,id);
                  return;
               } 
               intentos++;
            }
            
        }
    }
    
    public static int rand(int inf,int sup){
        return (int)(Math.random()*(sup-inf+1)+inf);
    }
    private static boolean comprobarLugar(int x,int y){
        if(tablero[y][x] == -1){
            return true;
        }
        return false;
    }
    private static void colocar(int x, int y,Barco b,int id){
        int x1=0,y1=0;
        switch(orientacion){
            case 0:
                x1=-1;
                y1=0;
                break;
            case 1:
                x1=1;
                y1=0;
                break;
            case 2:
                x1=0;
                y1=-1;
                break;
            case 3:
                x1=0;
                y1=1;
                break;
        }
        int i=y;
        int j=x;
        int espacios =b.getTamño();
        
        
        
        while(espacios>0){            
            tablero[i][j] = id;            
            i+=y1;
            j+=x1;            
            espacios--;
        }
    }
    private static boolean comprobar(int x, int y,Barco b){
        
        int x1=0,y1=0;
        switch(orientacion){
            case 0:
                x1=-1;
                y1=0;
                break;
            case 1:
                x1=1;
                y1=0;
                break;
            case 2:
                x1=0;
                y1=-1;
                break;
            case 3:
                x1=0;
                y1=1;
                break;
        }
        int i=y;
        int j=x;
        int espacios = b.getTamño();
        while(espacios>0){
            if(i<0 || i>9 || j<0 || j>9 || !comprobarLugar(j,i)){
                return false;
            }
            i+=y1;
            j+=x1;
            
            espacios--;
        }
        return true;
    }
    

    public static void main(String[] args) {
        try{
            cl = new DatagramSocket(pto);
            cl.setReuseAddress(true);
            System.out.println("Servidor de datagrama iniciado en el puerto "+cl.getLocalPort());
            //baos = new ByteArrayOutputStream();
            //oos = new ObjectOutputStream(baos);
            while(true){
                DatagramPacket p = new DatagramPacket(new byte[max],max);
                cl.receive(p);
                System.out.println("Paquete recibido");
                
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(p.getData()));
                Object auxiliar = ois.readObject();
                //if(auxiliar instanceof String)
                
                if(auxiliar instanceof Coordenadas){
                    //Coordenadas coord = (Coordenadas)ois.readObject();
                    Coordenadas coord = (Coordenadas)auxiliar;
                    int x = coord.getX();
                    int y = coord.getY();
                    System.out.println("Coordenadas recibidas de cliente: "+p.getAddress()+":"+p.getPort());
                    System.out.println("in serv from cl X:" + x);
                    System.out.println("in serv from cl y:" + y);
                    sendCoordenadas(p);
                    
                }else if(auxiliar instanceof String){
                    nombreJugador = (String)auxiliar;
                    auxiliar = null;
                    cl.connect(p.getSocketAddress());
                    ptoCl = p.getPort();
                    dir = p.getAddress();
                    System.out.println("Nombre establecido: "+ nombreJugador);
                    
                    sendPacket("OK");
                    System.out.println("Confirmacion enviada");
                    Inicializar();
                    for(int i = 0;i<tablero.length;i++){
                        for(int j=0;j<tablero.length;j++){
                            if(tablero[i][j]>=0){
                                System.out.print("+");
                            }
                            System.out.print(tablero[i][j]+" ");
                        }
                        System.out.println();
                    }
                }
                
                /*cl.receive(p);
                Coordenadas coord = (Coordenadas)ois.readObject();
                int x = coord.getX();
                int y = coord.getY();
                
                System.out.println("Coordenadas recibidas de cliente: "+p.getAddress()+":"+p.getPort());
                System.out.println("in serv from cl X:" + x);
                System.out.println("in serv from cl y:" + y);                
                
                sendCoordenadas(p);*/
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
