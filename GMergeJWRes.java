/* Program to combine the distinct parts of the output files
of the program JWatcher into a single file. */

package gmergeRes;

import java.awt.EventQueue;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class GMergeJWRes extends JFrame 
{ 
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					GMergeJWRes frame = new GMergeJWRes();
					frame.setVisible(true);
				//	frame.pack();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

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
		
public GMergeJWRes() 
{
	setResizable(false);
	final JFileChooser fcORG = new JFileChooser();
	final JFileChooser fcDEST = new JFileChooser();
	setTitle("mergeRes");
	setBounds(100, 100, 250, 255);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	
	JButton btnNewButton = new JButton("Elegir directorio origen");
	btnNewButton.setToolTipText("Seleccione la carpeta que contiene los archivos a procesar.");
	btnNewButton.addMouseListener(new MouseAdapter() 
	{	@Override
		public void mouseClicked(MouseEvent e) 
		{				  
			  fcORG.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			  fcORG.showOpenDialog(fcORG.getParent());				  
		}
	}); 			

	
	JButton btnNewButton_1 = new JButton("Elegir directorio destino");
	btnNewButton_1.setToolTipText("Seleccione la carpeta en la que se guardará el archivo resultado.");
	btnNewButton_1.addMouseListener(new MouseAdapter() 
	{	@Override
		public void mouseClicked(MouseEvent e) 
		{
			fcDEST.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fcDEST.showOpenDialog(fcDEST.getParent());				  		
		}
	});

	
	JButton btnCombinarArchivos = new JButton("Combinar archivos");
	btnCombinarArchivos.setToolTipText("Copiar las partes de todos los archivos .dat y .cd.res del directorio origen a uno solo.");
	GroupLayout groupLayout = new GroupLayout(getContentPane());
	groupLayout.setHorizontalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(22)
						.addComponent(btnCombinarArchivos, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE))
					.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
					.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 220, Short.MAX_VALUE)))
				.addContainerGap())
	);
	groupLayout.setVerticalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addContainerGap()
				.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
				.addGap(29)
				.addComponent(btnCombinarArchivos, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(134, Short.MAX_VALUE))
	);
	getContentPane().setLayout(groupLayout);
	btnCombinarArchivos.addMouseListener(new MouseAdapter() 
	{	@Override
		public void mouseClicked(MouseEvent e) 
		{
			if (dirsSelected(fcORG.getSelectedFile(), fcDEST.getSelectedFile())) 
			{
				File dir = new File(fcORG.getSelectedFile().getAbsolutePath().toString());
				File[] directoryListing = dir.listFiles();
				if(existSuppFile(directoryListing))
				{	
					PrintStream prinFile = null;
					try 
					{
						prinFile = new PrintStream(fcDEST.getSelectedFile().getAbsolutePath().toString() + File.separator + "combinedResults.txt");
					}	 
					catch (FileNotFoundException e1) 
					{
						e1.printStackTrace();
					}					
					for (int i = 0; i < directoryListing.length; i++) 
					{
						String file = directoryListing[i].getPath(), q1 = "";
						if (supportedFile(file))
						{	
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
					  showMessage("El proceso ha finalizado exitosamente.");
					  prinFile.close(); 		
				  }
				  else
				  {
					  showMessage("El directorio especificado está vacío o no contiene\narchivos de tipo soportado (.dat y/o .cd.res.).");
				  }		 
			} 
			else
			{
				showMessage("Es necesario especificar los directorios antes de comenzar.");
			}
		 }
	 });
}
} 