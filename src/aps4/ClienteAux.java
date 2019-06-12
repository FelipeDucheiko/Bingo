/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps4;

import java.net.InetAddress;

/**
 *
 * @author roque
 */
public class ClienteAux {
    private InetAddress ip;
    int[] cartela = new int[24];
    
    
    ClienteAux(){
        this.ip = null;
    }


    /**
     * @return the ip
     */
    public InetAddress getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(InetAddress ip) {
        this.ip = ip;
    }
}
