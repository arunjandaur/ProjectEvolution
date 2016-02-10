package evolution;

import java.util.ArrayList;
import java.util.Random;

public class Human 
{
	protected int age;
	protected ArrayList<Gene> genome = new ArrayList<Gene>();
	protected boolean taken;
	protected String gender;
	protected String name;
	public ArrayList<Double> potentialMatesValue = new ArrayList<Double>();
	public ArrayList<Human> potentialMates = new ArrayList<Human>(); 

	public Human(String name, ArrayList<Gene> genome, String gender)
	{
		this.genome.addAll(genome);
		age = 0;
		this.name = name;
		taken = false;
		this.gender = gender;
	}
	public String getName()
	{
		return name;
	}

	@Override
	public String toString()
	{
		return getName();
	}
	public int getAge()
	{
		return age;
	}
	public void increaseAge()
	{
		age++;
	}
	public String getGender()
	{
		return gender;
	}
	public ArrayList<Gene> getGenome()
	{
		return genome;
	}

	/*
	 * rates the list of potentialMates from highest potentialMatesValue to lowest potentialMatesValue, populates potentialMatesValue with values
	 */
	public void rate()
	{
		potentialMatesValue = new ArrayList<Double>();
		potentialMates = new ArrayList<Human>(); //Society.getOppositeGenderList(gender)
		//System.out.println("rate test: " + Society.femaleList.size() + "/" + Society.maleList.size());
		for (int i = 0; i < Society.getOppositeGenderList(gender).size(); i++)
		{
			potentialMates.add(Society.getOppositeGenderList(gender).get(i));
		}

		//if (gender==0) potentialMates.addAll(Society.femaleList);
		//else if (gender==1) potentialMates.addAll(Society.maleList);
		//System.out.println("potentialMates:" + potentialMates.size());
		/*
		 * Who wrote this method, and what is it for?
		 */
		/*
		for(int i = 0; i < potentialMates.size(); i++)
		{
			System.out.println("potentialMates2: " + potentialMates.size());
			int count = i;

			while(count > 0 && potentialMates.get(count).findCompatibility(this) > potentialMates.get(count-1).findCompatibility(this))
			{
				Human temp = potentialMates.get(count);
				potentialMates.remove(count);
				potentialMates.add(count-1, temp);
				count--;
			}
		}*/
		for(int i = 0; i < potentialMates.size(); i++)
		{
			Human h = potentialMates.get(i);
			potentialMatesValue.add(h.findCompatibility(this));
		}
		//System.out.println("potentialMatesValue: " + potentialMatesValue.size());
	}

