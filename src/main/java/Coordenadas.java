
import java.io.Serializable;

public class Coordenadas implements Serializable{
    int x, y;
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
