package comprador;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Comprador extends Agent{
    Interfaz interfaz;

    private int mejorPrecio;
    private AID mejorVendedor;
    private int numRespuestas = 0;
    private int etapa = 0;
    private MessageTemplate mt;
    private AID[] vendedores;
    
    protected void setup(){
        System.out.println( "AGENTE COMPRADOR "+getAID().getName() );
        interfaz = new Interfaz( this );
        interfaz.setVisible(true);
    }
    
    public void FindBook( String titulo ){
        interfaz.dispose();
        System.out.println( "VAMOS INTENTAR COMPRAR EL LIBRO "+titulo );
        
        this.addBehaviour( new TickerBehaviour(this, 1000){
            
            protected void onTick(){
                System.out.println( "Buscando el libro "+titulo );
                
                // OBTENER LISTA DE AGENTES VENDEDORES
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("VENTA_DE_LIBROS");
                template.addServices(sd);

                try{
                    DFAgentDescription[] listado = DFService.search(myAgent, template);
                    vendedores = new AID[listado.length];
                    for( int i = 0; i < listado.length; i++ ){
                        vendedores[i] = listado[i].getName();
                    }
                    
                }catch( FIPAException fe ){
                    fe.printStackTrace();
                }
                
                myAgent.addBehaviour(new RequestPerformer());
                
            }
            
            class RequestPerformer extends Behaviour {
                public void action(){
                    
                    switch( etapa ){
                        
                        case 0: 
                            ACLMessage cfp = new ACLMessage( ACLMessage.CFP );
//                            cfp.addReplyTo( new AID("vendedor-1", AID.ISLOCALNAME) );
//                            cfp.addReplyTo( new AID("vendedor-2", AID.ISLOCALNAME) );
                            for( int i = 0; i < vendedores.length; i++ ){
                                cfp.addReceiver( vendedores[i] );
                            }
                            
                            cfp.setContent( titulo );
                            cfp.setConversationId("COMPRA_VENTA_LIBROS");
                            cfp.setReplyWith("cfp"+System.currentTimeMillis());
                            myAgent.send(cfp);
                            
                            mt = MessageTemplate.and( MessageTemplate.MatchConversationId( "COMPRA_VENTA_LIBROS" ),MessageTemplate.MatchInReplyTo( cfp.getReplyWith()));
                            etapa = 1;
                         break;
                         
                        case 1:
                            
                            ACLMessage reply = receive(mt);
                            if( reply != null ){
                                if( reply.getPerformative() == ACLMessage.PROPOSE ){
                                    
                                    int precio = Integer.parseInt( reply.getContent() );
                                    if( mejorVendedor == null || precio < mejorPrecio ){
                                        mejorPrecio = precio;
                                        mejorVendedor = reply.getSender();
                                    }
                                    numRespuestas++;
                                    if( numRespuestas >= 2 ){
                                        etapa = 2;
                                    }
                                }
                            }else{
                                block();
                            }
                        break;
                        
                        case 2: 
                            ACLMessage orden = new ACLMessage( ACLMessage.ACCEPT_PROPOSAL );
                            orden.addReceiver( mejorVendedor );
                            orden.setContent(titulo);
                            orden.setConversationId("COMPRA_VENTA_LIBROS");
                            orden.setReplyWith("orden"+System.currentTimeMillis());
                            myAgent.send(orden);
                            
                            mt = MessageTemplate.and( MessageTemplate.MatchConversationId( "COMPRA_VENTA_LIBROS" ),
                                    MessageTemplate.MatchInReplyTo( orden.getReplyWith()));
                            etapa = 3;
                        break;
                        
                        case 3: 
                            ACLMessage informe = receive(mt);
                            if( informe != null ){
                                if( informe.getPerformative() == ACLMessage.INFORM ){
                                    System.out.println( titulo + " HA SIDO COMPRADO POR UN PRECIO DE: " + mejorPrecio );
                                    myAgent.doDelete();
                                }
                                etapa = 4;
                            }else{
                                block();
                            }
                        break;
                        
                    }

                }

                public boolean done(){
                    return ( (etapa == 2 && mejorVendedor == null) || etapa == 4 );
                }
            }
        });
    }
    
    public void takeDown(){
        System.out.println( "FINALIZANDO AGENTE COMPRADOR "+getAID().getName() );
    }
}
