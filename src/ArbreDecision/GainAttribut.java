package ArbreDecision;

import java.util.Vector;

public class GainAttribut {

	// Constructeur
	public GainAttribut () {
		
	}
	
	//Donne le gain d'un vecteur
	public double gainAttribut(Vector data) {
		double gain = 0;
		double max = data.size();
		int nbr_apparrition_total = 0;
		int i = 0;
		double nbr_apparrition = 0;
		double proba = 0;
		
		while(nbr_apparrition_total<max){
			nbr_apparrition = 0;
			for(int j = 0; j<data.size(); j++) {
				if(((Integer)data.elementAt(j)).intValue() == i) {
						nbr_apparrition++;
						nbr_apparrition_total++;
					}
			}
			proba = nbr_apparrition/max;
			gain += -(proba)*Math.log(proba);
			i++;
		}
		//System.out.println("i = "+i+" Gain = "+gain);
		return gain;
	}
}
