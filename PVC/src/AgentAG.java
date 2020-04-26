

import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class AgentAG  extends Agent{
	private Route  best_Route; 
	
	@Override
	protected void setup() {
		Object[] args = getArguments(); 
		if(args.length==1) {
			Route best_route = (Route) args[0]; 
			System.out.println("Agent AG : " + this.getAID().getName());
			System.out.println("\n j'ai trouvé la distance : "+ best_route.getTotalDistance()); 
			System.out.println("\n Ce qui correspond à la route : " + best_route +"\n"); 
			System.out.println("\n Distance initiale Agent AG : "+ best_route.getTotalDistance()); 
			// TODO Auto-generated method stub
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST); 
			msg.addReceiver(new AID("Gen", AID.ISLOCALNAME)); 
			msg.addReceiver(new AID("AgentAG", AID.ISLOCALNAME)); 
			try {
				msg.setContentObject(best_route);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			send(msg); 
			//addBehaviour(new MyTickerBehaviour(this, 1000)); 	
			send(msg);
			System.out.println("Agent AG envoie le premier Message");
			
			addBehaviour(new CyclicBehaviour(this) {

				@Override
				public void action() {
					// TODO Auto-generated method stub
					//MessageTemplate messageTemplate.and(MessageTemplate.matchPerformative(ACLMessage.INFORM), ); 
					ACLMessage msg = receive(); 
					if(msg!=null) {
						System.out.println("Agent AG a reçu un message envoyé par : " + msg.getSender().getName());	
						try {
							Route Route_reçue = (Route) msg.getContentObject(); 
							if (Route_reçue.getTotalDistance() < Route_reçue.getTotalDistance()) {
								System.out.println("Le critère reçu est meilleur que celui de l'agent AG"); 
								System.out.println("\n L'Agent AG recalcule à partir de la route reçue"); 
								
								SolutionAG ag = new SolutionAG(Route_reçue.getCities());
								Population population = new Population(SolutionAG.POPULATION_Size, Route_reçue.getCities());
								Route RouteAG=population.getRoutes().get(0);
								
								System.out.println("Agent AG a trouvé : " + RouteAG.getTotalDistance());
									if (RouteAG.getTotalDistance()< best_route.getTotalDistance()) {
										best_route = RouteAG; 
										System.out.println("Agent AG a amélioré sa solution et la transmet"); 

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
											System.out.println("Agent AG a le meilleur critère : " + best_route.getTotalDistance());
											System.out.println("La meilleure route associée est : " + best_route.toString());
										}


									}
									else {
										System.out.println("Pas d'amélioration pour l'agent AG"); 
									}
							}
							else {
								System.out.println("Le critère actuel de l'Agent AG est meilleure que celui reçu");
								System.out.println("Agent AG transmet sa meilleure route");

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
	System.out.println("Destruction de l'agent AG");
}

}
