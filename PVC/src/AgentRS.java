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
 
public class AgentRS extends Agent {
	
	private Route best_route; 
	
	@Override
	protected void setup() {
		Object[] args = getArguments(); 
		if(args.length==1) {
			best_route = (Route) args[0]; 
			System.out.println("\n Distance initiale Agent RS : "+ best_route.getTotalDistance()); 
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
			System.out.println("Agent RS envoie le premier Message");
			
			
			
			
			addBehaviour(new CyclicBehaviour(this) {
				
				@Override
				public void action() {
					// TODO Auto-generated method stub
					//MessageTemplate messageTemplate.and(MessageTemplate.matchPerformative(ACLMessage.INFORM), ); 
					ACLMessage msg = receive(); 
					if(msg!=null) {
						System.out.println("Agent RS a reçu un message envoyé par : " + msg.getSender().getName());	
						try {
							Route Route_reçue = (Route) msg.getContentObject(); 
							if (Route_reçue.getTotalDistance() < best_route.getTotalDistance()) {
								System.out.println("Le critère envoyé par" + msg.getSender().getName()+ "est meilleur"); 
								System.out.println("\n L'Agent RS recalcule à partir de la route reçue"); 
								SolutionRS solution1 = new SolutionRS(100, 0.995);
								Route RouteRS = solution1.RS(Route_reçue); 
								
								if (RouteRS.getTotalDistance()< best_route.getTotalDistance()) {
									best_route = RouteRS ; 
									System.out.println("Agent RS a amélioré sa solution ");
									
									if (best_route.getTotalDistance() < Route_reçue.getTotalDistance()) {
										System.out.println("Agent RS a un meilleur critère que : " + msg.getSender().getName() + " :" + best_route.getTotalDistance());
										System.out.println("La meilleure route actuelle est : " + best_route.toString());
										
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
									else {
										System.out.println("Mais elle n'est pas meilleure que celle de l'agent : "+ msg.getSender().getName());
										System.out.println("Solution actuelle Agent RS" + best_route.getTotalDistance()); 
									}
									
										
								}
								else {
									System.out.println("Pas d'amélioration pour l'agent RS");
									System.out.println("Solution actuelle Agent RS" + best_route.getTotalDistance()); 
								}
							}
							else {
								System.out.println("Le critère actuel de l'Agent RS est meilleure que celui reçu");
								System.out.println("Agent RS transmet sa solution à "+ msg.getSender().getName());
								
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
		System.out.println("Destruction de l'agent RS");
	}
			
}
