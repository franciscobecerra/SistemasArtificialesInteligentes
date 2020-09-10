/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agente;
/**
 *
 * @author franciscobecerra
 */
import jade.core.Agent;
import Agente.Comportamiento;
        
public class Agente extends Agent{
    
    protected void setup(){
        this.addBehaviour( new Comportamiento() );
    }   
}
