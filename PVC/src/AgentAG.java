

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
			System.out.println("\n j'ai trouv� la distance : "+ best_route.getTotalDistance()); 
			System.out.println("\n Ce qui correspond � la route : " + best_route +"\n"); 
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
						System.out.println("Agent AG a re�u un message envoy� par : " + msg.getSender().getName());	
						try {
							Route Route_re�ue = (Route) msg.getContentObject(); 
							if (Route_re�ue.getTotalDistance() < Route_re�ue.getTotalDistance()) {
								System.out.println("Le crit�re re�u est meilleur que celui de l'agent AG"); 
								System.out.println("\n L'Agent AG recalcule � partir de la route re�ue"); 
								
								SolutionAG ag = new SolutionAG(Route_re�ue.getCities());
								Population population = new Population(SolutionAG.POPULATION_Size, Route_re�ue.getCities());
								Route RouteAG=population.getRoutes().get(0);
								
								System.out.println("Agent AG a trouv� : " + RouteAG.getTotalDistance());
									if (RouteAG.getTotalDistance()< best_route.getTotalDistance()) {
										best_route = RouteAG; 
										System.out.println("Agent AG a am�lior� sa solution et la transmet"); 

										ACLMessage reply = msg.createReply(); 
										reply.setPerformative(ACLMessage.AGREE); 
										try {
											reply.setContentObject(best_route);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} 
										send(reply); 

										if (best_route.getTotalDistance() < Route_re�ue.getTotalDistance()) {
											System.out.println("Agent AG a le meilleur crit�re : " + best_route.getTotalDistance());
											System.out.println("La meilleure route associ�e est : " + best_route.toString());
										}


									}
									else {
										System.out.println("Pas d'am�lioration pour l'agent AG"); 
									}
							}
							else {
								System.out.println("Le crit�re actuel de l'Agent AG est meilleure que celui re�u");
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
