import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class MyCyclicBehaviour extends CyclicBehaviour {
	private Route currentRoute = null;
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		//MessageTemplate messageTemplate.and(MessageTemplate.matchPerformative(ACLMessage.INFORM), ); 
		ACLMessage msg = myAgent.receive(); 
		if(msg!=null) {
			System.out.println("Reception du message par : " + msg.getSender().getName());	
			try {
				//System.out.println("Contenu du message : " + msg.getContentObject());
				currentRoute = (Route) msg.getContentObject(); 
				System.out.println("La valeur commune a été modifiée"); 
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			ACLMessage reply = msg.createReply(); 
			reply.setPerformative(ACLMessage.AGREE); 
			reply.setContent("Le message a été reçu par " + myAgent.getLocalName()); 
			myAgent.send(reply); 
		}
		else {
			block(); 
		}
	}	
}




