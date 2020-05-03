package Algorithmes;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class SolutionAG {
	// Constantes
	public static final double MUTATION_PROBABILITY = 0.25;
	public static final int TOURNAMENT_SELECTION_SIZE = 2;
	public static int POPULATION_Size = 8;
	public static final int NUM_ELITE_ROUTES = 1;
	public static int NUM_GENERATIONS = 2400 ;
	// attributs
	private ArrayList<City> initialRoute = null;
	private static double time_taken ; // Ajout d'une variable pour déterminer le temps d'exécution de l'algorithm AG.  

	// Constructeurs
	public SolutionAG(ArrayList<City> initialRoute) {
		this.initialRoute = initialRoute;
	}

	// Getter
	public ArrayList<City> getInitialRoute() {
		return initialRoute;
	}

	// methodes
	public Population evolve(Population population) {
		long startTime = System.nanoTime(); 
		Population p = mutationPopulation(croisementPopulation(population));
		this.time_taken = System.nanoTime() - startTime ; // Temps d'exécution d'une évolution. 
		return p;
	}

	// croisement d'une population
	Population croisementPopulation(Population population) {
		Population croisementPopulation = new Population(population.getRoutes().size(), this);//initialisation d'une popultion des enfants
		IntStream.range(0, NUM_ELITE_ROUTES)
				.forEach(x -> croisementPopulation.getRoutes().set(x, population.getRoutes().get(x)));
		IntStream.range(NUM_ELITE_ROUTES, croisementPopulation.getRoutes().size()).forEach(x -> {
			Route route1 = selectTournamentPopulation(population).getRoutes().get(0);//On prend la meilleure route parmi deux qui ont été choisi au hazard de la population
			Route route2 = selectTournamentPopulation(population).getRoutes().get(0);//on refait la même chose
			croisementPopulation.getRoutes().set(x, croisementRoute(route1, route2));
		});
		return croisementPopulation;
	}

	Population mutationPopulation(Population population) {
		population.getRoutes().parallelStream().filter(x -> population.getRoutes().indexOf(x) >= NUM_ELITE_ROUTES)
				.forEach(x -> mutationRoute(x)); //On effectue une mutation de toutes les routes à part l'élite
		return population;
	}

	
	//la fonction prend en argument deux routes et retourne une route, la position du croisement ici est aléatoire
	Route croisementRoute(Route route1, Route route2) {
		Route croisementRoute = new Route(this);//initialiser une route
		Route tempRoute1 = route1;
		Route tempRoute2 = route2;
		if (Math.random() < 0.5) {//croisement aléatoire
			tempRoute1 = tempRoute2;
			tempRoute2 = tempRoute1;
		}
		for (int x = 0; x < croisementRoute.getCities().size() / 2; x++)
			croisementRoute.getCities().set(x, tempRoute1.getCities().get(x));

		return IntermediaireCroisementRoute(croisementRoute, tempRoute2);
	}

	
	/*pour éviter les doublons on crée cette fonction qui filtre la route des villes de la route "croisementRoute"
	et ajoute les villes de la route 2 */
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

	//la fonction mutationRoute choisi deux villes au hazard et les change
	Route mutationRoute(Route route) {
		route.getCities().stream().filter(x -> Math.random() < MUTATION_PROBABILITY).forEach(cityX -> {
			int y = (int) (route.getCities().size() * Math.random());
			City cityY = route.getCities().get(y);
			route.getCities().set(route.getCities().indexOf(cityX), cityY);
			route.getCities().set(y, cityX);

		});
		return route;
	}
	
	
	
/*On prend au hazard un nombre de route de la population , ici on a pris 2 : TOURNAMENT_SELECTION_SIZE=2
	et on les trie */
	Population selectTournamentPopulation(Population population) {
		Population tournamentPopulation = new Population(TOURNAMENT_SELECTION_SIZE, this);//initialisation d'une population
		IntStream.range(0, TOURNAMENT_SELECTION_SIZE).forEach(x -> tournamentPopulation.getRoutes().set(x,
				population.getRoutes().get((int) (Math.random() * population.getRoutes().size()))));
		tournamentPopulation.sortRouteByFitness();
		return tournamentPopulation;
	}

	public static double getTime_taken() {
		return time_taken;
	}

	public static void setTime_taken(double time_taken) {
		SolutionAG.time_taken = time_taken;
	}

}
