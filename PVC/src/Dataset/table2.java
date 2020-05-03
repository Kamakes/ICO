package Dataset;
public class table2 {

  public String Méthode;
  public double Configuration1;
  public double Configuration2;
  public double Durée;
  public double Distance;
  public double Critère;

  public table2(String Méthode, double MoyenneT, double EcartT, double DuréeT, double Distance,double critère) {
    this.Méthode = Méthode;
    this.Configuration1 = MoyenneT;
    this.Configuration2 = EcartT;
    this.Durée = DuréeT;
    this.Distance= Distance;
    this.Critère=critère;
    
  }


}