import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.ArrayList;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.ControllerException;
 
public class AgentRS extends Agent {
	
	// variable best_route : meilleure route de l'agent RS.
	private Route best_route; 
	
	@Override
	protected void setup() {
		
		// On récupère la route initiale de l'agent RS et on la stock dans best_route : 
		Object[] args = getArguments(); 
		if(args.length==1) {
			best_route = (Route) args[0]; 
			System.out.println("\n Distance initiale Agent RS : "+ best_route.getTotalDistance()); 
			
			// Agent RS envoie le premier message aux deux autres agents.
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST); 
			msg.addReceiver(new AID("AgentTabou", AID.ISLOCALNAME)); 
			msg.addReceiver(new AID("AgentAG", AID.ISLOCALNAME)); 
			try {
				// L'agent envoie sa meilleure route.
				msg.setContentObject(best_route);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			send(msg);
			System.out.println("Agent RS envoie le premier Message");
			
			// On ajoute un comportement cyclique : pour la réception et l'envoie de messages aux autres agents.
			addBehaviour(new CyclicBehaviour(this) {
				
				@Override
				public void action() {
					ACLMessage msg = receive(); 
					//réception d'un message : 
					if(msg!=null) {
						//System.out.println("Agent RS a reçu un message envoyé par : " + msg.getSender().getName());	
						try {
							// On crée une variable route_reçu qui stock la route reçu par message.
							Route Route_reçue = (Route) msg.getContentObject(); 
							
							if (Route_reçue.getTotalDistance() < best_route.getTotalDistance()) {
								
								// Route_reçu est meilleure que la meilleure route actuelle de l'agent, l'agent recalcule à partir de celle-ci : 
								System.out.println("\n L'Agent RS a reçu une meilleure route et recalcule à partir de celle-ci"); 
								SolutionRS solution1 = new SolutionRS(50, 0.995);
								Route RouteRS = solution1.RS(Route_reçue); 
								
								if (RouteRS.getTotalDistance()< best_route.getTotalDistance()) {
									// L'agent RS a amélioré sa meilleure route : 

									best_route = RouteRS ; 
									System.out.println("Agent RS a amélioré sa solution :" + best_route.getTotalDistance() + " et la transmet à tous : ");
									
									// Envoi de la nouvelle meilleure route de l'agent RS aux deux autres Agents : 
									ACLMessage reply = new ACLMessage(ACLMessage.REQUEST); 
									reply.addReceiver(new AID("AgentTabou", AID.ISLOCALNAME));
									reply.addReceiver(new AID("AgentAG", AID.ISLOCALNAME));

									try {
										reply.setContentObject(best_route);
									} catch (IOException e) {
										e.printStackTrace();
									} 
									send(reply); 
							
								}
								else {
									// Pas d'amélioration.
									System.out.println("Pas d'amélioration pour l'agent RS. Solution actuelle Agent RS : " + best_route.getTotalDistance());
								 
								}
							}
							else {
								// Cas où l'agent reçoit un message mais qu'il possède une meilleure route que celle reçu.
								// L'agent RS renvoie alors sa route à l'expéditeur pour lui donner sa solution (meilleure route).  
								//System.out.println("Le critère de l'Agent RS est meilleure que envoyé par "+ msg.getSender().getName() + ". Agent RS lui transmet sa solution." );
								
								ACLMessage reply = msg.createReply(); 
								reply.setPerformative(ACLMessage.AGREE); 
								try {
									reply.setContentObject(best_route);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
								send(reply); 
							}
							
							
							
						} catch (UnreadableException e) {
							e.printStackTrace();
						} 
						
					
					}
					else {
						block(); 
					}
				}
			}
			);
			
			// Condition d'arret : Arrêt au bout de une minute.
			addBehaviour(new WakerBehaviour(this, 60000) {
			      protected void handleElapsedTimeout() {
			        System.out.println("Critère final AgentRS : " + best_route.getTotalDistance());
			        System.out.println("Route finale Agent RS : " + best_route);

			      } 
			    });
			
		}
		
	else {
		doDelete();
	}	
	}
	
	@Override
	protected void takeDown() {
		System.out.println("Destruction de l'agent RS");
	}
			
}
