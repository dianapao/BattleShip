
import java.io.Serializable;

public class Coordenadas implements Serializable{
    int x, y;
    boolean isStart = false; //indica si es el inicio del juego
    public int tablero[][]; //Si es el inicio del juego envia su tablero a su contrincante
    public boolean whoInit = false; // Si es el inicio del juego envia quien empieza el juego;
                                    //false = servidor; true= cliente
    public Coordenadas(int tablero[][]){
        this.tablero = tablero;
        isStart=true;
    }
    public Coordenadas(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
}
