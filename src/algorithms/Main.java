/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import static java.lang.Thread.sleep;
import java.util.TimerTask;
import javax.swing.JLabel;
import java.util.Timer;

/**
 *
 * @author Edson García
 */
public class Main extends javax.swing.JFrame {

    // Referencia estatica al GUI
    static Main gui = new Main();

    // Procesos individuales
    Thread1 thread1 = new Thread1();
    Thread2 thread2 = new Thread2();

    //  Cola
    LinkedList cola = new LinkedList();//Usamos LinkedList

    // ID's
    int id = 0;
    Cliente clienteAtender = new Cliente(874328792, 0);

    // Variable booleana que indica si uno de los algoritmos se ejecuta o se detiene
    public static boolean eventFlag;

//    // Variable que indica cuanto tiempo debe procesarse la barra
//    int tiempoEspera = 0;
    public static boolean getEventFlag() {
        return eventFlag;
    }

    public static void setEventFlag(boolean _eventFlag) {
        eventFlag = _eventFlag;
    }

    // Variable int que indica a cuál numero de proceso corresponde ejecutarse
    public static int turn;

    public static int getTurn() {
        return turn;
    }

    public static void setTurn(int _turn) {
        turn = _turn;
    }

    public LinkedList getCola() {
        return cola;
    }

    public JLabel getAtenderCliente() {
        return AtenderCliente;
    }

    public void setAtenderCliente(JLabel AtenderCliente) {
        this.AtenderCliente = AtenderCliente;
    }

    public JLabel getClientesEspera() {
        return ClientesEspera;
    }

    public void setClientesEspera(JLabel ClientesEspera) {
        this.ClientesEspera = ClientesEspera;
    }

    public static void calcTurn() {
        int val = (int) (Math.random() * 2) + 1;
        setTurn(val);
    }

    public void updateStatusP1(String s) {
        lblStatusP1.setText(s);
    }

    public void updateStatusP2(String s) {
        lblStatusP2.setText(s);
    }

    public void setVisibleProgressBarP1(String s) {
        switch (s) {
            case "show":
                pbProceso1.setVisible(true);
                break;
            case "hide":
                pbProceso1.setVisible(false);
                break;
        }
    }

    public void setVisibleProgressBarP2(String s) {
        switch (s) {
            case "show":
                pbProceso2.setVisible(true);
                break;
            case "hide":
                pbProceso2.setVisible(false);
                break;
        }
    }

    public void setOperationProgressBarP1(String s) {
        switch (s) {
            case "bussy":
                pbProceso1.setIndeterminate(true);
                pbProceso1.setBackground(Color.decode("#73C9E3"));
                pbProceso1.setForeground(Color.decode("#5BC0DE"));
                break;
            case "wait":
                pbProceso1.setIndeterminate(true);
                pbProceso1.setBackground(Color.decode("#DE6C69"));
                pbProceso1.setForeground(Color.decode("#D9534F"));
                break;
            case "working":
                pbProceso1.setIndeterminate(true);
                pbProceso1.setBackground(Color.decode("#74C274"));
                pbProceso1.setForeground(Color.decode("#3E9A3E"));
//                pbProceso1.setMinimum(1);
//                pbProceso1.setMaximum(tiempoEspera);
//                for(int i = 1; i <= tiempoEspera; i++){
//                    pbProceso1.setValue(i);
//                    try{
//                        sleep(i * 1000);
//                    }catch(InterruptedException e){}
//                }
        }
    }

    public void setOperationProgressBarP2(String s) {
        switch (s) {
            case "bussy":
                pbProceso2.setIndeterminate(true);
                pbProceso2.setBackground(Color.decode("#73C9E3"));
                pbProceso2.setForeground(Color.decode("#5BC0DE"));
                break;
            case "wait":
                pbProceso2.setIndeterminate(true);
                pbProceso2.setBackground(Color.decode("#DE6C69"));
                pbProceso2.setForeground(Color.decode("#D9534F"));
                break;
            case "working":
                pbProceso2.setIndeterminate(true);
                pbProceso2.setBackground(Color.decode("#74C274"));
                pbProceso2.setForeground(Color.decode("#3E9A3E"));
//                pbProceso2.setMinimum(0);
//                pbProceso2.setMaximum(tiempoEspera);
//                for(int i = 1; i <= tiempoEspera; i++){
//                    pbProceso2.setValue(i);
//                    try{
//                        sleep(time * 1000);
//                    }catch(InterruptedException e){}
//                }
        }
    }