	/*
	 * finds the compatibility of this Human and potentialMate
	 * @param human the potentialMate
	 * @return compatibility as a double
	 */
	public double findCompatibility(Human human)
	{
		//Arun's version below.
		double cumulativeStereotypesProb = 0.0;

		ArrayList<Gene> potentialMatesGenome = human.getGenome();
		ArrayList<Gene> combinedGenome = new ArrayList<Gene>();
		ArrayList<String> combinedGenomeGeneNames = new ArrayList<String>();
		
		for(int i = 0; i < genome.size(); i++)
		{
			combinedGenome.add(genome.get(i));
			combinedGenomeGeneNames.add(genome.get(i).getTraitName());
		}

		for(int i = 0; i < potentialMatesGenome.size(); i++)
		{
			if(combinedGenomeGeneNames.contains(potentialMatesGenome.get(i).getTraitName()))
			{
				int index = combinedGenomeGeneNames.indexOf(potentialMatesGenome.get(i).getTraitName());
				Gene combinedGene = combinedGenome.get(index).combineGene(potentialMatesGenome.get(i));
				combinedGenome.remove(index);
				combinedGenome.add(combinedGene);
			}
			else
			{
				combinedGenome.add(potentialMatesGenome.get(i));
				combinedGenomeGeneNames.add(potentialMatesGenome.get(i).getTraitName());
			}
		}

		for (int s = 0; s < Society.stereotypes.size(); s++)
		{
			double alleleWeightTotalProb = 0.0;
			for (int al = 0; al < Society.stereotypes.get(s).alleleList.size(); al++)
			{
				for(int k = 0; k < combinedGenome.size(); k++)
				{
					Gene currentGene = combinedGenome.get(k);
					if(currentGene.hasAllele(Society.stereotypes.get(s).alleleList.get(al)))
					{
						alleleWeightTotalProb += currentGene.getAllelePercent(Society.stereotypes.get(s).alleleList.get(al).getName());
						break;
					}
				}
			}
			int numDisasters = Evolution.getDisasterQuantity(); // initialize properly
			double prob1 = (alleleWeightTotalProb / Society.stereotypes.get(s).alleleList.size()) / ((double) numDisasters); //careful with division here
			cumulativeStereotypesProb += prob1;
		}
		return (1 - cumulativeStereotypesProb) * 100;
		//Arun's version above.

//		double compatibility = 0;
//		double societalValue = 0;
//		double probability = 0;
//		for (int pm = 0; pm < potentialMates.size(); pm++)
//		{
//			ArrayList<Gene> g = potentialMates.get(pm).getGenome();
//
//			for(int i = 0; i < genome.size(); i++) //merges genomes of this human and potentialMate
//			{
//				if(g.contains(genome.get(i)))
//				{
//					g.get(genome.indexOf(genome.get(i))).combineGene(genome.get(i)); //I have no idea what's going on here, but I tired to adapt it to the new code.
//				}
//				else
//				{
//					g.add(genome.get(i));
//				}
//			}
//
//			for (int s = 0; s < Society.stereotypes.size(); s++)
//			{
//				int match = 0;
//
//				for (int al = 0; al < Society.stereotypes.get(s).alleleList.size(); al++)
//				{
//					for (int i = 0; i < g.size(); i++)
//						if (g.get(i).hasAllele(Society.stereotypes.get(s).alleleList.get(al))) 
//							match++;
//				}
//				if (match==Society.stereotypes.get(s).alleleList.size()) 
//				{
//					for (int i = 0; i < Society.stereotypes.get(s).alleleList.size(); i++)
//						probability*=g.get(i).getAllelePercent(g.get(g.indexOf(Society.stereotypes.get(s).alleleList.get(i))).traitName);
//				}
//				compatibility+=Society.stereotypes.get(s).getValue() * probability;
//			}
//		}
//		return compatibility;
	}

	/*
	 * called from society to submit couples to the matchmaking process
	 * @return list of Couples ranked from highest to lowest compatibility 
	 */
	public ArrayList<Couple> submitCouples()
	{
		//System.out.println("testsubmitCouples");
		rate();
		ArrayList<Couple> couples = new ArrayList<Couple>();
		for(int i = 0; i < potentialMates.size(); i++)
		{
			//System.out.println("potentialMates.get(i): " + potentialMates.get(i));
			//System.out.println("potentialMatesValue.get(i): " + potentialMatesValue.get(i));
			Couple couple = new Couple(this, potentialMates.get(i), potentialMatesValue.get(i) + 0.5);
			couples.add(couple);
		}
		//System.out.println("couples: " + couples.size());
		return couples;
	}
	/*
	 * Mates the male and the female, and creates a child.
	 * Female must call this method, male cannot.
	 * @param male a Human male to mate with
	 */
	public void mate(Human male)
	{
		//System.out.print("mate");
		Random rand = new Random();
		ArrayList<Gene> childGenome = new ArrayList<Gene>();
		ArrayList<String> childGenomeNames = new ArrayList<String>();

		for(int i = 0; i < getGenome().size(); i++)
		{
			childGenome.add(getGenome().get(i));
			childGenomeNames.add(getGenome().get(i).getTraitName());
		}
		
		for(int i = 0; i < male.getGenome().size(); i++)
		{
			//System.out.print("works?");
			//System.out.println(male.getGenome().size() - childGenome.size());
			int index = childGenomeNames.indexOf(male.getGenome().get(i).getTraitName());
			Gene combinedGene = childGenome.get(index).combineGene(male.getGenome().get(i));
			childGenome.remove(index);
			childGenome.add(combinedGene);
		}
		//System.out.println("BIRTH");
		Society.birth(rand.nextInt(2), childGenome);
	}
}