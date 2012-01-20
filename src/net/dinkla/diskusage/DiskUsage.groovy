/*
	Copyright (c) 2007, 2008 Joern Dinkla, www.dinkla.net, joern@dinkla.net

	This program is free software; you can redistribute it and/or
	modify it under the terms of the GNU General Public License
	as published by the Free Software Foundation; either version 2
	of the License, or (at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program; if not, write to the Free Software
	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
*/

package net.dinkla.diskusage;

import groovy.swing.SwingBuilder
import java.awt.*
import java.awt.GridLayout
import java.awt.event.ComponentListener
import java.awt.event.WindowListener
import java.beans.PropertyChangeListener
import java.lang.reflect.Method
import javax.swing.WindowConstants as WC
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreeSelectionModel
import javax.swing.tree.DefaultTreeCellRenderer
import javax.swing.event.TreeSelectionEvent
import javax.swing.event.TreeSelectionListener
import javax.swing.JOptionPane
import javax.swing.JFileChooser
import javax.swing.JTree
import javax.swing.JDialog
import javax.swing.ProgressMonitor
import javax.swing.SwingUtilities
import javax.swing.SwingConstants
import javax.swing.BorderFactory
import javax.swing.border.TitledBorder
import javax.swing.UIManager
import net.dinkla.macosx.OSXAdapter
import org.jfree.chart.ChartFactory
import org.jfree.data.general.DefaultPieDataset
import org.jfree.data.general.PieDataset
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PiePlot
import org.jfree.chart.plot.PiePlot3D
import org.jfree.chart.ChartPanel

import static Utilities.transform
import static net.dinkla.diskusage.Type.*
import static net.dinkla.diskusage.Unit.*
import static javax.swing.border.TitledBorder.*

/**
	DiskUsage shows JFreeChart chart diagram for a directory and its contents.
*/

class DiskUsage implements TreeSelectionListener {

	// The title
	final protected String title = 'Disk Usage V0.2'
	// the directory data structure
	protected Directory directory	
	// The swing builder
	protected SwingBuilder swing
	// The swing frame
	protected def frame
	// The swing tree
	protected JTree tree
	// The dialog for the waiting message
	protected JDialog dialogProgress
	// The swing tree model
	protected DefaultTreeModel treeModel
	// The chart
	protected JFreeChart chart
	// The dataset for the pie chart
	protected DefaultPieDataset piedataset
	// The format of the elements
	protected Format format
	// The previous format of the elements
	protected Format previousFormat
	// The maximal number of elements in a chart
	protected int maxElements = 5
	// Do we run on a Mac ?
	//protected final static boolean MAC_OS_X = (System.getProperty('mrj.version') != null)
	protected static boolean MAC_OS_X = (System.getProperty("os.name").toLowerCase().startsWith("mac os x"));
	
	/**
		Constructor.
	*/
	DiskUsage() {
		piedataset = new DefaultPieDataset(); 
		format = new Format(ABSOLUTE, KILOBYTES, true)
		previousFormat = format
	}
	
