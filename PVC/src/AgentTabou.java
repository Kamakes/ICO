import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class AgentTabou extends Agent {
	
	
	private Route best_route; 
	
	@Override
	protected void setup() {
		
		Object[] args = getArguments(); 
		if(args.length==1) {
			//critere = (double)args[0] ;
			best_route = (Route)args[0]; 
			System.out.println("\n La distance initiale Agent Tabou : "+ best_route.getTotalDistance()); 
		
		
			addBehaviour(new CyclicBehaviour(this) {
				
				@Override
				public void action() {
					// TODO Auto-generated method stub
					//MessageTemplate messageTemplate.and(MessageTemplate.matchPerformative(ACLMessage.INFORM), ); 
					ACLMessage msg = receive(); 
					if(msg!=null) {
						System.out.println("Agent Tabou a reçu un message envoyé par : " + msg.getSender().getName());	
						try {
							Route Route_reçue = (Route) msg.getContentObject(); 
							if (Route_reçue.getTotalDistance() < best_route.getTotalDistance()) {
								System.out.println("Le critère reçu est meilleur que celui de l'agent Tabou"); 
								System.out.println("\n L'Agent Tabou recalcule à partir de la route reçue"); 
								SolutionTabou solution = new SolutionTabou(Route_reçue.getCities(),10);
								solution.optimiserTabou();
								System.out.println("Agent Tabou a trouvé : " + solution.getBestPath().getTotalDistance());
									if (solution.getBestPath().getTotalDistance()< best_route.getTotalDistance()) {
										best_route = solution.getBestPath() ; 
										System.out.println("Agent Tabou a amélioré sa solution et la transmet"); 
										
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
											System.out.println("Agent Tabou a le meilleur critère : " + best_route.getTotalDistance());
											System.out.println("La meilleure route associée est : " + best_route.toString());
										}
										
										
									}
									else {
										System.out.println("Pas d'amélioration pour l'agent Tabou"); 
									}
							}
							else {
								System.out.println("Le critère actuel de l'Agent Tabou est meilleure que celui reçu");
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
		System.out.println("Destruction de l'agent Tabou");
	}
	
		
}