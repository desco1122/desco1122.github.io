//Daniel Escobedo
//CS-499 SNHU
//Artifact 3: Databases 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import org.bson.Document;

/**
 * This class represents the main application window for the SlideShow.
 * It manages the UI components and interactions with the MongoDB database.
 */
public class SlideShow extends JFrame {

    private JPanel slidePane;
    private JPanel textPane;
    private JPanel buttonPane;
    private JButton btnPrev;
    private JButton btnNext;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private List<Document> slides;
    private int currentIndex;

    /**
     * Constructor for the SlideShow class.
     * Initializes the UI components.
     */
    public SlideShow() throws HeadlessException {
        initComponent();
    }

    /**
     * Initializes and sets up all UI components.
     */
    private void initComponent() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("SNHU Travel Slideshow (MongoDB Edition)");
        getContentPane().setLayout(new BorderLayout(10, 50));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        slidePane = new JPanel(new CardLayout());
        textPane = new JPanel(new CardLayout());
        buttonPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        loadSlidesFromMongoDB();

        getContentPane().add(slidePane, BorderLayout.CENTER);
        getContentPane().add(textPane, BorderLayout.SOUTH);

        initializeButtons();

        getContentPane().add(buttonPane, BorderLayout.SOUTH);
    }

    /**
     * Initializes all buttons and adds them to the button pane.
     */
    private void initializeButtons() {
        btnPrev = new JButton("Previous");
        btnPrev.addActionListener(e -> goPrevious());
        buttonPane.add(btnPrev);

        btnNext = new JButton("Next");
        btnNext.addActionListener(e -> goNext());
        buttonPane.add(btnNext);

        btnAdd = new JButton("Add Slide");
        btnAdd.addActionListener(e -> addSlide());
        buttonPane.add(btnAdd);

        btnUpdate = new JButton("Update Slide");
        btnUpdate.addActionListener(e -> updateSlide());
        buttonPane.add(btnUpdate);

        btnDelete = new JButton("Delete Slide");
        btnDelete.addActionListener(e -> deleteSlide());
        buttonPane.add(btnDelete);
    }

    /**
     * Loads all slides from MongoDB and displays the first slide.
     */
    private void loadSlidesFromMongoDB() {
        slides = MongoDBManager.getAllSlides();
        currentIndex = 0;
        if (!slides.isEmpty()) {
            updateDisplay();
        }
    }

    /**
     * Updates the display with the current slide.
     */
    private void updateDisplay() {
        if (!slides.isEmpty()) {
            Document currentSlide = slides.get(currentIndex);
            slidePane.removeAll();
            textPane.removeAll();

            JLabel imageLabel = new JLabel(new ImageIcon(currentSlide.getString("imagePath")));
            JLabel textLabel = new JLabel(currentSlide.getString("description"));

            slidePane.add(imageLabel);
            textPane.add(textLabel);

            slidePane.revalidate();
            slidePane.repaint();
            textPane.revalidate();
            textPane.repaint();
        }
    }

    /**
     * Moves to the previous slide if available.
     */
    private void goPrevious() {
        if (currentIndex > 0) {
            currentIndex--;
            updateDisplay();
        }
    }

    /**
     * Moves to the next slide if available.
     */
    private void goNext() {
        if (currentIndex < slides.size() - 1) {
            currentIndex++;
            updateDisplay();
        }
    }

    /**
     * Adds a new slide to the database and refreshes the display.
     */
    private void addSlide() {
        String imagePath = JOptionPane.showInputDialog(this, "Enter image path:");
        String description = JOptionPane.showInputDialog(this, "Enter description:");
        if (imagePath != null && !imagePath.isEmpty() && description != null && !description.isEmpty()) {
            MongoDBManager.addSlide(imagePath, description);
            loadSlidesFromMongoDB();
        }
    }

    /**
     * Updates the current slide in the database and refreshes the display.
     */
    private void updateSlide() {
        String imagePath = JOptionPane.showInputDialog(this, "Enter new image path:");
        String description = JOptionPane.showInputDialog(this, "Enter new description:");
        if (imagePath != null && !imagePath.isEmpty() && description != null && !description.isEmpty()) {
            Document currentSlide = slides.get(currentIndex);
            MongoDBManager.updateSlide(currentSlide.getObjectId("_id").toString(), imagePath, description);
            loadSlidesFromMongoDB();
        }
    }

    /**
     * Deletes the current slide from the database and refreshes the display.
     */
    private void deleteSlide() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this slide?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Document currentSlide = slides.get(currentIndex);
            MongoDBManager.deleteSlide(currentSlide.getObjectId("_id").toString());
            loadSlidesFromMongoDB();
        }
    }

    /**
     * Main method to launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SlideShow ss = new SlideShow();
            ss.setVisible(true);
        });
    }
}