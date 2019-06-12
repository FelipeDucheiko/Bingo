/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps4;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author felip
 */
public class Cliente extends javax.swing.JFrame {

    Thread t;
    private Socket socket = null;
    DefaultTableModel model;
    boolean posicao1 = false;
    boolean posicao2 = false;
    boolean posicao3 = false;
    boolean posicao4 = false;
    boolean posicao5 = false;
    boolean posicao6 = false;
    boolean posicao7 = false;
    boolean posicao8 = false;
    boolean posicao9 = false;
    boolean posicao10 = false;
    boolean posicao11 = false;
    boolean posicao12 = false;
    boolean posicao13 = false;
    boolean posicao14 = false;
    boolean posicao15 = false;
    boolean posicao16 = false;
    boolean posicao17 = false;
    boolean posicao18 = false;
    boolean posicao19 = false;
    boolean posicao20 = false;
    boolean posicao21 = false;
    boolean posicao22 = false;
    boolean posicao23 = false;
    boolean posicao24 = false;
    int[] cartela = new int[24];
    
    /**
     * Creates new form Cliente
     */
    public Cliente(String ip, String porta, String usuario, Socket socket) throws IOException {
        initComponents();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.socket = socket;
        jTextIp.setText(ip);
        jTextPorta.setText(porta);
        model = (DefaultTableModel) jTableClientes2.getModel();
        jButton_X.setEnabled(false);
        

        jLabelCliente.setText("Cliente (" + usuario + ")");

        if (socket.isConnected()) {
            System.out.println("\nServer conectado com sucesso!");
        }

        t = new Thread() {
            @Override
            public void run() {
                try {
                    ArrayList<String> lista = null;
                    Scanner resposta = new Scanner(socket.getInputStream());
                    String string;

                    while (resposta.hasNextLine()) {
                        string = resposta.nextLine();
                        System.out.println("Recebido: " + string + "\n");
                        lista = new ArrayList<>(Arrays.asList(string.split("#")));

                        if (lista.get(0).compareTo("02") == 0) {
                            model.setNumRows(0);
                            for (int i = 1; i < lista.size() - 2; i = i + 3) {
                                model.addRow(new String[]{lista.get(i), lista.get(i + 1), lista.get(i + 2)});
                            }
                        }
                        if (lista.get(0).compareTo("06") == 0) {
                            jButtonEntrarNoBingo.setEnabled(false);
                            atualizarCartela(lista);
                            atribuirCartela(lista);
                        }
                        if (lista.get(0).compareTo("04") == 0) {
                            jTextAreaChat.setText(jTextAreaChat.getText() + "Cliente " + lista.get(1) + "/" + lista.get(2) + " diz: " + lista.get(3) + "\n");
                        }
                        
                        if (lista.get(0).compareTo("10") == 0) {
                            jLabelNumSorteado.setText("Numero Sorteado: " + lista.get(1));
                        }
                        if (lista.get(0).compareTo("12") == 0) {
                            jTextAreaChat.setText(jTextAreaChat.getText() + "Cliente " + lista.get(1) + "/" + lista.get(2) + " Gritou Bingo \n");
                        }
                        
                        if (lista.get(0).compareTo("14") == 0) {
                            jTextAreaChat.setText(jTextAreaChat.getText() + "Cliente " + lista.get(1) + "/" + lista.get(2) + " é o vencedor lol lol lol \n");
                        }
                        
                        if (lista.get(0).compareTo("16") == 0) {
                            jTextAreaChat.setText(jTextAreaChat.getText() + "Cliente " + lista.get(1) + "/" + lista.get(2) + " perdeu e foi excluido do bingo \n");
                        }
                        
                        if (lista.get(0).compareTo("18") == 0) {
                            jButtonGritarBingo.setEnabled(false);
                            JOptionPane.showMessageDialog(null, "Você Perdeu !!!");
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private void atualizarCartela(ArrayList<String> lista) {
                jButton_1.setText(lista.get(1));
                jButton_2.setText(lista.get(2));
                jButton_3.setText(lista.get(3));
                jButton_4.setText(lista.get(4));
                jButton_5.setText(lista.get(5));
                jButton_6.setText(lista.get(6));
                jButton_7.setText(lista.get(7));
                jButton_8.setText(lista.get(8));
                jButton_9.setText(lista.get(9));
                jButton_10.setText(lista.get(10));
                jButton_11.setText(lista.get(11));
                jButton_12.setText(lista.get(12));
                jButton_13.setText(lista.get(13));
                jButton_14.setText(lista.get(14));
                jButton_15.setText(lista.get(15));
                jButton_16.setText(lista.get(16));
                jButton_17.setText(lista.get(17));
                jButton_18.setText(lista.get(18));
                jButton_19.setText(lista.get(19));
                jButton_20.setText(lista.get(20));
                jButton_21.setText(lista.get(21));
                jButton_22.setText(lista.get(22));
                jButton_23.setText(lista.get(23));
                jButton_24.setText(lista.get(24));                
            }

            private void atribuirCartela(ArrayList<String> lista) {
                for(int i = 1; i<cartela.length; i++){
                    cartela[i-1] = Integer.parseInt(lista.get(i));
                }
            }
        };
        t.start();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                if (!socket.isConnected()) {
                    return;
                }

                try {
                    new PrintStream(socket.getOutputStream()).println("11#");
                    System.out.println("Enviado: " + "11#");
                    System.exit(0);
                } catch (IOException ex) {
                    System.out.println("Erro");
                }
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */;
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTableClientes = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jTextIp = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextPorta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextMensagem = new javax.swing.JTextField();
        jButtonEnviarBroadCast = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableClientes2 = new javax.swing.JTable();
        jLabelCliente = new javax.swing.JLabel();
        jButton_1 = new javax.swing.JButton();
        jButton_2 = new javax.swing.JButton();
        jButton_4 = new javax.swing.JButton();
        jButton_5 = new javax.swing.JButton();
        jButton_6 = new javax.swing.JButton();
        jButton_7 = new javax.swing.JButton();
        jButton_8 = new javax.swing.JButton();
        jButton_9 = new javax.swing.JButton();
        jButton_10 = new javax.swing.JButton();
        jButton_X = new javax.swing.JButton();
        jButton_12 = new javax.swing.JButton();
        jButton_13 = new javax.swing.JButton();
        jButton_14 = new javax.swing.JButton();
        jButton_11 = new javax.swing.JButton();
        jButton_17 = new javax.swing.JButton();
        jButton_16 = new javax.swing.JButton();
        jButton_18 = new javax.swing.JButton();
        jButton_19 = new javax.swing.JButton();
        jButton_15 = new javax.swing.JButton();
        jButton_23 = new javax.swing.JButton();
        jButton_20 = new javax.swing.JButton();
        jButton_21 = new javax.swing.JButton();
        jButton_24 = new javax.swing.JButton();
        jButton_22 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaChat = new javax.swing.JTextArea();
        jButtonEnviarPrivado = new javax.swing.JButton();
        jButtonGritarBingo = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButtonEntrarNoBingo = new javax.swing.JButton();
        jButton_3 = new javax.swing.JButton();
        jLabelNumSorteado = new javax.swing.JLabel();

        jTableClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Clientes conectados"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableClientes);

        jButton3.setText("1");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setText("1");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("IP:");

        jTextIp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextIpActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Porta:");

        jTextPorta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextPortaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Chat");

        jTextMensagem.setText("Mensagem");
        jTextMensagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextMensagemActionPerformed(evt);
            }
        });

        jButtonEnviarBroadCast.setText("Enviar BroadCast");
        jButtonEnviarBroadCast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnviarBroadCastActionPerformed(evt);
            }
        });

        jButtonLogout.setText("Logout");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });

        jTableClientes2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IP", "Porta", "Nome"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTableClientes2);

        jLabelCliente.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelCliente.setText("Cliente");

        jButton_1.setText("1");
        jButton_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_1ActionPerformed(evt);
            }
        });

        jButton_2.setText("1");
        jButton_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_2ActionPerformed(evt);
            }
        });

        jButton_4.setText("1");
        jButton_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_4ActionPerformed(evt);
            }
        });

        jButton_5.setText("1");
        jButton_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_5ActionPerformed(evt);
            }
        });

        jButton_6.setText("1");
        jButton_6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_6ActionPerformed(evt);
            }
        });

        jButton_7.setText("1");
        jButton_7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_7ActionPerformed(evt);
            }
        });

        jButton_8.setText("1");
        jButton_8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_8ActionPerformed(evt);
            }
        });

        jButton_9.setText("1");
        jButton_9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_9ActionPerformed(evt);
            }
        });

        jButton_10.setText("1");
        jButton_10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_10ActionPerformed(evt);
            }
        });

        jButton_X.setText("X");
        jButton_X.setDoubleBuffered(true);
        jButton_X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_XActionPerformed(evt);
            }
        });

        jButton_12.setText("1");
        jButton_12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_12ActionPerformed(evt);
            }
        });

        jButton_13.setText("1");
        jButton_13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_13ActionPerformed(evt);
            }
        });

        jButton_14.setText("1");
        jButton_14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_14ActionPerformed(evt);
            }
        });

        jButton_11.setText("1");
        jButton_11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_11ActionPerformed(evt);
            }
        });

        jButton_17.setText("1");
        jButton_17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_17ActionPerformed(evt);
            }
        });

        jButton_16.setText("1");
        jButton_16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_16ActionPerformed(evt);
            }
        });

        jButton_18.setText("1");
        jButton_18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_18ActionPerformed(evt);
            }
        });

        jButton_19.setText("1");
        jButton_19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_19ActionPerformed(evt);
            }
        });

        jButton_15.setText("1");
        jButton_15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_15ActionPerformed(evt);
            }
        });

        jButton_23.setText("1");
        jButton_23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_23ActionPerformed(evt);
            }
        });

        jButton_20.setText("1");
        jButton_20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_20ActionPerformed(evt);
            }
        });

        jButton_21.setText("1");
        jButton_21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_21ActionPerformed(evt);
            }
        });

        jButton_24.setText("1");
        jButton_24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_24ActionPerformed(evt);
            }
        });

        jButton_22.setText("1");
        jButton_22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_22ActionPerformed(evt);
            }
        });

        jTextAreaChat.setColumns(20);
        jTextAreaChat.setRows(5);
        jScrollPane1.setViewportView(jTextAreaChat);

        jButtonEnviarPrivado.setText("Enviar Privado");
        jButtonEnviarPrivado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnviarPrivadoActionPerformed(evt);
            }
        });

        jButtonGritarBingo.setText("Gritar Bingo");
        jButtonGritarBingo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGritarBingoActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Mensagem:");

        jButtonEntrarNoBingo.setText("Entrar no Bingo");
        jButtonEntrarNoBingo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEntrarNoBingoActionPerformed(evt);
            }
        });

        jButton_3.setText("1");
        jButton_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_3ActionPerformed(evt);
            }
        });

        jLabelNumSorteado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelNumSorteado.setText("Numero Sorteado: 00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextIp, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextPorta, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButton_20, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                                    .addComponent(jButton_15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton_11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton_6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButton_16, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton_17, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton_18, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButton_21, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton_22, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton_23, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton_24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jButton_19, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                    .addComponent(jButton_7, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(jButton_8, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(jButton_9, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(jButton_10, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                    .addComponent(jButton_12, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(jButton_X, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(jButton_13, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(jButton_14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelNumSorteado)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton_1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton_2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton_3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton_4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton_5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonEntrarNoBingo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonGritarBingo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(21, 21, 21)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(199, 199, 199))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonEnviarBroadCast, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonEnviarPrivado, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextMensagem)))
                        .addGap(0, 30, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(503, 503, 503)
                .addComponent(jLabelCliente)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextIp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jTextPorta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabelCliente)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabelNumSorteado)))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton_1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton_6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton_11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_X, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton_15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_17, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_18, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton_20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton_21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton_22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton_23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton_24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonGritarBingo)
                        .addComponent(jButtonEntrarNoBingo))
                    .addComponent(jButtonLogout)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonEnviarBroadCast)
                        .addComponent(jButtonEnviarPrivado)))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextIpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextIpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextIpActionPerformed

    private void jTextPortaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPortaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPortaActionPerformed

    private void jTextMensagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextMensagemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextMensagemActionPerformed

    private void jButtonEnviarBroadCastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnviarBroadCastActionPerformed
        try {

            if (socket.isClosed()) {
                JOptionPane.showMessageDialog(this, "Conexão fechada");
                return;
            }

            new PrintStream(socket.getOutputStream()).println("05#" + jTextMensagem.getText());

            System.out.println("Enviado: " + "05#" + jTextMensagem.getText() + "\n");

            Scanner resposta = new Scanner(socket.getInputStream());


        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButtonEnviarBroadCastActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        if (!socket.isConnected()) {
            return;
        }

        try {
            new PrintStream(socket.getOutputStream()).println("11#");
            System.out.println("Enviado: " + "11#");
            this.dispose();
        } catch (IOException ex) {
            System.out.println("Erro");
        }
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButton_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_1ActionPerformed
        if (posicao1 == false) {
            jButton_1.setBackground(Color.CYAN);
            posicao1 = true;
        } else {
            jButton_1.setBackground(Color.LIGHT_GRAY);
            posicao1 = false;
        }
    }//GEN-LAST:event_jButton_1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_2ActionPerformed
        if (posicao2 == false) {
            jButton_2.setBackground(Color.CYAN);
            posicao2 = true;
        } else {
            jButton_2.setBackground(Color.LIGHT_GRAY);
            posicao2 = false;
        }
    }//GEN-LAST:event_jButton_2ActionPerformed

    private void jButton_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_5ActionPerformed
        if (posicao5 == false) {
            jButton_5.setBackground(Color.CYAN);
            posicao5 = true;
        } else {
            jButton_5.setBackground(Color.LIGHT_GRAY);
            posicao5 = false;
        }
    }//GEN-LAST:event_jButton_5ActionPerformed

    private void jButton_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_4ActionPerformed
        if (posicao4 == false) {
            jButton_4.setBackground(Color.CYAN);
            posicao4 = true;
        } else {
            jButton_4.setBackground(Color.LIGHT_GRAY);
            posicao4 = false;
        }
    }//GEN-LAST:event_jButton_4ActionPerformed

    private void jButton_24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_24ActionPerformed
        if (posicao24 == false) {
            jButton_24.setBackground(Color.CYAN);
            posicao24 = true;
        } else {
            jButton_24.setBackground(Color.LIGHT_GRAY);
            posicao24 = false;
        }
    }//GEN-LAST:event_jButton_24ActionPerformed

    private void jButton_23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_23ActionPerformed
        if (posicao23 == false) {
            jButton_23.setBackground(Color.CYAN);
            posicao23 = true;
        } else {
            jButton_23.setBackground(Color.LIGHT_GRAY);
            posicao23 = false;
        }
    }//GEN-LAST:event_jButton_23ActionPerformed

    private void jButton_22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_22ActionPerformed
        if (posicao22 == false) {
            jButton_22.setBackground(Color.CYAN);
            posicao22 = true;
        } else {
            jButton_22.setBackground(Color.LIGHT_GRAY);
            posicao22 = false;
        }
    }//GEN-LAST:event_jButton_22ActionPerformed

    private void jButton_21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_21ActionPerformed
        if (posicao21 == false) {
            jButton_21.setBackground(Color.CYAN);
            posicao21 = true;
        } else {
            jButton_21.setBackground(Color.LIGHT_GRAY);
            posicao21 = false;
        }
    }//GEN-LAST:event_jButton_21ActionPerformed

    private void jButton_20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_20ActionPerformed
        if (posicao20 == false) {
            jButton_20.setBackground(Color.CYAN);
            posicao20 = true;
        } else {
            jButton_20.setBackground(Color.LIGHT_GRAY);
            posicao20 = false;
        }
    }//GEN-LAST:event_jButton_20ActionPerformed

    private void jButton_15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_15ActionPerformed
        if (posicao15 == false) {
            jButton_15.setBackground(Color.CYAN);
            posicao15 = true;
        } else {
            jButton_15.setBackground(Color.LIGHT_GRAY);
            posicao15 = false;
        }
    }//GEN-LAST:event_jButton_15ActionPerformed

    private void jButton_16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_16ActionPerformed
        if (posicao16 == false) {
            jButton_16.setBackground(Color.CYAN);
            posicao16 = true;
        } else {
            jButton_16.setBackground(Color.LIGHT_GRAY);
            posicao16 = false;
        }
    }//GEN-LAST:event_jButton_16ActionPerformed

    private void jButton_17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_17ActionPerformed
        if (posicao17 == false) {
            jButton_17.setBackground(Color.CYAN);
            posicao17 = true;
        } else {
            jButton_17.setBackground(Color.LIGHT_GRAY);
            posicao17 = false;
        }
    }//GEN-LAST:event_jButton_17ActionPerformed

    private void jButton_18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_18ActionPerformed
        if (posicao18 == false) {
            jButton_18.setBackground(Color.CYAN);
            posicao18 = true;
        } else {
            jButton_18.setBackground(Color.LIGHT_GRAY);
            posicao18 = false;
        }
    }//GEN-LAST:event_jButton_18ActionPerformed

    private void jButton_14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_14ActionPerformed
        if (posicao14 == false) {
            jButton_14.setBackground(Color.CYAN);
            posicao14 = true;
        } else {
            jButton_14.setBackground(Color.LIGHT_GRAY);
            posicao14 = false;
        }
    }//GEN-LAST:event_jButton_14ActionPerformed

    private void jButton_19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_19ActionPerformed
        if (posicao19 == false) {
            jButton_19.setBackground(Color.CYAN);
            posicao19 = true;
        } else {
            jButton_19.setBackground(Color.LIGHT_GRAY);
            posicao19 = false;
        }
    }//GEN-LAST:event_jButton_19ActionPerformed

    private void jButton_13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_13ActionPerformed
        if (posicao13 == false) {
            jButton_13.setBackground(Color.CYAN);
            posicao13 = true;
        } else {
            jButton_13.setBackground(Color.LIGHT_GRAY);
            posicao13 = false;
        }
    }//GEN-LAST:event_jButton_13ActionPerformed

    private void jButton_XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_XActionPerformed
        if (posicao19 == false) {
            jButton_19.setBackground(Color.CYAN);
            posicao19 = true;
        } else {
            jButton_19.setBackground(Color.LIGHT_GRAY);
            posicao19 = false;
        }
    }//GEN-LAST:event_jButton_XActionPerformed

    private void jButton_12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_12ActionPerformed
        if (posicao12 == false) {
            jButton_12.setBackground(Color.CYAN);
            posicao12 = true;
        } else {
            jButton_12.setBackground(Color.LIGHT_GRAY);
            posicao12 = false;
        }
    }//GEN-LAST:event_jButton_12ActionPerformed

    private void jButton_11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_11ActionPerformed
        if (posicao11 == false) {
            jButton_11.setBackground(Color.CYAN);
            posicao11 = true;
        } else {
            jButton_11.setBackground(Color.LIGHT_GRAY);
            posicao11 = false;
        }
    }//GEN-LAST:event_jButton_11ActionPerformed

    private void jButton_9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_9ActionPerformed
        if (posicao9 == false) {
            jButton_9.setBackground(Color.CYAN);
            posicao9 = true;
        } else {
            jButton_9.setBackground(Color.LIGHT_GRAY);
            posicao9 = false;
        }
    }//GEN-LAST:event_jButton_9ActionPerformed

    private void jButton_10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_10ActionPerformed
        if (posicao10 == false) {
            jButton_10.setBackground(Color.CYAN);
            posicao10 = true;
        } else {
            jButton_10.setBackground(Color.LIGHT_GRAY);
            posicao10 = false;
        }
    }//GEN-LAST:event_jButton_10ActionPerformed

    private void jButton_8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_8ActionPerformed
        if (posicao8 == false) {
            jButton_8.setBackground(Color.CYAN);
            posicao8 = true;
        } else {
            jButton_8.setBackground(Color.LIGHT_GRAY);
            posicao8 = false;
        }
    }//GEN-LAST:event_jButton_8ActionPerformed

    private void jButton_6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_6ActionPerformed
        if (posicao6 == false) {
            jButton_6.setBackground(Color.CYAN);
            posicao6 = true;
        } else {
            jButton_6.setBackground(Color.LIGHT_GRAY);
            posicao6 = false;
        }
    }//GEN-LAST:event_jButton_6ActionPerformed

    private void jButton_7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_7ActionPerformed
        if (posicao7 == false) {
            jButton_7.setBackground(Color.CYAN);
            posicao7 = true;
        } else {
            jButton_7.setBackground(Color.LIGHT_GRAY);
            posicao7 = false;
        }
    }//GEN-LAST:event_jButton_7ActionPerformed

    private void jButtonEnviarPrivadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnviarPrivadoActionPerformed
        try {

            if (socket.isClosed()) {
                JOptionPane.showMessageDialog(this, "Conexão fechada");
                return;
            }
            int linha = jTableClientes2.getSelectedRow();

            new PrintStream(socket.getOutputStream()).println("03#" + jTableClientes2.getModel().getValueAt(linha,0) + "#" + jTableClientes2.getModel().getValueAt(linha,1) + "#" + jTextMensagem.getText());

            System.out.println("03#" + jTableClientes2.getModel().getValueAt(linha,0) + "#" + jTableClientes2.getModel().getValueAt(linha,1) + "#" + jTextMensagem.getText() + "\n");

            Scanner resposta = new Scanner(socket.getInputStream());
            
            jTextAreaChat.setText(jTextAreaChat.getText() + "Mensagem Privada enviada:" + jTextMensagem.getText() + "\n");
            
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButtonEnviarPrivadoActionPerformed

    private void jButtonEntrarNoBingoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEntrarNoBingoActionPerformed
        try {
            new PrintStream(socket.getOutputStream()).println("07#");
            System.out.println("Enviado: " + "07#");
        } catch (IOException ex) {
            System.out.println("Erro");
        }
    }//GEN-LAST:event_jButtonEntrarNoBingoActionPerformed

    private void jButton_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_3ActionPerformed
        if (posicao3 == false) {
            jButton_3.setBackground(Color.CYAN);
            posicao3 = true;
        } else {
            jButton_3.setBackground(Color.LIGHT_GRAY);
            posicao3 = false;
        }
    }//GEN-LAST:event_jButton_3ActionPerformed

    private void jButtonGritarBingoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGritarBingoActionPerformed
        try {
            new PrintStream(socket.getOutputStream()).println("09#");
            System.out.println("Enviado: " + "09#");
            
        } catch (IOException ex) {
            System.out.println("Erro");
        }
    }//GEN-LAST:event_jButtonGritarBingoActionPerformed

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
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Cliente("localhost", "20000", null, null).setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonEntrarNoBingo;
    private javax.swing.JButton jButtonEnviarBroadCast;
    private javax.swing.JButton jButtonEnviarPrivado;
    private javax.swing.JButton jButtonGritarBingo;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButton_1;
    private javax.swing.JButton jButton_10;
    private javax.swing.JButton jButton_11;
    private javax.swing.JButton jButton_12;
    private javax.swing.JButton jButton_13;
    private javax.swing.JButton jButton_14;
    private javax.swing.JButton jButton_15;
    private javax.swing.JButton jButton_16;
    private javax.swing.JButton jButton_17;
    private javax.swing.JButton jButton_18;
    private javax.swing.JButton jButton_19;
    private javax.swing.JButton jButton_2;
    private javax.swing.JButton jButton_20;
    private javax.swing.JButton jButton_21;
    private javax.swing.JButton jButton_22;
    private javax.swing.JButton jButton_23;
    private javax.swing.JButton jButton_24;
    private javax.swing.JButton jButton_3;
    private javax.swing.JButton jButton_4;
    private javax.swing.JButton jButton_5;
    private javax.swing.JButton jButton_6;
    private javax.swing.JButton jButton_7;
    private javax.swing.JButton jButton_8;
    private javax.swing.JButton jButton_9;
    private javax.swing.JButton jButton_X;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelCliente;
    private javax.swing.JLabel jLabelNumSorteado;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTableClientes;
    private javax.swing.JTable jTableClientes2;
    private javax.swing.JTextArea jTextAreaChat;
    private javax.swing.JTextField jTextIp;
    private javax.swing.JTextField jTextMensagem;
    private javax.swing.JTextField jTextPorta;
    // End of variables declaration//GEN-END:variables
}
