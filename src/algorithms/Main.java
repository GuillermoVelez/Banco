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
import java.util.Iterator;
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

    //  Cola
    LinkedList cola = new LinkedList();//Usamos LinkedList
    // Booleano
    boolean atendiendo = false;
    // ID's
    int contador2 = 201810200;
    int id = 2018102010;
    Cliente clienteAtender = new Cliente(874328792, 0);
    String Matriz[][];
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

    public void inicializarClientes() {
        for (int i = 1; i <= 10; i++) {
            Cliente c2 = new Cliente(contador2, i);
            cola.add(c2);
            contador2++;
        }
        Matriz = new String[cola.size()][2];
        int cont = 0;
        for (Iterator it = cola.iterator(); it.hasNext();) {
            Cliente c = (Cliente) it.next();
            Matriz = mostrar(Matriz, cont, c);
            System.out.println(c.getId());
            cont += 1;
        }
        informacionCliente.setModel(new javax.swing.table.DefaultTableModel(
                Matriz,
                new String[]{
                    "ID", "Peticiones"
                }
        ));
        setVisibleProgressBarP1("show");
        setOperationProgressBarP1("wait");
        ClientesEspera.setText("Clientes en espera: " + cola.size());

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

                    System.out.println(atendiendo);
                    while (cont > 0) {
                        c.setTransacciones(c.getTransacciones() - 1);
                        AtenderCliente.setText("Atendiendo Cliente: ");
                        clienteAtendido.setText("" + c.getId());
                        lblStatusP2.setText("" + c.getTransacciones());
                        try {
                            sleep(1000);

                            cont--;
                        } catch (InterruptedException e) {
                        }

                    }
                    atendiendo = false;
                    System.out.println(atendiendo);
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
                    clienteAtendido.setText("");
                    lblStatusP2.setText("");
                    Matriz = new String[cola.size()][2];
                    int cont = 0;
                    for (Iterator it = cola.iterator(); it.hasNext();) {
                        Cliente c = (Cliente) it.next();
                        Matriz = mostrar(Matriz, cont, c);
                        System.out.println(c.getId());
                        cont += 1;
                    }
                    informacionCliente.setModel(new javax.swing.table.DefaultTableModel(
                            Matriz,
                            new String[]{
                                "ID", "Peticiones"
                            }
                    ));
                    setOperationProgressBarP2("wait");
                    if (cola.size() == 0) {
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
                    System.out.println(atendiendo);
                    while (c.getTransacciones() > 0) {
                        c.setTransacciones(c.getTransacciones() - 1);
                        AtenderCliente.setText("Atendiendo Cliente: ");
                        clienteAtendido.setText("" + c.getId());
                        lblStatusP2.setText("" + c.getTransacciones());
                        try {
                            sleep(1000);
                            System.out.println(c.getTransacciones());

                        } catch (InterruptedException e) {
                        }

                    }
                    atendiendo = false;
                    System.out.println(atendiendo);
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
                    clienteAtendido.setText("");
                    lblStatusP2.setText("");
                    setOperationProgressBarP2("wait");
                    if (cola.size() == 0) {
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
        inicializarClientes();
    }

    private void inicializarObjetos() {
        // Inicializa estilos por defecto de objetos
        pbProceso1.setVisible(false);
        pbProceso2.setVisible(false);
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
        clienteAtendido = new javax.swing.JLabel();
        lblStatusP2 = new javax.swing.JLabel();
        Aniadir = new javax.swing.JButton();
        Atender = new javax.swing.JButton();
        Transacciones = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        informacionCliente = new javax.swing.JTable();

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

        clienteAtendido.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clienteAtendido.setMaximumSize(new java.awt.Dimension(300, 20));
        clienteAtendido.setMinimumSize(new java.awt.Dimension(300, 20));

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

        jLabel1.setText("Peticiones");

        jLabel2.setText("Peticiones:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("ID:");

        informacionCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Peticiones"
            }
        ));
        jScrollPane1.setViewportView(informacionCliente);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ClientesEspera, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pbProceso1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Aniadir)
                        .addGap(34, 34, 34)
                        .addComponent(Transacciones, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1)))
                .addGap(227, 227, 227)
                .addComponent(Atender)
                .addGap(70, 70, 70)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pbProceso2, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(clienteAtendido, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(AtenderCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(50, 50, 50)
                        .addComponent(lblStatusP2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(Transacciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(ClientesEspera))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AtenderCliente)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pbProceso1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pbProceso2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(clienteAtendido, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lblStatusP2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        eventFlag = false;
        inicializarObjetos();
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void AniadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AniadirActionPerformed
        // TODO add your handling code here:

        id += 1;
        Cliente cliente = new Cliente(id, Integer.parseInt(Transacciones.getText()));
        cola.add(cliente);
        cliente = (Cliente) cola.element();
        if (atendiendo) {
            ClientesEspera.setText("Clientes en espera: " + (cola.size() - 1));
            Matriz = new String[cola.size() - 1][2];
            int cont = 0;
            for (Iterator it = cola.iterator(); it.hasNext();) {
                Cliente c = (Cliente) it.next();
                if (cont != 0) {
                    Matriz = mostrar(Matriz, cont - 1, c);
                }
                System.out.println(c.getId());
                cont += 1;
            }
            informacionCliente.setModel(new javax.swing.table.DefaultTableModel(
                    Matriz,
                    new String[]{
                        "ID", "Peticiones"
                    }
            ));
        } else {
            Matriz = new String[cola.size()][2];
            int cont = 0;
            for (Iterator it = cola.iterator(); it.hasNext();) {
                Cliente c = (Cliente) it.next();
                Matriz = mostrar(Matriz, cont, c);
                System.out.println(c.getId());
                cont += 1;
            }
            informacionCliente.setModel(new javax.swing.table.DefaultTableModel(
                    Matriz,
                    new String[]{
                        "ID", "Peticiones"
                    }
            ));
            ClientesEspera.setText("Clientes en espera: " + cola.size());
        }

        setVisibleProgressBarP1("show");
        setOperationProgressBarP1("wait");

    }//GEN-LAST:event_AniadirActionPerformed
    public String[][] mostrar(String tabla[][], int contador, Cliente c) {
        tabla[contador][0] = String.valueOf(c.getId());
        tabla[contador][1] = String.valueOf(c.getTransacciones());
        return tabla;
    }
    private void AtenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtenderActionPerformed
        Atender.setEnabled(false);
        if (cola.size() > 0) {
            if (cola.size() - 1 == 0) {
                setVisibleProgressBarP1("hide");
            }
            Matriz = new String[cola.size() - 1][2];
            int cont = 0;
            for (Iterator it = cola.iterator(); it.hasNext();) {
                Cliente c = (Cliente) it.next();
                if (cont != 0) {
                    Matriz = mostrar(Matriz, cont - 1, c);
                }
                System.out.println(c.getId());
                cont += 1;
            }
            informacionCliente.setModel(new javax.swing.table.DefaultTableModel(
                    Matriz,
                    new String[]{
                        "ID", "Peticiones"
                    }
            ));
            ClientesEspera.setText("Clientes en espera: " + (cola.size() - 1));
            setVisibleProgressBarP2("show");
            setOperationProgressBarP2("bussy");
            clienteAtender = (Cliente) cola.element();
            AtenderCliente.setText("Atendiendo Cliente: ");
            clienteAtendido.setText("" + clienteAtender.getId());
            lblStatusP2.setText("" + clienteAtender.getTransacciones());
            atendiendo = true;
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
    private javax.swing.JLabel clienteAtendido;
    private javax.swing.JTable informacionCliente;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblStatusP2;
    private javax.swing.JProgressBar pbProceso1;
    private javax.swing.JProgressBar pbProceso2;
    // End of variables declaration//GEN-END:variables
}
