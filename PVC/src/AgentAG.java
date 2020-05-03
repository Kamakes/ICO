import java.io.IOException;

import Algorithmes.Population;
import Algorithmes.Route;
import Algorithmes.SolutionAG;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class AgentAG  extends Agent{
	
	// variable best_route : meilleure route de l'agent RS.
	public Route  best_route; 
	
	@Override
	protected void setup() {
		// On récupère la route initiale de l'agent AG et on la stock dans best_route : 
		Object[] args = getArguments(); 
		if(args.length==1) {
			best_route = (Route) args[0]; 
			System.out.println("\n La distance initiale Agent génétique : "+ best_route.getTotalDistance()); 

			// On ajoute un comportement cyclique : pour la réception et l'envoie de messages aux autres agents.
			addBehaviour(new CyclicBehaviour(this) {

				@Override
				public void action() {
					ACLMessage msg = receive(); 
					//réception d'un message :
					if(msg!=null) {
						//System.out.println("Agent AG a reçu un message envoyé par : " + msg.getSender().getName());	
						try {
							// On crée une variable route_reçu qui stock la route reçu par message.
							Route Route_reçue = (Route) msg.getContentObject(); 
							
							if (Route_reçue.getTotalDistance() < best_route.getTotalDistance()) {
								// Route_reçu est meilleure que la meilleure route actuelle de l'agent, l'agent recalcule à partir de celle-ci : 
								System.out.println("\n L'Agent AG a reçu une meilleure route et recalcule à partir de celle-ci"); 
								
								SolutionAG ag = new SolutionAG(Route_reçue.getCities());
								Population population = new Population(SolutionAG.POPULATION_Size, Route_reçue);								
								population.sortRouteByFitness();
								int generationNumbre = 1;
								while (generationNumbre < SolutionAG.NUM_GENERATIONS) {
									population = ag.evolve(population);
									population.sortRouteByFitness();
									generationNumbre++;
								}
								
								Route RouteAG = population.getRoutes().get(0);
								
								if (RouteAG.getTotalDistance()< best_route.getTotalDistance()) {
									// L'agent AG a amélioré sa meilleure route : 

									best_route = RouteAG; 
									System.out.println("Agent AG a amélioré solution :" + best_route.getTotalDistance() + " et la transmet à tous :"); 
									// Envoi de la nouvelle meilleure route de l'agent AG aux deux autres Agents : 
									ACLMessage reply = new ACLMessage(ACLMessage.REQUEST); 
									reply.addReceiver(new AID("AgentRS", AID.ISLOCALNAME));
									reply.addReceiver(new AID("AgentTabou", AID.ISLOCALNAME));

									try {
										reply.setContentObject(best_route);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} 
									send(reply); 

									}
									else {
										// Pas d'amélioration.
										System.out.println("Pas d'amélioration pour l'agent AG. Solution actuelle Agent AG : " + best_route.getTotalDistance());
							
									}
							}
							else {
								// Cas où l'agent reçoit un message mais qu'il possède une meilleure route que celle reçu.
								// L'agent AG renvoie alors sa route à l'expéditeur pour lui donner sa solution (meilleure route).  
								//System.out.println("Le critère de l'Agent AG est meilleure que celui envoyé par "+ msg.getSender().getName() + ". Agent AG lui transmet sa solution." );
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
			        System.out.println("Critère final AgentAG : " + best_route.getTotalDistance());
			        System.out.println("Route finale AgentAG : " + best_route);

			      } 
			    });
			
		}
	
	    else {
		doDelete();
	}	

	}
@Override
protected void takeDown() {
	System.out.println("Destruction de l'agent AG");
}

}
