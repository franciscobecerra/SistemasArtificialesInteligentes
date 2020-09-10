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
import jade.core.behaviours.Behaviour;

public class Comportamiento extends Behaviour{
    protected int cont = 0;
    
    Comportamiento(){
        System.out.println( "Estoy vivo!" );
    }

    @Override
    public void action() {
        System.out.println( "# "+( this.cont + 1) );
        this.cont += 1;
    }

    @Override
    public boolean done() {
        if( cont == 100 )
            return true;
        
        return false;
    }
    
}
