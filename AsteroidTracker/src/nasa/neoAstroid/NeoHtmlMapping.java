package nasa.neoAstroid;

public class NeoHtmlMapping {

	
	public enum ImpactRisk {
		table(2), ObjectDesignation(1), YearRange(2), PotentialImpacts(3), 
		ImpactProb(4), VInfinity(5), Hmag(6), EstDiameter(7),
		palermoScaleMin(8), palermoScaleMax(9), TorinoScale(10);
        private int value;

        private ImpactRisk(int value) {
                this.value = value;
        }
};   	
}



