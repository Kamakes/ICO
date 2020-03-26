import java.util.ArrayList;

public class SolutionTabou {

	private final int tailleTabou;
	private Route [] tabou;
	private Route bestPath;
	private Route currentPath;
	private int compteur;
	
	
	public SolutionTabou (ArrayList<City> cities, int tailleTabou) {
		this.tailleTabou = tailleTabou;
		this.tabou = new Route [this.tailleTabou];
		this.currentPath = new Route (cities);
		this.bestPath = new Route (currentPath);
		this.compteur = 0;
	}
	
	public Route getBestPath() {
		return this.bestPath;
	}

	
	public void optimiserTabou () {
		int i = 0;
		while (this.compteur < 50000 && i<1000000) {
			this.actualiserTabou(currentPath);
			this.actualiserChemin(currentPath);
			i++;
		}
	}
	
	
	private void actualiserTabou (Route currentPath) {
		for (int i=this.tailleTabou-1 ; i>0 ; i--) {
			this.tabou[i]=this.tabou[i-1];
		}
		this.tabou[0]=currentPath;
	}
	
	private ArrayList<Route> creerVoisins (Route currentPath) {
		ArrayList<Route> voisins = new ArrayList<Route>();
		for (int i=1 ; i<currentPath.getCities().size() ; i++) {
			if (!this.existe(ajouterVoisin(currentPath, i), this.tabou)) {
				voisins.add(ajouterVoisin(currentPath, i));
			}
		}
		return voisins;
	}
	
	private Route ajouterVoisin (Route currentPath, int i) {
		Route voisin = new Route(currentPath);
		City decalage = voisin.getCities().get(0);
		voisin.getCities().set(0, voisin.getCities().get(i));
		voisin.getCities().set(i, decalage);
		return voisin;
	}
		
	private void actualiserChemin (Route currentPath) {
		ArrayList<Route> voisins = this.creerVoisins(currentPath);
		double currentDistance = voisins.get(0).getTotalDistance();
		for (Route test : voisins) {
			if (test.getTotalDistance()<this.bestPath.getTotalDistance()) {
				this.bestPath = test;
				this.compteur = 0;
			}
			if (test.getTotalDistance()<=currentDistance) {
				currentDistance = test.getTotalDistance();
				this.currentPath = test;
			}
		}
		this.compteur ++;
	}
	
	private boolean existe (Route test, Route[] tabou) {
		for (int i=0 ; i<tabou.length-1 ; i++) {
			if (tabou[i]!=null && test.toString().equals(tabou[i].toString())) {
				return true;
			}
		}
		return false;
	}
	
}


