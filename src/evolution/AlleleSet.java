package evolution;

import java.util.ArrayList;

public class AlleleSet 
{
	public ArrayList<Allele> alleleList = new ArrayList<Allele>(); //alleles associated with this set
	public double value; //value placed on this allele by society
	
	public AlleleSet(ArrayList<Allele> alleles, double societalValue)
	{
		alleleList.addAll(alleles);
		value = societalValue;
	}
	
	/*
	 * Accessor method
	 * @return alleleList
	 */
	public ArrayList<Allele> getAlleleList()
	{
		return alleleList;
	}
	
	/*
	 * Accessor method
	 * @return value 
	 */
	public double getValue()
	{
		return value;
	}
}