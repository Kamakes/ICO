import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		//Declaration villes
		City Paris = new City("Paris", 48.8534, 2.3488);
		City Lyon = new City("Lyon", 45.75, 4.85);
		City Nantes = new City("Nantes", 47.2173, -1.5534); 
		City Marseille = new City("Marseille", 43.3, 5.4);
		City Dijon = new City("Dijon", 47.3167, 5.0167);
		City Bordeaux = new City("Bordeaux", 44.8333, -0.5667);
		City Rennes = new City("Rennes", 48.069, -1.6146);
		City Brest = new City("Brest", 48.309, -4.507);
		City Toulouse = new City("Montpellier", 43.571, 3.9624);
		City Strasbourg = new City("Strasbourg", 48.522, 7.727);
		City Nice = new City("Nice", 43.702, 7.315);
		City Lille = new City("Lille", 50.657, 30.004);
		
		//creation chemin
		ArrayList<City> cities = new ArrayList<City>();
		cities.add(Paris);
		cities.add(Lyon);
		cities.add(Nantes);
		cities.add(Marseille); 
		cities.add(Dijon);
		cities.add(Bordeaux);
		cities.add(Rennes);
		//cities.add(Brest);
		//cities.add(Toulouse);
		//cities.add(Strasbourg);
		//cities.add(Nice);
		//cities.add(Lille);
		
		
		//Tabou
		
		SolutionTabou solution = new SolutionTabou(cities,10);
		System.out.println(solution.getBestPath().toString());
		System.out.println(solution.getBestPath().getTotalDistance());
		solution.optimiserTabou();
		System.out.println(solution.getBestPath().toString());
		System.out.println(solution.getBestPath().getTotalDistance());
		
		
		//RC
		
		//Route Route1 = new Route(cities);
		//System.out.println(Route1.toString());
		//System.out.println(Route1.getTotalDistance());
		
		//SolutionRC solution1 = new SolutionRC(1000, 1500, 0.1);
		//Route s_f = solution1.RC(Route1); 
		//System.out.print(s_f);
		//System.out.print(s_f.getTotalDistance());

		
		
		
		
	}
}
