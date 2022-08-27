package filediff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class compareraf {

     ArrayList<Long> positions;
     ArrayList <String> tosearch;
    Long current;
    static HashMap<String, ArrayList<Long>> wordregister;
  
   File fileName;
    Path filePath;
    Charset charset;
    RandomAccessFile myrafile; 
    String data = "";
    long fileoffset;
    char charread;
    String line, templine,tosearchstring;
   
    public compareraf()
    {
    	wordregister=new HashMap<String, ArrayList<Long>>(1);
    	positions=new ArrayList<Long>(1);
    	tosearch=new ArrayList<String>(1);
    	
    }
   
    public void fileselect(File fileone) {
		try {
	
		//	JOptionPane.showMessageDialog(null,dofurther.commonwords.size());
	                  fileName = fileone;
	                  
	                 // JOptionPane.showMessageDialog(null, fileone);
	    	    	// 'r' means opening file in Read mode  "rw" means read write mode
	                  myrafile = new RandomAccessFile(fileName, "r");
	                  myrafile.seek(0L);
	                  byte[] arr = new byte[(int) myrafile.length()];
	                  // read the file
	                  myrafile.readFully(arr);
	                  myrafile.seek(0L);
	                 // tosearch.addAll(dofurther.commonwords);
	                  for(String abc: dofurther.commonwords) {
	                	//  JOptionPane.showMessageDialog(null, abc);
	                	  this.tosearch.add(abc);
	                  }
	                  // moves pointer to the first location to read
	                for (String tempstring:tosearch)
	                {
	                	positions=new ArrayList<Long>(); // create this instance for each of the word otherwise it will simply overwrite older posted values also
	                	myrafile.seek(0); // reset pointer to the beginning of the file. 
	                	tosearchstring=tempstring.trim();
	                	current = 0L; 
	                
	                	while (current < myrafile.length()) 
	                	{
	                		current = myrafile.getFilePointer(); // update to the next location to be read this is important that it is done right at the begining
	     
	                    // read the file line by line
	                    //String result = myrafile.readUTF(); // this gives error
	                		String result = myrafile.readLine();
	                		templine=result;
	                		if (result!=null)
	                			if (result.contains(tosearchstring))
	                			{
	                				doparsegotline();
	                			} // end of nested iff
	
	                		}  // end of while
	                	doaddtomap();
	                	} // end of foreach tosearch
	        
	                myrafile.close();
	                System.out.println("Keys in the Map----->forfile"+fileone+wordregister.keySet());
	                System.out.println(wordregister.entrySet());
	              
				} catch (IOException e2) {
  		    	e2.printStackTrace();
  			} // end of catch
		
    }	//end of action performed

    private  void doaddtomap() {
	
	   //if (!wordregister.containsKey(tosearchstring))
		//	{
				//System.out.println("entered");
		   wordregister.put(tosearchstring, positions);
		
			//}
	   	
	// TODO Auto-generated method stub
	
		} // end of method doaddtomap
private  void doparsegotline() {

	    try
	    {
		//System.out.println("\n"+tosearchstring);
	    	positions.add(current);
	    	positions.add((long) templine.indexOf(tosearchstring));// current word position in the line
	   	} catch (Exception e1) 
	    {
		// TODO Auto-generated catch block
	    		e1.printStackTrace();
	    }// current line position
    	} // end of parsegotline  


} // end of class 
