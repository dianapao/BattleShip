
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author david
 */
public class Estado implements Serializable{
    public boolean isEnd;
    public String ganador;
    
    public Estado(boolean fin, String ganador){
        isEnd = fin;
        this.ganador = ganador;
    }
}
