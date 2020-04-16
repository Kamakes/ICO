import java.util.ArrayList;
import java.util.stream.IntStream;

public class SolutionAG {
	// Constantes
	public static final double MUTATION_PROBABILITY = 0.25;
	public static final int TOURNAMENT_SELECTION_SIZE = 3;
	public static final int POPULATION_Size = 8;
	public static final int NUM_ELITE_ROUTES = 1;
	public static final int NUM_GENERATIONS = 30;
	// attributs
	private ArrayList<City> initialRoute = null;

	// Constructeurs
	public SolutionAG(ArrayList<City> initialRoute) {
		this.initialRoute = initialRoute;
	}

	// Gettre
	public ArrayList<City> getInitialRoute() {
		return initialRoute;
	}

	// methodes
	public Population evolve(Population population) {
		return mutationPopulation(croisementPopulation(population));
	}

	// croisement d'une population
	Population croisementPopulation(Population population) {
		Population croisementPopulation = new Population(population.getRoutes().size(), this);
		IntStream.range(0, NUM_ELITE_ROUTES)
				.forEach(x -> croisementPopulation.getRoutes().set(x, population.getRoutes().get(x)));
		IntStream.range(NUM_ELITE_ROUTES, croisementPopulation.getRoutes().size()).forEach(x -> {
			Route route1 = selectTournamentPopulation(population).getRoutes().get(0);
			Route route2 = selectTournamentPopulation(population).getRoutes().get(0);
			croisementPopulation.getRoutes().set(x, croisementRoute(route1, route2));
		});
		return croisementPopulation;
	}

	Population mutationPopulation(Population population) {
		population.getRoutes().parallelStream().filter(x -> population.getRoutes().indexOf(x) >= NUM_ELITE_ROUTES)
				.forEach(x -> mutationRoute(x));
		return population;
	}

	Route croisementRoute(Route route1, Route route2) {
		Route croisementRoute = new Route(this);
		Route tempRoute1 = route1;
		Route tempRoute2 = route2;
		if (Math.random() < 0.5) {
			tempRoute1 = tempRoute2;
			tempRoute2 = tempRoute1;
		}
		for (int x = 0; x < croisementRoute.getCities().size() / 2; x++)
			croisementRoute.getCities().set(x, tempRoute1.getCities().get(x));

		return IntermediaireCroisementRoute(croisementRoute, tempRoute2);
	}

	private Route IntermediaireCroisementRoute(Route croisementRoute, Route route) {
		route.getCities().stream().filter(x -> !croisementRoute.getCities().contains(x)).forEach(cityX -> {
			for (int y = 0; y < route.getCities().size(); y++) {
				if (croisementRoute.getCities().get(y) == null) {
					croisementRoute.getCities().set(y, cityX);
					break;
				}
			}
		});
		return croisementRoute;
	}

	Route mutationRoute(Route route) {
		route.getCities().stream().filter(x -> Math.random() < MUTATION_PROBABILITY).forEach(cityX -> {
			int y = (int) (route.getCities().size() * Math.random());
			City cityY = route.getCities().get(y);
			route.getCities().set(route.getCities().indexOf(cityX), cityY);
			route.getCities().set(y, cityX);

		});
		return route;
	}

	Population selectTournamentPopulation(Population population) {
		Population tournamentPopulation = new Population(TOURNAMENT_SELECTION_SIZE, this);
		IntStream.range(0, TOURNAMENT_SELECTION_SIZE).forEach(x -> tournamentPopulation.getRoutes().set(x,
				population.getRoutes().get((int) (Math.random() * population.getRoutes().size()))));
		tournamentPopulation.sortRouteByFitness();
		return tournamentPopulation;
	}
}