    public void criticalSection(Cliente c) {
        /*Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                c.setTransacciones(c.getTransacciones() - 1);
                AtenderCliente.setText("Atendiendo Cliente: " + c.getId() + " " + c.getTransacciones());

            }
        });
        timer.start();*/
        if (c.getTransacciones() >= 5) {
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                int cont = 5;

                @Override
                public void run() {
                    while (cont > 0) {
                        c.setTransacciones(c.getTransacciones() - 1);
                        AtenderCliente.setText("Atendiendo Cliente: " + c.getId() + " " + c.getTransacciones());
                        try {
                            sleep(1000);

                            cont--;
                        } catch (InterruptedException e) {
                        }

                    }
                    Atender.setEnabled(true);
                    if (c.getTransacciones() > 0) {
                        System.out.println("Vuelve a entrar");
                        cola.add(cola.poll());
                        setVisibleProgressBarP1("show");
                    } else {
                        System.out.println("Atendido");
                        cola.poll();
                    }
                    ClientesEspera.setText("Clientes en espera: " + cola.size());
                    AtenderCliente.setText("Esperando cliente...");
                    setOperationProgressBarP2("wait");
                    if(cola.size()==0){
                        setVisibleProgressBarP2("hide");
                    }
                    //t.cancel();
                }
            }, 1000);

        } else {
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    while (c.getTransacciones() > 0) {
                        c.setTransacciones(c.getTransacciones() - 1);
                        AtenderCliente.setText("Atendiendo Cliente: " + c.getId() + " " + c.getTransacciones());
                        try {
                            sleep(1000);
                            System.out.println(c.getTransacciones());

                        } catch (InterruptedException e) {
                        }

                    }
                    Atender.setEnabled(true);
                    if (c.getTransacciones() > 0) {
                        System.out.println("Vuelve a entrar");
                        cola.add(cola.poll());
                        setVisibleProgressBarP1("show");
                    } else {
                        System.out.println("Atendido");
                        cola.poll();
                    }
                    ClientesEspera.setText("Clientes en espera: " + cola.size());
                    AtenderCliente.setText("Esperando cliente...");
                    setOperationProgressBarP2("wait");
                    if(cola.size()==0){
                        setVisibleProgressBarP2("hide");
                    }
                }
            }, 1000);
        }

        /* if (c.getTransacciones() == 0) {
            ("holaaa");
            timer.stop();
        }*/

 /*try {
            //sleep((int)((Math.random() * 2) + 2) * 1000);
            sleep(transacciones * 1000);
        } catch (InterruptedException e) {
        }*/
    }

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();

        eventFlag = false;

        pbProceso1.setBorderPainted(false);
        pbProceso2.setBorderPainted(false);

        inicializarObjetos();
        //     cola.offer(3);
        //     cola.add(14);
        //     cola.offer(12);
        //     cola.add(7);
        //     cola.offer(10);
        //     cola.add(10.5);
        //     cola.add("cadena");

        // ("Cola llena: " + cola);
        // while(cola.poll()!=null){      
        //     ("Cola llenaaa: " + cola);
        //     (cola.peek());        
        // }    
        // (cola.peek()); 
    }

    private void inicializarObjetos() {
        // Inicializa estilos por defecto de objetos
        pbProceso1.setVisible(false);
        pbProceso2.setVisible(false);

        lblStatusP1.setText("-");
        lblStatusP2.setText("-");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        ClientesEspera = new javax.swing.JLabel();
        AtenderCliente = new javax.swing.JLabel();
        pbProceso1 = new javax.swing.JProgressBar();
        pbProceso2 = new javax.swing.JProgressBar();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblStatusP1 = new javax.swing.JLabel();
        lblStatusP2 = new javax.swing.JLabel();
        Aniadir = new javax.swing.JButton();
        Atender = new javax.swing.JButton();
        Transacciones = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Finish");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        ClientesEspera.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ClientesEspera.setText("Clientes en espera: ");

        AtenderCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AtenderCliente.setText("No hay clientes para atender");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Status");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Status");

        lblStatusP1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatusP1.setText("-");

        lblStatusP2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatusP2.setText("-");

        Aniadir.setText("Añadir");
        Aniadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AniadirActionPerformed(evt);
            }
        });

        Atender.setText("Atender");
        Atender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AtenderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ClientesEspera, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pbProceso1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Aniadir)
                                .addGap(34, 34, 34)
                                .addComponent(Transacciones, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblStatusP1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(pbProceso2, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(137, 137, 137))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblStatusP2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addComponent(Atender)
                        .addGap(70, 70, 70)
                        .addComponent(jButton1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AtenderCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(Aniadir)
                    .addComponent(Atender)
                    .addComponent(Transacciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(AtenderCliente))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ClientesEspera)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pbProceso1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pbProceso2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStatusP1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatusP2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        eventFlag = false;
        inicializarObjetos();
        thread1.interrupt();
        thread2.interrupt();
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void AniadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AniadirActionPerformed
        // TODO add your handling code here:
        id += 1;
        Cliente cliente = new Cliente(id, Integer.parseInt(Transacciones.getText()));
        cola.add(cliente);
        cliente = (Cliente) cola.element();

        ClientesEspera.setText("Clientes en espera: " + cola.size());
        setVisibleProgressBarP1("show");
        setOperationProgressBarP1("wait");

    }//GEN-LAST:event_AniadirActionPerformed

    private void AtenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtenderActionPerformed
        Atender.setEnabled(false);
        if (cola.size() > 0) {
            if (cola.size() - 1 == 0) {
                setVisibleProgressBarP1("hide");
            }
            ClientesEspera.setText("Clientes en espera: " + (cola.size() - 1));
            setVisibleProgressBarP2("show");
            setOperationProgressBarP2("bussy");
            clienteAtender = (Cliente) cola.element();
            AtenderCliente.setText("Atendiendo Cliente: " + clienteAtender.getId() + " " + clienteAtender.getTransacciones());
            criticalSection(clienteAtender);
        } else {
            Atender.setEnabled(true);
            AtenderCliente.setText("No hay clientes para atender");
            setVisibleProgressBarP2("hide");
            setOperationProgressBarP2("bussy");
        }
        
    }//GEN-LAST:event_AtenderActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Aniadir;
    private javax.swing.JButton Atender;
    private javax.swing.JLabel AtenderCliente;
    private javax.swing.JLabel ClientesEspera;
    private javax.swing.JTextField Transacciones;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblStatusP1;
    private javax.swing.JLabel lblStatusP2;
    private javax.swing.JProgressBar pbProceso1;
    private javax.swing.JProgressBar pbProceso2;
    // End of variables declaration//GEN-END:variables
}
