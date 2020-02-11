package application;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.naming.CannotProceedException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import filters.CannyEdge;
import filters.GrayScale;
import filters.SharpImg;
import rotate.RotateImg;

public class MainFrame implements ActionListener{

	JMenu file, edit, filter, editSubMenu;
	JMenuItem itemOpenImage, saveImg, grayScale, cannyEdge, 
				silverEdge, sharpImg, blurImg, rotate180, rotate90, rotateNeg90;
	JMenuBar menuBar;
	
	ImageIcon icon;
	JFrame frame;
	JLabel img;
	
	public MainFrame() throws IOException {
		
		//main editing frame
		frame = new JFrame("Image Editor");
		
		file = new JMenu("File");
		edit = new JMenu("Edit");
		filter = new JMenu("Filter");
		editSubMenu = new JMenu("Rotate");
		
		//file properties
		saveImg = new JMenuItem("Save");
		itemOpenImage = new JMenuItem("Open");
		
		//filter properties
		grayScale = new JMenuItem("B&W");
		cannyEdge = new JMenuItem("Edge Highlight");
		silverEdge = new JMenuItem("Silver Cypher");
		sharpImg = new JMenuItem("Sharp Img");
		blurImg = new JMenuItem("Blur Img");
		
		//edit properties
		rotate180 = new JMenuItem("Rotate 180");
		rotate90 = new JMenuItem("Rotate 90");
		
		menuBar = new JMenuBar();
	
		
		//sub menus
		editSubMenu.add(rotate180);
		editSubMenu.add(rotate90);
		
		//file options
		file.add(itemOpenImage);
		file.add(saveImg);
		//edit options
		edit.add(editSubMenu);
		edit.add(blurImg);
		//filter options
		filter.add(grayScale);
		filter.add(cannyEdge);
		filter.add(silverEdge);
		filter.add(sharpImg);
		
		//main menu options
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(filter);
		
		events();
		
		//default img
		BufferedImage bi = ImageIO.read(new File("C:\\Users\\Ramesh\\Desktop\\opencv\\test_img.jpg"));
		icon = new ImageIcon(bi);
		img = new JLabel(icon);
		img.setSize(bi.getWidth(), bi.getHeight());
		
	
		frame.setSize(1000, 1000);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setJMenuBar(menuBar);
		frame.add(img);

	}

	private void events(){
		
		grayScale.addActionListener(this);
		rotate90.addActionListener(this);
		saveImg.addActionListener(this);
		rotate180.addActionListener(this);
		cannyEdge.addActionListener(this);
		silverEdge.addActionListener(this);
		blurImg.addActionListener(this);
		sharpImg.addActionListener(this);
	}
	
	private BufferedImage getImageFromLabel() {
		
		Icon icon = img.getIcon();

        BufferedImage bi = new BufferedImage(
            icon.getIconWidth(),
            icon.getIconHeight(),
            BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        // paint the Icon to the BufferedImage.
        icon.paintIcon(null, g, 0,0);
        
        g.dispose();
        
        return bi;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		CannyEdge cEdge = new CannyEdge(getImageFromLabel());
		
		if(e.getSource() == grayScale) {
			System.out.println("Applying filter!");
			
			GrayScale gray = new GrayScale(getImageFromLabel());
			img.setIcon(new ImageIcon(gray.getGrayScaledImg()));
			img.revalidate();
		}
		
		if(e.getSource() == saveImg) {
			try {
				ImageIO.write(getImageFromLabel(), "jpg", new File("C:\\Users\\Ramesh\\Desktop\\out.jpg"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if(e.getSource() == rotate180) {
			RotateImg rotate = new RotateImg(180, getImageFromLabel());
			img.setIcon(new ImageIcon(rotate.getRotatedImg()));
			img.revalidate();
		}
		
		if(e.getSource() == rotate90) {
			RotateImg rotate = new RotateImg(90, getImageFromLabel());
			img.setIcon(new ImageIcon(rotate.getRotatedImg()));
			img.revalidate();
		}
		if(e.getSource() == cannyEdge) {
			cEdge.highlight();
			img.setIcon(new ImageIcon(cEdge.getHighlightedImg()));
			img.revalidate();
		}
		if (e.getSource() == silverEdge) {
			cEdge.silverEffect();
			img.setIcon(new ImageIcon(cEdge.getHighlightedImg()));
			img.revalidate();
		}
		if(e.getSource() == sharpImg) {
			SharpImg sharp = null;
			try {
				sharp = new SharpImg(getImageFromLabel());
				img.setIcon(new ImageIcon(sharp.getSharpedImg()));
				img.revalidate();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
	}

}
