/*
 * Created on 01.10.2004
 * Copyright (c) 2004 by Christian Dietrich, Boris Leidner, 
 * Jan Gall and Sammy Okasha
 *
 * This file is part of warpainting.
 *
 * warpainting is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * warpainting is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package gui;

import java.awt.*;
import java.awt.event.*;


import javax.swing.*;

/**
 * @author jan, chris
 * Creates, initializes, displays and manages the main window of the application.
 */
public class MainFrame {
	
	/* the jpanel with the map, which gets the commands for display update etc. */
	MapPanel mapPanel;
	
	/**
     * Creates a new GridBagConstraints-object for a given range of cells.
     * @param x the column in GridBagLayout
     * @param y the row in GridBagLayout
     * @param width the width in GridBagLayout
     * @param height the height in GridBagLayout
     * @return the created GridBagConstraints-object
     */
    private GridBagConstraints makeGBC(int x, int y, int width, int height) {
    	GridBagConstraints constraints = new GridBagConstraints();
    	constraints.gridx = x;
    	constraints.gridy = y;
    	constraints.gridwidth = width;
    	constraints.gridheight = height;
    	constraints.anchor = GridBagConstraints.CENTER;
    	constraints.fill = GridBagConstraints.NONE;
    	constraints.insets = new Insets(5, 5, 5, 5);
    	return constraints;
    }
    
