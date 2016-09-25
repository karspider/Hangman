import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
public class Word {
	
	static String[] sWordBank;
	String sWord;
	int iWordLength;
	char[] cGuesses;
	
	Word() 
	{
		Random r = new Random();
		try {
			sWordBank =  openFile("E:\\testpics\\words.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		sWord = sWordBank[r.nextInt(sWordBank.length)];
		iWordLength = sWord.length();
		cGuesses = new char[iWordLength];

	}

	public static int readLines(String path) throws IOException
	{
		int n = 0;
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		
		while(br.readLine() != null)
		{
			n++;
		}
		br.close();
		
		return n;
	}
	
	public static String[] openFile(String path) throws IOException
	{
		FileReader fr = new FileReader(path);
		BufferedReader textReader  = new BufferedReader(fr);
		
		int numberOfLines = readLines(path);

		String[] data = new String[numberOfLines];
		
		for(int i = 0; i < numberOfLines; i++)
		{
			data[i] = textReader.readLine();
		}
		
		textReader.close();
		return data;
	}

}
