package imageMap;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;


public class Zeichenfläche extends JPanel implements MouseListener, ActionListener,
MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	//Festgelegt übergebenen Formate fürs Zeichnen
	private int something;
	public final static int auswahl = 0;
	public final static int rechteck = 1;
	public final static int kreis = 2;
	public final static int mehreck = 3;
	
	//Bild
	private BufferedImage bild;
	private double faktorW = 0;
	private double faktorH = 0;
	
	private Main main = null;
	
	private Form currentForm;
	private Form urlForm;
	
	// für Rectangle
	private Point startPoint;
	private Point endPoint;
	
	private Vector<Form> vec = new Vector<Form>();
	
	//img tag Aufbereitung
	private String htmlTagAuf ="<html>" + "\n" + "<head>" + "\n" + "</head>" + "\n" + "<body>" + "\n";
	private String start = "<img src=\"\">";
	private final String mapAuf = "<map name=hiddekuddi>";
	private final String mapZu = "</map>"+ "\n";
	private String htmlTagZu ="</body>" + "\n" + "</html>";
	
	private Color globalColor = Color.BLACK;
	
	//Stroke
	private BasicStroke stroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, new float[] { 1.0f, 1.0f }, 0.0f);
	
	//Popup für URL
	private JPopupMenu popup = new JPopupMenu();
	private JMenuItem url = new JMenuItem("Bitte URL eingeben");
	
	
	
	public Zeichenfläche(Main main){
		this.main = main;
		addMouseListener(this);
		addMouseMotionListener(this);
		
		createPopup();			
	}
	
	/**
	 * Popup und Add an Action Listener 
	 */
	private void createPopup() {
		popup.add(url);
		url.addActionListener(this);		
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D graphic = (Graphics2D) g;
		
		g.drawImage(bild, 0, 0, getWidth(), getHeight(), this);

		String area = "";

		if (vec.isEmpty()) {
			String fertigerHttp = htmlTagAuf + start + "\n" + mapAuf + "\n" + area + mapZu + htmlTagZu;
			Main.setHtmlTextArea(fertigerHttp);

		} else {
			for (int i = 0; i < vec.size(); i++) {
				Form f = vec.get(i);

				String url = "";
				if (!(f.getHref().equals(null))) {
					url = f.getHref();
				}
				
				int x;
				int y;
				int width;
				int height;

				//graphic.setColor(f.getC());

				switch (f.getType()) {
				
				//Rechteck
				case rechteck:
					Rectangle r1 = f.getR();
					graphic.drawRect(r1.x, r1.y, r1.width, r1.height);

					x = r1.x;
					y = r1.y;
					width = r1.x + r1.width;
					height = r1.y + r1.height;

					if (!(bild == null)) {
						bildFaktor();

						if (bild.getWidth() > getWidth()) {
							x = (int) (x * faktorW);
							width = (int) (width * faktorW);
						} else {
							x = (int) (x / faktorW);
							width = (int) (width / faktorW);
						}

						if (bild.getHeight() > getHeight()) {
							y = (int) (y * faktorH);
							height = (int) (height * faktorH);
						} else {
							y = (int) (y / faktorH);
							height = (int) (height / faktorH);
						}

					}

					String tmp = x + "," + y + "," + width + "," + height;
					area = area + "<area shape=\"rect\" coords=\"" + tmp + "\" href=\"" + url + "\" alt=\"Text\">\n";
					break;
				
					//Kreis
					case kreis:
					Rectangle r2 = f.getR();
					graphic.drawOval(r2.x, r2.y, r2.width, r2.width);

					int radius = r2.width / 2;
					x = r2.x + radius;
					y = r2.y + radius;

					if (!(bild == null)) {
						bildFaktor();

						if (bild.getWidth() > getWidth()) {
							x = (int) (x * faktorW);
							radius = (int) (radius * faktorW);
						} else {
							x = (int) (x / faktorW);
							radius = (int) (radius / faktorW);
						}

						if (bild.getHeight() > getHeight()) {
							y = (int) (y * faktorH);
						} else {
							y = (int) (y / faktorH);
						}

					}

					String tmp1 = x + "," + y + "," + radius;
					area = area + "<area shape=\"circle\" coords=\"" + tmp1 + "\" href=\"" + url + "\" alt=\"Text\">\n";
					break;
				}
				String fertigerHttp = htmlTagAuf + start + "\n" + mapAuf + "\n" + area + mapZu + htmlTagZu;
				Main.setHtmlTextArea(fertigerHttp);
			}
		}

		if ((something == rechteck || something == kreis) && startPoint != null
				&& endPoint != null) { 
			Rectangle r = calcRechteckKreis();
			graphic.setColor(Color.RED);
			graphic.setStroke(stroke);

			switch (something) {
			case rechteck:
				graphic.drawRect(r.x, r.y, r.width, r.height);
				break;
			case kreis:
				graphic.drawOval(r.x, r.y, r.width, r.width);
				break;
			}
		}
	}
	
	
	private void bildFaktor() {
		
		if (bild.getWidth() > getWidth()) {
			faktorW = (bild.getWidth() + 0.0) / getWidth();
		} else {
			faktorW = (getWidth() + 0.0) / bild.getWidth();
		}

		if (bild.getHeight() > getHeight()) {
			faktorH = (bild.getHeight() + 0.0) / getHeight();
		} else {
			faktorH = (getHeight() + 0.0) / bild.getHeight();
		}
		
	}
	
	/**
	 * Start Point bei Mausklick festlegen 
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		int button = e.getButton();
		startPoint = e.getPoint();
		
		if (button == MouseEvent.BUTTON3) {
			urlForm = null;
			for (int i = vec.size() - 1; i >= 0; i--) {
				Form f = vec.get(i);

				switch (f.getType()) {
				case rechteck:
					Rectangle r = f.getR();
					if (r.contains(startPoint)) {
						urlForm = f;
					}
					break;

				case kreis:
					Rectangle r2 = f.getR();
					if (r2.contains(startPoint)) {
						urlForm = f;
					}
					break;
				}
			}
			popup.show(this, e.getX(), e.getY());
		}
	}
	
	/**
	 * End Point bei Maus loslassen festlegen 
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		endPoint = e.getPoint();
		mousePixel(e);

		if (something == auswahl && currentForm != null) {
			int offX;
			int offY;
			Rectangle r;

			switch (currentForm.getType()) {
			case rechteck:
				offX = endPoint.x - startPoint.x;
				offY = endPoint.y - startPoint.y;
				startPoint = endPoint;
				r = currentForm.getR();
				r.translate(offX, offY);
				currentForm.setR(r);
				currentForm.setRatioWidth((getWidth() + 0.0) / r.width);
				currentForm.setRatioHeight((getHeight() + 0.0) / r.height);
				currentForm.setRatioX((getWidth() + 0.0) / r.x);
				currentForm.setRatioY((getHeight() + 0.0) / r.y);
				break;

			case kreis:
				offX = endPoint.x - startPoint.x;
				offY = endPoint.y - startPoint.y;
				startPoint = endPoint;
				r = currentForm.getR();
				r.translate(offX, offY);
				currentForm.setR(r);
				currentForm.setRatioWidth((getWidth() + 0.0) / r.width);
				currentForm.setRatioHeight((getHeight() + 0.0) / r.height);
				currentForm.setRatioX((getWidth() + 0.0) / r.x);
				currentForm.setRatioY((getHeight() + 0.0) / r.y);
				break;
			}
		}
		repaint();		
	}

	@Override
	public void mouseMoved(MouseEvent point) {
		mousePixel(point);
		
	}

	private void mousePixel(MouseEvent point) {
		Point p = point.getPoint();
		String s = p.x + "," + p.y + "px";
		main.setMousePixel(s);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src == url) {
			if (!(urlForm == null)) {
				String s = JOptionPane.showInputDialog(this,"Geben Sie eine URL ein:");
					urlForm.setHref("http://" + s);
					repaint();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	/**
	 * Rechteck und Kreis
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		int mausButton = e.getButton();
		Rectangle r = calcRechteckKreis();
		Form f = new Form (r.x, r.y, r.width, r.height);
		
		if (mausButton == MouseEvent.BUTTON1 && something != auswahl && something != mehreck) {
			
			switch (something) {
			case rechteck:
				f.setType(rechteck);
				break;
			case kreis:
				f.setType(kreis);
				break;
			}
			f.setRatioWidth((getWidth() + 0.0) / r.width);
			f.setRatioHeight((getHeight() + 0.0) / r.height);
			f.setRatioX((getWidth() + 0.0) / r.x);
			f.setRatioY((getHeight() + 0.0) / r.y);
			f.setC(globalColor);
			vec.add(f);
			repaint();			
		}
		currentForm = null;
		startPoint = null;
		endPoint = null;
	}

	private Rectangle calcRechteckKreis() {
		Rectangle r = new Rectangle();

		if (endPoint != null && startPoint != null) {
			int width = endPoint.x - startPoint.x;
			int height = endPoint.y - startPoint.y;
			r = new Rectangle(startPoint.x, startPoint.y, width, height);
			r = korrekturRechteckKreis(r);
		}
		return r;
	}
	/**
	 * Korrektur, damit keine negativen Werte entstehen
	 * 
	 * @param r Objekt vom Typ Rectangle
	 * 
	 * @return Objekt vom Typ Rectangle ohne negative Werte
	 */
	private Rectangle korrekturRechteckKreis(Rectangle r) {
		if (r != null) {
			if (r.width < 0) {
				r.width = -r.width;
				r.x -= r.width;
			}
			if (r.height < 0) {
				r.height = -r.height;
				r.y -= r.height;
			}
		}
		return r;
	}

	public void doSomething(int i) {
		something = i;
		if (i == 0) {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} else {
			setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		}	
	}

	/**
	 * Bild Einlesen.
	 * Außerdem findet hier die Umwandlung des Path statt
	 * Backslash wird zu Slash geändert
	 */
	public void openPicture() {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				File file = fileChooser.getSelectedFile();
				bild = ImageIO.read(file);
				String path = file.getPath();
				String pathSlash = path.replace('\\', '/');
				String pathSlashPlusFile = "file:/" + pathSlash;			
				start = "<img usemap=\"#hiddekuddi\" src=\"" + pathSlashPlusFile + "\" width=\"" + bild.getWidth() + "\" height=\"" + bild.getHeight()+"\">";
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			repaint();
		}
		
	}

}
