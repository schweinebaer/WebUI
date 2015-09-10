package imageMap;

/**
 * Main-Frame Klasse
 * Container für Bildschirmausgabe und Interaktionen
 * 
 * @author Benedikt Breitschopf
 * 
 * @version 2.0
 */


import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener, WindowListener,
		ClipboardOwner, ComponentListener, KeyListener {
	
	//Menu
	private JMenuBar menuBar = new JMenuBar();
	private JMenu dateiMenu = new JMenu("Datei");
	private JMenu hilfeMenu = new JMenu("Hilfe");
	private JMenuItem newImageMap = new JMenuItem("Neue Image Map erstellen");
	private JMenuItem saveImageMap = new JMenuItem("Image Map speichern");
	private JMenuItem openPicture = new JMenuItem("Bild einfügen");
	private JMenuItem openImageMap = new JMenuItem("Image Map öffnen");
	private JMenuItem quit = new JMenuItem("Programm schließen");
	private JMenuItem aboutImageMap = new JMenuItem("Über Image Map");
	
	//Button + Bilderpfad
	private JToggleButton auswahlButton = new JToggleButton(new ImageIcon("img/auswaehlen.png"));
	private JToggleButton kreisButton = new JToggleButton(new ImageIcon("img/kreis.png"));
	private JToggleButton mehreckButton = new JToggleButton(new ImageIcon("img/mehreck.png"));
	private JToggleButton rechteckButton = new JToggleButton(new ImageIcon("img/rechteck.png"));
	
	//Toolbar
	private JToolBar toolbar = new JToolBar(JToolBar.VERTICAL);
	
	//Pixelanzeige
	private JToolBar pixel = new JToolBar(JToolBar.HORIZONTAL);
	private JLabel pixelStandpunkt = new JLabel("");
	

	/**
	 * Konstruktor
	 * 
	 * @param title String als Titel.
	 * 
	 * @throws HeadlessException Exception-Handler
	 */
	public Main(String title) throws HeadlessException {
		super(title);
		mainWindow();
	}

	private void mainWindow() {
		createMenuBar();
		createToolBar();
		
	}
	
	/**
	 * Erstellt klassische Menü Bar mit Datei und Hilfe
	 */
	private void createMenuBar() {
		menuBar.add(dateiMenu);
		menuBar.add(hilfeMenu);
		
		dateiMenu.add(newImageMap);
		dateiMenu.add(saveImageMap);
		dateiMenu.add(openImageMap);
		dateiMenu.add(openPicture);
		dateiMenu.add(quit);
		
		hilfeMenu.add(aboutImageMap);
		
		//Action Listener
		newImageMap.addActionListener(this);
		saveImageMap.addActionListener(this);
		openImageMap.addActionListener(this);
		openPicture.addActionListener(this);
		quit.addActionListener(this);
		aboutImageMap.addActionListener(this);
		
		//Kürzel
		newImageMap.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK));
		saveImageMap.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
		openImageMap.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK));
		openPicture.setAccelerator(KeyStroke.getKeyStroke('I', InputEvent.CTRL_MASK));
		quit.setAccelerator(KeyStroke.getKeyStroke('Q', InputEvent.CTRL_MASK));
		aboutImageMap.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK));
		
		setJMenuBar(menuBar);
	}

	/**
	 * Erstellt die Tool Bar.
	 * Es besteht die Möglichkeit zwischen Auswählen, Rechteck, Kreis und Mehreck auszuwählen
	 */
	private void createToolBar() {
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(auswahlButton);
		auswahlButton.setSelected(true);
		buttonGroup.add(rechteckButton);
		buttonGroup.add(kreisButton);
		buttonGroup.add(mehreckButton);
		
		add(toolbar, BorderLayout.WEST);
		
		toolbar.add(auswahlButton);
		toolbar.add(rechteckButton);
		toolbar.add(kreisButton);
		toolbar.add(mehreckButton);
		
		add(pixel, BorderLayout.SOUTH);
		
		pixel.add(pixelStandpunkt);
		
		//Action Listener
		auswahlButton.addActionListener(this);
		rechteckButton.addActionListener(this);
		kreisButton.addActionListener(this);
		mehreckButton.addActionListener(this);
		
		
	}
	
	/**
	 * pixelStandort Koordinaten Ermittlung
	 * 
	 * @param location Es wird ein Objekt von Typ Location erzeugt
	 */
	public void setMousePixel(String location) {
		mousePixel.setText(location);
	}



	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		//Fenster schließen über disponse Methode
		if (src == quit) {
			int tmp;
			tmp = JOptionPane.showConfirmDialog(this, "Wollen sie das Programm beenden?", "Programm Beenden", 0, 3);
			if (tmp == 0) {
				dispose();
			}
		}
		
		
	}
	
	public static void main(String[] args) {
		Main window = new Main("Der Image Map Editor 2015");
		window.setBounds(0, 0, 700, 800);
		window.setVisible(true);
		window.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //für Abfrage nach "Sind Sie sicher?"
	}

}
