import java.util.ArrayList;
import java.util.Random;

public class SolutionRS {
	
	private double T_init;
	private double coolingRate;
	
	private Route previousRoute ;
	private int iterations;
	
	public SolutionRS(double T_init, double coolingRate) {
		this.T_init = T_init; 
		this.coolingRate = coolingRate; 
	}

	public Route RS(Route route) {
		
		iterations = 0; 
		double t = T_init ;
		
		double best_distance = route.getTotalDistance(); // Distance initiale
		Route s = new Route(route) ; 
		
		while (t>0.001) {
			iterations +=1 ;
			s = swapCities(s);
			double current_distance = s.getTotalDistance(); 
			if (current_distance < best_distance) {
				best_distance = current_distance ; 
			}
			else if (Math.exp((best_distance - current_distance)/t) < Math.random()) {
				s = revertSwap();
			}
			
			t= coolingRate*t ; 
		} 	
		return s ;
	}
	
	public Route swapCities(Route r) {
		Random rand = new Random();
		ArrayList<City> cities = r.getCities(); 
		int a = rand.nextInt(cities.size());
		int b = rand.nextInt(cities.size());

		previousRoute = new Route(r) ; 
		City x = cities.get(a);
		City y = cities.get(b);
		r.getCities().set(a,y);
		r.getCities().set(b,x);
		return r; 
		 	
	}


	public Route revertSwap() {
		return previousRoute; 	
	}
	
	//Get Methods : 
	public double T_init() {return this.T_init;} 
	public double coolingRate(){return this.coolingRate;}
	public double number_iterations() {return this.iterations;}
}






