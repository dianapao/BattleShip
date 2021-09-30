

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Board implements  MouseListener{
    private JButton[][] jArrayButtons; 
    private Barco[] barcos = new Barco[7];
    private int rows = 10;
    private int columns = 10;
    private int[][] tablero = new int[10][10];
    private int barcosDisponibles;
    private int orientacion=0;
    public Board() {
        rows = 10;
        columns = 10;
        barcos[0]=new Barco(4);
        barcos[1]=new Barco(3);
        barcos[2]=new Barco(3);
        barcos[3]=new Barco(2);
        barcos[4]=new Barco(2);
        barcos[5]=new Barco(2);
        barcos[6]=new Barco(5);
        this.barcosDisponibles = 0;
        this.orientacion=0;
        for(int i =0;i<10;i++){
            for(int j=0;j<10;j++){
                tablero[i][j]=-1;
            }
        }
        
    }
    
    public void setMatrix(JPanel matrix){
        jArrayButtons = new JButton[rows][columns];
        int wButton = matrix.getWidth() / rows;
        int hButton = matrix.getHeight() / columns;
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                jArrayButtons[i][j] = new JButton();
                //CUADRO[i][j].setBackground(Color.green);
                jArrayButtons[i][j].setBounds(j*wButton, i*hButton, wButton, hButton);
                jArrayButtons[i][j].setBackground(Color.CYAN);
              
                jArrayButtons[i][j].setName(i+":"+j);
                matrix.add(jArrayButtons[i][j]);
                matrix.updateUI();
            }
        }
        System.out.println("Implementando tablero");
    }

    public void ColocarBarcos(){
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                jArrayButtons[i][j].addMouseListener(this);
            }
        }   
    }
    
    public boolean comprobarPintar(int x,int y){
        if(tablero[y][x] == -1){
            return true;
        }
        return false;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        
        if(e.getButton() == MouseEvent.BUTTON3){
            this.mouseExited(e);
            this.orientacion = (this.orientacion+1)%4;
            this.mouseEntered(e);
        }else if(e.getButton() == MouseEvent.BUTTON1){
            System.out.println("Boton 1");
            Object aux = e.getSource();
            JButton boton=null;
            if(aux instanceof JButton){
                boton = (JButton) aux;
            }
            String[] cord = boton.getName().split(":");
            int y = Integer.parseInt(cord[0]);
            int x = Integer.parseInt(cord[1]);
            if(comprobar(x,y)){
                colocar(x,y);
            }
            if(barcosDisponibles>6){
                for(int i=0; i<rows; i++){
                    for(int j=0; j<columns; j++){
                        jArrayButtons[i][j].removeMouseListener(this);
                    }
                }
            }
        }
            
    }
    
    public void colocar(int x, int y){
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
        int espacios = barcos[barcosDisponibles].getTamño();
        while(espacios>0){
            jArrayButtons[i][j].setBackground(Color.DARK_GRAY);
            tablero[i][j]=barcosDisponibles;
            
            i+=y1;
            j+=x1;
            
            espacios--;
        }
        barcosDisponibles++;
    }
    public boolean comprobar(int x, int y){
        
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
        int espacios = barcos[barcosDisponibles].getTamño();
        while(espacios>0){
            if(i<0 || i>9 || j<0 || j>9 || !comprobarPintar(j,i)){
                return false;
            }
            i+=y1;
            j+=x1;
            
            espacios--;
        }
        return true;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object aux = e.getSource();
        JButton boton=null;
        if(aux instanceof JButton){
            boton = (JButton) aux;
        }
        int x=0,y=0,x1=0,y1=0;
        String[] cord = boton.getName().split(":");
        y = Integer.parseInt(cord[0]);
        x = Integer.parseInt(cord[1]);
        
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
        int espacios = barcos[barcosDisponibles].getTamño();
        while(espacios>0){
            if(i<0 || i>9 || j<0 || j>9){
                break;
            }
            if(comprobarPintar(j,i)){
                jArrayButtons[i][j].setBackground(Color.DARK_GRAY);
            }
            
            i+=y1;
            j+=x1;
            
            espacios--;
        }
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object aux = e.getSource();
        JButton boton=null;
        if(aux instanceof JButton){
            boton = (JButton) aux;
        }
        int x=0,y=0,x1=0,y1=0;
        String[] cord = boton.getName().split(":");
        y = Integer.parseInt(cord[0]);
        x = Integer.parseInt(cord[1]);
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
        int espacios = barcos[barcosDisponibles].getTamño();
        while(espacios>0){
            if(i<0 || i>9 || j<0 || j>9){
                break;
            }
            if(comprobarPintar(j,i)){
                jArrayButtons[i][j].setBackground(Color.CYAN);
            }
            
            i+=y1;
            j+=x1;
            
            espacios--;
        }
        
    }
    
}

    
