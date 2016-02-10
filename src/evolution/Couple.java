package evolution;

public class Couple
{
	Human male;
	Human female;
	double compat;

	public Couple(Human human1, Human human2, Double double1)
	{
		if (human1.getGender().equals("Male"))
		{
			this.male = human1;
			this.female = human2;
		}
		else
		{
			this.male = human2;
			this.female = human1;
		}
		this.compat = double1;
	}

	public Human getMale()
	{
		return male;
	}

	public Human getFemale()
	{
		return female;
	}

	public double getCompat()
	{
		return compat;
	}
}
