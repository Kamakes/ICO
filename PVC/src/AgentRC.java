import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.ArrayList;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.ControllerException;
 
public class AgentRC extends Agent {
	
	public Route best_route; 
	
	@Override
	protected void setup() {
		Object[] args = getArguments(); 
		if(args.length==1) {
			best_route = (Route) args[0]; 
			System.out.println("\n Distance initiale Agent RC : "+ best_route.getTotalDistance()); 
			// TODO Auto-generated method stub
			
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST); 
			msg.addReceiver(new AID("AgentTabou", AID.ISLOCALNAME)); 
			try {
				msg.setContentObject(best_route);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			send(msg);
			System.out.println("Agent RC envoie le premier Message");
			
			
			
			
			addBehaviour(new CyclicBehaviour(this) {
				
				@Override
				public void action() {
					// TODO Auto-generated method stub
					//MessageTemplate messageTemplate.and(MessageTemplate.matchPerformative(ACLMessage.INFORM), ); 
					ACLMessage msg = receive(); 
					if(msg!=null) {
						System.out.println("Agent RC a reçu un message envoyé par : " + msg.getSender().getName());	
						try {
							Route Route_reçue = (Route) msg.getContentObject(); 
							if (Route_reçue.getTotalDistance() < best_route.getTotalDistance()) {
								System.out.println("Le critère reçu est meilleur que celui de l'agent RC"); 
								System.out.println("\n L'Agent RC recalcule à partir de la route reçue"); 
								SolutionRC solution1 = new SolutionRC(100, 0.995);
								Route RouteRC = solution1.RC(Route_reçue); 
								
								System.out.println("Agent RC a trouvé : " + RouteRC.getTotalDistance());
									if (RouteRC.getTotalDistance()< best_route.getTotalDistance()) {
										best_route = RouteRC ; 
										System.out.println("Agent RC a amélioré sa solution et la transmet"); 
										
										ACLMessage reply = msg.createReply(); 
										reply.setPerformative(ACLMessage.AGREE); 
										try {
											reply.setContentObject(best_route);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} 
										send(reply); 
										
										if (best_route.getTotalDistance() < Route_reçue.getTotalDistance()) {
											System.out.println("Agent RC a le meilleur critère : " + best_route.getTotalDistance());
											System.out.println("La meilleure route associée est : " + best_route.toString());
										}
										
										
									}
									else {
										System.out.println("Pas d'amélioration pour l'agent RC"); 
									}
							}
							else {
								System.out.println("Le critère actuel de l'Agent RC est meilleure que celui reçu");
								System.out.println("Agent RC transmet sa meilleure route");
								
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
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						
					
					}
					else {
						block(); 
					}
				}
			}
			);
			
		}
		
	else {
		doDelete();
	}	
	}
	
	@Override
	protected void takeDown() {
		System.out.println("Destruction de l'agent RC");
	}
			
}
