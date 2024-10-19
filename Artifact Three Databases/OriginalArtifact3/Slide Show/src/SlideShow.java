/**
 * SlideShow.java
 * Original Slideshow Application
 * 
 * This file contains the original implementation of the SlideShow application.
 * It uses Java Swing to create a simple slideshow of travel destinations.
 * 
 * @author Daniel Escobedo
 * @contact danielescobedo1122@gmail.com
 * @version 1.0
 * @date 10/16/2024
 * 
 * Intent:
 * The purpose of this application is to display a series of travel destination
 * images with accompanying descriptions. It provides basic navigation through
 * the slides using "Previous" and "Next" buttons.
 * 
 * Functionality:
 * - Displays images and text descriptions for travel destinations
 * - Allows navigation between slides
 * - Uses hardcoded data for slides
 * 
 * Future Enhancement Considerations:
 * - Implement database integration for dynamic slide management
 * - Add functionality to add, update, and delete slides
 * - Create a web interface for broader accessibility
 */


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

public class SlideShow extends JFrame {

	//Declare Variables
	private JPanel slidePane;
	private JPanel textPane;
	private JPanel buttonPane;
	private CardLayout card;
	private CardLayout cardText;
	private JButton btnPrev;
	private JButton btnNext;
	private JLabel lblSlide;
	private JLabel lblTextArea;

	/**
	 * Create the application.
	 */
	public SlideShow() throws HeadlessException {
		initComponent();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initComponent() {
		//Initialize variables to empty objects
		//Changed color of background to white 
		
		card = new CardLayout();
		cardText = new CardLayout();
		slidePane = new JPanel();
		textPane = new JPanel();
		textPane.setBackground(Color.WHITE);
		textPane.setBounds(5, 470, 790, 50);
		textPane.setVisible(true);
		buttonPane = new JPanel();
		btnPrev = new JButton();
		btnNext = new JButton();
		lblSlide = new JLabel();
		lblTextArea = new JLabel();
		

		//Setup frame attributes 
		
		//Changed the title
		
		setSize(800, 600);
		setLocationRelativeTo(null);
		setTitle("SNHU Travel Presents : The Top Detox & Wellness Vacation Destinations!");
		getContentPane().setLayout(new BorderLayout(10, 50));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Setting the layouts for the panels
		slidePane.setLayout(card);
		textPane.setLayout(cardText);
		

		//Logic to add each of the slides and text
		for (int i = 1; i <= 8; i++) {
			lblSlide = new JLabel();
			lblTextArea = new JLabel();
			lblSlide.setText(getResizeIcon(i));
			lblTextArea.setText(getTextDescription(i));
			slidePane.add(lblSlide, "card" + i);
			textPane.add(lblTextArea, "cardText" + i);
		}

		getContentPane().add(slidePane, BorderLayout.CENTER);
		getContentPane().add(textPane, BorderLayout.SOUTH);

		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		btnPrev.setText("Previous");
		btnPrev.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goPrevious();
			}
		});
		buttonPane.add(btnPrev);

		btnNext.setText("Next Spot"); //changed next to next spot
		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goNext();
			}
		});
		buttonPane.add(btnNext);

		getContentPane().add(buttonPane, BorderLayout.SOUTH);
	}

	/**
	 * Previous Button Functionality
	 */
	private void goPrevious() {
		card.previous(slidePane);
		cardText.previous(textPane);
	}
	
	/**
	 * Next Button Functionality
	 */
	private void goNext() {
		card.next(slidePane);
		cardText.next(textPane);
	}

	/**
	 * Method to get the images
	 */
	private String getResizeIcon(int i) {
		String image = "";
		if (i == 1) {
			image = "<html><body><img width= '800' height='500' src='" + getClass().getResource("/resources/TestImage1.jpg") + "'</body></html>";
		} else if (i==2){
			image = "<html><body><img width= '800' height='500' src='" + getClass().getResource("/resources/TestImage2.jpg") + "'</body></html>";
		} else if (i==3){
			image = "<html><body><img width= '800' height='500' src='" + getClass().getResource("/resources/TestImage3.jpg") + "'</body></html>";
		} else if (i==4){
			image = "<html><body><img width= '800' height='500' src='" + getClass().getResource("/resources/TestImage4.jpg") + "'</body></html>";
		} else if (i==5){
			image = "<html><body><img width= '800' height='500' src='" + getClass().getResource("/resources/TestImage5.jpg") + "'</body></html>";
		}
		return image;
	}
	
	/**
	 * Method to get the text values
	 */
	
	//changed the font size and titles
	//added my custom images 
	
	private String getTextDescription(int i) {
		String text = ""; 
		if (i==1){
			text = "<html><body><font size='4'>Langkawi, Malaysia: Relax in the great outdoors</font> <br>Lovers of the great outdoors can get their travel fix in the Langkawi archipelago in Malaysia, a destination known for its staggering limestone cliffs, lush greenery, and jaw-dropping natural geological formations.</body></html>";
		} else if (i==2){
			text = "<html><body><font size='4'>Galle, Sri Lanka: Relax in historic luxury</font> <br>The city of Galle on the southwest coast of Sri Lanka is perfect for the traveler looking for a little luxury and a spot of relaxation.</body></html>";
		} else if (i==3){
			text = "<html><body><font size='4'>Bagan, Myanmar: High flying relaxation</font> <br>Travelers seeking remote adventure and laid-back discovery would be correct in adding Bagan to their list of most relaxing places.</body></html>";
		} else if (i==4){
			text = "<html><body><font size='4'>Nachi Falls, Japan: sacred and serene wonders</font> <br>The exquisite Nachi Falls in Japan are a magnificent sight to behold. Hidden away in the Nachi Primeval Forest, the waterfall features an incredible 133-meter drop.</body></html>";
		} else if (i==5){
			text = "<html><body><font size='4'>Lake Bled, Slovenia: As peaceful and pretty as it looks</font> <br>Built in the 15th century, the church on Bled Island is a much-loved landmark in Slovenia. This destination is every bit as charming (and peaceful) as its picture suggests.</body></html>";
		}
		
		return text;
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				SlideShow ss = new SlideShow();
				ss.setVisible(true);
			}
		});
	}
}