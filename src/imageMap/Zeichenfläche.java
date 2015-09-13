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
import java.util.Vector;

import javax.swing.JPanel;


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
	private double factorW = 0;
	private double factorH = 0;
	
	private Main main = null;
	
	private Form currentForm;
	
	// für Rectangle
	private Point startPoint;
	private Point endPoint;
	
	private Vector<Form> vec = new Vector<Form>();
	
	//img tag Aufbereitung
	private String start = "<img src=\"";
	private final String mapAuf = "<map name=MAPNAME>";
	private final String mapZu = "</map>";
	
	private Color globalColor = Color.BLACK;
	
	//Stroke
	private BasicStroke stroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
			BasicStroke.JOIN_MITER, 1.0f, new float[] { 1.0f, 1.0f }, 0.0f);
	
	
	
	public Zeichenfläche(Main main){
		this.main = main;
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D gr = (Graphics2D) g;
		
		g.drawImage(bild, 0, 0, getWidth(), getHeight(), this);

		String part3 = "";

		if (vec.isEmpty()) {
			String endString = start + "\n" + mapAuf + "\n" + part3 + mapZu;
			Main.setHtmlTextArea(endString);

		} else {
			for (int i = 0; i < vec.size(); i++) {
				Form f = vec.get(i);

				String tmp2 = "";
				if (!(f.getHref().equals(null))) {
					tmp2 = f.getHref();
				}
				int x;
				int y;
				int width;
				int height;

				gr.setColor(f.getC());

				switch (f.getType()) {
				case rechteck:
					Rectangle r1 = f.getR();
					gr.drawRect(r1.x, r1.y, r1.width, r1.height);

					x = r1.x;
					y = r1.y;
					width = r1.x + r1.width;
					height = r1.y + r1.height;

					if (!(bild == null)) {
						calcFac();

						if (bild.getWidth() > getWidth()) {
							x = (int) (x * factorW);
							width = (int) (width * factorW);
						} else {
							x = (int) (x / factorW);
							width = (int) (width / factorW);
						}

						if (bild.getHeight() > getHeight()) {
							y = (int) (y * factorH);
							height = (int) (height * factorH);
						} else {
							y = (int) (y / factorH);
							height = (int) (height / factorH);
						}

					}

					String tmp = x + "," + y + "," + width + "," + height;
					part3 = part3 + "<area form=rect coords=\"" + tmp
							+ "\" href=\"" + tmp2 + "\" alt=\"Text\">\n";
					break;

				case kreis:
					Rectangle r2 = f.getR();
					gr.drawOval(r2.x, r2.y, r2.width, r2.width);

					int radius = r2.width / 2;
					x = r2.x + radius;
					y = r2.y + radius;

					if (!(bild == null)) {
						calcFac();

						if (bild.getWidth() > getWidth()) {
							x = (int) (x * factorW);
							radius = (int) (radius * factorW);
						} else {
							x = (int) (x / factorW);
							radius = (int) (radius / factorW);
						}

						if (bild.getHeight() > getHeight()) {
							y = (int) (y * factorH);
						} else {
							y = (int) (y / factorH);
						}

					}

					String tmp1 = x + "," + y + "," + radius;
					part3 = part3 + "<area form=circle coords=\"" + tmp1
							+ "\" href=\"" + tmp2 + "\" alt=\"Text\">\n";
					break;

				case mehreck:
					Polygon p = f.getP();
					gr.drawPolygon(p);

					String tmp3 = "";

					if (!(bild == null)) {
						calcFac();

						for (int j = 0; j < p.xpoints.length; j++) {
							x = p.xpoints[j];
							y = p.ypoints[j];

							if (bild.getWidth() > getWidth()) {
								x = (int) (x * factorW);
							} else {
								x = (int) (x / factorW);
							}

							if (bild.getHeight() > getHeight()) {
								y = (int) (y * factorH);
							} else {
								y = (int) (y / factorH);
							}

							if (j == p.xpoints.length - 1) {
								tmp3 = tmp3 + x + "," + y;
							} else {
								tmp3 = tmp3 + x + "," + y + ",";
							}
						}
					} else {
						for (int j = 0; j < p.xpoints.length; j++) {
							if (j == p.xpoints.length - 1) {
								tmp3 = tmp3 + p.xpoints[j] + "," + p.ypoints[j];
							} else {
								tmp3 = tmp3 + p.xpoints[j] + "," + p.ypoints[j]
										+ ",";
							}
						}
					}
					part3 = part3 + "<area form=poly coords=\"" + tmp3
							+ "\" href=\"" + tmp2 + "\" alt=\"Text\">\n";
					break;
				}
				String endString = start + "\n" + mapAuf + "\n" + part3 + mapZu;
				Main.setHtmlTextArea(endString);
			}
		}

		if ((something == rechteck || something == kreis) && startPoint != null
				&& endPoint != null) { 
			Rectangle r = calcRect();
			gr.setColor(Color.RED);
			gr.setStroke(stroke);

			switch (something) {
			case rechteck:
				gr.drawRect(r.x, r.y, r.width, r.height);
				break;
			case kreis:
				gr.drawOval(r.x, r.y, r.width, r.width);
				break;
			}
		}
	}
	
	
	private void calcFac() {
		if (bild.getWidth() > getWidth()) {
			factorW = (bild.getWidth() + 0.0) / getWidth();
		} else {
			factorW = (getWidth() + 0.0) / bild.getWidth();
		}

		if (bild.getHeight() > getHeight()) {
			factorH = (bild.getHeight() + 0.0) / getHeight();
		} else {
			factorH = (getHeight() + 0.0) / bild.getHeight();
		}
		
	}

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

			case mehreck:
				Polygon p = currentForm.getP();
				offX = endPoint.x - startPoint.x;
				offY = endPoint.y - startPoint.y;
				startPoint = endPoint;

				for (int i = 0; i < p.xpoints.length; i++) {
					p.xpoints[i] = p.xpoints[i] + offX;
					p.ypoints[i] = p.ypoints[i] + offY;
				}
				p.invalidate();
				vec.remove(currentForm);
				currentForm.setP(p);
				vec.add(currentForm);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		int button = e.getButton();
		startPoint = e.getPoint();
	}

	/**
	 * Rechteck und Kreis
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		int mausButton = e.getButton();
		Rectangle r = calcRect();
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

	private Rectangle calcRect() {
		Rectangle r = new Rectangle();

		if (endPoint != null && startPoint != null) {
			int width = endPoint.x - startPoint.x;
			int height = endPoint.y - startPoint.y;
			r = new Rectangle(startPoint.x, startPoint.y, width, height);
			r = korrekturRect(r);
		}
		return r;
	}
	/**
	 * Korrektur, damit keine negativen Werte entstehen
	 * 
	 * @param r Objekt vom Typ Rectangle
	 * 
	 * @return Objekt vom Typ Rectangle ohne negativ Werte
	 */
	private Rectangle korrekturRect(Rectangle r) {
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

}
