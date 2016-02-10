package evolution;

import java.util.ArrayList;

/* Restructure computations: gene has list of all possible alleles and percentages (in decimals), no merging of numbers. 
 * other things to change: change how disaster functions: old age disaster
 * find a way to generate random set of initialized disasters
 * 
 * finish for loop / initialization of humans
*/
public class Gene
{
	String traitName;
	private Allele[] alleleTypes;
	private double[] percentages;
	
	/*
	 * Constructor
	 * @param traitName the name of the allele
	 * @param alleleTypes a list of the different types alleles contained by the gene
	 * @param percentages a list of percentages that indicate the allele distribution of a gene
	 */
	public Gene(String traitName, Allele[] alleleTypes, double[] percentages) //The Allele at index i of alleleTypes corresponds to the index i of percentages.
	{
		this.traitName = traitName;
		this.alleleTypes = new Allele[alleleTypes.length];
		this.percentages = new double[percentages.length];
		for (int i = 0; i < alleleTypes.length; i++)
		{
			this.alleleTypes[i] = alleleTypes[i];
		}
		this.percentages = new double[percentages.length];
		for (int i = 0; i < percentages.length; i++)
		{
			this.percentages[i] = percentages[i];
		}
	}
	
	public Gene combineGene(Gene gene) //Returns a new Gene that is the combination of the contents of this gene and the inputed gene
	{
		int newLength = alleleTypes.length + gene.getAlleleTypes().length;
		Allele[] newAlleleTypes = new Allele[newLength];
		double[] newPercentages = new double[newLength];
		
		//The following 2 for loops add together all the allele types of the 2 genes.
		for (int i = 0; i < alleleTypes.length; i++)
		{
			newAlleleTypes[i] = alleleTypes[i];
			newPercentages[i] = percentages[i];
		}
		
		for (int i = alleleTypes.length; i < gene.getAlleleTypes().length + alleleTypes.length; i++)
		{
			newAlleleTypes[i] = gene.getAlleleTypes()[i-alleleTypes.length];
			newPercentages[i] = gene.getPercentages()[i-alleleTypes.length];
		}
		
		ArrayList<Allele> finalAlleleTypes = new ArrayList<Allele>();
		ArrayList<String> finalAlleleTypesNames = new ArrayList<String>();
		ArrayList<Double> finalPercentages = new ArrayList<Double>(); //Capital D?
		//This goes through the master list of allele types and checks and filters repeats. It checks for repeats by checking if the names of the
		//allele types have been logged already.
		for(int i = 0; i < newAlleleTypes.length; i++)
		{
			//System.out.println("HIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII" + newAlleleTypes[i]);
			if(finalAlleleTypesNames.contains(newAlleleTypes[i].getName()))
			{
				int index = finalAlleleTypesNames.indexOf(newAlleleTypes[i].getName());
				double prevPercent = finalPercentages.get(index);
				double newPercent = (prevPercent + newPercentages[i]) / 2.0; // Combine percents here.
				finalPercentages.set(index, newPercent);
			}
			else
			{
				finalAlleleTypes.add(newAlleleTypes[i]);
				finalAlleleTypesNames.add(newAlleleTypes[i].getName());
				finalPercentages.add(newPercentages[i]);
			}
		}
		
		Allele[] returnedAlleleTypes = new Allele[finalAlleleTypes.size()];
		double[] returnedNewPercents = new double[finalPercentages.size()];
		
		for(int i = 0; i < finalAlleleTypes.size(); i++)
		{
			returnedAlleleTypes[i] = finalAlleleTypes.get(i);
		}
		for (int i = 0; i < finalPercentages.size(); i++)
		{
			returnedNewPercents[i] = finalPercentages.get(i);
		}
		return new Gene(traitName, returnedAlleleTypes, returnedNewPercents);
	}
	
	/*
	 * checks to see if an allele is present
	 * @param allele the allele that may or may not be in the Gene
	 * @return whether the allele is in the Gene
	 */
	public boolean hasAllele(Allele allele)
	{
		for(int i = 0; i < alleleTypes.length; i++)
		{
			if(allele.getName().equals(alleleTypes[i].getName()))
			{
				return true;
			}
		}
		return false;
	}
	
	/*
	 * gets percent weight of an allele
	 * @param name of allele
	 * @return percent weight of allele
	 */
	public double getAllelePercent(String name) //Uses the name of the allele to obtain its index in numAlleles and then use that index to obtain the percentage
	{
		int index = 0;
		for(int i = 0; i < alleleTypes.length; i++)
		{
			if (alleleTypes[i].equals(name))
			{
				index = i;
				break;
			}
		}
		return percentages[index];
	}
	
	public String getTraitName()
	{
		return traitName;
	}
	
	/*
	 * gets the types of alleles in a gene
	 */
	public Allele[] getAlleleTypes()
	{
		return alleleTypes;
	}
	public void setPercentage(double percent, int index)
	{
		percentages[index] = percent;
	}
	
	public double[] getPercentages()
	{
		return percentages;
	}
}