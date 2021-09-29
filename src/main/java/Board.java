
import javax.swing.JButton;
import javax.swing.JPanel;

public class Board {
    private JButton[][] jArrayButtons; 
    private int rows = 10;
    private int columns = 10;
    
    public Board() {
        rows = 10;
        columns = 10;
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
               
                matrix.add(jArrayButtons[i][j]);
                matrix.updateUI();
            }
        }
        System.out.println("Implementando tablero");
    }
    
}

    

