package net.dinkla.diskusage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.awt.GridLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;

public class SettingsDialog extends JDialog {

	private JPanel panel1 = null;
	private JRadioButton rb1 = null;
	private JRadioButton rb2 = null;
	private JRadioButton rb3 = null;
	private JRadioButton rb4 = null;
	private JRadioButton rb5 = null;
	private JLabel l1 = null;
	private JLabel l2 = null;
	private JLabel l3 = null;
	private JLabel l4 = null;
	private JLabel l5 = null;
	private JPanel panel2 = null;
	private JPanel panel3 = null;
	private JLabel l6 = null;
	private JTextField txtMaxNum = null;
	private JButton buttonOk = null;
	private JButton buttonCancel = null;
	private JPanel panelTop = null;
	/**
	 * This method initializes 
	 * 
	 */
	public SettingsDialog() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
        gridBagConstraints17.fill = GridBagConstraints.BOTH;
        gridBagConstraints17.gridy = 0;
        gridBagConstraints17.gridx = 0;
        this.setSize(new Dimension(510, 250));
        this.setLayout(new GridBagLayout());
        this.setTitle("Settings");
        this.setContentPane(getPanelTop());
        this.setModal(true);
        this.setVisible(true);
			
	}

	/**
	 * This method initializes panel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanel1() {
		if (panel1 == null) {
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 1;
			gridBagConstraints9.gridy = 4;
			l5 = new JLabel();
			l5.setText("Terabytes (TB)");
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 1;
			gridBagConstraints8.gridy = 3;
			l4 = new JLabel();
			l4.setText("Gigabytes (GB)");
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 1;
			gridBagConstraints7.gridy = 2;
			l3 = new JLabel();
			l3.setText("Megabytes (MB)");
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.gridy = 1;
			l2 = new JLabel();
			l2.setText("Kilobytes (KB)");
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 0;
			l1 = new JLabel();
			l1.setText("Bytes");
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 4;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 3;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			panel1 = new JPanel();
			panel1.setLayout(new GridBagLayout());
			panel1.setBorder(BorderFactory.createTitledBorder(null, "Unit", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Lucida Grande", Font.PLAIN, 13), Color.black));
			panel1.add(getRb1(), gridBagConstraints);
			panel1.add(getRb2(), gridBagConstraints1);
			panel1.add(getRb3(), gridBagConstraints2);
			panel1.add(getRb4(), gridBagConstraints3);
			panel1.add(getRb5(), gridBagConstraints4);
			panel1.add(l1, gridBagConstraints5);
			panel1.add(l2, gridBagConstraints6);
			panel1.add(l3, gridBagConstraints7);
			panel1.add(l4, gridBagConstraints8);
			panel1.add(l5, gridBagConstraints9);
		}
		return panel1;
	}

	/**
	 * This method initializes rb1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRb1() {
		if (rb1 == null) {
			rb1 = new JRadioButton();
			rb1.setSelected(true);
		}
		return rb1;
	}

	/**
	 * This method initializes rb2	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRb2() {
		if (rb2 == null) {
			rb2 = new JRadioButton();
		}
		return rb2;
	}

	/**
	 * This method initializes rb3	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRb3() {
		if (rb3 == null) {
			rb3 = new JRadioButton();
		}
		return rb3;
	}

	/**
	 * This method initializes rb4	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRb4() {
		if (rb4 == null) {
			rb4 = new JRadioButton();
		}
		return rb4;
	}

	/**
	 * This method initializes rb5	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRb5() {
		if (rb5 == null) {
			rb5 = new JRadioButton();
		}
		return rb5;
	}

	/**
	 * This method initializes panel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanel2() {
		if (panel2 == null) {
			TitledBorder titledBorder = BorderFactory.createTitledBorder(null, "Max num", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Lucida Grande", Font.PLAIN, 13), Color.black);
			titledBorder.setTitle("Parameters");
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints14.gridy = 0;
			gridBagConstraints14.weightx = 1.0;
			gridBagConstraints14.gridx = 1;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.gridy = 0;
			l6 = new JLabel();
			l6.setText("Maximal number of items");
			panel2 = new JPanel();
			panel2.setLayout(new GridBagLayout());
			panel2.setBorder(titledBorder);
			panel2.add(l6, gridBagConstraints10);
			panel2.add(getTxtMaxNum(), gridBagConstraints14);
		}
		return panel2;
	}

	/**
	 * This method initializes panel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanel3() {
		if (panel3 == null) {
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridx = 1;
			gridBagConstraints16.gridy = 0;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.gridx = 0;
			gridBagConstraints15.gridy = 0;
			panel3 = new JPanel();
			panel3.setLayout(new GridBagLayout());
			panel3.add(getButtonOk(), gridBagConstraints15);
			panel3.add(getButtonCancel(), gridBagConstraints16);
		}
		return panel3;
	}

	/**
	 * This method initializes txtMaxNum	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtMaxNum() {
		if (txtMaxNum == null) {
			txtMaxNum = new JTextField();
			txtMaxNum.setPreferredSize(new Dimension(100, 22));
		}
		return txtMaxNum;
	}

	/**
	 * This method initializes buttonOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButtonOk() {
		if (buttonOk == null) {
			buttonOk = new JButton();
			buttonOk.setText("Ok");
		}
		return buttonOk;
	}

	/**
	 * This method initializes buttonCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButtonCancel() {
		if (buttonCancel == null) {
			buttonCancel = new JButton();
			buttonCancel.setText("Cancel");
		}
		return buttonCancel;
	}

	/**
	 * This method initializes panelTop	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelTop() {
		if (panelTop == null) {
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.insets = new Insets(3, 134, 0, 135);
			gridBagConstraints19.gridx = 0;
			gridBagConstraints19.gridy = 1;
			gridBagConstraints19.fill = GridBagConstraints.BOTH;
			gridBagConstraints19.anchor = GridBagConstraints.SOUTH;
			gridBagConstraints19.ipadx = 10;
			gridBagConstraints19.ipady = 10;
			gridBagConstraints19.gridwidth = 2;
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.insets = new Insets(49, 3, 46, 37);
			gridBagConstraints18.gridy = 0;
			gridBagConstraints18.fill = GridBagConstraints.BOTH;
			gridBagConstraints18.anchor = GridBagConstraints.NORTHEAST;
			gridBagConstraints18.ipadx = 20;
			gridBagConstraints18.gridx = 1;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.insets = new Insets(5, 36, 2, 2);
			gridBagConstraints13.gridy = 0;
			gridBagConstraints13.fill = GridBagConstraints.BOTH;
			gridBagConstraints13.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints13.weightx = 0.0D;
			gridBagConstraints13.ipadx = 5;
			gridBagConstraints13.ipady = 10;
			gridBagConstraints13.gridx = 0;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = -1;
			gridBagConstraints12.ipadx = 400;
			gridBagConstraints12.ipady = 91;
			gridBagConstraints12.gridy = -1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = -1;
			gridBagConstraints11.ipadx = 379;
			gridBagConstraints11.ipady = -3;
			gridBagConstraints11.gridy = -1;
			panelTop = new JPanel();
			panelTop.setLayout(new GridBagLayout());
			panelTop.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			panelTop.add(getPanel1(), gridBagConstraints13);
			panelTop.add(getPanel2(), gridBagConstraints18);
			panelTop.add(getPanel3(), gridBagConstraints19);
		}
		return panelTop;
	}

}  //  @jve:decl-index=0:visual-constraint="16,-6"
