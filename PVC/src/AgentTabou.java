import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class AgentTabou extends Agent {
	
	
	public Route best_route; 
	
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
						System.out.println("Agent Tabou a re�u un message envoy� par : " + msg.getSender().getName());	
						try {
							Route Route_re�ue = (Route) msg.getContentObject(); 
							if (Route_re�ue.getTotalDistance() < best_route.getTotalDistance()) {
								System.out.println("Le crit�re envoy� par" + msg.getSender().getName()+ "est meilleur"); 
								System.out.println("\n L'Agent Tabou recalcule � partir de la route re�ue"); 
								SolutionTabou solution = new SolutionTabou(Route_re�ue.getCities(), 3, 500);
								solution.optimiserTabou();
								
								if (solution.getBestPath().getTotalDistance()< best_route.getTotalDistance()) {
									best_route = solution.getBestPath() ; 
									System.out.println("Agent Tabou a am�lior� sa solution"); 
									
									
									
									if (best_route.getTotalDistance() < Route_re�ue.getTotalDistance()) {
										System.out.println("Agent Tabou a un meilleur crit�re que : " + msg.getSender().getName() + " : " + best_route.getTotalDistance());
										System.out.println("La meilleure route associ�e est : " + best_route.toString());
										
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
										System.out.println("Solution actuelle Agent Tabou" + best_route.getTotalDistance()); 
									}
									
										
								}
								else {
									System.out.println("Pas d'am�lioration pour l'agent Tabou");
									System.out.println("Solution actuelle Agent Tabou" + best_route.getTotalDistance()); 
								}
							}
							else {
								System.out.println("Le crit�re actuel de l'Agent Tabou est meilleure que celui re�u");
								System.out.println("Agent Tabou transmet sa solution � "+ msg.getSender().getName());
								
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