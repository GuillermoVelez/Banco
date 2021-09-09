/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;
/**
 *
 * @author Edson García
 */
public class Thread2 extends Thread {
    
    boolean eventFlag = Main.getEventFlag();
    
    int dekkerNo = 0;
    int turn = 0;
    Cliente cliente= new Cliente(3265326,0);
    
    public void Dekker(int No,Cliente c){
      
        dekkerNo = No;
        turn = Main.gui.getTurn();
        Main.gui.setEventFlag(true);
        eventFlag = true;
    }
    
    @Override
    public void run() {
       while(true && !this.isInterrupted()) {
           while (eventFlag){
               switch(dekkerNo){
                   case 1:
                       dekker1();
                       break;
                   default:
                       break;
               }
               eventFlag = Main.gui.getEventFlag();
           }
       }
    }
    
    private void dekker1(){
            // Hace tasks
     /*       doTasks("initial", 2);
            // Espera a que la región crítica se desocupe
            while (Main.gui.getTurn() == 2) {
                waitcs();
            }*/
            // Accede a Región Crítica
            criticalSection(2);
            // Hace final tasks            
     /*       Main.gui.setTurn(2);
            doTasks("final", 2);*/
    }
    
    
    private void criticalSection(int time){
        try{
            Main.gui.setVisibleProgressBarP2("show");
            Main.gui.setOperationProgressBarP2("bussy");
            Main.gui.updateStatusP2("Using critical section...");
            //sleep((int)((Math.random() * 2) + 2) * 1000);
            sleep(time * 1000);
        }catch(InterruptedException e){}
    }

    private void waitcs(){
        Main.gui.updateStatusP2("Waiting  ");
        Main.gui.setOperationProgressBarP2("wait");
        Main.gui.setVisibleProgressBarP2("show");
    }   
}