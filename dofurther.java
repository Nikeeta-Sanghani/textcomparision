package filediff;

import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class dofurther {
    
    
    ArrayList<String> allraw = new ArrayList<String>(1); 
	static  ArrayList<String> wordsleft = new ArrayList<String>(1);
	static ArrayList<String> wordsright = new ArrayList<String>(1);
	static ArrayList<String> commonwords = new ArrayList<String>(1);
	ArrayList<String> worddifference = new ArrayList<String>(1);
	
	 String  templines ="";
	 int wordcount=0;
    static File temporary;
    
    public dofurther()
    {
	
	allraw.clear();
	//wordsright.clear();
	//wordsleft.clear();
	commonwords.clear();
	worddifference.clear();
	// default constructor
	// setup buttons 
    }

    public void parsefile(File temporary ) {
	
	// parse everything in the file
	FileReader filereader;
	BufferedReader bufferedreader;
	String line;
	
	try {
	    filereader = new FileReader(temporary);
	    bufferedreader=new BufferedReader(filereader);
	    
	    while ((line=bufferedreader.readLine())!=null)
	    {
	    	if (!line.isEmpty())
	    	{
	    		allraw.add(line);
	    	}
	    	else 
	    		continue;
	    }
	    
	    dowordposting();
	    bufferedreader.close();
	    filereader.close();
		} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
		} // end of filenotfound exception
		catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} // end of IO exception catch
	
    } // end of method


    private  void dowordposting() {
	
	
	// first split lines into words till you reach end of lines in arraylist
	 // below iterator is short way of doing things
	   
	   for (String allrawtemp:allraw)
	   {
	       templines=allrawtemp;
	       doaddwords();
	   }
	   dofiledetails();
    }
	   
    private   void doaddwords() {
	String[] tempword;
	
	templines.trim();
	tempword=templines.split("[ ]+"); // best way to remove all the spaces in between the words.
	if ((myframe.whichfile).equals("first"))
		{
	    	//populate in words left   
	    for (String strtmp:tempword)   // means for each element in the string type array of templines it will iterate and store that work in words arraylist
		if (wordsleft.contains(strtmp))
		{
		    wordcount++;
		}
		else {
		    wordsleft.add(strtmp);
		    wordcount++;
		}
	     }  // end of if whichfile
	 		
	else if ((myframe.whichfile).equals("second"))
	{
	    for (String strtmp:tempword)    // means for each element in the string type array of templines it will iterate and store that work in words arraylist
		if (wordsright.contains(strtmp))
		{
		    wordcount++;
		    
		}
		else {
		    wordsright.add(strtmp);
		    wordcount++;
		}
	
	} // end of else if
	
    }
    private   void dofiledetails() {

	if ((myframe.whichfile).equals("first"))
	    {
	   
	    (myframe.resultleft).append("File name is ----->"+"    "+myframe.firstfile.getName()+"\n");
	    (myframe.resultleft).append("Path---->"+"    "+ myframe.firstfile.getPath()+"\n");
	    (myframe.resultleft).append("Total Words ------>"+"    "+wordcount+"\n");
	    (myframe.resultleft).append("length----------->"+"    "+ myframe.firstfile.length()+"\n");
	   
	    } // end of which file 
	
	else if (myframe.whichfile.equals("second"))
	    
	{
	    
	    (myframe.resultright).append("File name is ----->"+"    "+myframe.secondfile.getName()+"\n");
	    (myframe.resultright).append("Path---->"+"    "+ myframe.secondfile.getPath()+"\n");
	    (myframe.resultright).append("Total Words ------>"+"    "+wordcount+"\n");
	    (myframe.resultright).append("length----------->"+"    "+ myframe.secondfile.length()+"\n");
	    
	}

    }
    public   void compareboth() {
	
	Collections.sort(wordsleft);
	Collections.sort(wordsright);
	String templeft="", tempright="";
	int i=0;
	if (wordsleft.size()<=wordsright.size())
	{
	       for (String strtmp11:wordsleft)
	   {
	       tempright=strtmp11;
		    for (String strtmp111:wordsright)
	       	{
	       	    templeft=strtmp111;
	       	    if(tempright.equals(templeft))
	       	    {
	       	    	commonwords.add(templeft);
	       	    
	       	    } // end of if equals
	       	} // end of inner while
		
	    }  // end of out while 
	// TODO Auto-generated method stub
	    myframe.middle.append("Following are common words in both files\n");
	    myframe.middle.append("===============================\n");
	    for (String strtemp2:commonwords)
	    {
		myframe.middle.append(strtemp2+"\n");
	    }
	} //
	
	else
	    if (wordsright.size()<wordsleft.size())
		{
		   for (String strtmp11:wordsright)
		   {
		       tempright=strtmp11;
			    
			    for (String strtmp111:wordsleft)
		       	{
		       	    templeft=strtmp111;
		       	        
		       	    if(tempright.equals(templeft))
				{
		       		commonwords.add(templeft);
				} // end of if equals
			} // end of inner while
			
		    }  // end of out while 
		     
		// TODO Auto-generated method stub
		    myframe.middle.append("Following are common words in both files\n");
		    myframe.middle.append("===============================\n");
		    for (String strtemp2:commonwords)
		    {
			myframe.middle.append(strtemp2+"\n");
		    }
		} //
    
    }  // end of method
}
