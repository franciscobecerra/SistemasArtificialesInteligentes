package vendedor;
import vendedor.Interfaz;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.Hashtable;

public class Vendedor extends Agent{
    Interfaz interfaz;
    private Hashtable catalogo;
    
    protected void setup(){
        
        System.out.println( "AGENTE VENDEDOR "+getAID().getName() );
        
        // REGISTRO EN DF
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName( getAID() );
        ServiceDescription sd = new ServiceDescription();
        sd.setType("VENTA_DE_LIBROS");
        sd.setName("COMPRA_VENTA_LIBROS");
        dfd.addServices(sd);
        
        try{
            DFService.register(this, dfd);
        }catch( FIPAException fe ){
            fe.printStackTrace();
        }
        
        catalogo = new Hashtable();
        
        interfaz = new Interfaz( this );
        interfaz.setVisible(true);
        
        addBehaviour( new OfferRequestServer() );
        addBehaviour( new PurchaseOrdersServer() );
    }
    
    protected void takeDown(){
        System.out.println( "FINALIZANDO AGENTE VENDEDOR "+getAID().getName() );
        
        try{
            DFService.deregister(this);
        }catch( FIPAException fe ){
            fe.printStackTrace();
        }
        
        interfaz.dispose();
        
    }
    
    
    public void sellBook( final String titulo, final String precio ){
        addBehaviour( new OneShotBehaviour(){
            public void action(){
                catalogo.put( titulo, new Integer(precio) );
                System.out.println( titulo + "HA SIDO INSERTADO EN EL CATALOGO - PRECIO: " + precio );
            }
        });
    }
    
    class OfferRequestServer extends CyclicBehaviour{
        public void action(){
            MessageTemplate mt = MessageTemplate.MatchPerformative( ACLMessage.CFP );
            ACLMessage msg = receive(mt);

            if(msg != null) {
                String titulo = msg.getContent();
                ACLMessage reply = msg.createReply();

                Integer precio = (Integer) catalogo.get(titulo);

                if(precio != null) {
                    reply.setPerformative(ACLMessage.PROPOSE);
                    reply.setContent(String.valueOf( precio.intValue()) );
                } else {
                    reply.setPerformative(ACLMessage.REFUSE);
                    reply.setContent("LIBRO_NO_DISPONIBLE");
                }

                myAgent.send(reply);
            } else {
                block();
            }
        }
    }

    class PurchaseOrdersServer extends CyclicBehaviour{
        
        public void action(){
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
            ACLMessage msg = receive(mt);

            if (msg != null) {

                String titulo = msg.getContent();
                ACLMessage reply = msg.createReply();
                Integer precio = (Integer) catalogo.remove(titulo);

                if ( precio != null ) {
                    reply.setPerformative(ACLMessage.INFORM);
                    System.out.println( titulo + "VENDIDO AL ANGENTE " + msg.getSender().getName() );
                } else {
                    reply.setPerformative(ACLMessage.FAILURE);
                    reply.setContent("LIBRO_NO_DISPONIBLE");
                }
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }
}

