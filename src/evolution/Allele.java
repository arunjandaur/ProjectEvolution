package evolution;

public class Allele
{
	private String name = ""; //Name of the allele
	private String geneName = ""; //Name of the gene that this allele belongs to
	
	/*
	 * constructor
	 * @param name: name of the allele
	 * @param geneName: name of the gene that this allele belongs to
	 */
	public Allele(String name, String geneName)
	{
		this.name = name;
		this.geneName = geneName;
	}
	
	/*
	 * @return name of the allele
	 */
	public String getName()
	{
		return name;
	}
	
	/*
	 * @return name of the gene
	 */
	public String getGeneName()
	{
		return geneName;
	}
}