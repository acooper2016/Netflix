/**
*Class that stores usable data for the Netflix class.
*
*
*/
public class NetflixDataPackage
{
	private int numShows;
	private Vector<String> shows;
	private Vector<Integer[]> dataSet;
	
	/**
	*Constructs a NetflixDataPackage with a given number of shows, Vector of shows, and Vector containing integer arrays detailing
	*the scores of each user for each show.
	*
	*@param ns Number of shows
	*@param s Vector containing strings representing each show
	*@param ds Vector of Integer[]s that contains the data for each user.
	*/
	public NetflixDataPackage(int ns, Vector<String> s, Vector<Integer[]> ds)
	{
		numShows = ns;
		shows = s;
		dataSet = ds;
	}
	
	/**
	*Return # of shows
	*
	*@return # of shows
	*/
	public int getNumShows()
	{
		return numShows;
	}
	
	/**
	*Returns Vector of shows
	*
	*@return Vector containing strings representing each show
	*/
	public Vector<String> getShows()
	{
		return shows;
	}
	
	/**
	*Returns dataset
	*
	*@return Vector of Integer[]s containing the data for each user
	*/
	public Vector<Integer[]> getDataSet()
	{
		return dataSet;
	}
	
	/**
	*Returns the String representation of the NetflixDataPackage
	*
	*@return String representing this NetflixDataPackage
	*/
	public String toString()
	{
		String outString = "";
		
		outString += numShows + "\n\n";
		
		for(int i = 0; i < numShows; i++)
		{
			outString += shows.get(i) + "\n";
		}
		
		outString += "\n\n";
		
		for(int i = 0; i < dataSet.size() - 1 ; i++)
		{
			for(int j = 0; j < numShows; j++)
			{
				//System.out.print(i);
				//System.out.print("," + j);
				//System.out.println( " " + dataSet.get(i)[j]);
				outString += dataSet.get(i)[j] + " ";
				
			}
			outString += "\n";
		}
		

		return outString;

	}
	
}