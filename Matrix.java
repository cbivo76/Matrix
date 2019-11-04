
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Matrix implements ActionListener 
{
	private static int col, row;  //dimentions
	private static double myMatrix [][];
	private static double tempMatrix [][]; 
	private static JTextField inputField [][];
	private static int result;
	private static JButton nMultiplyB, 
	getValueB, showMatrix,
	newMatrix;
	private static JPanel choosePanel [] = new JPanel[8];
	private static int lastCol , lastRow ;

	/*
	 * Methods:
	 * 1- private static void getDimension() 
	 *    Um die Dimension anzugeben
	 * 2- private static void setElements(double matrix [][], String title )
	 *    Einfüllen der Matrix mit Werte. 
	 * 3- private static void checkTextField (JTextField field [][] ) 
	 *    Leere Felder sind Nullfelder
	 * 4- private void ChooseOperation () 
	 *    Operation mit der Matrix
	 * 5 -  private void actionPerformed(ActionEvent e) 
	 *      Ausgabemethode
	 * 6 -  Matrix () - constructor 
	 *      Programmprozess
	 * 7 -  public void actionPerformed(ActionEvent e)
	 *      Button
	 * 8 -  private static void showMatrix(double [][] matrix, String title )
	 *      Zeigen der Matrix
	 * 9 -  private static void guiMultliplyByScaler ()
	 *      Multiplikation mit einen Skalar, der Stauchfaktor
	 * 10 - private static double [][] multliplyByScaler (double [][] matrix , double x)
	 *      Multiplikation mit einen Skalar
	 * 11 - private static void guiGetValue ()
	 *      Bestimmung des Wertes mittels getValue method 
	 * 12 - private static void newMatrix ()
	 *      Eingabe einer neuen Matrix
	 * 13 - public static void main (String [] args)
	 *      Aufruf des Programms
	 */

	Matrix ()
	{
		col = row = 0;
		myMatrix = new double [0][0];
		ChooseOperation();
	}


	private static void getDimension() 
	{
		JTextField wField = new JTextField(5); //col field

		//design input line
		JPanel choosePanel [] = new JPanel [2];
		choosePanel [0] = new JPanel();
		choosePanel [1] = new JPanel();
		choosePanel[0].add(new JLabel("Gebe Dimensionen ein:") );
		choosePanel[1].add(Box.createHorizontalStrut(15)); // a spacer
		choosePanel[1].add(new JLabel("Anzahl Spalten:"));
		choosePanel[1].add(wField);

		result = JOptionPane.showConfirmDialog(null, choosePanel, 
				null,JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.PLAIN_MESSAGE);

		//Speichern der letzten Dimension
		lastCol = col;
		lastRow = row;

		//ok option
		if(result == 0)
		{

			if(wField.getText().equals(""))
				col = 0;
			else
			{
				if(isInt(wField.getText()))
				{
					col = Integer.parseInt(wField.getText());
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Falsche Dimension");
					col = lastCol;
					row = lastRow;
					return;
				}

				row = 2;
			
			}
			if(col < 1 || row < 1)
			{
				JOptionPane.showConfirmDialog(null, "Du hast eine falsche Dimension eingegeben", 
						"Error",JOptionPane.PLAIN_MESSAGE);
				col  = lastCol;
				row = lastRow;

			}
			else
			{
				tempMatrix = myMatrix;
				myMatrix = new double [row][col];
				if(!setElements(myMatrix, "Gib die neue Matrix ein")) //filling the new matrix
				{
					//backup

					myMatrix = tempMatrix;
				}
			}
		}
		else if(result == 1)
		{
			col = lastCol;
			row = lastRow;
		}
	}//end get Dimension

	//Matrix Elementen
	private static boolean setElements(double matrix [][], String title )
	{
		int temp, temp1;             //Temporäre Variable
		String tempString;

		JPanel choosePanel [] = new JPanel [row+2];
		choosePanel[0] = new JPanel();
		choosePanel[0].add(new Label(title ));
		choosePanel[choosePanel.length-1] = new JPanel();
		choosePanel[choosePanel.length-1].add(new Label("Falls keine Eingabe nimmt das Feld als Null an"));
		inputField  = new JTextField [matrix.length][matrix[0].length];


		//Größe der loop
		for(temp = 1; temp <= matrix.length; temp++)
		{
			choosePanel[temp] = new JPanel();


			for(temp1 = 0; temp1 < matrix[0].length; temp1++)
			{
				inputField [temp-1][temp1] = new JTextField(3);
				choosePanel[temp].add(inputField [temp-1][temp1]);

				if(temp1 < matrix[0].length -1)
				{
					choosePanel[temp].add(Box.createHorizontalStrut(15)); // a spacer
				}

			}//end Spalten loop

		}//end Zeilen loop

		result = JOptionPane.showConfirmDialog(null, choosePanel, 
				null, JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);


		if(result == 0)
		{
			checkTextField(inputField);
			for(temp = 0; temp < matrix.length; temp++)
			{
				for(temp1 = 0; temp1 < matrix[0].length; temp1++)
				{
					tempString = inputField[temp][temp1].getText();


					if(isDouble(tempString))
					{
						matrix [temp][temp1] = Double.parseDouble(inputField[temp][temp1].getText());
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Du hast falsche Elemente eingegeben");

						//backup
						col = lastCol;
						row = lastRow;

						return false;
					}                      
				}
			}
			return true;
		}
		else
			return false;


	}//end get Inputs

	//Leerfelder sind Nullfelder angenommen.
	private static void checkTextField (JTextField field [][] )
	{
		for(int temp = 0; temp < field.length; temp++)
		{
			for(int temp1 = 0; temp1 < field[0].length; temp1++)
			{
				if(field[temp][temp1].getText().equals(""))
					field[temp][temp1].setText("0");
			}
		}
	}//end reset

	private void ChooseOperation ()
	{
		int temp;


		for(temp = 0; temp < choosePanel.length; temp++)
		{
			choosePanel [temp] = new JPanel ();
		}

		choosePanel[1].add(Box.createHorizontalStrut(15)); // a spacer

		choosePanel[6].add(Box.createHorizontalStrut(15)); // a spacer

		showMatrix = new JButton ("Alte Matrix");
		showMatrix.setPreferredSize(new Dimension(175,35));
		showMatrix.addActionListener(this);
		choosePanel[2].add(showMatrix);

		nMultiplyB = new JButton ("Stauchfaktor n");
		nMultiplyB.setPreferredSize(new Dimension(175,35));
		nMultiplyB.addActionListener(this);
		choosePanel[3].add(nMultiplyB);

		newMatrix = new JButton("Gib die Matrix ein");
		newMatrix.setPreferredSize(new Dimension(175,35));
		newMatrix.addActionListener(this);
		choosePanel[5].add(newMatrix);

		JOptionPane.showConfirmDialog(null, choosePanel, null,
				JOptionPane.CLOSED_OPTION , JOptionPane.PLAIN_MESSAGE);

	}


	@Override
	public  void actionPerformed(ActionEvent e) 
	{

		if(e.getSource() == showMatrix)
		{
			showMatrix( myMatrix, "Deine Matrix");
		}
		
		else    if(e.getSource() ==  nMultiplyB)
		{
			guiMultliplyByScaler();
		}

		else   if(e.getSource() == getValueB)
		{
			guiGetValue();
		}

		else   if(e.getSource() == newMatrix)
		{
			newMatrix();
		}
	}//end action performed


	private static void showMatrix(double [][] matrix, String title )
	{
		int temp, temp1;             //Temporäre Variable

		JPanel choosePanel [] = new JPanel [matrix.length+1];
		choosePanel[0] = new JPanel ();
		choosePanel[0].add( new JLabel (title) );

		for(temp = 1; temp <= matrix.length; temp++)
		{
			choosePanel[temp] = new JPanel();


			for(temp1 = 0; temp1 < matrix[0].length; temp1++)
			{
				if(matrix[temp-1][temp1] == -0)
				{
					matrix[temp-1][temp1] = 0; 
				}
				choosePanel[temp].add(new JLabel(String.format("%.2f", matrix[temp-1][temp1])));

				if(temp1 < matrix[0].length -1)
				{
					choosePanel[temp].add(Box.createHorizontalStrut(15)); // a spacer
				}

			}//end Spalten loop

		}//end Zeilen loop

		if(col == 0 || row == 0)
		{
			JOptionPane.showMessageDialog(null, "Du hast keine Matrix eingegeben");
		}
		else
		{

			JOptionPane.showMessageDialog(null, choosePanel, null, 
					JOptionPane.PLAIN_MESSAGE, null);
		}  
	}//end show Matrix

	
		private static void guiMultliplyByScaler ()
	{

		double[][]results=new double [row][col];
		double x;
		String tempString;

		if(myMatrix.length < 1)
		{
			JOptionPane.showMessageDialog(null, "Du hast keine Matrix eingegeben");
			return;
		}

		tempString = JOptionPane.showInputDialog(null, 
				"Gig den Faktor ein:");

		if (tempString == null) //cancel option
		{
			return;
		}

		else if(!tempString.equals(""))
			x= Double.parseDouble(tempString);
		else
		{
			JOptionPane.showMessageDialog(null, "Du hast kein Faktor eingegeben");
			return;
		}
		results = multliplyByScaler(myMatrix, x);
		showMatrix(results, "Multiplikationsergebnis");

	}//end Multiplikation mit einem Skalar - Stauchfaktor


	private static double [][] multliplyByScaler (double [][] matrix , double x)
	{

		double[][]results=new double [row][col];
		int i,j;

		for (i=0;i<matrix.length;i++)
		{
			for(j=0;j<matrix[0].length;j++)
			{
				results[i][j] = x*matrix[i][j];
			}
		}
		return results;
	}//end Multiplikation mit einem Skalar - Stauchfaktor

	private static void guiGetValue ()
	{
		if(myMatrix.length < 1)
		{
			JOptionPane.showMessageDialog(null, "Du hast keine Matrix eingegeben");
		}
		else if(col != row)
		{
			JOptionPane.showMessageDialog(null, "Du must eine Quadratmatrix eingeben");
		}

	}//end gui get Value


	private static boolean isInt (String str)
	{
		int temp;
		if (str.length() == '0')
			return false;

		for(temp = 0; temp < str.length();temp++)
		{
			if(str.charAt(temp) != '+' && str.charAt(temp) != '-'
					&& !Character.isDigit(str.charAt(temp)))
			{
				return false;
			}
		}
		return true;
	}

	private static boolean isDouble (String str)
	{
		int temp;
		if (str.length() == '0')
			return false;

		for(temp = 0; temp < str.length();temp++)
		{
			if(str.charAt(temp) != '+' && str.charAt(temp) != '-'
					&& str.charAt(temp) != '.'
					&& !Character.isDigit(str.charAt(temp))
					)
			{
				return false;
			}
		}
		return true;
	}

	private static void newMatrix ()
	{        
		getDimension();
	}
	public static void main (String [] args)
	{
		Matrix m1 = new Matrix ();

	}
}//end class

