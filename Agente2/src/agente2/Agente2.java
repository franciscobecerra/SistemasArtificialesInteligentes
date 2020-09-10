/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agente2;

/**
 *
 * @author franciscobecerra
 */
import jade.core.Agent;
import agente2.Comportamiento;

public class Agente2 extends Agent{

    /**
     * @param args the command line arguments
     */
    protected void setup(){
        this.addBehaviour( new Comportamiento() );
    }
}
