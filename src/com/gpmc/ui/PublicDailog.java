package com.gpmc.ui;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Timer;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.gpmc.bean.Node;
import com.gpmc.dao.PublicDialogDao;


@SuppressWarnings("serial")
public class PublicDailog extends JPanel {

	private Dimension area; // Indicates area taken up by graphics
	private Vector<Rectangle> circles; // Coordinates used to draw graphics
	public JPanel drawingPane;
	
	private static String fileName = "src/xml/Debategsf663gkd3856vmo/PublicDialog.xml" ;
	private PublicDialogDao publicDialogDao;
	private List<Node> nodeList = null;
	
	private final String CLAIM = "claim";
	private final String SUBCLAIM = "subclaim";
	private final String COUNTERCLAIM = "counterclaim";
	private final String EVIDENCE = "evidence";
	private final String COUNTEREVIDENCE = "counterevidence";
	private final String BIPOLARQUESTION = "bipolarQuestion";
	private final String RESPONSETOBIPOLAR = "responseToBipolar";
	/*private final String CHALLENGE = "challenge";*/
	
	@SuppressWarnings("static-access")
	public PublicDailog(String fileName) {
		super(new BorderLayout());
		this.fileName = fileName;
		area = new Dimension(0, 0);
		circles = new Vector<Rectangle>();
		// Set up the drawing area
		drawingPane = new DrawingPane();
		drawingPane.setBackground(Color.white);
		// Put the drawing area ina scroll pane
		JScrollPane scroller = new JScrollPane(drawingPane);
		scroller.setPreferredSize(new Dimension(750, 320));
		add(scroller, BorderLayout.CENTER);
		publicDialogDao = new PublicDialogDao(fileName);
		//publicDialogDao = new PublicDialogDao("src/xml/Debatesaa730htm8679qxg/PublicDialog.xml");		
		nodeList = publicDialogDao.getPublicDialogList();
		initGraphicData();
		// Refresh the public dialog in certain time 
		Timer timer = new Timer();  
		   timer.schedule(new TimerTaskTest(), 10000, 20000);  
	}
	

	/**
	 * Create the drawing panel for the public dialog
	 * @author
	 *
	 */
	public class DrawingPane extends JPanel {
		protected void paintComponent (Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			// Draw middle line
			g.drawLine(area.width*2/5, 0, area.width*2/5, area.height); 
			
			// Draw shapes
			Rectangle rect;
			for (int i = 0; i < circles.size(); i++) {
				rect = circles.elementAt(i);
				g2d.setStroke(new BasicStroke(3.0f));
				
				if (nodeList.get(i).getTeamType().equals("A")) {
					g2d.setColor(new Color(111, 168, 220));
				} else {
					g2d.setColor(new Color(147, 196, 125));
				}
				// Claim rectangle
				if (nodeList.get(i).getType().equals(CLAIM)
						|| nodeList.get(i).getType().equals(COUNTERCLAIM)
						|| nodeList.get(i).getType().equals(SUBCLAIM)) {
					g2d.drawRoundRect(rect.x, rect.y, rect.width, rect.height, 20, 20);
					g2d.setColor(Color.black);
					g2d.drawString(nodeList.get(i).getType(), rect.x + 15, rect.y + 30);
					g2d.drawString(nodeList.get(i).getTitle(), rect.x + 15, rect.y + 50);
				// Response to Bipolar ecllipse
				} else if (nodeList.get(i).getType().equals(RESPONSETOBIPOLAR)){
					g2d.drawOval(rect.x, rect.y, rect.width, rect.height);
					g2d.setColor(Color.black);
					g2d.drawString(nodeList.get(i).getType(), rect.x + 10, rect.y + 50);
					g2d.drawString(nodeList.get(i).getTitle(), rect.x + 10, rect.y + 70);
				// Bipolar Question diamond
				} else if (nodeList.get(i).getType().equals(BIPOLARQUESTION)){
					int x, x1 =rect.x, x2 = rect.width; 
					int y, y1 = rect.y, y2 = rect.height;
				    x = (x1+x2)/2;  
				    y = (y1+y2)/2;  
				    g.drawLine(x1, y, x, y1);  
				    g.drawLine(x, y1, x2, y);  
				    g.drawLine(x2, y, x, y2);  
				    g.drawLine(x, y2, x1, y); 
				    g2d.setColor(Color.black);
					g2d.drawString(nodeList.get(i).getType(), rect.x + 50, rect.y + 70);
					g2d.drawString(nodeList.get(i).getTitle(), rect.x + 50, rect.y + 90);
				// Evidence diamond
				} else if (nodeList.get(i).getType().equals(EVIDENCE)
						|| nodeList.get(i).getType().equals(COUNTEREVIDENCE)){
					int x, x1 =rect.x, x2 = x1 + 100; 
					int y, y1 = rect.y, y2 = y1 + 200;
				    x = (x1+x2)/2;  
				    y = (y1+y2)/2;  
				    g.drawLine(x1, y, x, y1);  
				    g.drawLine(x, y1, x + 200, y1);  
				    g.drawLine(x + 200, y1, x1 + 200, y);  
				    g.drawLine(x1 + 200, y, x1, y); 
				    g2d.setColor(Color.black);
					g2d.drawString(nodeList.get(i).getType(), rect.x + 50, rect.y + 30);
					g2d.drawString(nodeList.get(i).getTitle(), rect.x + 50, rect.y + 50);
				}
			}
			
			// Draw lines
			float[] dashes = {10};
			g2d.setPaint(Color.gray);
			for (int i = 0; i < circles.size(); i++) {
				Node currentNode = nodeList.get(i), parentNode = null;
				Rectangle currentRect = circles.get(i), parentRect = null;
				String parentId = null;
				// Counter claim and Response to Bipolar draw dash
				if (currentNode.getType().equals(COUNTERCLAIM)
						|| currentNode.getType().equals(RESPONSETOBIPOLAR)
						|| currentNode.getType().equals(EVIDENCE)
						|| currentNode.getType().equals(COUNTEREVIDENCE)
						|| currentNode.getType().equals(SUBCLAIM)
						|| currentNode.getType().equals(BIPOLARQUESTION)) {
					parentId = currentNode.getParentId();
					for (int j = 0; j < i; j++) {
						if (nodeList.get(j).getId() != null
								&& nodeList.get(j).getId().equals(parentId)) {
								parentNode = nodeList.get(j);
								parentRect = circles.get(j);
								break;
						}
						
					}
					
					if (parentNode != null 
							&& (currentNode.getType().equals(EVIDENCE)
									|| currentNode.getType().equals(COUNTEREVIDENCE)
									|| currentNode.getType().equals(SUBCLAIM))) {
						int x1 = parentRect.x + 100;
						int y1 = parentRect.y + 100;
						int x2 = currentRect.x + 100;
						int y2 = currentRect.y;
						g2d.setStroke(new BasicStroke(2));
						g2d.draw(new Line2D.Double(x1, y1, x2, y2)); 
					} else if (parentNode != null && currentNode.getType().equals(COUNTERCLAIM)) {
						int x1 = parentRect.x + 200;
						int y1 = parentRect.y + 50;
						int x2 = currentRect.x;
						int y2 = currentRect.y + 50;
						g2d.setStroke(
								new BasicStroke(2, BasicStroke.CAP_ROUND,
								BasicStroke.JOIN_ROUND, 5, dashes, 0));
						g2d.draw(new Line2D.Double(x1, y1, x2, y2));
					} else if(parentNode != null && currentNode.getType().equals(RESPONSETOBIPOLAR)) {
						int x1 = parentRect.x + 100;
						int y1 = parentRect.y + 100;
						int x2 = currentRect.x;
						int y2 = currentRect.y + 100;
						g2d.setStroke(
								new BasicStroke(2, BasicStroke.CAP_ROUND,
								BasicStroke.JOIN_ROUND, 5, dashes, 0));
						g2d.draw(new Line2D.Double(x1, y1, x1, y2));
						g2d.draw(new Line2D.Double(x1, y2, x2, y2));
					} else if (parentNode != null) {

						int x1 = parentRect.x + 200;
						int y1 = parentRect.y + 100;
						int x2 = currentRect.x;
						int y2 = currentRect.y;
						g2d.setStroke(
								new BasicStroke(2, BasicStroke.CAP_ROUND,
								BasicStroke.JOIN_ROUND, 5, dashes, 0));
						g2d.draw(new Line2D.Double(x1, y1, x2, y2));
					}
				}
				
			}
		}
	}
	
