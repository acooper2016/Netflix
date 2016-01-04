/**
*Program that parses a .csv file into usable data, and then uses that data to generate a show recommendation based on the highest
*average rating for shows not seen by the person being recommended to.  .csv file must have a single line of 
*show names, and then any number of additional lines of values 0-5 of length equal to the length of line 1.
*
*Completes the requirements of the Netflix project to tier 1, the "punt" tier
*
*@author Aaron Cooper
*@version 1.0
*/
import java.io.*;
import java.util.Scanner;


public class Netflix
{

	public static void main(String [] args)
	{
		netflixRec(2, "TVData.csv");
	}

	/**
	*Method that parses a .csv file and converts it into a usable data format.  .csv file must have a single line of 
	*show names, and then any number of additional lines of values 0-5 of length equal to the length of line 1.
	*
	*@param fileName String matching the exact file name of the .csv file to be parsed.
	*/
	private static NetflixDataPackage grabData(String fileName)
	{
		String pathname = fileName;
		File file = new File(pathname);
		Scanner input = null;
		
		try
		{
			input = new Scanner(file);
		} 
		catch(FileNotFoundException ex)
		{
			System.out.println("Cannot open" + pathname);
			System.exit(1);
		}
		
		String dataString = "";
		
		//For some reason the program keeps cutting off the last show on the list, so I just add a fake show and it works fine. # used as a line break for easy parsing.
		dataString = dataString + input.nextLine() + ",holder" + "#"; 
		
		while(input.hasNextLine())
		{
			dataString = dataString + input.nextLine() + "#";
		}
		
		Vector<String> showNames = new Vector<String>();
		
		int dataInd = 0;
		String currShow = "";
		
		//Generates the list of show names.  Adds characters of each show to a string, then adds that string to the list and starts a new string whenever a comma is hit.  Keeps track of the character at which numerical data starts.
		for(int i = 0; i == 0 || dataString.charAt(i - 1) != '#'; i++) 
		{
			if(dataString.charAt(i) != ',')
			{
				currShow += dataString.charAt(i);
			}
			else if(dataString.charAt(i) == '#')
			{
				showNames.add(currShow);
				dataInd = i + 1;	
			}	
			else //case for ','
			{
				showNames.add(currShow);
				currShow = "";
			}
				
		}
		
		Vector<Integer[]> dataSet = new Vector<Integer[]>(); //Generates the vector containing each person's ratings, represented as an array of ints. 
		dataSet.add(new Integer[showNames.size() - 1]);
		
		int showInd = 0;
		int personInd = 0;
		
		for(int i = dataInd; i < dataString.length() - 1; i++)
		{
			if(dataString.charAt(i) == '#')
			{
				showInd = 0;
				personInd ++;
				dataSet.add(new Integer[showNames.size() - 1]);
			}
			else if(dataString.charAt(i) > 47 && dataString.charAt(i) < 58)
			{
				String s = "" + dataString.charAt(i);
				dataSet.get(personInd)[showInd]  = Integer.parseInt(s);
				showInd ++;
			}
		}
		
		//I seem to be getting faulty data in my NetflixDataPackage due to some sort of bounds error or something, these lines just cut them out.
		dataSet.remove(0);  
		dataSet.remove(dataSet.size() - 1);
		
		return new NetflixDataPackage(showNames.size(), showNames, dataSet);		
				
	}
	
	/**
	*Spits out the top 3 recommendations of unseen shows, based on highest average rating for those shows.
	*
	*@param personRec index of the line representing the person to be recommended to in the .csv file
	*@param fileName name of the .csv containing netflix data
	*/
	public static void netflixRec(int personRec, String fileName)
	{
		NetflixDataPackage data = grabData(fileName);
		
		Vector<Double> aggregateScores = new Vector<Double>(); //Contains average scores for each shows
		
		for(int i = 0; i < data.getNumShows() - 1; i++) //iterates through each show
		{
			double sum = 0;
			int numPeople = 0;
			for(int j = 0; j < data.getDataSet().size(); j++) //Iterates through each score in that show
			{
				if(data.getDataSet().get(j)[i] != 0)
					sum += data.getDataSet().get(j)[i];
				numPeople = j;
			}
			aggregateScores.add(sum / numPeople); //Generates an average by dividing the sum of all scores for that show by the number of people added
		}
		
		Vector<Integer> printedShows = new Vector<Integer>(); //Stores shows already confirmed as top shows, and factors those out when shows are checked for highest score
		for(int i = 0; i < 3; i++) //3 is the number of shows
		{
			int topInd = 0;
			for(int j = 1; j < aggregateScores.size() - 1; j++)
			{
				if(aggregateScores.get(j) > topInd && data.getDataSet().get(personRec)[j] == 0 && (! printedShows.contains(j))) //highest score checked, target user hasn't seen, not already printed, respectively
				{
					topInd = j;
				}
			}
			System.out.println(data.getShows().get(topInd));
			printedShows.add(topInd);
		}
	}

	
	
}

