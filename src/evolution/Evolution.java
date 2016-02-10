package evolution;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Evolution
{
	int endYear; //total number of years to evolve
	static Disaster[] disasters; //a list of disasters to use
	Timer timer; //timer to step through each year
	int runInterval; //interval between evolutions in milliseconds

	public Evolution(int interval, int timeSpan, ArrayList<Gene> genome, int malesToGenerate, int femalesToGenerate, int numDisasters)
	{
		Society.currentYear = 0;
		endYear = timeSpan;
		runInterval = interval;
		Random rand = new Random();
		disasters = new Disaster[numDisasters];
		timer = new Timer();
		ArrayList<Gene> humanGenome = new ArrayList<Gene>();

		for (int i = 0; i < malesToGenerate; i++)
		{
			for(int j = genome.size()-1; j >= 0; j--)
			{
				humanGenome.add(setPercent(genome.get(j), 0, 1.0));
			}
			Society.maleList.add(new Human("M" + i, humanGenome, "Male"));
		}
		for (int i = 0; i < femalesToGenerate; i++)
		{
			for(int j = genome.size()-1; j >= 0; j--)
			{
				humanGenome.add(setPercent(genome.get(j), 0, 1.0));
			}
			Society.femaleList.add(new Human("F" + i, humanGenome, "Female"));
		}
		for (int i = 0; i < disasters.length; i++)
		{
			Gene targetGene = genome.get(rand.nextInt(genome.size()));
			Allele[] targetAllele = targetGene.getAlleleTypes();
			int a = rand.nextInt(targetAllele.length);
			String alleleName = targetAllele[a].getName();
			String geneN = targetAllele[a].getGeneName();
			Allele[] targetAlleles = {new Allele(alleleName, geneN)};
			System.out.println("allele Name + gene name: " + alleleName + ", " + geneN);
			disasters[i] = new Disaster(0.1 + 0.9*rand.nextDouble(), targetAlleles);//do i put in 1.0 or 100?
		}
		for (int x = 0; x < disasters.length; x++)
		{
			System.out.println(x + ": " + disasters[x].targetAlleles[0].getName() + ", " + disasters[x].targetAlleles[0].getGeneName());
		}
		
		/*//ArrayList<Gene> genome = new ArrayList<Gene>(); //genome of new Human
		Allele[] alleleList = {new Allele("Fast", "Running Speed"), new Allele("Slow", "Running Speed")}; //alleles in genes of genome
		double[] percentages = {.5, .5};
		genome.add(new Gene("Running Speed", alleleList, percentages));
		Society.maleList.add(new Human("Adam", genome, "Male"));
		System.out.println("added");
		Society.femaleList.add(new Human("Eve", genome, "Female"));*/

		timer.schedule(new Time(), 1000, runInterval);



		//		//Michael, generates disasters here:
		//		//Begin
		//		
		//		//End
		//
		//		for (int i = 0; i < malesToGenerate; i++)
		//		{
		//			ArrayList<Gene> genome = new ArrayList<Gene>();
		//			
		//			Human temp = new Human("MaleSomebody" + i, genome, "Male");
		//		}	
	}
	/*
	 * Sets the percentage of each allele's weight. 
	 * @param g the gene that needs arranging
	 * @param index the index of the allele (always enter 0)
	 * @param percent the percent to assign to the entirety of the gene (always enter 1.0)
	 */
	public Gene setPercent(Gene g, int index, double percent)
	{
		Random rand = new Random();
		if (index>=g.getAlleleTypes().length-1) 
		{
			//System.out.print(percent);
			g.setPercentage(percent, index);
			return g;
		}
		else
		{
			double thisPercent = percent-rand.nextDouble();
			g.setPercentage(thisPercent, index);
			setPercent(g, ++index, thisPercent);
		}
		return g;
	}
	public static int getDisasterQuantity()
	{
		return disasters.length;
	}

	class Time extends TimerTask
	{
		@Override
		public void run()
		{

			if(Society.currentYear < endYear)
			{

				evolve();
				Main.f.repaint();

			}
			else
			{
				System.out.println("THE END");
				timer.cancel();
			}
			Society.currentYear++;
		}
	}

	/*
	 * task performed by Timer, cycle of human behavior, including mating and deaths by age and disaster
	 */
	public void evolve()
	{

		/*		for(int i = Society.maleList.size() - 1; i >= 0; i--)
		{
			if(Society.maleList.get(i).getAge() >= 50)
			{
				Society.maleList.remove(i);
			}
		}

		for(int i = Society.femaleList.size() - 1; i >= 0; i--)
		{
			if(Society.femaleList.get(i).getAge() >= 50)
			{
				Society.femaleList.remove(i);
			}
		}*/

		ArrayList<Human> femaleSurvivors = (ArrayList<Human>) Society.femaleList.clone();
		ArrayList<Human> maleSurvivors = (ArrayList<Human>) Society.maleList.clone();

		for(int i = 0; i < disasters.length; i++)
		{
			//System.out.print("disaster");
			maleSurvivors = disasters[i].unleash(Society.maleList);
			femaleSurvivors = disasters[i].unleash(Society.femaleList);
		}

		Society.maleList = maleSurvivors;
		Society.femaleList = femaleSurvivors;

		System.out.println(Society.deaths.size());

		if (disasters.length > 0)
		{
			Society.evaluateStereotypes();
		}
		Society.match();
	}
}
