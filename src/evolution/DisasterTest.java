package evolution;

import java.util.ArrayList;

public class DisasterTest
{
	public static void main(String [] args)
	{	
		double count = 0;
		double timesLived = 0;
		
		while (count < 10000)
		{
			//Initialize gene pool

			//Society's alleles:
			Allele fast = new Allele("Fast Runner", "Running Speed");
			Allele slow = new Allele("Slow Runner", "Running Speed");
			Allele strong = new Allele("Strong", "Strength");
			Allele weak = new Allele("Weak", "Strength");

			//Robert
			Gene robertRunningSpeed = new Gene("Running Speed", new Allele[] {fast, slow}, new double[] {0.5, 0.5});
			Gene robertStrength = new Gene("Strength", new Allele[] {strong, weak}, new double[] {0.5, 0.5});
			ArrayList<Gene> robertGenome = new ArrayList<Gene>();
			robertGenome.add(robertRunningSpeed);
			robertGenome.add(robertStrength);
			Human robert = new Human("Robert", robertGenome, "Male");

			ArrayList<Human> humans = new ArrayList<Human>();
			humans.add(robert);

			Allele[] targetAlleles = {new Allele("Slow Runner", "Running Speed")};
			Disaster mountainLions = new Disaster(.80, targetAlleles);

			ArrayList<Human> survivors = mountainLions.unleash(humans);
			timesLived += survivors.size();
			
			count++;
		}
		System.out.println(timesLived / count); //Survival rate
	}
}