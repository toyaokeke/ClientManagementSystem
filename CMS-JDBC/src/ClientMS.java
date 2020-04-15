import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;

public class ClientMS extends JFrame {

	private JPanel northPanel, eastPanel, westPanel, westTopPanel, westBotPanel, wtpBot, centerPanel, southPanel, panelLabel;
	private JPanel ep1, ep2, ep3, ep4, ep5, ep6, ep7, ep8;
	private JLabel el1, el2, el3, el4, el5, el6, el7;
	JTextField et1, et2, et3, et4, et5, et6;
	private JLabel heading, westTopPanelLabel, searchText, searchPar, searchResultText, eastLabel, footer; 
	JRadioButton clientId;
	JRadioButton lastName;
	JRadioButton clientType;
	private Container ctn;
	private BorderLayout mainLayout, westLayout;
	private BoxLayout wtp, eastLayout;
	private FlowLayout wtpBptLayout;
	JTextField searchBox;
	JButton searchButton, clearSearch, eb1, eb2, eb3, eb4;
	JList textArea;
	private JScrollPane scrollWindow;
	JComboBox dropDown;
	private String [] dropDownList = {" ", "R", "C"};
	private ButtonGroup group;
	private Font font, southFont, idFont;
	
	/**
	 * Create the frame.
	 */
	public ClientMS() {
		/**
		 * Creating the view of the main frame 
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 800, 600);
		mainLayout = new BorderLayout(7,7);
		setLayout(mainLayout);
		ctn = new Container();
		ctn = getContentPane();
		ctn.setBackground(Color.WHITE);
		
		/**
		 * This creates the label for the frame
		 */
		northPanel = new JPanel(); 
		heading = new JLabel ("Client Management System");
		font = new Font("Serif", Font.BOLD, 22);
		//font.deriveFont(font.getStyle() ^ Font.BOLD);
		heading.setFont(font);
		heading.setForeground(Color.WHITE);
		northPanel.add(heading);
		northPanel.setBackground(Color.DARK_GRAY);
		
		/**
		 * This creates the West Panel
		 */
		westPanel = new JPanel();
		westTopPanel = new JPanel();
		westBotPanel = new JPanel();
		wtpBot = new JPanel();
		panelLabel = new JPanel();
		westTopPanelLabel = new JLabel("Search Clients");
		westTopPanelLabel.setForeground(Color.WHITE);
		//westTopPanelLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelLabel.add(westTopPanelLabel);
		//westTopPanelLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelLabel.setBackground(Color.DARK_GRAY);
		
		searchText = new JLabel("Select type of search to be performed:");
		searchPar = new JLabel ("Enter the search parameter below:");
		
		clientId = new JRadioButton("Client ID");
		lastName = new JRadioButton("Last Name");
		clientType = new JRadioButton("Client Type");
		group = new ButtonGroup();
		group.add(clientId);
		group.add(lastName);
		group.add(clientType);
		westLayout = new BorderLayout();
		wtp = new BoxLayout(westTopPanel, BoxLayout.PAGE_AXIS);
		
		
		wtpBot.setLayout(new FlowLayout());
		searchBox = new JTextField();
		searchBox.setPreferredSize( new Dimension(100, 24));
		searchButton = new JButton("Search");
		clearSearch = new JButton("Clear Search");
		
		//wtpBot.add(Box.createRigidArea(new Dimension(50,0)));
		wtpBot.add(searchBox);
		//wtpBot.add(Box.createRigidArea(new Dimension(10,0)));
		wtpBot.add(searchButton);
//		wtpBot.add(Box.createRigidArea(new Dimension(10,0)));
		wtpBot.add(clearSearch);
		wtpBot.setBackground(Color.DARK_GRAY);
		
		/**
		 * This builds the upper west layout
		 */
		westTopPanel.setBackground(Color.GRAY);
		westTopPanel.setLayout(wtp);
		
		westTopPanel.add(panelLabel);
		//westTopPanel.add(Box.createRigidArea(new Dimension(0,10)));
		//searchText.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		westTopPanel.add(searchText);
		//searchText.setAlignmentY(Component.LEFT_ALIGNMENT);
		westTopPanel.add(Box.createRigidArea(new Dimension(0,10)));
		westTopPanel.add(clientId);
		westTopPanel.add(Box.createRigidArea(new Dimension(0,10)));
		westTopPanel.add(lastName);
		westTopPanel.add(Box.createRigidArea(new Dimension(0,10)));
		westTopPanel.add(clientType);
		westTopPanel.add(Box.createRigidArea(new Dimension(0,50)));
		westTopPanel.add(searchPar);
		westTopPanel.add(Box.createRigidArea(new Dimension(0,10)));
		//westTopPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		westTopPanel.add(wtpBot);
		
		westBotPanel.setLayout(new BoxLayout(westBotPanel, BoxLayout.Y_AXIS));
		
		
		//addText = new DefaultListModel();
		textArea = new JList();
		//textArea.setEditable(false);
		//textArea.setPreferredSize(new Dimension(250,250));
		//textArea.setLineWrap(true);
		//textArea.setWrapStyleWord(true);
		scrollWindow = new JScrollPane(textArea);
		scrollWindow.setPreferredSize(new Dimension(10, 190));
		//scrollWindow.setViewportView(textArea);
		searchResultText = new JLabel("Search Results");
		searchResultText.setForeground(Color.WHITE);
		
		westBotPanel.add(searchResultText);
		westBotPanel.setBackground(Color.DARK_GRAY);
		westBotPanel.add(scrollWindow);
		
		westPanel.setLayout(westLayout);
		westPanel.add(westTopPanel, BorderLayout.NORTH);
		westPanel.add(westBotPanel, BorderLayout.SOUTH);
		
		/**
		 * Building the east panel
		 */
		ep1 = new JPanel();
		el1 = new JLabel("Client ID:");
		et1 = new JTextField(10);
		et1.setEnabled(false);
		idFont = new Font("Serif", Font.BOLD, 15);
		et1.setFont(idFont);
		et1.setBackground(Color.RED);
		//et1.setFont(font);
		ep1.setLayout(new FlowLayout());
		ep1.setBackground(Color.GRAY);
		ep1.add(el1);
		ep1.add(et1);
		
		ep2 = new JPanel();
		el2 = new JLabel("First Name:");
		el2.setForeground(Color.WHITE);
		et2 = new JTextField(10);
		ep2.setLayout(new FlowLayout());
		ep2.setBackground(Color.DARK_GRAY);
		ep2.add(el2);
		ep2.add(et2);
		
		ep3 = new JPanel();
		el3 = new JLabel("Last Name:");
		et3 = new JTextField(10);
		ep3.setLayout(new FlowLayout());
		el3.setBackground(Color.WHITE);
		ep3.setBackground(Color.GRAY);
		ep3.add(el3);
		ep3.add(et3);
		
		ep4 = new JPanel();
		el4 = new JLabel("Address:");
		el4.setForeground(Color.WHITE);
		et4 = new JTextField(10);
		ep4.setLayout(new FlowLayout());
		ep4.setBackground(Color.DARK_GRAY);
		ep4.add(el4);
		ep4.add(et4);
		
		ep5 = new JPanel();
		el5 = new JLabel("Postal Code:");
		et5 = new JTextField(10);
		ep5.setLayout(new FlowLayout());
		ep5.setBackground(Color.GRAY);
		ep5.add(el5);
		ep5.add(et5);
		
		ep6 = new JPanel();
		el6 = new JLabel("Phone Number:");
		el6.setForeground(Color.WHITE);
		et6 = new JTextField(10);
		ep6.setLayout(new FlowLayout());
		ep6.setBackground(Color.DARK_GRAY);
		ep6.add(el6);
		ep6.add(et6);
		
		ep7 = new JPanel();
		el7 = new JLabel("Client Type:");
		dropDown = new JComboBox(dropDownList);
		ep7.setLayout(new FlowLayout());
		ep7.setBackground(Color.GRAY);
		ep7.add(el7);
		ep7.add(dropDown);
		
		ep8 = new JPanel();
		eb1 = new JButton("Add");
		eb2 = new JButton("Delete");
		eb3 = new JButton("Modify");
		eb4 = new JButton("Clear");
		
		ep8.setLayout(new FlowLayout());
		ep8.setBackground(Color.DARK_GRAY);
		ep8.add(eb1);
		ep8.add(eb2);
		ep8.add(eb3);
		ep8.add(eb4);
		
		eastPanel = new JPanel();
		eastLabel = new JLabel("Client Information");
		eastLabel.setForeground(Color.WHITE);
		eastLayout = new BoxLayout(eastPanel, BoxLayout.PAGE_AXIS);

		eastPanel.setBackground(Color.DARK_GRAY);
		eastPanel.setLayout(eastLayout);
		eastPanel.add(eastLabel);
		//eastPanel.add(Box.createRigidArea(new Dimension(0,10)));
		eastPanel.add(ep1);
		//eastPanel.add(Box.createRigidArea(new Dimension(0,10)));
		eastPanel.add(ep2);
		eastPanel.add(ep3);
		eastPanel.add(ep4);
		eastPanel.add(ep5);
		eastPanel.add(ep6);
		eastPanel.add(ep7);
		eastPanel.add(ep8);
		
		southPanel = new JPanel();
		footer = new JLabel ("Client Management System");
		southFont = new Font("Serif", Font.BOLD, 22);
		//font.deriveFont(font.getStyle() ^ Font.BOLD);
		footer.setFont(southFont);
		footer.setForeground(Color.WHITE);
		southPanel.add(footer);
		southPanel.setBackground(Color.DARK_GRAY);
		//southPanel.add(heading);
		
		centerPanel = new JPanel();
		centerPanel.setMaximumSize(new Dimension(100, 100));
		centerPanel.setBackground(Color.BLACK);
		/**
		 * Adding the panels to the Main frame 
		 */
		ctn.add(northPanel, BorderLayout.NORTH);
		ctn.add(westPanel, BorderLayout.CENTER);
		ctn.add(eastPanel, BorderLayout.EAST);
		//ctn.add(centerPanel, BorderLayout.WEST);
		ctn.add(southPanel, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	void registerRadioButtonListener1 (ActionListener ac){
		clientId.addActionListener(ac);
		
	}
	
	void registerRadioButtonListener2(ActionListener ac){
		lastName.addActionListener(ac);
		
	}
	
	void registerRadioButtonListener3 (ActionListener ac){
		clientType.addActionListener(ac);
	}

	void registerSearchListener1(ActionListener ac) {
		searchButton.addActionListener(ac);
		clearSearch.addActionListener(ac);
	}
	
	void registerSearchListener2(ActionListener ac) {
		searchButton.addActionListener(ac);
		clearSearch.addActionListener(ac);
	}
	
	void registerSearchListener3(ActionListener ac) {
		searchButton.addActionListener(ac);
		clearSearch.addActionListener(ac);
	}
	
	void registerTextAreaListener(MouseListener ac) {
		textArea.addMouseListener(ac);
	}
	
	void registerModifyListeners(ActionListener ac) {
		eb1.addActionListener(ac);
		eb2.addActionListener(ac);
		eb3.addActionListener(ac);
		eb4.addActionListener(ac);
	}
	
	void closeWIndow (WindowListener ac) {
		this.addWindowListener(ac);
	}
	
	void clientIdMessage(MouseListener ac) {
		et1.addMouseListener(ac);
	}

}
