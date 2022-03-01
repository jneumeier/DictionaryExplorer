package DictionaryExplorer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.util.Random;

/**
 * Abstract: This main class contains and performs all neccessary actions for the
 * application to operate properly.
 * @author John Neumeier
 * @since  02/28/2022
 * @version 1.1.2
 */
public class DictionaryExplorer {

	/**
	 * Abstract: Executes the main procedures of the application.
	 * @author John Neumeier
	 * @since  02/17/2022
	 * @version 1.0
	 * @param args Args not utilized
	 */
	public static void main(String[] args) {
		
		// display program introduction
		System.out.println("--------------------Dictionary Explorer------------------");
		System.out.println("Welcome to Dictionary Explorer. This program displays    ");
		System.out.println("random words and allows you to save them.                ");
		System.out.println("---------------------------------------------------------\n");
		
		// display user options
		ProgramMenu_Display();
		
		// run sub that takes user input and performs program actions
		ProgramMenu_Action();
	}
	
	/**
	 * Abstract: Shows the options the user has to control the program
	 * @author John Neumeier
	 * @since  02/20/2022
	 * @version 1.0
	 */
	public static void ProgramMenu_Display() {
		
		// display menu options
		System.out.println("---------------------------Menu--------------------------");
		System.out.println("New Word(s) - press Enter      Display Menu - \'M\'" );
		System.out.println("Change number of words to display on one line - \'N\'");
		System.out.println("Set word length maximum or minimum = \'Max\' / \'Min\'");
		System.out.println("Type Word (automatically saves) - \'T\'");
		System.out.println("Set Text File Path - \'F\'     Save word - \'S\'");
		System.out.println("Definition of most recent word (opens browser) - \'D\'");
		System.out.println("Exit Program - \'E\'");
		System.out.println("---------------------------------------------------------");
	}
	
	/**
	 * Abstract: Takes user input and calls functions/subs based on that input
	 * @author John Neumeier
	 * @since  02/20/2022
	 * @version 1.0
	 */
	public static void ProgramMenu_Action() {
		
		// create variables
		String strUserAction = "";
		String strWordRetrieved = "";
		int intNumberOfWords = 1;
		int intWordSizeMaximum = 1000;
		int intWordSizeMinimum = 1;
		int intMaxWordsFromDictionary = 69904;
		String strFilePath = "";
		Boolean blnContinueProgram = true;
		Boolean blnAddToFile = true;
		Boolean blnWriteSuccessful = false;
	
		// keep running the program after each user action has been ran, except the exit selection,
		// which flips blnContinueProgram to false
		while(blnContinueProgram == true) {
			
			// reset variables that need to be reset for new iteration of user action
			blnWriteSuccessful = false;
			
			// get menu option selection from user
			strUserAction = ReadStringFromUser();
			
			// make sure that if strUserAction is one character, we ensure that it is upper case
			
			// perform actions based on user input
			switch (strUserAction) {
			
				case "":
					strWordRetrieved = ProgramAction_GetWord(intNumberOfWords, intWordSizeMaximum, 
							intWordSizeMinimum, intMaxWordsFromDictionary);
					break;
					
				case "M":
					ProgramMenu_Display();
					break;
					
				case "N":
					intNumberOfWords = GetNumberOfWords();
					break;
					
				case "Max":
					intWordSizeMaximum = GetWordSizeMaximum(intWordSizeMinimum);
					break;
					
				case "Min":
					intWordSizeMinimum = GetWordSizeMinimum(intWordSizeMaximum);
					break;
					
				case "F":
					strFilePath = GetFilePath();
					blnAddToFile = GetFileWritePreference(strFilePath);
					System.out.println("File path has been set to " + strFilePath);
					break;
					
				case "T":
					System.out.println("Enter a word to save: ");
					strWordRetrieved = ReadStringFromUser();
					// no break, so we can go straight to case for saving
					
				case "S":
					if(strFilePath != "") {
						blnWriteSuccessful = WriteWordToFile(strWordRetrieved, strFilePath, blnAddToFile);
						if(blnWriteSuccessful == true) {
							blnAddToFile = true;
							System.out.println(strWordRetrieved + " has been saved to " + strFilePath);
						} else {System.out.println("Save unsuccessful. File path may be invalid.");}				
									
					} else {
						System.out.println("File path must be set before saving.");
					}
					break;
					
				case "D":
					LoadWebPage("https://www.dictionary.com/browse/" + strWordRetrieved);
					break;
					
				case "E":
					System.out.println("Exiting the program. Goodbye!");
					System.out.println("");
					blnContinueProgram = false;
					break;
					
				default:
					System.out.println("Sorry--the selection you have given is invalid. Try Again.\n");
					break;			
			}		
		}
	}

