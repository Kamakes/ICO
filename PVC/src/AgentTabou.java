import java.io.IOException;

import Algorithmes.Route;
import Algorithmes.SolutionTabou;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class AgentTabou extends Agent {
	
	// variable best_route : meilleure route de l'agent Tabou.
	private Route best_route; 
	
	@Override
	protected void setup() {
		
		// On récupère la route initiale de l'agent Tabou et on la stock dans best_route : 
		Object[] args = getArguments(); 
		if(args.length==1) {
			best_route = (Route)args[0]; 
			System.out.println("\n La distance initiale Agent Tabou : "+ best_route.getTotalDistance()); 
		
			// On ajoute un comportement cyclique : pour la réception et l'envoie de messages aux autres agents.
			addBehaviour(new CyclicBehaviour(this) {
				
				@Override
				public void action() {
					
					ACLMessage msg = receive(); 
					// Réception d'un message :
					if(msg!=null) {
						//System.out.println("Agent Tabou a reçu un message envoyé par : " + msg.getSender().getName());	
						try {
							// On crée une variable route_reçu qui stock la route reçu par message.
							Route Route_reçue = (Route) msg.getContentObject(); 
							
							if (Route_reçue.getTotalDistance() < best_route.getTotalDistance()) {
								// Si Route_reçu est meilleure que la meilleure route actuelle de l'agent, l'agent recalcule à partir de celle-ci : 
								System.out.println("\n L'Agent Tabou a reçu une meilleure route et recalcule à partir de celle-ci"); 
								SolutionTabou solution = new SolutionTabou(Route_reçue.getCities(), 3, 500);
								solution.optimiserTabou();
								
								if (solution.getBestPath().getTotalDistance()< best_route.getTotalDistance()) {
									// L'agent Tabou a amélioré sa meilleure route : 
									best_route = solution.getBestPath() ; 
									System.out.println("Agent Tabou a amélioré solution :" + best_route.getTotalDistance() + " et la transmet à tous :"); 
									
									// Envoi de la nouvelle meilleure route de l'agent Tabou aux deux autres Agents : 
									ACLMessage reply = new ACLMessage(ACLMessage.REQUEST); 
									reply.addReceiver(new AID("AgentRS", AID.ISLOCALNAME));
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
									System.out.println("Pas d'amélioration pour l'agent Tabou. Solution actuelle Agent Tabou : " + best_route.getTotalDistance());
								}
							}
							else {
								// Cas où l'agent reçoit un message mais qu'il possède une meilleure route que celle reçu.
								// L'agent Tabou renvoie alors sa route à l'expéditeur pour lui donner sa solution (meilleure route).  
								//System.out.println("Le critère de l'Agent Tabou est meilleure que celui envoyé par "+ msg.getSender().getName() + ". Agent Tabou lui transmet sa solution." );
								
								ACLMessage reply = msg.createReply(); 
								reply.setPerformative(ACLMessage.AGREE); 
								try {
									reply.setContentObject(best_route);
								} catch (IOException e) {
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