	/**
	 * Draw the shape for the public dialog
	 */
	public void initGraphicData() {
		int W = 200;
		int H = 100;
		int x = 10;
		int y = 10;
		if (x < 0) x = 0;
		if (y < 0) y = 0;
	
		for (int i = 0; i < nodeList.size(); i++) {
			H = 100;
			y = y + H + 10;
			Rectangle rect = null;
			if (nodeList.get(i).getTeamType().equals("A")) {
				x = 400;
			} else {
				x = 10;
			}
			if  (nodeList.get(i).getType().equals(EVIDENCE)
					|| nodeList.get(i).getType().equals(COUNTEREVIDENCE)) {
				y = y + 80;
			} 
			
			if (nodeList.get(i).getType().equals(BIPOLARQUESTION)) {
				W = x + 200;
				H = y + 200;
			} else {
				W = 200;
				H = 100;
			}
			
			if (nodeList.get(i).getType().equals(COUNTERCLAIM)) {
				//y = y - H - 10;
			}
			rect = new Rectangle(x, y, W, H);
			if (nodeList.get(i).getType().equals(BIPOLARQUESTION)) {
				y = y + 150;
			}
		
			if (i == nodeList.size() - 1) {
				y = y + 100;
			}
			circles.addElement(rect);
			drawingPane.scrollRectToVisible(rect);
			if (area.width < x) {
				area.width = x + 350;
			}
			
			if (area.height < y) {
				area.height = y + 20;
			}
			// Update client's preferred size because the area taken
			// up by the graphics has gotten larger or smaller(id cleared)
			drawingPane.setPreferredSize(area);
			
			// Let the scroll pane know to update itself and its scrollbars
			drawingPane.revalidate();
		}
		drawingPane.repaint();
	}
	
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("ScrollDemo2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JComponent newContentPane = new PublicDailog(fileName);
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Refresh Public Dialog within 10 seconds
	 * @author 
	 *
	 */
	class TimerTaskTest extends java.util.TimerTask{  
		  
		@Override  
		public void run() {  
			area = new Dimension(0, 0);
			circles = new Vector<Rectangle>();
			publicDialogDao = new PublicDialogDao(fileName);		
			nodeList = publicDialogDao.getPublicDialogList();
			initGraphicData();
		}  
	}  
	
	public static void main(String [] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
		
	}
}