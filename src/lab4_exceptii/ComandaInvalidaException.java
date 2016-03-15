/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab4_exceptii;

/**
 *
 * @author Alin
 */
public class ComandaInvalidaException extends Exception {
    public ComandaInvalidaException(String msg){
        super(msg);
    }
}
