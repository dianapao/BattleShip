/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author david
 */
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