	/**
	 * Abstract: Gets the number of words the user would like to see displayed on one line
	 * @author John Neumeier
	 * @since  02/22/2022
	 * @version 1.0
	 * @return intNumberOfWords The number of words the user would like to see displayed on one line
	 */
	public static int GetNumberOfWords() {
		
		int intNumberOfWords = 0;
		
		// prompt user for input
		System.out.println("Enter the amount of words that you would like to display and save on one line: ");
		
		// keep prompting for correct integer input until valid entry is made
		while(intNumberOfWords < 1 || intNumberOfWords > 5) {
			System.out.println("Please enter a number 1 through 5.");
			intNumberOfWords = ReadIntegerFromUser();
		}
		
		System.out.println("Number of words set to: " + intNumberOfWords);
		
		return intNumberOfWords;
	}
	
	/**
	 * Abstract: Gets the maximum length of a single word that the user would like to be generated
	 * @author John Neumeier
	 * @since  02/25/2022
	 * @version 1.0
	 * @param intWordSizeMinimum The user's desired minimum word length
	 * @return intWordSizeMaximum The user's desired maximum word length
	 */
	public static int GetWordSizeMaximum(int intWordSizeMinimum) {
		
		int intWordSizeMaximum = 0;
		
		// prompt user for input
		System.out.println("Enter the maximum size of words you would like to be generated: ");
		
		do {					
			// keep prompting for correct integer input until valid entry is made
			do {				
				intWordSizeMaximum = ReadIntegerFromUser();
				
				if(intWordSizeMaximum < 1 || intWordSizeMaximum > 1000) {
					System.out.println("Please enter a number greater than zero.");
				}
			} while(intWordSizeMaximum < 1 || intWordSizeMaximum > 1000);
			
			if(intWordSizeMaximum < intWordSizeMinimum) {
				System.out.println("Max not set, because min is above the desired max. \nPlease try again: ");
			}
			
		} while(intWordSizeMaximum < intWordSizeMinimum);
		
		System.out.println("Max word size set to: " + intWordSizeMaximum);	
		
		return intWordSizeMaximum;
	}
	
	/**
	 * Abstract: Gets the minimum length of a single word that the user would like to be generated
	 * @author John Neumeier
	 * @since  02/25/2022
	 * @version 1.0
	 * @param intWordSizeMaximum The user's desired maximum word length
	 * @return intWordSizeMinimum The user's desired minimum word length
	 */
	public static int GetWordSizeMinimum(int intWordSizeMaximum) {
		
		int intWordSizeMinimum = 0;
		
		// prompt user for input
		System.out.println("Enter the minimum size of words you would like to be generated: ");
		
		do {						
			// keep prompting for correct integer input until valid entry is made
			do {
				
				intWordSizeMinimum = ReadIntegerFromUser();
				
				if(intWordSizeMinimum < 1 || intWordSizeMinimum > 1000) {
					System.out.println("Please enter a number greater than zero.");
				}
			} while(intWordSizeMinimum < 1 || intWordSizeMinimum > 1000);
			
			if(intWordSizeMinimum > intWordSizeMaximum) {
				System.out.println("Min not set, because max is below the desired min. \nPlease try again: ");
			}	
			
		} while(intWordSizeMinimum > intWordSizeMaximum);
		
		System.out.println("Minimum word size set to: " + intWordSizeMinimum);	
		
		return intWordSizeMinimum;
	}
	
