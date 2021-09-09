/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

/**
 *
 * @author Guillermo_Velez
 */
public class Cliente {

    private int id;
    private int transacciones;

    public Cliente(int id, int transacciones) {
        this.id = id;
        this.transacciones = transacciones;
    }

    public int getId() {
        return id;
    }
    public int getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(int transacciones) {
        this.transacciones = transacciones;
    }
    

    
}
