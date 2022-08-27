package filediff;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class myframe extends JFrame implements ActionListener{
    
    /**
     * 
     */
    
    private static final long serialVersionUID = 1L;
    JFrame myframe = new JFrame("FileDiff ver 0.1");
    JPanel mypanel = new JPanel();    // this is main panel
    JPanel buttonpanel = new JPanel();
    public static JPanel showresults = new JPanel();
    JTextPane ep1 = new JTextPane();
    JTextPane ep2 = new JTextPane();
    JScrollPane editorScrollPane;
    JMenuBar menubar = new JMenuBar(); // this is the bar in which menus will be shown
    JMenu filemenu = new JMenu("File");  // this is the menuitem
    JMenuItem openitem= new JMenuItem("Open");  // all these three are submenuitems which would be added to menuitem
    JMenuItem saveitem= new JMenuItem("Save");
    JMenuItem exititem= new JMenuItem("Exit");
    JButton compare = new JButton("Compare");
    JButton copyselectedright = new JButton("Copy Selected =>");
    JButton copyselectedleft = new JButton("Copy Selected <=");
    JScrollPane scroll = new JScrollPane(ep1);
    JScrollPane scroll1 = new JScrollPane(ep2);
    int i;
    
    static File dir;
    static Boolean firstloaded=false, secondloaded=false;
    public static JTextArea resultleft =new JTextArea();
    public static JTextArea resultright =new JTextArea();
    public static JTextArea middle = new JTextArea();
    public static File firstfile,secondfile;
    public static String whichfile="";
    public static File fileoneforraf,filetwoforraf;
  
    String currentline="";
    
    int length;    // to store length of the file or anything
    ArrayList<String> file1list = new ArrayList<String>(1);
    ArrayList<String> file2list = new ArrayList<String>(1);	    
    
    
    public myframe(){

	// create the instance of dofurther class as it is use for further processing
	myframe.setLayout(null);
	mypanel.setLayout(null);
	
	myframe.setBounds(0, 0, 1000, 1000);  // full size of the frame
	mypanel.setBounds(0, 0, 1000,1000); // full size of panel
	ep1.setBounds(10, 0, 500,500);// text pane half size vertically split
	scroll.getBorder();
	scroll.setBounds(10,0,0,0);
	int verticalPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS;
	scroll.setVerticalScrollBarPolicy(verticalPolicy);
	ep2.setBounds(501,0, 500,500);// second text pane on right side
	//ep1.add(scroll);
	
	menubar.add(filemenu);
	filemenu.add(openitem);
	filemenu.add(saveitem);
	filemenu.add(exititem);
	
	filemenu.setMnemonic(KeyEvent.VK_F);
	openitem.setMnemonic(KeyEvent.VK_O);
	saveitem.setMnemonic(KeyEvent.VK_S);
	exititem.setMnemonic(KeyEvent.VK_X);
	
	openitem.addActionListener(this);
	saveitem.addActionListener(this);
	exititem.addActionListener(this);
	mypanel.setVisible(true);
	
	ep1.setEnabled(true);
	ep2.setEnabled(true);
	ep1.setEditable(true);
	ep2.setEditable(true);
	ep1.setVisible(true);
	ep2.setVisible(true);
	
	ep1.setFont(new Font("Arial", Font.PLAIN,14));
	
	buttonpanel.setLayout(new FlowLayout());
	showresults.setLayout(new GridLayout());
	
	resultleft.setLineWrap(true);
	resultright.setLineWrap(true);
	middle.setLineWrap(true);
	resultleft.setWrapStyleWord(true);
	resultright.setWrapStyleWord(true);
	middle.setWrapStyleWord(true);
	
	resultleft.setEditable(false);
	resultright.setEditable(false);
	middle.setEditable(false);
	
	resultleft.setBackground(Color.BLACK);
	resultright.setBackground(Color.BLACK);
	middle.setBackground(Color.BLACK);
	
	resultleft.setForeground(Color.WHITE);
	resultright.setForeground(Color.WHITE);
	middle.setForeground(Color.ORANGE);
	
	resultleft.setFont(new Font("Arial", Font.PLAIN,16));
	resultright.setFont(new Font("Arial", Font.PLAIN,16));
	middle.setFont(new Font("Arial", Font.BOLD,16));
	
	buttonpanel.setBounds(200,501, 610, 30);
	showresults.setBounds(0, 531, 1000, 459);
	
    buttonpanel.add(copyselectedright);
	buttonpanel.add(compare);
	buttonpanel.add(copyselectedleft);

	
	// initially disable all the buttons till the files are selected
	copyselectedleft.setEnabled(false);
	copyselectedright.setEnabled(false);
	compare.setEnabled(false);
	
	compare.addActionListener( this);
	copyselectedleft.addActionListener(this);
	copyselectedright.addActionListener(this);
	
	showresults.add(resultleft);
	showresults.add(middle);
	showresults.add(resultright);
	mypanel.add(showresults);
	mypanel.add(ep1);
	mypanel.add(ep2);
	mypanel.add(buttonpanel);
	mypanel.add(showresults);
	myframe.add(menubar);
	myframe.add(mypanel);
	myframe.setJMenuBar(menubar);
	
	myframe.setVisible(true);
	
	if (firstloaded && secondloaded)
	{
	    new dofurther();
	}

    } // end of constructor


    @Override
    public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub

    if(e.getSource()==copyselectedright)
	{
	  		StyledDocument doc = (StyledDocument) ep1.getDocument();
	    		int selectionEnd = ep1.getSelectionEnd();
	    		int selectionStart = ep1.getSelectionStart();
	    		if (selectionStart == selectionEnd)
	    		{
	    		    	return;
	    		}
	    		Element element = doc.getCharacterElement(selectionStart);
	    		AttributeSet as = element.getAttributes();

	    		MutableAttributeSet asNew = new SimpleAttributeSet(as.copyAttributes());
	    		StyleConstants.setBold(asNew, !StyleConstants.isBold(as));
	    		doc.setCharacterAttributes(selectionStart, ep1.getSelectedText().length(), asNew, true);
	}
	
	if(e.getSource()==copyselectedleft)
	{
	  		StyledDocument doc = (StyledDocument) ep2.getDocument();
	    		int selectionEnd = ep2.getSelectionEnd();
	    		int selectionStart = ep2.getSelectionStart();
	    		if (selectionStart == selectionEnd) {
	    		    	return;
	    			}
	    		Element element = doc.getCharacterElement(selectionStart);
	    		AttributeSet as = element.getAttributes();

	    		MutableAttributeSet asNew = new SimpleAttributeSet(as.copyAttributes());
	    		StyleConstants.setBold(asNew, !StyleConstants.isBold(as));
	    		doc.setCharacterAttributes(selectionStart, ep2.getSelectedText().length(), asNew, true);
		
	}
	if(e.getSource()==compare)
	{
	      ///JOptionPane.showMessageDialog(null, " inside compare ");
	    dofurther mydofurther2 = new dofurther(); 
	    mydofurther2.compareboth();
	    compareraf cf=new compareraf();
	    cf.fileselect(fileoneforraf);
	    cf.fileselect(filetwoforraf);
	   
	}
	
	if (e.getSource()==openitem)
	{
	 if(firstloaded==true & secondloaded==true)
	   {
				// clear the results
			resultleft.setText("");
			middle.setText("");
			resultright.setText("");
			firstloaded=false;
			secondloaded=false;
			dofurther.commonwords.clear();
			dofurther.wordsleft.clear();
			dofurther.wordsright.clear();
		}
	    if ((!firstloaded) || (!secondloaded)) 
	    {	    
	    	JFileChooser fileChoser=new JFileChooser(System.getProperty("user.dir","."));  // this will by default open the user current directory to choose the file
	    	fileChoser.setAcceptAllFileFilterUsed(false); // this means not all file types will be shown. only files which match your fileters defined will be shown
	    	fileChoser.addChoosableFileFilter(new FileNameExtensionFilter("*.text", "txt"));   // this will show only all txt file
	    	public static fileChoser;
	    	if ( fileChoser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) 
		   	{
	       		String fileName = fileChoser.getSelectedFile().getPath(); // this gives the OS file path
		   		dir = fileChoser.getSelectedFile(); // give the selected file in object format
		   		System.out.println(fileName);
		   	//	fileoneforraf=dir;
	    // end of approve option 
	   	   		try {
		 				FileReader filereader = new FileReader(dir); // read  the file
		   				BufferedReader breader = new BufferedReader(filereader);
           // before reading the file check which pane is empty
		   				if (!firstloaded)
		   				{
		   					firstfile=dir;
		   					fileoneforraf=dir;
		   					whichfile="first";
		   					ep1.read(breader, filereader);
	    					breader.close();
	    					filereader.close();
	    					dofurther mydofurther = new dofurther();
	    					mydofurther.parsefile(firstfile);
	    				//	filetwo=dir;
	    					firstloaded=true;
	    					dir=null;
	    			
	    				}
     // end of if fistloaded
		
	    				else if (firstloaded && secondloaded==false)
	    				{
	    					// since second file is loaded enable all buttons
	    					secondfile=dir;
	    					filetwoforraf=dir;
	    					whichfile="second";
	    					dofurther mydofurther1 = new dofurther();
	    					mydofurther1.parsefile(secondfile);
	    					secondfile=dir;
	    					copyselectedleft.setEnabled(true);
	    					copyselectedright.setEnabled(true);
	    					compare.setEnabled(true);
		    			
	    					ep2.read(breader, filereader);
	    					secondloaded=true;
		    				breader.close();
		    				filereader.close();
	    				}
	    
	
	//ep1.setEditable(true);
	 
	    		} catch (IOException  e2) {
	// TODO Auto-generated catch block
	    			e2.printStackTrace();

     // end of catch
    
	    			System.out.println("You opened a file");
	    
	    
	    		}  // end of catch 
	    
	    	} // file chose approve action
	    
	    	else
	    	{
	    		JOptionPane.showMessageDialog(mypanel, "You did not chose any file to open","Alert",JOptionPane.WARNING_MESSAGE );
	    	}
	
	    }  // end of checking that both files have been already loaded or not firstload and secondload
	} // end of openitem

	if (e.getSource()==saveitem)
		{System.out.println("You saved a file");}
	if (e.getSource()==exititem)
		{System.out.println("You exited program");}
	
    } // end of action event
    //end of loaded true if 

    private void parsefile(String filesequence) {
	
	// parse everything in the file
	Iterator <String> li =file1list.listIterator(); 
	if (filesequence.equalsIgnoreCase("first"))
	{
		System.out.println(file1list.size());
	    while (li.hasNext())
	    System.out.println("added  ----> "+ li.next());
	}
	// TODO Auto-generated method stub
	}
    } // end of class