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
public class Thread1 extends Thread {

    boolean eventFlag = Main.gui.getEventFlag();

    int dekkerNo = 0;
    int turn = 0;

    public void Dekker(int No) {
        dekkerNo = No;
        turn = Main.gui.getTurn();
        Main.gui.setEventFlag(true);
        eventFlag = true;
    }

    @Override
    public void run() {
        while (true && !this.isInterrupted()) {
            while (eventFlag) {
                switch (dekkerNo) {
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

    private void dekker1() {
        // Hace tasks
     //   doTasks("initial", 2);
        // Espera a que la región crítica se desocupe
        while (Main.gui.getTurn() == 1) {
            waitcs();
        }
        // Accede a Región Crítica
    /*    criticalSection(3);
        // Hace final tasks   
        Main.gui.setTurn(1);
        doTasks("final", 3);*/
    }



    private void criticalSection(int time) {
        try {
            Main.gui.setVisibleProgressBarP1("show");
            Main.gui.setOperationProgressBarP1("bussy");
            Main.gui.updateStatusP1("Using critical section...");
            //sleep((int)((Math.random() * 2) + 2) * 1000);
            sleep(time * 1000);
        } catch (InterruptedException e) {
        }
    }

    private void waitcs() {
        Main.gui.updateStatusP1("Waiting  ");
        Main.gui.setOperationProgressBarP1("wait");
   //     Main.gui.setVisibleProgressBarP1("show");
    }
}
