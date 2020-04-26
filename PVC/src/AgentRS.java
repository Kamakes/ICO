import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.ArrayList;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.core.behaviours.WakerBehaviour;
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
			
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST); 
			msg.addReceiver(new AID("AgentTabou", AID.ISLOCALNAME)); 
			msg.addReceiver(new AID("AgentAG", AID.ISLOCALNAME)); 
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
						//System.out.println("Agent RS a reçu un message envoyé par : " + msg.getSender().getName());	
						try {
							Route Route_reçue = (Route) msg.getContentObject(); 
							if (Route_reçue.getTotalDistance() < best_route.getTotalDistance()) {
								//System.out.println("Le critère envoyé par" + msg.getSender().getName()+ "est meilleur"); 
								System.out.println("\n L'Agent RS a reçu une meilleure route et recalcule à partir de celle-ci"); 
								SolutionRS solution1 = new SolutionRS(50, 0.995);
								Route RouteRS = solution1.RS(Route_reçue); 
								
								if (RouteRS.getTotalDistance()< best_route.getTotalDistance()) {
									best_route = RouteRS ; 
									System.out.println("Agent RS a amélioré sa solution :" + best_route.getTotalDistance() + " et la transmet à tous : ");
									
									ACLMessage reply = new ACLMessage(ACLMessage.REQUEST); 
									reply.addReceiver(new AID("AgentTabou", AID.ISLOCALNAME));
									reply.addReceiver(new AID("AgentAG", AID.ISLOCALNAME));

									try {
										reply.setContentObject(best_route);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} 
									send(reply); 
									
									if (best_route.getTotalDistance() < Route_reçue.getTotalDistance()) {
										//System.out.println("Agent RS a un meilleur critère que : " + msg.getSender().getName() + " :" + best_route.getTotalDistance());
										//System.out.println("La meilleure route actuelle de l'agent RS est : " + best_route.toString());
									}
										
								}
								else {
									System.out.println("Pas d'amélioration pour l'agent RS. Solution actuelle Agent RS : " + best_route.getTotalDistance());
								 
								}
							}
							else {
								//System.out.println("Le critère de l'Agent RS est meilleure que envoyé par "+ msg.getSender().getName() + ". Agent RS lui transmet sa solution." );
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
			        System.out.println("Critère final AgentRS : " + best_route.getTotalDistance());
			        System.out.println("Route finale Agent RS : " + best_route);

			      } 
			    });
			
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
