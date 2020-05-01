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
		
		// On r�cup�re la route initiale de l'agent RS et on la stock dans best_route : 
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
			
			// On ajoute un comportement cyclique : pour la r�ception et l'envoie de messages aux autres agents.
			addBehaviour(new CyclicBehaviour(this) {
				
				@Override
				public void action() {
					ACLMessage msg = receive(); 
					//r�ception d'un message : 
					if(msg!=null) {
						//System.out.println("Agent RS a re�u un message envoy� par : " + msg.getSender().getName());	
						try {
							// On cr�e une variable route_re�u qui stock la route re�u par message.
							Route Route_re�ue = (Route) msg.getContentObject(); 
							
							if (Route_re�ue.getTotalDistance() < best_route.getTotalDistance()) {
								
								// Route_re�u est meilleure que la meilleure route actuelle de l'agent, l'agent recalcule � partir de celle-ci : 
								System.out.println("\n L'Agent RS a re�u une meilleure route et recalcule � partir de celle-ci"); 
								SolutionRS solution1 = new SolutionRS(50, 0.995);
								Route RouteRS = solution1.RS(Route_re�ue); 
								
								if (RouteRS.getTotalDistance()< best_route.getTotalDistance()) {
									// L'agent RS a am�lior� sa meilleure route : 

									best_route = RouteRS ; 
									System.out.println("Agent RS a am�lior� sa solution :" + best_route.getTotalDistance() + " et la transmet � tous : ");
									
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
									// Pas d'am�lioration.
									System.out.println("Pas d'am�lioration pour l'agent RS. Solution actuelle Agent RS : " + best_route.getTotalDistance());
								 
								}
							}
							else {
								// Cas o� l'agent re�oit un message mais qu'il poss�de une meilleure route que celle re�u.
								// L'agent RS renvoie alors sa route � l'exp�diteur pour lui donner sa solution (meilleure route).  
								//System.out.println("Le crit�re de l'Agent RS est meilleure que envoy� par "+ msg.getSender().getName() + ". Agent RS lui transmet sa solution." );
								
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
			
			// Condition d'arret : Arr�t au bout de une minute.
			addBehaviour(new WakerBehaviour(this, 60000) {
			      protected void handleElapsedTimeout() {
			        System.out.println("Crit�re final AgentRS : " + best_route.getTotalDistance());
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