	/**
	 * Abstract: Prints the word that is retrieved from the dictionary, and returns the word
	 * @author John Neumeier
	 * @since  02/17/2022
	 * @version 1.2
	 * @param intNumberOfWords The amount of words the user would like to see in one call
	 * and, on one line.
	 * @param intWordMaximum The maximum amount of chars the user wants possible in each word found
	 * @param intWordMinimum The minimum amount of chars the user wants possible in each word found
	 * @param intMaxWordsFromDictionary The maximum or limit desired of words to read from dictionary text file
	 * @return strWordRetrieved The word that is randomly pulled from the dictionary
	 */
	public static String ProgramAction_GetWord(int intNumberOfWords, int intWordMaximum, 
			int intWordMinimum, int intMaxWordsFromDictionary) {
		
		// create variables
		String strWordRetrieved = "";
		String strWordOutput = "";
		int intIndex = 0;
		
		// proceed if user's desired length range has words within it
		if(DoWordsExistWithLengthRange(intWordMinimum, intWordMaximum, intMaxWordsFromDictionary) == true) {
			
			// append amount of words the user would like to see in one line
			for(intIndex = 0; intIndex < intNumberOfWords; intIndex += 1) {
				
				// keep grabbing until grabbed word is inside the desired length range
				do {
					strWordRetrieved = ReadWordFromFile(intMaxWordsFromDictionary);
				} while (strWordRetrieved.length() < intWordMinimum 
						|| strWordRetrieved.length() > intWordMaximum);
				
				// append
				strWordOutput += strWordRetrieved + " ";
			}
			
		} else {
			// return the word as an error message if no word in dictonary is in the given length range
			strWordOutput = "Error:_No_Word_Found_In_Given_Length_Range";
		}
		
		// print to the user
		System.out.print(strWordOutput);
				
		return strWordOutput;
		
	}
	
	/**
	 * Abstract: Allows for string input from the user
	 * Method: ReadStringFromUser
	 * Abstract: Allows for string input from user
	 * @return strString
	 */
	public static String ReadStringFromUser( ) {	  
	
		// establish variables
		String strBuffer = "";	
		
		try {	
			// Input stream
			BufferedReader burInput = new BufferedReader( new InputStreamReader( System.in ) ) ;
	
			// Read a line from the user
			strBuffer = burInput.readLine( );
		}
		catch( Exception excError ) {
			System.out.println( excError.toString( ) );
		}
		
		// Return integer value
		return strBuffer;
	}
	
	/**
	 * Abstract: Allows for integer input from the user
	 * Method: ReadIntegerFromUser
	 * Abstract: Allows for input from user and returns the input as data type int
	 * @return intValue
	 */
	public static int ReadIntegerFromUser( )
	{

		int intValue = 0;

		try
		{
			String strBuffer = "";	

			// Input stream
			BufferedReader burInput = new BufferedReader( new InputStreamReader( System.in ) ) ;

			// Read a line from the user
			strBuffer = burInput.readLine( );
			
			// Convert from string to integer
			intValue = Integer.parseInt( strBuffer );
		}
		catch( Exception excError )
		{
			System.out.println( "Please enter a valid number." );
		}
		

		// Return integer value
		return intValue;
	}
	
