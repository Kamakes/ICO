import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.WakerBehaviour;
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
						//System.out.println("Agent Tabou a reçu un message envoyé par : " + msg.getSender().getName());	
						try {
							Route Route_reçue = (Route) msg.getContentObject(); 
							if (Route_reçue.getTotalDistance() < best_route.getTotalDistance()) {
								//System.out.println("Le critère envoyé par" + msg.getSender().getName()+ "est meilleur"); 
								System.out.println("\n L'Agent Tabou a reçu une meilleure route et recalcule à partir de celle-ci"); 
								SolutionTabou solution = new SolutionTabou(Route_reçue.getCities(), 3, 500);
								solution.optimiserTabou();
								
								if (solution.getBestPath().getTotalDistance()< best_route.getTotalDistance()) {
									best_route = solution.getBestPath() ; 
									System.out.println("Agent Tabou a amélioré solution :" + best_route.getTotalDistance() + " et la transmet à tous :"); 
									
									ACLMessage reply = new ACLMessage(ACLMessage.REQUEST); 
									reply.addReceiver(new AID("AgentRS", AID.ISLOCALNAME));
									reply.addReceiver(new AID("AgentAG", AID.ISLOCALNAME));

									try {
										reply.setContentObject(best_route);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} 
									send(reply); 
									
									if (best_route.getTotalDistance() < Route_reçue.getTotalDistance()) {
										//System.out.println("Le critère trouvé par agent Tabou est meilleur que celui de : " + msg.getSender().getName() + " : " + best_route.getTotalDistance());
										//System.out.println("La meilleure route actuelle de l'agent Tabou est : " + best_route.toString());

									}
										
								}
								else {
									System.out.println("Pas d'amélioration pour l'agent Tabou. Solution actuelle Agent Tabou : " + best_route.getTotalDistance());
								}
							}
							else {
								//System.out.println("Le critère de l'Agent Tabou est meilleure que celui envoyé par "+ msg.getSender().getName() + ". Agent Tabou lui transmet sa solution." );
								
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

			addBehaviour(new WakerBehaviour(this, 60000) {
			      protected void handleElapsedTimeout() {
			        System.out.println("Critère final Agent Tabou : " + best_route.getTotalDistance());
			        System.out.println("Route finale Agent Tabou : " + best_route);
			      } 
			    });
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