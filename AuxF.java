package gmergeRes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AuxF {
	
	public boolean noDirSelected(File f)
	{
		return f == null;
	}
	
	public boolean dirsSelected(File f0, File f1)
	{
		return (!noDirSelected(f0)) && (!noDirSelected(f1));
	}
	
	public boolean supportedFile(String s)
	{
		return s.contains(".dat") || s.contains(".cd.res");
	}
	
	public boolean existSuppFile(File[] fl)
	{
		boolean res = false;
		for(File f : fl)
		{
			if (supportedFile(f.getName()))
			{
				res = true;
				break;
			}
		}
		return res;
	}
	
	public void showMessage(String msg)
	{
		JFrame f = new JFrame();
		JOptionPane.showMessageDialog(f, msg);
	}
	
	public void proceedIfSupportedFilesInDir(File[] directoryListing, String destDir) {
		if(existSuppFile(directoryListing))
		{
			PrintStream prinFile = null;
			try 
			{
				prinFile = new PrintStream(destDir + File.separator + "combinedResults.txt");
			}	 
			catch (FileNotFoundException e1) 
			{
				e1.printStackTrace();
			}					
			for (int i = 0; i < directoryListing.length; i++) 
			{
				String file = directoryListing[i].getPath(), q1 = "";
				processFileIfSupported(prinFile, file, q1);
			}
			prinFile.close();
			showMessage("El proceso ha finalizado exitosamente.");		 
		}
		else
	    {
		  showMessage("El directorio especificado está vacío o no contiene\narchivos de tipo soportado (.dat y/o .cd.res.).");
		}		 
		
	}
	
	public void processFileIfSupported(PrintStream prinFile, String file, String q1) {
		if (supportedFile(file)) {	
			extractTargetLines(prinFile, file, q1);
		}
	}
	
	public void extractTargetLines(PrintStream prinFile, String file, String q1) {
		Scanner scanFile = null;
		try 
		{
			scanFile = new Scanner(new File(file));
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		}
		while (scanFile.hasNextLine())
		{
			if (q1.startsWith("Answer.1"))
			{
				prinFile.println(q1);
				break;
			}
			q1 = scanFile.nextLine();
		}
		while(scanFile.hasNextLine())
		{	
			prinFile.println(scanFile.nextLine()); 
		}
		prinFile.println(""); 
		scanFile.close();	
	}

}
