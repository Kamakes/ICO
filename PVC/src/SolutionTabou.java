import java.util.ArrayList;
import java.util.Random;

public class SolutionTabou {

	private final int tailleTabou;
	private Route [] tabou;
	private Route bestPath;
	private Route currentPath;
	private final int nbIterations;
	
	
	//Constructeur
	
	public SolutionTabou (ArrayList<City> cities, int tailleTabou, int nbIterations) {
		this.tailleTabou = tailleTabou;
		this.tabou = new Route [this.tailleTabou]; //le tabou est initialement uniquement constitu� d'�l�ments "null"
		this.currentPath = new Route (cities); //currentPath est une route cr��e � partir du m�lange al�atoire des villes de la liste cities
		this.bestPath = new Route (currentPath); //bestPath est une copie de la route currentPath (m�me ordre des villes)
		this.nbIterations = nbIterations;
	}
	
	
	
	//Getter
	
	public Route getBestPath() {
		return this.bestPath;
	}

	
	
	//Proc�dure publique
	
	public void optimiserTabou () { 
		for (int i=0 ; i<this.nbIterations ; i++) { //on effectue la recherche sur un nombre d'it�rations d�fini
			//nbIterations sert de condition d'arr�t pour l'algorithme de recherche
			this.actualiserTabou(); //ajout du currentPath au d�but du tabou, l'�l�ment le plus ancien disparait
			this.actualiserChemin(); //on actualise le currentPath, et potentiellement le bestPath
		}
	}
	
	
	
	//M�thodes priv�es
	
	private void actualiserTabou () {
		for (int i=this.tailleTabou-1 ; i>0 ; i--) { //chaque �l�ment est d�cal� 
				this.tabou[i]=this.tabou[i-1];       //vers le bout de la liste
		}
		this.tabou[0]=this.currentPath; //la solution courante est ajout�e au tabou
	}
	
	
	private Route ajouterVoisin (int i, int k) {
		Route voisin = new Route(this.currentPath); //copie de currentPath destin�e � devenir un voisin
		int n = this.currentPath.getCities().size();
		if (i+k < n) { //si il existe une ville i+k dans la route
			City memoire = voisin.getCities().get(i+k);            //on �change
			voisin.getCities().set(i+k, voisin.getCities().get(i)); //les villes
			voisin.getCities().set(i, memoire);                    //i et i+k
		}
		else { //si i+k est plus grand que la longueur de la liste (la ville i+k n'existe pas)
			City memoire = voisin.getCities().get(i+k-n);            //on �change
			voisin.getCities().set(i+k-n, voisin.getCities().get(i)); //les villes
			voisin.getCities().set(i, memoire);                      //i et i+k-n
		}
		return voisin;
	}
		
	
	//on v�rifie si la route test appartient � la liste tabou de l'objet
	private boolean existe (Route test) {
		for (int i=0 ; i<this.tailleTabou ; i++) {
			if (this.tabou[i]!=null && test.toString().equals(this.tabou[i].toString())) {
				return true;
			}
		}
		return false;
	}
	
	
	private ArrayList<Route> creerVoisins () {
		ArrayList<Route> voisins = new ArrayList<Route>(); //ArrayList de route destin� � acceuillir les voisins du currentPath
		int n = this.currentPath.getCities().size();
		Random rand = new Random();    //on cr�e un entier k al�atoire compris entre 1 et n-1 afin de permuter une ville 
		int k = rand.nextInt(n-1) + 1; //avec celle situ�e k rangs plus loins dans la route lors de la cr�aton de voisins
		for (int i=0 ; i<n ; i++) {  //on cr�e autant de voisins potentiels que le nombre de villes que contient la route
			Route newVoisin = ajouterVoisin(i, k);
			if (!this.existe(newVoisin)) { //un voisin potentiel est effectivement ajout� 
				voisins.add(newVoisin);    //� la liste des voisins si il n'est pas tabou
			}
		}
		return voisins;
	}
	
		
	private void actualiserChemin () {
		ArrayList<Route> voisins = this.creerVoisins(); //on cr�e les voisins du currentPath
		double newCurrentDistance = voisins.get(0).getTotalDistance();
		for (Route test : voisins) { //on it�re sur tous le voisins
			double distanceTest = test.getTotalDistance();
			if (distanceTest < this.bestPath.getTotalDistance()) { //si un voisin est le meilleur chemin test� depuis
				this.bestPath = test;                              //le d�but de la recherche, on update le bestPath
			}
			if (distanceTest <= newCurrentDistance) { //Si un voisin est meilleurs que les autres voisins 
				newCurrentDistance = distanceTest;    //d�j� test�s au cours de cette it�ration, on update 
				this.currentPath = test;              //le currentPath en vue de l'it�ration suivante
			}
		}
	}

}


