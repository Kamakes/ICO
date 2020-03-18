import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		

		City paris = new City("Paris", 20, 30);
		City lyon = new City("Lyon", 15, 15);
		City Nantes = new City("Nantes", 10, 5); 
		City Marseille = new City("Marseille", 20, 50);
		
		ArrayList<City> cities = new ArrayList<City>();
		cities.add(paris);
		cities.add(lyon);
		cities.add(Nantes);
		cities.add(Marseille); 
		
		Route Route1 = new Route(cities);
		System.out.print(Route1.toString());
		System.out.print(Route1.getTotalDistance());
		
		
		SolutionRC solution1 = new SolutionRC(1000, 1500, 0.1);
		Route s_f = solution1.RC(Route1); 
		System.out.print(s_f);
		System.out.print(s_f.getTotalDistance());

		
		
		
		
	}
}
