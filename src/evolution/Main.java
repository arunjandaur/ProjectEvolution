package evolution;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main
{
	public static JFrame f;
	
	
	public static void main(String [] args)
	{
		class MouseClickListener implements MouseListener
		{
			public int timeSpan; //timeSpan in years
			public int interval; //milliseconds per year
			public int malesToGenerate;
			public int femalesToGenerate;
			public int numDisasters;
			public String[] geneList;
			public double[] percentages;
			Scanner in = new Scanner (System.in);
			@Override
			public void mouseClicked(MouseEvent m)
			{
				//System.out.println("clicked: (" + m.getX() + ", " + m.getY() + ")");
				if (m.getX()< 158 && m.getX() > 108 && m.getY() < 198 && m.getY() > 178)
				{
					/*
					 * Original! Don't delete
					 * System.out.print("Enter interval: ");
					interval = in.nextInt();
					System.out.print("Enter time span in years: ");
					timeSpan = in.nextInt();
					System.out.print("Number of males to generate: ");
					malesToGenerate = in.nextInt();
					System.out.print("Number of females to generate: ");
					femalesToGenerate = in.nextInt();
					System.out.print("Number of disasters: ");
					numDisasters = in.nextInt();
					System.out.print("How many genes do you want?: " );
					int genesNum = in.nextInt();
					ArrayList<Gene> genome = new ArrayList<Gene>();
					for(int i = 0; i < genesNum; i++)
					{
						
						System.out.print("Name of gene " + i + ": ");
						String geneName = in.next();
						System.out.print("Number of alleles: ");
						int allelesNum = in.nextInt();
						Allele[] alleles = new Allele[allelesNum];
						for (int j = 0; j < allelesNum; j++)
						{
							System.out.print("Name of allele: ");
							String alleleName = in.next();
							alleles[j] = new Allele(alleleName, geneName);
						}
						percentages = new double[allelesNum];
						genome.add(new Gene(geneName, alleles, percentages));
					}
					Evolution e = new Evolution(interval, timeSpan, genome, malesToGenerate, femalesToGenerate, numDisasters);
					Original! Don't delete*/
					int i = 1000;
					int ts = 15;
					Allele[] a1 = {new Allele("Fast", "Speed"), new Allele("Slow", "Speed")};
					Allele[] a2 = {new Allele("Strong", "Strength"), new Allele("Weak", "Strength")};
					ArrayList<Gene> g = new ArrayList<Gene>(); g.add(new Gene("Speed", a1, new double[2])); g.add(new Gene("Strength", a2, new double[2]));
					int mtg = 2;
					int ftg = 2;
					int nd = 2;
					Evolution e = new Evolution(i, ts, g, mtg, ftg, nd);
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		}
		f = new JFrame("Swing Paint Demo");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(250,250);
		f.add( new StatsDisplay());
		f.setVisible(true);
		
		f.addMouseListener(new MouseClickListener());
	}	
	
}
@SuppressWarnings("serial")
class StatsDisplay extends JPanel {

	public StatsDisplay() {
		setBorder(BorderFactory.createLineBorder(Color.black));
	}

	public Dimension getPreferredSize() {
		return new Dimension(250,200);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); 
		String stereotypesDisplay = "Stereotypes: "; //displays society's stereotypes
		for (int i = 0; i < Society.stereotypes.size(); i++)
		{
			for (int j = 0; j < Society.stereotypes.get(i).getAlleleList().size(); j++)
			{
				stereotypesDisplay+=Society.stereotypes.get(i).getAlleleList().get(j).getName();
			}
			stereotypesDisplay+=Society.stereotypes.get(i).getValue() + "";
			
		}
		

		// Draw Text
		int yspacing = 20;
		int xspacing = 10;
		g.drawString("Statistics: Year " + Society.currentYear, xspacing, yspacing);
		g.drawString("Males: " + Society.maleList.size(), xspacing, 2*yspacing);
		g.drawString("Females: " + Society.femaleList.size(), xspacing, 3*yspacing);
		g.drawString("Deaths: " + Society.deaths.size(), xspacing, 4*yspacing);
		g.drawString(stereotypesDisplay,xspacing,5*yspacing);
		g.drawString("Start", 112, 165);
		g.drawRect(100, 150, 50, 20);
		
	}
}
