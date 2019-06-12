/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps4;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class Conexao extends Thread {
    boolean cartelaEnviada = false;
    boolean ativoBingo = true;
    int cli;
    Socket cliente;
    JTextArea serverTextArea;
    DefaultTableModel model;
    public String nomeCliente = null;
    ArrayList<Conexao> clientesConectados;
    ArrayList<String> cartelas = new ArrayList<String>();
    ClienteAux[] clientesBingo = new ClienteAux[100];

    public Conexao(Socket cliente, int cli, JTextArea serverTextArea, DefaultTableModel model, ArrayList<Conexao> clientesConectados, int[] numerosSorteados) {
        this.cliente = cliente;
        this.cli = cli;
        this.serverTextArea = serverTextArea;
        this.model = model;
        this.clientesConectados = clientesConectados;
        for (int i = 0; i < clientesBingo.length; i++) {
            clientesBingo[i] = new ClienteAux();
        }
        
    }

    @Override
    public void run() {
        Scanner resposta;
        String string;
        PrintStream ps;
        ArrayList<String> lista = null;

        try {

            serverTextArea.setText(serverTextArea.getText() + "Cliente aceito\n");
            serverTextArea.setText(serverTextArea.getText() + "IP: " + cliente.getInetAddress() + "\n");

            if (cliente.isClosed()) {
                serverTextArea.setText(serverTextArea.getText() + "Conexão encerrada\n");
                return;
            }

            resposta = new Scanner(cliente.getInputStream());

            ps = new PrintStream(cliente.getOutputStream());

            while (resposta.hasNextLine()) {
                string = resposta.nextLine();

                System.out.println("Recebido: " + string + "\n");

                lista = new ArrayList<>(Arrays.asList(string.split("#")));

                if (lista.get(0).compareTo("01") == 0) {
                    nomeCliente = lista.get(1);
                    serverTextArea.setText(serverTextArea.getText() + "Login efetuado do usuario " + nomeCliente + "\n");
                    model.addRow(new String[]{cliente.getInetAddress().getHostAddress(), Integer.toString(cliente.getPort()), nomeCliente});
                    enviarClientesConectados();
                }
                if (lista.get(0).compareTo("05") == 0) {
                    serverTextArea.setText(serverTextArea.getText() + "De: " + cliente.getInetAddress() + "/" + cliente.getPort() + " Para: Broadcast" + " Mensagem: " + lista.get(1) + "\n");
                    ps.println(lista.get(1).toUpperCase());

                    for (Conexao c : clientesConectados) {
                        if (!c.cliente.isConnected()) {
                            continue;
                        }
                        c.enviarMensagem("04#" + cliente.getInetAddress().getHostAddress() + "#" + cliente.getPort() + "#" + lista.get(1));
                    }

                }
                if (lista.get(0).compareTo("11") == 0) {
                    //model.removeRow(cli);
                    cliente.close();
                    serverTextArea.setText(serverTextArea.getText() + "Cliente " + nomeCliente + " deslogou\n");
                    clientesConectados.remove(this);
                    atualizarTabela();
                    enviarClientesConectados();
                    return;
                }
                
                if (lista.get(0).compareTo("07") == 0) {
                    if (!Servidor.bingoComecou && !cartelaEnviada){
                        enviarCartela(this);
                        cartelaEnviada = true;
                    }
                }
                
                if (lista.get(0).compareTo("03") == 0) {
                    serverTextArea.setText(serverTextArea.getText() + "De: " + cliente.getInetAddress() + "/" + cliente.getPort() + " Para: " + lista.get(1) + "/" + lista.get(2) + " Mensagem: " + lista.get(3) + "\n");
                    ps.println(lista.get(1).toUpperCase());
                    String aux = lista.get(1);

                    for (Conexao c : clientesConectados) {
                        //System.out.println(c.cliente.getInetAddress().toString() + " /" + lista.get(1));
                        //System.out.println(c.cliente.getInetAddress().getHostAddress().compareTo("/" + lista.get(1)));
                        String s = c.cliente.getInetAddress().getHostAddress();

                        if (c.cliente.getPort() == Integer.parseInt(lista.get(2))) {
                            c.enviarMensagem("04#" + cliente.getInetAddress().getHostAddress() + "#" + cliente.getPort() + "#" + lista.get(3));
                            break;
                        }
                    }

                }
                if (lista.get(0).compareTo("09") == 0) {

                    avisaGritoBingo();

                    if (checagemCartela(this)) {
                        enviarVencedor();
                    } else {
                        ativoBingo = false;
                        this.enviarMensagem("18#");
                    }
                }

            }
        } catch (IOException ex) {

            //serverTextArea.setText(serverTextArea.getText() + "Erro na mensagem\n");
        } finally {
            try {

                if (cliente.isConnected()) {
                    cliente.close();
                }

            } catch (Exception e) {

            }

        }

    }

    public void enviarMensagem(String mensagem) {
        PrintStream saida;
        try {
            new PrintStream(cliente.getOutputStream()).println(mensagem);
            System.out.println("Enviado: " + mensagem + "\n");
        } catch (IOException ex) {
            serverTextArea.setText(serverTextArea.getText() + "Erro ao enviar mensagem ao cliente " + nomeCliente + "\n");
        }
    }

    public void enviarClientesConectados() {
        String output = "02";
        for (Conexao c : clientesConectados) {
            if (!c.cliente.isConnected()) {
                continue;
            }

            output = output + "#" + c.cliente.getInetAddress().getHostAddress() + "#" + c.cliente.getPort() + "#" + c.nomeCliente;
        }

        for (Conexao c : clientesConectados) {
            if (!c.cliente.isConnected()) {
                continue;
            }
            c.enviarMensagem(output);
        }
        serverTextArea.setText(serverTextArea.getText() + "Lista de clientes enviada\n");
        //serverTextArea.setText(serverTextArea.getText() + output);
    }

    private void atualizarTabela() {
        model.setNumRows(0);
        for (Conexao c : clientesConectados) {
            if (!c.cliente.isConnected()) {
                continue;
            }
            model.addRow(new String[]{c.cliente.getInetAddress().getHostAddress(), Integer.toString(c.cliente.getPort()), c.nomeCliente});
        }
    }

    private void enviarCartela(Conexao c) {
        String output = "06";
        String cartela = "";
        for (int j = 0; j < clientesBingo.length; j++) {
            if (clientesBingo[j].getIp() == null) {
                clientesBingo[j].setIp(c.cliente.getInetAddress());
                for (int i = 0; i < 24; i++) {
                    int rand = new Random().nextInt(75) + 1;
                    clientesBingo[j].cartela[i] = rand;
                    output += "#" + Integer.toString(rand);
                    cartela += "#" + Integer.toString(rand);
                }
                break;
            }
        }

        cartelas.add(cartela);

        c.enviarMensagem(output);
    }

    private boolean checagemCartela(Conexao c) {
        
        if (!ativoBingo){
            return false;
        }
        
        int contador = 0;
        for (int i = 0; i < 100; i++) {
            if (c.cliente.getInetAddress() == clientesBingo[i].getIp()) {
                for (int j = 0; j < 24; j++) {
                    for (int z = 0; z <= Servidor.posicaoNumSorteado; z++) {
                        System.out.println(clientesBingo[i].cartela[j] + " " + Servidor.numSorteados[z]);
                        if (clientesBingo[i].cartela[j] == Servidor.numSorteados[z]) {
                            contador++;
                            break;
                        }
                    }
                }
                break;
            }
        }
        if (contador == 24) {
            return true;
        } else {
            return false;
        }
    }

    private void removerBingo() {
//        for (int i = 0; i < 100; i++) {
//            if (this.cliente.getInetAddress() == clientesBingo[i].getIp()) {
//                clientesBingo[i] = null;
//            }
//        }
        
        for (Conexao c : clientesConectados) {
            if (!c.cliente.isConnected()) {
                continue;
            }
            c.enviarMensagem("16#" + this.cliente.getInetAddress().getHostAddress() + "#" + this.cliente.getPort() + "#");
        }
    }

    private void avisaGritoBingo() {
        for (Conexao c : clientesConectados) {
            if (!c.cliente.isConnected()) {
                continue;
            }
            c.enviarMensagem("12#" + this.cliente.getInetAddress().getHostAddress() + "#" + this.cliente.getPort() + "#");
        }
    }

    private void enviarVencedor() {
        for (Conexao c : clientesConectados) {
            if (!c.cliente.isConnected()) {
                continue;
            }
            c.enviarMensagem("14#" + this.cliente.getInetAddress().getHostAddress() + "#" + this.cliente.getPort() + "#");
        }
    }
}
