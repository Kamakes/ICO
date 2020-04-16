import java.util.ArrayList;
import java.util.stream.IntStream;

public class Population {
	// Attribut
	private ArrayList<Route> routes = new ArrayList<Route>(SolutionAG.POPULATION_Size);

	public Population(int populationSize, SolutionAG ag) {
		IntStream.range(0, populationSize).forEach(x -> routes.add(new Route(ag.getInitialRoute())));

	}

	// constructeur,initialis la popoulation
	public Population(int populationSize, ArrayList<City> cities) {
		IntStream.range(0, populationSize).forEach(x -> routes.add(new Route(cities)));
	}

	// Gettre
	public ArrayList<Route> getRoutes() {
		return routes;
	}

	// methode pour trier les routes par leurs Fitness
	public void sortRouteByFitness() {
		routes.sort((route1, route2) -> {
			int flag = 0;
			if (route1.getFitness() > route2.getFitness())
				flag = -1;
			else if (route1.getFitness() < route2.getFitness())
				flag = 1;
			return flag;
		});
	}
}

