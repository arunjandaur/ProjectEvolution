package evolution;

import java.util.ArrayList;
import java.util.Random;

public class Society
{
	public static ArrayList<Human> maleList = new ArrayList<Human>(); //list of all males in society
	public static ArrayList<Human> femaleList = new ArrayList<Human>(); //list of all females in society
	public static ArrayList<Human> freeMaleList = (ArrayList<Human>) maleList.clone(); //list of all unmated males each year
	public static ArrayList<Human> freeFemaleList = (ArrayList<Human>) femaleList.clone(); //list of all unmated females each year
	public static ArrayList<Human> deaths = new ArrayList<Human>(); //the list of Human deaths by disaster, not old age
	//stats: avgAge, male/female ratio, ranked AlleleSets' values
	public static ArrayList<AlleleSet> stereotypes = new ArrayList<AlleleSet>(); //stereotypes of the society used to judge good and bad characteristics
	public static int currentYear;
	public static Random rand = new Random();
	/*
	 * give birth to a new Human
	 * @param gender the gender of the new Human
	 * @param childGenome the genome of the new child
	 */
	public static void birth(int gender, ArrayList<Gene> childGenome)
	{
		if (gender==0) maleList.add(new Human("SomebodyM" + rand.nextInt(500), childGenome, "Male")); /////ERORORROORROROROROOO!
		else femaleList.add(new Human("SomebodyF" + rand.nextInt(500), childGenome, "Female"));
	}
	public static ArrayList<Human> getOppositeGenderList(String gender)
	{
		if (gender.equals("Male")) return femaleList;
		else return maleList;
	}
	/*
	 * Stereotypes are generated and reevaluated each year. Alleles of dead are sorted into groups that appear in common, and rated based on how often they come up. 
	 * This method must deal with old stereotypes that have already been generated and reevaluate them each year after new deaths happen. 
	 * 
	 */
	public static void evaluateStereotypes()
	{
		double societalValue = 0;
		ArrayList<Allele> alleles = new ArrayList<Allele>();
		for (int i = 0; i < deaths.size(); i++)
		{
			//only works for one allele diseases
			for (int j = 0; j < deaths.get(i).getGenome().size(); j++)
			{
				Allele[] alleleTypes = deaths.get(i).getGenome().get(j).getAlleleTypes();
				for(int k = 0; k < alleleTypes.length; k++)
				{
					alleles.add(alleleTypes[k]); // block of alleles
				}
			}
		}
		AlleleSet a = new AlleleSet(alleles, societalValue);
	}
	/*
	 * perform matchmaking process. Gets a list of couples and ranks them via highest compatibility, then moves down ArrayList<Couple> to perform matches,
	 * checks if they are still on freeMaleList or freeFemaleList. If so, they mate, else they do not and move on.
	 */
	public static void match()
	{
		
		ArrayList<Couple> allCoupleCombos = new ArrayList<Couple>();
		freeMaleList = (ArrayList<Human>) maleList.clone();
		freeFemaleList = (ArrayList<Human>) femaleList.clone();
		for (int i = 0; i < freeMaleList.size(); i++)
		{
			
			allCoupleCombos.addAll(freeMaleList.get(i).submitCouples());
		}
		
		for(int i = 0; i < allCoupleCombos.size(); i++)
		{
			int count = i;
			while(count > 0 && allCoupleCombos.get(count).getCompat() > allCoupleCombos.get(count-1).getCompat())
			{
				Couple temp = allCoupleCombos.get(count);
				allCoupleCombos.remove(count);
				allCoupleCombos.add(count-1, temp);
				count--;
			}
		}
		//System.out.println("allCoupleCombos: " + allCoupleCombos.size());
		for (int i = 0; i < allCoupleCombos.size(); i++)
		{
			//System.out.println("beforepair");
			if(freeMaleList.contains(allCoupleCombos.get(i).getMale()) && freeFemaleList.contains(allCoupleCombos.get(i).getFemale())) //PROBLEM HERE
			{
				//System.out.println("matched");
				allCoupleCombos.get(i).getFemale().mate(allCoupleCombos.get(i).getMale());//PROBLEM HERE
				freeFemaleList.remove(allCoupleCombos.get(i).getFemale());
				freeMaleList.remove(allCoupleCombos.get(i).getMale());
			}
		}
	}	
}