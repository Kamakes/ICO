import java.util.ArrayList;
import java.util.Random;

public class SolutionRC {
	
	private double T_init;
	private double n; 
	private double coolingRate;
	
	private Route previousRoute ;
	
	public SolutionRC(double T_init, double n, double coolingRate) {
		this.T_init = T_init;
		this.n = n; 
		this.coolingRate = coolingRate; 
	}
	

	public Route RC(Route route) {
		
		double t = T_init ;
		
		double best_distance = route.getTotalDistance(); // Distance initiale
		Route s = route ; 
		
		for (int i = 0; i<n ; i++) {
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
		return s;
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
		return previousRoute ; 	
	}
	
	//Get Methods : 
	public double T_init() {return this.T_init;} 
	public double n(){return this.n;} 
	public double coolingRate(){return this.coolingRate;}
}