	/**
		Builds up the Swing stuff and shows it.
	*/
	void show() {
		
		if ( MAC_OS_X ) {
			java.lang.System.setProperty("com.apple.mrj.application.apple.menu.about.name", "DiskUsage")
			try {
			    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception exc) {
			    System.err.println("Error loading L&F: " + exc);
			}
			try {
			    java.lang.System.setProperty("apple.laf.useScreenMenuBar", "true")
			} catch (Exception e) {
			    java.lang.System.setProperty("com.apple.macos.useScreenMenuBar", "true")
			}
			if ( MAC_OS_X ) registerForMacOSXEvents()
		}
		
		// The 3D chart
		chart = ChartFactory.createPieChart3D('untitled', piedataset, true, true, true)
		chart.backgroundPaint = Color.white

		// Change the parameters of the 3D chart
        PiePlot3D pieplot3d = (PiePlot3D) chart.getPlot();
        pieplot3d.setForegroundAlpha(0.6F);
        pieplot3d.setNoDataMessage('No data to display');

		// A JTree cell renderer, that only display folder icons
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer()
		renderer.setLeafIcon(renderer.getClosedIcon())
        
		// The Swing Builder
		swing = new SwingBuilder()
		
		swing.actions {
			
			// The action for the about box
			def actionAbout = swing.action(
					id:			 'actionAbout',
				    name:        'About ...', 
				    closure:     this.&about,
				    mnemonic:    'A',
				    accelerator: 'F1'
			)
	
			// The action for exiting the application 
			def actionExit = swing.action(
					id:			 'actionExit',
				    name:        'Exit', 
				    closure:     this.&quit,
				    mnemonic:    'X',
				    accelerator: 'F9'
			)
			
			// The action for changing the directory
			def actionChoose = swing.action(
					id:			 'actionChoose',
				    name:        'Choose directory ...', 
				    closure:     this.&actionChooseDirectory,
				    mnemonic:    'D',
					accelerator: 'F5'
	        )		
	
	        // The action for the settings dialog
			def actionSettings = swing.action(
					id:			 'actionSettings',
				    name:        'Settings ...', 
				    closure:     this.&showSettingsDialog,
				    mnemonic:    'S'
	        )		
	
	        // The action for the ok button in the settings dialog
			def actionSettingsOk = swing.action(
					id:			 'actionSettingsOk',
				    name:        'Ok', 
				    closure:     this.&actionSettingsOk,
				    mnemonic:    'O'
	        )		
	        
	        // The action for the cancel button in the settings dialog
			def actionSettingsCancel = swing.action(
					id:			 'actionSettingsCancel',
				    name:        'Cancel', 
				    closure:     { event -> 
				    	swing.settings.setVisible(false)
				    	format = previousFormat
				    },
				    mnemonic:    'C'
	        )		
	
		}
		
        //
        def actionForRadioButton = { unit ->
        	return swing.action(
			    closure: { event -> 
			    	format = new Format(ABSOLUTE, unit, format.showUnits) 
			    },
        	)
        }
		
        // The directory chooser dialog
        JFileChooser fileChooserDialog = swing.fileChooser(
        		id: 'fileChooser',
        		dialogType: JFileChooser.OPEN_DIALOG,
        		fileSelectionMode: JFileChooser.DIRECTORIES_ONLY		
        )
        
       def buttonTexts = [ ['Bytes', BYTES], 
                           ['Kilobytes (KB)', KILOBYTES], 
                           ['Megabytes (MB)', MEGABYTES], 
                           ['Gigabytes (GB)', GIGABYTES], 
                           ['Terabytes (TB)', TERABYTES] ]
		
        //
        def settingsDialog = swing.dialog(
        		id: 'settings',
        		title: 'Settings', 
        		name: 'Settings', 
        		size: [width: 430, height: 250],
        		minimumSize: [width: 430, height: 250],
        		modal: true,
        		layout: new java.awt.BorderLayout()) {}
		def p1 = swing.panel(  name: 'Unit‚', 
				id: 'panel1',
				layout: new GridBagLayout(),
				border: BorderFactory.createTitledBorder(null, "Unit", 
							DEFAULT_JUSTIFICATION, 
							DEFAULT_POSITION, 
							new Font("Lucida Grande", Font.PLAIN, 13), Color.black) )  {
			def buttons = []
			def group = buttonGroup( id: 'group')
			int y = 0
			for (txt in buttonTexts) {
				def gbc = new GridBagConstraints();
				gbc.gridx = 0
				gbc.gridy = y
				buttons << radioButton( constraints: gbc, action: actionForRadioButton.call(txt[1]) )
				gbc = new GridBagConstraints();
				gbc.gridx = 1
				gbc.gridy = y++
				label( text: txt[0], constraints: gbc, 
						horizontalTextPosition: SwingConstants.RIGHT,
						horizontalAlignment: SwingConstants.RIGHT
				)
			}
			for (b in buttons) {
				group.add(b)
			}
			buttons[0].selected = true
		}
		def p2 = swing.panel(name: 'Order of elements',
			  border: BorderFactory.createTitledBorder(null, 
						"Limits", DEFAULT_JUSTIFICATION, 
						DEFAULT_POSITION, 
						new Font("Lucida Grande", Font.PLAIN, 13), 
						Color.black)) {
			label('Maximal number of items')
			textField(preferredSize: [width: 100, height: 22], id: 'maxElems', "$maxElements" as String)
		}
		def p3 = swing.panel( name: 'Buttons' ) {
			button( text: 'Ok', selected: true, action: actionSettingsOk)
			button( text: 'Cancel', selected: false, action: actionSettingsCancel)
		}
		settingsDialog.add(p1, BorderLayout.LINE_START)		
		settingsDialog.add(p2, BorderLayout.LINE_END)		
		settingsDialog.add(p3, BorderLayout.PAGE_END)		
		
		// The waiting message dialog 
		def pane = swing.optionPane(
				id: 'waitingpane',
				message: 'Reading directories. Please wait ...',
				messageType: JOptionPane.INFORMATION_MESSAGE,
				options: ['OK'],
				optionType: JOptionPane.OK_OPTION
				)
		dialogProgress = pane.createDialog(frame, title)
     	// the user should not be able to close the dialog
     	dialogProgress.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
     	dialogProgress.setModal(true)
     	pane.addPropertyChangeListener( { e -> 
     		if ( !MAC_OS_X ) dialogProgress.setVisible(true) 
     	} as PropertyChangeListener);

        // The main window / frame
		frame = swing.frame(
				title: title, 
				layout: new GridLayout(1,1), 
				minimumSize: [width: 600, height: 500],
				defaultCloseOperation:WC.EXIT_ON_CLOSE) {
			// the menu
			menuBar  {
				menu(mnemonic: 'F', text: 'File') {
					menuItem(actionChoose)
					if ( ! MAC_OS_X ) {
						menuItem(actionSettings)
						separator()
						menuItem(actionExit)
					}
				}
				if ( ! MAC_OS_X ) {
					menu(mnemonic: 'H', text: 'Help') {
						menuItem(actionAbout)
					}
				}
			}
			// the panes
			splitPane(id: 'panes') {
				scrollPane(minimumSize: [width: 200, height: 100]) {
					tree = tree(id: 'tree', model: treeModel, cellRenderer: renderer)
				}
				scrollPane() {
					panel(id:'canvas', layout: new GridLayout(1,1) ) {
						widget(new ChartPanel(chart))
					}
				}
			}
		}
		frame.pack()
		frame.setVisible(true)
		
		// Set the selection model of the tree
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION)
		// Add a selection listener
		tree.addTreeSelectionListener((TreeSelectionListener)this)
	}
	
	/**
		Selection listener for the tree.
	*/
	void valueChanged(TreeSelectionEvent e) {
		def source = e.getSource()
		if ( source.isValid() ) {
			// get the selected node in the tree 
			def node = tree.getLastSelectedPathComponent();
			// update the data set
			if ( node ) {
				def elem = node.getUserObject()
				updatePieDataSet(elem)
				chart.setTitle(elem.getFullyQualifiedName())
			}
		}
	}

	/**
		Sets the new directory and updates everything.
	*/
	private void updateDirectory(String fileName) {
		Boolean finished = false
		// clear the diagram
		piedataset.clear()
		chart.setTitle('loading ...')
		// read in the new directory in a separate thread
		Closure calculate = {
			try {
				directory = new Directory(fileName)
				treeModel = new DefaultTreeModel(transform(directory.root), false)
			} catch ( AssertionError e) {
				directory = null
				treeModel = null
			}
			finished = true
		}
		new Thread( calculate as Runnable).start()
     	// we are waiting in a third thread (Thread 1: GUI dialog box, 2: Directory, 3: Waiting)
     	Closure waitAndStop = {
 			while ( ! finished ) {
 				Thread.sleep(500)
 			}
 			dialogProgress.setVisible(false)
     	}
     	new Thread( waitAndStop as Runnable).start()
     	dialogProgress.setVisible(true)
     	updateChart()
	}
	
	private void updateChart() {
     	if ( directory ) {
			// update the information
			chart.setTitle(directory.root.name)
			updatePieDataSet(directory.root)
			tree.setModel(treeModel)
			tree.setSelectionRow((int) 0)
     	}
	}
	
	/**
		Updates the PieDataSet.
	*/
	private void updatePieDataSet(Element node) {
		assert node
		piedataset.clear()
		directory.root.clear()
 		directory.root.prune(maxElements)
		for (child in node.children) {
			if ( !child.hidden )
				piedataset.setValue(format.format(child.name, child.sum), child.sum)
		}
	}
	
	/**
		Shows the directory chooser and updates the app
	*/
	private void actionChooseDirectory(event) {
		int rc = swing.fileChooser.showDialog(frame, 'Choose a directory')
		if ( 0 == rc ) updateDirectory(swing.fileChooser.getSelectedFile().toString())
	}

	/**
	*/
	private void actionSettingsOk(event) {
		swing.settings.setVisible(false)
		String str = swing.maxElems.getText()
		str = str.replaceAll(' ', '')
		maxElements = Integer.parseInt(str)
		updateChart()
	}

	
	/**
		Shows the settings dialog.
	*/
	void showSettingsDialog() {
		previousFormat = format
		swing.settings.setVisible(true)
	}

	/**
		Shows the settings dialog. (Linux)
	*/
	void showSettingsDialog(event) {
		showSettingsDialog()
	}

	/**
		Shows the About dialog
	*/
	void about() {
	    JOptionPane.showMessageDialog(frame, 
'''Disk Usage Chart Tool. Version 0.3. (c) 2007, 2008 Joern Dinkla (http://www.dinkla.net).
Written in Groovy (http://groovy.codehaus.org).
The chart is drawn using the JFreeChart library (http://www.jfree.org/jfreechart/).
''',
'About Disk Usage Tool', JOptionPane.INFORMATION_MESSAGE
	    )
	}

	/**
		Shows the About dialog (Linux)
	*/
	void about(event) {
		about()
	}
	
	/**
		Shows the "Really exit" dialog box and exits.
	*/
	boolean quit() {
		int rc = JOptionPane.showConfirmDialog(frame, 'Do you really want to exit ?', 'Please confirm', JOptionPane.YES_NO_OPTION )
		if (MAC_OS_X) return ( 0 == rc )
		if (rc == 0) System.exit(0)
	}
	
	/**
		Shows the "Really exit" dialog box and exits. (Linux)
	*/
	boolean quit(event) {
		return quit()
	}
	
	// The following method is from: 
	// https://developer.apple.com/leopard/devcenter/docs/samplecode/OSXAdapter/listing2.html
	// Generic registration with the Mac OS X application menu
    // Checks the platform, then attempts to register with the Apple EAWT
    // See OSXAdapter.java to see how this is done without directly referencing any Apple APIs
    private void registerForMacOSXEvents() {
        if (MAC_OS_X) {
            try {
                // Generate and register the OSXAdapter, passing it a hash of all the methods we wish to
                // use as delegates for various com.apple.eawt.ApplicationListener methods
                OSXAdapter.setQuitHandler(this, getClass().getDeclaredMethod("quit", (Class[])null));
                OSXAdapter.setAboutHandler(this, getClass().getDeclaredMethod("about", (Class[])null));
                OSXAdapter.setPreferencesHandler(this, getClass().getDeclaredMethod("showSettingsDialog", (Class[])null));
            } catch (Exception e) {
                System.err.println("Error while loading the OSXAdapter:");
                e.printStackTrace();
            }
        }
    }
	/**
		Runs the application.
	*/
	static void main(args) {
		new DiskUsage().show()
	}

}