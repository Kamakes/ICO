import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class AgentAG  extends Agent{
	
	public Route  best_route; 
	
	@Override
	protected void setup() {
		Object[] args = getArguments(); 
		if(args.length==1) {
			best_route = (Route) args[0]; 
			System.out.println("\n La distance initiale Agent g�n�tique : "+ best_route.getTotalDistance()); 

			
			addBehaviour(new CyclicBehaviour(this) {

				@Override
				public void action() {
					// TODO Auto-generated method stub
					//MessageTemplate messageTemplate.and(MessageTemplate.matchPerformative(ACLMessage.INFORM), ); 
					ACLMessage msg = receive(); 
					if(msg!=null) {
						//System.out.println("Agent AG a re�u un message envoy� par : " + msg.getSender().getName());	
						try {
							Route Route_re�ue = (Route) msg.getContentObject(); 
							if (Route_re�ue.getTotalDistance() < best_route.getTotalDistance()) {
								//System.out.println("Le crit�re envoy� par" + msg.getSender().getName()+ "est meilleur"); 
								System.out.println("\n L'Agent AG a re�u une meilleure route et recalcule � partir de celle-ci"); 
								
								SolutionAG ag = new SolutionAG(Route_re�ue.getCities());
								Population population = new Population(SolutionAG.POPULATION_Size, Route_re�ue);								
								population.sortRouteByFitness();
								int generationNumbre = 1;
								while (generationNumbre < SolutionAG.NUM_GENERATIONS) {
									population = ag.evolve(population);
									population.sortRouteByFitness();
									generationNumbre++;
								}
								
								Route RouteAG = population.getRoutes().get(0);
								
								if (RouteAG.getTotalDistance()< best_route.getTotalDistance()) {
									best_route = RouteAG; 
									System.out.println("Agent AG a am�lior� solution :" + best_route.getTotalDistance() + " et la transmet � tous :"); 
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

									if (best_route.getTotalDistance() < Route_re�ue.getTotalDistance()) {
										//System.out.println("Le crit�re trouv� par agent AG est meilleur que celui de : " + msg.getSender().getName() + " : " + best_route.getTotalDistance());
										//System.out.println("La meilleure route actuelle de l'agent AG est : " + best_route.toString());
									}


									}
									else {
										System.out.println("Pas d'am�lioration pour l'agent AG. Solution actuelle Agent AG : " + best_route.getTotalDistance());
							
									}
							}
							else {
								//System.out.println("Le crit�re de l'Agent AG est meilleure que celui envoy� par "+ msg.getSender().getName() + ". Agent AG lui transmet sa solution." );
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
			        System.out.println("Crit�re final AgentAG : " + best_route.getTotalDistance());
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
