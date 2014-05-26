package lifepath;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultEditorKit;



/**
 * A simple GUI for the Life Path creator. 
 * @author terrasaur
 *
 */
public class LifePathGUI extends JFrame implements ActionListener, 
									ItemListener, ListSelectionListener {

	private static final long serialVersionUID = 1L;
	private JPanel    mainPanel;
	private JButton   newCharacterButton;
	private JButton   clearTextButton;
	private JButton   writeToFileButton;
	private JTextArea display;
	private LifePath  lifePath;
	private JPanel    optionsPanel;
	private JCheckBox chooseSkillsButton;
	private JCheckBox getRandomMotivationsButton;
	private boolean   randomMotivations = false;
	private boolean   chooseSkills      = false;
	private JList<String>     backgroundSelect;
	private ArrayList<String> backgrounds; 
	private JList<String>     factionSelect;
	private ArrayList<String> factions;
	
	/**
	 * The initializer of the GUI. This does most of the work.
	 */
	public LifePathGUI(){		
		this.setTitle("Transhuman Life Path Generator");
		
		// Setting up the main layout
		this.mainPanel = new JPanel();	
		SpringLayout layout = new SpringLayout();
		this.mainPanel.setLayout(layout);
		
		// Options panel
		this.optionsPanel = new JPanel();
		this.optionsPanel.setLayout(new BoxLayout(this.optionsPanel, BoxLayout.PAGE_AXIS));
		
		this.getRandomMotivationsButton = new JCheckBox("Randomize motivations");
		//this.getRandomMotivationsButton.setSelected(true);
		this.getRandomMotivationsButton.addItemListener(this);				
		this.optionsPanel.add(this.getRandomMotivationsButton);
				
		this.chooseSkillsButton = new JCheckBox("Assign skills randomly");
		this.chooseSkillsButton.addItemListener(this);
		this.optionsPanel.add(this.chooseSkillsButton);	
		
		this.optionsPanel.add(new JLabel("Allowed Backgrounds"));

		DefaultListModel<String> bgList = new DefaultListModel<String>();
		bgList.addElement("(Select All)");
		bgList.addElement("Earthborn");
		bgList.addElement("Orbital");
		bgList.addElement("Lunar");
		bgList.addElement("Martian Settler");
		bgList.addElement("Sunward Hab");
		bgList.addElement("Rimward Hab");
		bgList.addElement("Migrant");
		bgList.addElement("Created"); // Index 7
		bgList.addElement("   AGI");
		bgList.addElement("   Lost Generation");
		bgList.addElement("   Uplift");
		bgList.addElement("Colonial Staff");
		this.backgroundSelect = new JList<String>(bgList);
		
		this.backgroundSelect.setSelectionInterval(0, bgList.size()-1);
		this.backgrounds = new ArrayList<String>(this.backgroundSelect.getSelectedValuesList());
		this.backgroundSelect.setVisibleRowCount(-1);
		this.backgroundSelect.addListSelectionListener(this);
		this.backgroundSelect.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane bgScroller = new JScrollPane(this.backgroundSelect);
		bgScroller.setPreferredSize(new Dimension(150, 237));
		this.optionsPanel.add(bgScroller);

		//this.selectFocus; // TODO: NYI

		this.optionsPanel.add(new JLabel(" "));
		this.optionsPanel.add(new JLabel("Allowed Factions"));
		DefaultListModel<String> factList = new DefaultListModel<String>();
		factList.addElement("(Select All)");
		factList.addElement("Autonomist");
		factList.addElement("Civilian");
		factList.addElement("Criminal");
		factList.addElement("Elite");
		factList.addElement("Enclaver");
		factList.addElement("Indenture");
		factList.addElement("Military");
		factList.addElement("Scientist");
		factList.addElement("Spacer");
		factList.addElement("Techie");
		factList.addElement("Mercurial");
		this.factionSelect = new JList<String>(factList);
		this.factionSelect.setSelectionInterval(0, factList.size()-1);
		this.factions = new ArrayList<String>(this.factionSelect.getSelectedValuesList());
		this.factionSelect.setVisibleRowCount(-1);
		this.factionSelect.addListSelectionListener(this);
		this.factionSelect.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane factScroller = new JScrollPane(this.factionSelect);
		factScroller.setPreferredSize(new Dimension(150, 219));
		this.optionsPanel.add(factScroller);	
		
		
		this.mainPanel.add(this.optionsPanel);
				
		this.newCharacterButton = new JButton("New Character");		
		this.newCharacterButton.addActionListener(this);
		this.mainPanel.add(this.newCharacterButton);
		this.clearTextButton = new JButton("Clear Text");		
		this.clearTextButton.addActionListener(this);
		this.mainPanel.add(this.clearTextButton);
		
		this.writeToFileButton = new JButton("Write to File");
		this.writeToFileButton.addActionListener(this); 
		
		this.display = new JTextArea();
		this.display.setLineWrap(true);
		this.display.setMargin(new Insets(3,3,3,3));
		this.display.setAlignmentY(TOP_ALIGNMENT);
		this.display.setFont(new Font("monospaced", Font.PLAIN, 12));
	    MouseListener popupListener = new PopupListener();
	    display.addMouseListener(popupListener);
	    
		JScrollPane scrollbar = new JScrollPane(this.display);

		this.mainPanel.add(scrollbar);

		// Springs: the two buttons should be 12px right of the west edge of the main 
		// panel, and 12 px left of the west of the text panel
		layout.putConstraint(SpringLayout.WEST, this.newCharacterButton, 12, 
				SpringLayout.WEST, this.mainPanel);
		layout.putConstraint(SpringLayout.EAST, this.newCharacterButton, -12, 
				SpringLayout.WEST, scrollbar);
		layout.putConstraint(SpringLayout.NORTH, this.newCharacterButton, 5, 
				SpringLayout.NORTH, this.mainPanel);
		
		layout.putConstraint(SpringLayout.WEST, this.clearTextButton, 12, 
				SpringLayout.WEST, this.mainPanel);
		layout.putConstraint(SpringLayout.EAST, this.clearTextButton, -12, 
				SpringLayout.WEST, scrollbar);
		layout.putConstraint(SpringLayout.NORTH, this.clearTextButton, 5, 
				SpringLayout.SOUTH, this.newCharacterButton);
		
		// The options panel is the widest element, so it only needs to spring 
		// from the west edge of the main panel
		layout.putConstraint(SpringLayout.WEST, this.optionsPanel, 5, 
				SpringLayout.WEST, this.mainPanel);
		layout.putConstraint(SpringLayout.EAST, this.optionsPanel, 175, 
				SpringLayout.WEST, this.mainPanel);
		layout.putConstraint(SpringLayout.NORTH, this.optionsPanel, 5, 
				SpringLayout.SOUTH, this.clearTextButton);
		
		// Since the options panel is widest, the text area springs from it
		layout.putConstraint(SpringLayout.WEST, scrollbar, 10, 
				SpringLayout.EAST, this.optionsPanel);
		layout.putConstraint(SpringLayout.NORTH, scrollbar, 5, 
				SpringLayout.NORTH, this.mainPanel);
		
		// The text area should anchor in the southeast corner of the main panel
		layout.putConstraint(SpringLayout.EAST, this.mainPanel, 5, 
				SpringLayout.EAST, scrollbar);
		layout.putConstraint(SpringLayout.SOUTH, this.mainPanel, 5, 
				SpringLayout.SOUTH, scrollbar);
		
		this.mainPanel.setSize(800, 662);
		this.add(this.mainPanel);
		
		this.setSize(800, 662);
		this.setLocation(50, 50);
		//this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.setVisible(true);
	}
	

	private JPopupMenu getCopyPopup(){
		JPopupMenu copyMenu = new JPopupMenu();
		Action copyAction = this.display.getActionMap().get(DefaultEditorKit.copyAction);
		copyAction.putValue(Action.NAME, "Copy");
		copyMenu.add(copyAction);
		
		copyMenu.validate();
		
		return copyMenu;
	}
	
	class PopupListener extends MouseAdapter {

	    @Override
		public void mouseReleased(MouseEvent e) {	        
	        if (e.isPopupTrigger()) {	        	
	        	getCopyPopup().show(e.getComponent(),
	                       e.getX(), e.getY());
	        }
	    }

	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if (source == this.newCharacterButton){
			this.lifePath = new LifePath();
			this.lifePath.setUserOptions(this.randomMotivations, this.chooseSkills, 
					this.backgrounds, this.factions); //TODO: this.focuses);
			this.lifePath.generateLifePath();
			this.display.append(this.lifePath.getText());
			this.display.setCaretPosition(this.display.getDocument().getLength());
			/*if (this.chooseSkills)
				this.display.append("**Note: Selecting 'choose skills randomly' often results in strange \n"
						+ "    stuff. You have been warned.**\n");*/
		} else if (source == this.clearTextButton){
			this.display.setText("");
		} 		
	}
	
	@Override
	public void itemStateChanged(ItemEvent event){
		Object source = event.getSource();
		if (source == this.chooseSkillsButton){
			if (event.getStateChange() == ItemEvent.DESELECTED){
				this.chooseSkills = false;
			} else {
				this.chooseSkills = true;
			}
		} else if (source == this.getRandomMotivationsButton){
			if (event.getStateChange() == ItemEvent.DESELECTED){
				this.randomMotivations = false;
			} else {
				this.randomMotivations = true;
			}
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent event) {
		Object source = event.getSource();
		if (source == this.backgroundSelect){
			if (this.backgroundSelect.isSelectedIndex(0)){
				this.backgroundSelect.setSelectionInterval(0, 12);
			} else if (this.backgroundSelect.isSelectedIndex(8)){ // Created
				this.backgroundSelect.addSelectionInterval(8, 11);
			} 			
			this.backgrounds = new ArrayList<String>(this.backgroundSelect.getSelectedValuesList());
			//this.display.append("Selected:" + this.backgrounds);
		} else if (source == this.factionSelect){
			if (this.factionSelect.isSelectedIndex(0)){
				this.factionSelect.setSelectionInterval(0, 11);
			}
			this.factions = new ArrayList<String>(this.factionSelect.getSelectedValuesList());
		}
	}

		
	private void setLifePath(LifePath path) {
		this.lifePath = path;		
	}
		
	
	public static void main(String[] args) {
		LifePath path = new LifePath();
		LifePathGUI gui = new LifePathGUI();
		
		gui.setLifePath(path);		
		
	}

}
