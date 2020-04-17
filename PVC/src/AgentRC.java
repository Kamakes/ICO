import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;
 

public class AgentRC extends Agent {
	
	private Route best_route; 
	
	@Override
	protected void setup() {
		Object[] args = getArguments(); 
		if(args.length==1) {
			best_route = (Route) args[0]; 
			System.out.println("Agent RC : " + this.getAID().getName());
			System.out.println("\n j'ai trouvé la distance : "+ best_route.getTotalDistance()); 
			System.out.println("\n Ce qui correspond à la route : " + best_route +"\n"); 
			// TODO Auto-generated method stub
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST); 
			msg.addReceiver(new AID("Gen", AID.ISLOCALNAME)); 
			try {
				msg.setContentObject(best_route);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			send(msg); 
			//addBehaviour(new MyTickerBehaviour(this, 1000)); 	
		}
		else {
			doDelete();
		}	
	}
	@Override
	protected void takeDown() {
		System.out.println("Destruction de l'agent RC");
	}
		
}
