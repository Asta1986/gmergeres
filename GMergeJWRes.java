/* Program to combine the distinct parts of the output files
of the program JWatcher into a single file. */

package gmergeRes;

import java.awt.EventQueue;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


		
public GMergeJWRes() 
{
	AuxF auxf = new AuxF();
	
	setResizable(false);
	final JFileChooser fcORG = new JFileChooser();
	final JFileChooser fcDEST = new JFileChooser();
	setTitle("mergeRes");
	setBounds(100, 100, 250, 255);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	
	JButton btnNewButton = new JButton("Elegir directorio origen");
	btnNewButton.setToolTipText("Seleccione la carpeta que contiene los archivos a procesar.");
	btnNewButton.addActionListener(new ActionListener()
	{	@Override
		public void actionPerformed(ActionEvent e) 
		{				  
			  fcORG.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			  fcORG.showOpenDialog(fcORG.getParent());				  
		}
	}); 			

	
	JButton btnNewButton_1 = new JButton("Elegir directorio destino");
	btnNewButton_1.setToolTipText("Seleccione la carpeta en la que se guardará el archivo resultado.");
	btnNewButton_1.addActionListener(new ActionListener()
	{	@Override
		public void actionPerformed(ActionEvent e) 
		{
			fcDEST.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fcDEST.showOpenDialog(fcDEST.getParent());				  		
		}
	});

	
	JButton btnCombinarArchivos = new JButton("Combinar archivos");
	btnCombinarArchivos.setToolTipText("Copiar las partes de todos los archivos .dat y .cd.res del directorio origen a uno solo.");
	GroupLayout groupLayout = new GroupLayout(getContentPane());
	

	
	getContentPane().setLayout(groupLayout);
	btnCombinarArchivos.addActionListener(new ActionListener()
	{	@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (auxf.dirsSelected(fcORG.getSelectedFile(), fcDEST.getSelectedFile())) 
			{
				File dir = new File(fcORG.getSelectedFile().getAbsolutePath().toString());
				File[] directoryListing = dir.listFiles();
				String destDir = fcDEST.getSelectedFile().getAbsolutePath().toString();
				auxf.proceedIfSupportedFilesInDir(directoryListing, destDir);
			}
			else
			{
				auxf.showMessage("Es necesario especificar los directorios antes de comenzar.");
			}
		 }
	 });
	
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
}
} 