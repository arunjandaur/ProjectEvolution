package evolution;

import java.util.ArrayList;
import java.util.Random;

public class Disaster
{
	Allele[] targetAlleles;
	double killRate;

	/*
	 * constructor
	 * @param killRate killRate
	 * @param targetAlleles targetAlleles
	 */
	public Disaster(double killRate, Allele[] targetAlleles)
	{
		this.killRate = killRate;
		this.targetAlleles = targetAlleles;
	}

/*
 * unleashes the disaster on society
 * @param humans the list of all humans before disaster
 * Finds the death chance of a human by multiplying together all the allele percents of alleles that are in targetAlleles. It first multiplies together
 * allele percents that are in the same gene (which is called singleGeneDeathChance), then multiplies that total with the singleGeneDeathChance of all
 * the other genes that contain a targeted allele. This end product for any given human is called the cumulativeDeathChance. This is then multiplied
 * by the disaster's killRate. A random number is generated between 1 to 100, and if it is less than the cumulative * killRate * 100, then the human dies.
 * @return survivors after disaster
 */
	public ArrayList<Human> unleash(ArrayList<Human> humans)
	{
		ArrayList<Human> survivors = new ArrayList<Human>();
		Random ran = new Random();

		for(int i = 0; i < humans.size(); i++)
		{
			double cumulativeDeathChance = 1.0; //The death chance of a human without having factored in the kill rate of the disaster
			ArrayList<Gene> genome = humans.get(i).getGenome();
			for(int j = 0; j < genome.size(); j++) //Loop through each gene.
			{
				double singleGeneDeathChance = 1.0; //Somebody's death chance based on one gene
				for(int k = 0; k < targetAlleles.length; k++)
				{
					if(genome.get(j).hasAllele(targetAlleles[k]))
					{
						singleGeneDeathChance *= genome.get(j).getAllelePercent(targetAlleles[k].getName());
					}
				}
				cumulativeDeathChance *= singleGeneDeathChance;
			}
			int randomDeathChance = 1 + ran.nextInt(100);
			
			System.out.println("Left: " + randomDeathChance + " <= " + "Right: " + (killRate * cumulativeDeathChance * 100));
			
			if(randomDeathChance <= (killRate * cumulativeDeathChance * 100))
			{
				Society.deaths.add(humans.get(i));
			}
			else
			{
				survivors.add(humans.get(i));
			}
		}
		return survivors;
	}
}