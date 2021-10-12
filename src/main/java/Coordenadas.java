
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Coordenadas implements Serializable{
    int x, y;
    boolean isStart = false; //indica si es el inicio del juego
    public int tablero[][]; //Si es el inicio del juego envia su tablero a su contrincante
    public List<Coordenadas> listCoordShips = new ArrayList<>();
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
  
    public Coordenadas(int tablero[][], List listCoord){
        this.tablero = tablero;
        this.listCoordShips = listCoord;
        isStart=true;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public List getListCoordShips(){
        return listCoordShips;
    }
}