	/**
	 * Abstract: Returns a random word from the dictionary text file
	 * @author John Neumeier
	 * @since  02/17/2022
	 * @version 1.0
	 * @param intMaxWordsFromDictionary The maximum or limit desired of words to read from dictionary text file
	 * @return strWordRead The string that is randomly pulled from the dictionary text file
	 */
	public static String ReadWordFromFile(int intMaxWordsFromDictionary) {
		
		// establish variables
		String strWordRead = "";
		int intLineNumber = 1;
		int intRandomNumber = 0;
		
		// generate a random integer within max-number-of-words bounds
		Random clsRandomInteger = new Random();
		
		while(intRandomNumber <= 0 || intRandomNumber >= intMaxWordsFromDictionary) {
			
			intRandomNumber = clsRandomInteger.nextInt();
		}
			
		// get the word from the dictionary based on the random integer generated
		try {		
			// create FileReader object, 'dictionary', from our text file that has all of the words
		      FileReader dictionary = new FileReader("wordlist.txt");
		      
		      // create buffer to read through the FileReader object
		      BufferedReader readbuffer = new BufferedReader(dictionary);
		      
		      // iterate through all word entries by line, until the random word line number is hit
		      for (intLineNumber = 1; intLineNumber <= intMaxWordsFromDictionary; intLineNumber += 1) {
		        
		    	  if (intLineNumber == intRandomNumber) {
		        		    		  
		    		  // save the found word into a string
		    		  strWordRead = readbuffer.readLine();
		    		  	    		  
		    		  break;
		    		  
		    	  } else {
		    		  
			    	  // continue to iterate thru the actual text file if the random line number is not hit
			          readbuffer.readLine();		    		  
			    	  }
		       }
		      
		      readbuffer.close();	
			  dictionary.close();
		      
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Contact the support desk and reference this error in class VolunteerDataIO");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} 
		
		// return the string
		return strWordRead;
	}
	
	/**
	 * Abstract: Get user preference on whether or not to set the file save path to default desktop
	 * @author John Neumeier
	 * @since  02/22/2021
	 * @version 1.0
	 * @return blnSaveToDefaultDesktop Shows if the user selected to save to default desktop or not
	 */
	public static Boolean AskIfWriteToDesktop() {
		
		boolean blnExitLoop = false;
		boolean blnSaveToDefaultDesktop = false;
		String strUserSelection = "";
			
		// Loop until correct entry is made
		do {
			System.out.println("Set file path to default desktop, or enter a path manually? (Y/N): ");
			strUserSelection = ReadStringFromUser(); // get user selection
			
			switch (strUserSelection) {
				case "Y":
					System.out.println("Path set to default desktop.\n");
					blnSaveToDefaultDesktop = true;
					blnExitLoop = true;
					break;
				case "N":
					blnExitLoop = true;
					blnSaveToDefaultDesktop = false;
					break;
			}
		} while(blnExitLoop == false); // loop until correct entry is made
		
		return blnSaveToDefaultDesktop;
	}
	
	/**
	 * Abstract: Returns the file path that is to be used to write to a text file
	 * @author John Neumeier
	 * @since  02/20/2022
	 * @version 1.0
	 * @return strFilename_Full The user's desired text file path
	 */
	public static String GetFilePath() {
			
		String strFilename = "";
		String strFileDirectory = "";
		String strFilename_Full = "";
		
		strFilename = GetTextFilename();
		
		if(AskIfWriteToDesktop() == false) {
			
			strFileDirectory = GetFileDirectory();
			
		} else {
			
			strFileDirectory = System.getProperty("user.home") + "\\Desktop\\";
		}
				
		strFilename_Full = strFileDirectory + strFilename;
		
		return strFilename_Full;
	}
	
	/**
	 * Abstract: Gets preferences on how the word is to be written to the text file
	 * @author John Neumeier
	 * @since  02/20/2021
	 * @version 1.0
	 * @param strFilePath The full file name of the text file to be written to
	 * @return blnAddToFile The preference whether or not to add word to file, or start new file.
	 */
	public static Boolean GetFileWritePreference(String strFilePath) {
		
		Boolean blnAddToFile = true;
		boolean blnFileExists = false;
		boolean blnExitLoop = false;
		String strUserSelection = "";
		
		// try {
			blnFileExists = new File(strFilePath).exists();
		// } catch {
			
			// }
			
		// File already exists. Ask user permission to overwrite. Loop until correct entry is made
		if(blnFileExists == true) {
			
			blnExitLoop = false;
			
			do {
				System.out.println("File already exists. Set to add to existing file (\'Y\'), or set to overwrite (\'N\')?:");
				strUserSelection = ReadStringFromUser(); // get user selection
				
				switch (strUserSelection) {
					case "Y":
						System.out.println("Set to add to existing file.\n");
						blnAddToFile = true;
						blnExitLoop = true;
						break;
					case "N":
						System.out.println("Set to overwrite existing file.\n");
						blnAddToFile = false;
						blnExitLoop = true;
						break;
				}
			} while(blnExitLoop == false); // loop until correct entry is made
		}
		
		return blnAddToFile;
	}
	
	/**
	 * Abstract: Gets a directory from the user
	 * @author John Neumeier
	 * @since  11/2/2021
	 * @version 1.0
	 * @return strFileDirectory The desired directory
	 */
	public static String GetFileDirectory() {
		
		String strFileDirectory = "";
		
		System.out.println("Please enter the desired note file path only (use double backslashes.)");
		System.out.println("Make sure to end with double backslashes.");
		System.out.println("ex. \"C:\\\\Temp\\\\\"");
		
		// keep looping until something is entered
		do {
		strFileDirectory = ReadStringFromUser();
		} while(strFileDirectory == "");
		
		// return the directory as a string
		return strFileDirectory;
	}
	
	/**
	 * Abstract: Gets a text filename from the user
	 * @author John Neumeier
	 * @since  11/2/2021
	 * @version 1.0
	 * @return strFilename The desired name of a text file
	 */
	public static String GetTextFilename() {
		
		// establish variables
		String strFilename = "";
		
		// give user directions
		System.out.println("Please enter the desired note filename only, with .txt extension");
		System.out.println("ex. \"myNote.txt\"");
		
		// keep looping until something is entered
		do {
		strFilename = ReadStringFromUser();
		} while(strFilename == "");
		
		// returning the filename only
		return strFilename;
	}
	
	/**
	 * Abstract: Writes words to a text file
	 * @author John Neumeier
	 * @since  02/20/2022
	 * @version 1.0
	 * @param strWordToWrite Word to write to the text file
	 * @param strFilename_Full Full path name, including file name, for the text file
	 * @param blnAddToExisting Indicates if writer should add word to existing words, or overwrite existing file
	 * @return blnWriteSuccess Indicates whether or not the word was successfully written to the file
	 */
	public static Boolean WriteWordToFile(String strWordToWrite, String strFilename_Full,
											Boolean blnAddToExisting) {
		
		// establish variables
		boolean blnWriteSuccess = false;
		
		try {
		      FileWriter myWriter = new FileWriter(strFilename_Full, blnAddToExisting);
		      myWriter.write(strWordToWrite + "\n");
		      myWriter.close();
		      blnWriteSuccess = true;
		    } catch (IOException e) {
		      blnWriteSuccess = false;
		      e.printStackTrace();
		    }
		
		
		// return the write success flag so the caller knows if the process was successful or not
		return blnWriteSuccess;
	}
	
	/**
	 * Abstract: Writes words to a text file
	 * @author John Neumeier
	 * @since  03/1/2022
	 * @version 1.0
	 * @param intWordMaximum The maximum amount of chars the user wants possible in each word found
	 * @param intWordMinimum The minimum amount of chars the user wants possible in each word found
	 * @param intMaxWordsFromDictionary The maximum or limit desired of words to read from dictionary text file
	 * @return blnWordsExistInLengthRange Indicates whether or not a word exists in given length range
	 */
	public static boolean DoWordsExistWithLengthRange(int intWordMinimum, int intWordMaximum,
			int intMaxWordsFromDictionary) {
		
		boolean blnWordsExistInLengthRange = false;
		
		// establish variables
		String strWordRead = "";
		int intLineNumber = 1;
		
			
		// get the word from the dictionary based on the random integer generated
		try {		
			// create FileReader object, 'dictionary', from our text file that has all of the words
		      FileReader dictionary = new FileReader("wordlist.txt");
		      
		      // create buffer to read through the FileReader object
		      BufferedReader readbuffer = new BufferedReader(dictionary);
		      
		      // iterate through all word entries by line, grab each word, and check its length
		      for (intLineNumber = 1; intLineNumber <= intMaxWordsFromDictionary; intLineNumber += 1) {
		        
		    	  // read the word
		    	  strWordRead = readbuffer.readLine();
		    	  
		    	  // check its length against user's range
		    	  if(strWordRead.length() >= intWordMinimum && strWordRead.length() <= intWordMaximum) {
		    		  
		    		  blnWordsExistInLengthRange = true;
		    		  
		    		  // we found at least one that fits the range, so we can exit the loop
		    		  break;
		    	  }
		       }
		      
		      readbuffer.close();	
			  dictionary.close();
		      
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Contact the support desk and reference this error in class VolunteerDataIO");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}
		
		return blnWordsExistInLengthRange;
	}
	
	/**
	 * Abstract: Loads a web page from the command line. mkyong had this as main() for
	 * his StartBrowser class. I modified it only with a new function name.
	 * @author mkyong (https://mkyong.com/java/open-browser-in-java-windows-or-linux/)
	 * @param strFullURL String containing the url to be loaded into a web browser.
	 */
	  public static void LoadWebPage(String strFullURL)
	  {
		String url = strFullURL;
		String os = System.getProperty("os.name").toLowerCase();
	        Runtime rt = Runtime.getRuntime();
		
		try {

		    if (os.indexOf( "win" ) >= 0) {

		        // this doesn't support showing urls in the form of "page.html#nameLink" 
		        rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);

		    } else if (os.indexOf( "mac" ) >= 0) {

		        rt.exec( "open " + url);

	            } else if (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) {

		        // Do a best guess on unix until we get a platform independent way
		        // Build a list of browsers to try, in this order.
		        String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
		       			             "netscape","opera","links","lynx"};
		        	
		        // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
		        StringBuffer cmd = new StringBuffer();
		        for (int i=0; i<browsers.length; i++)
		            cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");
		        	
		        rt.exec(new String[] { "sh", "-c", cmd.toString() });

	           } else {
	                return;
	           }
		    
	       } catch (Exception e){
	    	   
		    return;
		    
	       }
		
	      return;			  
	}
}
