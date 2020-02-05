package application;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import filters.GrayScale;
import rotate.RotateImg;

public class MainFrame{

	JMenu file, edit, editSubMenu;
	JMenuItem itemOpenImage, grayScale, rotate180, rotate90;
	JMenuBar menuBar;
	//JM
	
	ImageIcon icon;
	JFrame frame;
	JLabel img;
	
	public MainFrame() {
		
		file = new JMenu("File");
		edit = new JMenu("Edit");
		editSubMenu = new JMenu("Rotate");
		
		itemOpenImage = new JMenuItem("Open");
		grayScale = new JMenuItem("B&W");
		rotate180 = new JMenuItem("Rotate 180");
		rotate90 = new JMenuItem("Rotate 90");
		
		menuBar = new JMenuBar();
		
		//sub menus
		editSubMenu.add(rotate180);
		editSubMenu.add(rotate90);
		
		file.add(itemOpenImage);
		edit.add(editSubMenu);
		edit.add(grayScale);
		
		menuBar.add(file);
		menuBar.add(edit);
		
		events();
		
		//default img
		icon = new ImageIcon("C:\\Users\\Ramesh\\Desktop\\opencv\\test_img.jpg");
		img = new JLabel(icon);
		img.setSize(500, 500);
		
		frame = new JFrame("Image Editor");
		frame.setSize(500, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setJMenuBar(menuBar);
		frame.add(img);
	}

	private void events() {
		grayScale.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Applying filter!");
				
				GrayScale gray = new GrayScale(getImageFromLabel());
				
				img.setIcon(new ImageIcon(gray.getGrayScaledImg()));
				img.revalidate();
				
			}
		});
		
		rotate180.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					RotateImg rotate = new RotateImg(180, getImageFromLabel());
					
					img.setIcon(new ImageIcon(rotate.getRotatedImg()));
					img.revalidate();
			
			}
		});
		
		rotate90.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					RotateImg rotate = new RotateImg(90, getImageFromLabel());
					
					img.setIcon(new ImageIcon(rotate.getRotatedImg()));
					img.revalidate();
			
			}
		});
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
}