    /**
     * Creates a panel with all components displayed in the main window.
     * @param mapname the name of a map on disc
     * @return a panel containing all components of the main window
     */
	public Component createComponents(String mapname) {
		// create the MapPanel
		mapPanel = new MapPanel(mapname);
		// create the mapScrollPane
		JScrollPane mapScrollPane = new JScrollPane(mapPanel);
		mapScrollPane.getVerticalScrollBar().setUnitIncrement(10);
		mapScrollPane.getHorizontalScrollBar().setUnitIncrement(10);
		
		/* here i am trying to give an idea of how the main application
		   window should look like
		 
		 +----------------------------------------------------------------+
		 |                                            |  rightSidePanel   |
		 |                                            | +---------------+ |
		 |                                            | | loadPanel     | |
		 |                                            | +----+------+---+ |
		 |                                            | |     \      \  | |
		 |                                            | | tab1 | tab2 | | |
		 |                                            | +------+------+-+ |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 |                                            |                   |
		 +----------------------------------------------------------------+
		 
		 */
		// the panel for all the stuff on the right side
		JPanel rightsidePanel = new JPanel(new GridLayout(3, 1));
		
		// create the container load panel
		JPanel loadPanel = new JPanel(new GridLayout(3, 1));
		loadPanel.setBorder(BorderFactory.createTitledBorder("Load"));
		
		// create the buttons to load gps data etc.
		JButton loadGpsButton = new JButton("Load GPS track");
		loadPanel.add(loadGpsButton);
		loadGpsButton.setMnemonic(KeyEvent.VK_G);
		loadGpsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
			    StringFileFilter filter = new StringFileFilter();
			    filter.addExtension("gps");
			    filter.addExtension("xml");
			    filter.setDescription("GPS or XML files");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       //System.out.println("You chose to open this file: " +
			       //     chooser.getSelectedFile().getName());
			       mapPanel.loadGPSfile(chooser.getSelectedFile());
			    }
			}
		});
		loadPanel.add(loadGpsButton);
		
		// create the buttons to load xml data etc.
		JButton loadNetsButton = new JButton("Load WLANs");
		loadPanel.add(loadNetsButton);
		loadNetsButton.setMnemonic(KeyEvent.VK_W);
		loadNetsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
			    StringFileFilter filter = new StringFileFilter();
			    filter.addExtension("gps");
			    filter.addExtension("xml");
			    filter.setDescription("GPS or XML files");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       //System.out.println("You chose to open this file: " +
			       //     chooser.getSelectedFile().getName());
			       mapPanel.loadXMLfile(chooser.getSelectedFile());
			    }
			}
		});
		
		//	 create the buttons to load gps data etc.
		JButton exitButton = new JButton(" Exit Application ");
		loadPanel.add(exitButton);
		exitButton.setMnemonic(KeyEvent.VK_X);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		
		rightsidePanel.add(loadPanel);
		
		
		//create the container options panel
		JPanel optionsPanel = new JPanel(new GridLayout(2, 1));
		optionsPanel.setBorder(BorderFactory.createTitledBorder("Display Options"));
		
		//create the check boxes
		final JCheckBox trackButton = new JCheckBox("Display track");
		trackButton.setMnemonic(KeyEvent.VK_T);
		trackButton.setSelected(true);
		trackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapPanel.displayTrack(trackButton.isSelected());
			}
		});
		final JCheckBox encryptedAPsButton = new JCheckBox("WEP Encryption");
		encryptedAPsButton.setMnemonic(KeyEvent.VK_E);
		encryptedAPsButton.setSelected(true);
		encryptedAPsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapPanel.displayWep(encryptedAPsButton.isSelected());
			}
		});
		final JCheckBox decryptedAPsButton = new JCheckBox("No WEP Encryption");
		decryptedAPsButton.setMnemonic(KeyEvent.VK_N);
		decryptedAPsButton.setSelected(true);
		decryptedAPsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapPanel.displayNonWep(decryptedAPsButton.isSelected());
			}
		});
		final JCheckBox slowNetsButton = new JCheckBox("<= 11 MBit/s");
		slowNetsButton.setMnemonic(KeyEvent.VK_S);
		slowNetsButton.setSelected(true);
		slowNetsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapPanel.displaySlowNets(slowNetsButton.isSelected());
			}
		});
		final JCheckBox fastNetsButton = new JCheckBox("> 11 MBit/s");
		fastNetsButton.setMnemonic(KeyEvent.VK_F);
		fastNetsButton.setSelected(true);
		fastNetsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapPanel.displayFastNets(fastNetsButton.isSelected());
			}
		});
		
		// put the check boxes in a column in a panel and put it in optionsPanel
		JPanel checkPanel = new JPanel(new GridLayout(4, 1));
		JPanel drawElementsPanel = new JPanel(new GridLayout(1, 1));
		checkPanel.setBorder(BorderFactory.createEtchedBorder());
		drawElementsPanel.add(trackButton);
		//drawElementsPanel.add(wlanButton);
		checkPanel.setBorder(BorderFactory.createTitledBorder("Security"));
		drawElementsPanel.setBorder(BorderFactory.createTitledBorder("Draw Elements"));
		checkPanel.add(encryptedAPsButton);
		checkPanel.add(decryptedAPsButton);
		checkPanel.add(slowNetsButton);
		checkPanel.add(fastNetsButton);
		
		optionsPanel.add(drawElementsPanel);
		optionsPanel.add(checkPanel);
		
		
		// create the radio buttons
		/*JRadioButton ap11MBitButton = new JRadioButton("11 MBit");
		ap11MBitButton.setMnemonic(KeyEvent.VK_1);
		JRadioButton ap54MBitButton = new JRadioButton("54 MBit");
		ap54MBitButton.setMnemonic(KeyEvent.VK_5);
		JRadioButton apAllButton = new JRadioButton("all");
		apAllButton.setMnemonic(KeyEvent.VK_A);
		apAllButton.setSelected(true);
		// group the radio buttons
		ButtonGroup group = new ButtonGroup();
		group.add(ap11MBitButton);
		group.add(ap54MBitButton);
		group.add(apAllButton);
		
		// put the radio buttons in a column in a panel and add it to optionsPanel
		JPanel radioPanel = new JPanel(new GridLayout(0, 1));
		radioPanel.setBorder(BorderFactory.createEtchedBorder());
		radioPanel.add(ap11MBitButton);
		radioPanel.add(ap54MBitButton);
		radioPanel.add(apAllButton);
		optionsConstraints = makeGBC(0, 1, 1, 1);
		optionsLayout.setConstraints(radioPanel, optionsConstraints);
		optionsPanel.add(radioPanel);
		*/
		JButton updateButton = new JButton("Update");
		updateButton.setMnemonic(KeyEvent.VK_U);
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// let's see what the checkboxes look like
				
			}
		});
		
		//optionsPanel.add(updateButton);
		
		JPanel spinnerPanel = new JPanel(new BorderLayout());
		JLabel scaleLabel = new JLabel("Scale");
		SpinnerModel spinnerModel = new SpinnerNumberModel(new Integer(11800), new Integer(0), new Integer(100000), new Integer(100));
		JSpinner spinner = new JSpinner(spinnerModel);
		spinnerPanel.add(scaleLabel, BorderLayout.NORTH);
		spinnerPanel.add(spinner, BorderLayout.SOUTH);
		rightsidePanel.add(spinnerPanel);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createRaisedBevelBorder());
		
		panel.add(mapScrollPane, BorderLayout.CENTER);
		panel.add(new JScrollPane(rightsidePanel), BorderLayout.EAST);
		rightsidePanel.add(optionsPanel);
		rightsidePanel.setBackground(Color.GRAY);
		return panel;
	}
	
	/**
	 * Creates the main window.
	 * @param windowName the title of the window
	 * @param mapname the name of a map on disc
	 */
    public MainFrame(String windowName, String mapname) {
    	//String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
    	/*
	String lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
    	try {
    		UIManager.setLookAndFeel(lookAndFeel);
    	} catch (Exception e) { e.printStackTrace(); }
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
	*/
        //Create and set up the window.
        JFrame frame = new JFrame(windowName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Component contents = this.createComponents(mapname);
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
}
