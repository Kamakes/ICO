package Algorithmes;
import java.util.ArrayList;
import java.util.Random;

public class SolutionRS {
	// Param�tres de l'objet : 
	private double T_init;
	private double coolingRate;
	
	// Variables priv�es utiles aux m�thodes : 
	private Route previousRoute ;
	private double time_taken ; // Ajout d'une variable pour d�terminer le temps d'ex�cution de l'algorithm RS.  

	
	// Constructeur : On construit un objet solutionRS � partir de ses deux param�tres : 
	public SolutionRS(double T_init, double coolingRate) {
		this.T_init = T_init; // Temp�rature initiale 
		this.coolingRate = coolingRate; // Taux de refroidissement
	}

	// M�thodes publiques : 
	public Route RS(Route route) {
		// Algorithme RS : prend en entr�e une route et retourne une route optimis�e.
		
		
		long startTime = System.nanoTime(); 

		double t = T_init ;
		
		double best_distance = route.getTotalDistance(); // Distance initiale de la route en entr�e. 
		Route s = new Route(route) ; // on stock l'entr�e de l'algorithme dans une nouvelle variable s.
		
		while (t>0.001) {
			s = swapCities(s); // Permutation de deux villes. 
			double current_distance = s.getTotalDistance(); 
			if (current_distance < best_distance) {
				best_distance = current_distance ; // Si la nouvelle route est meilleure, on l'a d�fini comme nouvelle meilleure route. 
			}
			else if (Math.exp((best_distance - current_distance)/t) < Math.random()) {
				s = revertSwap(); // On annule la permutation/ 
			}
			
			t= coolingRate*t ; // On refroidit � chaque it�ration de l'algorithme. 
		} 	
		
		time_taken = System.nanoTime() - startTime ; // Temps d'ex�cution de l'algorithme. 

		return s ;
	}
	
	public Route swapCities(Route r) {
		// Permet la permutation de deux villes pr�sentent dans une route r. 
		Random rand = new Random();
		ArrayList<City> cities = r.getCities(); 
		int a = rand.nextInt(cities.size());
		int b = rand.nextInt(cities.size());

		previousRoute = new Route(r) ; // avant de permutter on stock la route dans previousroute.
		City x = cities.get(a);
		City y = cities.get(b);
		r.getCities().set(a,y);
		r.getCities().set(b,x);
		return r; 
		 	
	}

	public Route revertSwap() {
		// On annule la permutation.
		return previousRoute; 	
	}
	
	//Get Methods : 
	public double T_init() {return this.T_init;} 
	public double coolingRate(){return this.coolingRate;}
	public double time_taken() {return this.time_taken;} 

}






