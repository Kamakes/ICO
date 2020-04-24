import java.util.ArrayList;
import java.util.Random;

public class SolutionTabou {

	private final int tailleTabou;
	private Route [] tabou;
	private Route bestPath;
	private Route currentPath;
	private int nbIterations;
	
	
	public SolutionTabou (ArrayList<City> cities, int tailleTabou, int nbIterations) {
		this.tailleTabou = tailleTabou;
		this.tabou = new Route [this.tailleTabou];
		City defaut = new City ("null",0,0);
		ArrayList<City> defauts = new ArrayList<City>();
		defauts.add(defaut);
		for (int i=0 ; i<this.tailleTabou ; i++) {
			this.tabou[i] = new Route (defauts);
		}
		this.currentPath = new Route (cities);
		this.bestPath = new Route (currentPath);
		this.nbIterations = nbIterations;
	}
	
	public Route getBestPath() {
		return this.bestPath;
	}

	
	public void optimiserTabou () {
		for (int i=0 ; i < this.nbIterations ; i++) {
			//System.out.println("optimisation etape " + i);
			//System.out.println("best path" + this.bestPath.toString() + this.bestPath.getTotalDistance());
			//System.out.println("current path" + this.currentPath.toString() + this.currentPath.getTotalDistance());
			this.actualiserTabou(currentPath);
			this.actualiserChemin(currentPath);
		}
	}
	
	
	private void actualiserTabou (Route currentPath) {
		//System.out.println("actualisation tabou");
		for (int i=this.tailleTabou-1 ; i>0 ; i--) {
				this.tabou[i]=this.tabou[i-1];
		}
		this.tabou[0]=currentPath;
		//System.out.println("tabou " + this.tabou[0].toString());
	}
	
	private ArrayList<Route> creerVoisins (Route currentPath) {
		//System.out.println("creation voisins");
		ArrayList<Route> voisins = new ArrayList<Route>();
		int n = currentPath.getCities().size();
		Random rand = new Random();
		int k = rand.nextInt(n-1) + 1;
		//int k = 1;
		for (int i=0 ; i<n ; i++) {
			Route newVoisin = ajouterVoisin(currentPath, i, k);
			//System.out.println(this.existe(newVoisin, this.tabou));
			if (!this.existe(newVoisin, this.tabou)) {
				voisins.add(newVoisin);
				//System.out.println("liste de voisins" + voisins.toString());
			}
		}
		//System.out.println("voisins" + voisins.toString());
		return voisins;
	}
	
	private Route ajouterVoisin (Route currentPath, int i, int k) {
		//System.out.println("voisin n°" + i);
		Route voisin = new Route(currentPath);
		int n = currentPath.getCities().size();
		if (i+k < n) {
			City decalage = voisin.getCities().get(i+k);
			voisin.getCities().set(i+k, voisin.getCities().get(i));
			voisin.getCities().set(i, decalage);
		}
		else {
			City decalage = voisin.getCities().get(i+k-n);
			voisin.getCities().set(i+k-n, voisin.getCities().get(i));
			voisin.getCities().set(i, decalage);
		}
		//System.out.println("voisin " + i + " : " + voisin.toString() + voisin.getTotalDistance());
		return voisin;
	}
		
	private void actualiserChemin (Route currentPath) {
		//System.out.println("actualisation chemin");
		ArrayList<Route> voisins = this.creerVoisins(currentPath);
		double newCurrentDistance = voisins.get(0).getTotalDistance();
		for (Route test : voisins) {
			if (test.getTotalDistance() < this.bestPath.getTotalDistance()) {
				this.bestPath = test;
			}
			if (test.getTotalDistance() <= newCurrentDistance) {
				newCurrentDistance = test.getTotalDistance();
				this.currentPath = test;
			}
		}
		//System.out.println("new best path" + bestPath.toString() + bestPath.getTotalDistance());
		//System.out.println("new current path" + this.currentPath.toString() + this.currentPath.getTotalDistance());
	}
	
	private boolean existe (Route test, Route[] tabou) {
		for (int i=0 ; i<tabou.length ; i++) {
			//System.out.println("tabou n " + i + tabou[i].toString());
			//System.out.println("test" + test.toString());
			if (tabou[i]!=null && test.toString().equals(tabou[i].toString())) {
				return true;
			}
		}
		return false;
	}

}


