public class Barco {
    private int tamaño;
    private boolean[] estado;
    private int[] posicion;
    
    public Barco(int tamaño){
        this.tamaño = tamaño;
        estado = new boolean[tamaño];
        this.posicion = new int[tamaño];
    }
    
    public int getTamño(){
        return tamaño; 
    }
    
}
