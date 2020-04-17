import jade.core.Agent;

public class AgentTabou extends Agent {
	
	
	private double critere; 
	private Route best_route; 
	
	@Override
	protected void setup() {
		Object[] args = getArguments(); 
		if(args.length==1) {
			//critere = (double)args[0] ;
			best_route = (Route)args[0]; 
			System.out.println("Agent Tabou : " + this.getAID().getName());
			//System.out.println("\n j'ai trouvé la distance : "+ critere); 
			System.out.println("\n Ce qui correspond à la route : " + best_route +"\n"); 
		
			addBehaviour(new MyCyclicBehaviour()); 

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