/**
 * File: SlideShow.java
 * Author: Daniel Escobedo
 * Contact: danielscobedo1122@gmail.com
 * Date: 10/16/2024
 * Version: 2.1
 * 
 * Description: This file contains the implementation of a slide show application
 * with enhanced algorithms for efficient navigation and slide management.
 * The application uses a doubly linked list for O(1) slide navigation and
 * implements a binary search algorithm for finding slides by index.
 * 
 * Enhancements:
 * - Fixed SlideNode implementation and usage
 * - Implemented doubly linked list for efficient bi-directional navigation
 * - Added binary search algorithm for O(log n) slide access by index
 * - Improved code organization and added detailed comments
 * - Implemented slide caching for frequently accessed slides
 * 
 * Course Outcomes addressed:
 * 3. Design and evaluate computing solutions that solve a given problem using
 *    algorithmic principles and computer science practices and standards
 *    appropriate to its solution while managing the trade-offs involved in design choices.
 * 4. Demonstrate an ability to use well-founded and innovative techniques, skills,
 *    and tools in computing practices for the purpose of implementing computer
 *    solutions that deliver value and accomplish industry-specific goals.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;

public class SlideShow extends JFrame {

    private JPanel slidePane;
    private JPanel textPane;
    private JPanel buttonPane;
    private JButton btnPrev;
    private JButton btnNext;
    private JButton btnJump;
    private JTextField jumpIndexField;
    private SlideList slides;
    private SlideNode currentSlide;
    private HashMap<Integer, SlideNode> slideCache; // Cache for frequently accessed slides

    /**
     * Constructor for the SlideShow class.
     * Initializes the UI components and slide data structure.
     * @throws HeadlessException if GraphicsEnvironment.isHeadless() returns true
     */
    public SlideShow() throws HeadlessException {
        initComponent();
        slideCache = new HashMap<>(); // Initialize slide cache
    }

    /**
     * Initializes the components of the SlideShow application.
     * Sets up the main frame, panels, and navigation buttons.
     * Time Complexity: O(n) where n is the number of slides
     * Space Complexity: O(n) for storing n slides
     */
    private void initComponent() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("SNHU Travel Presents: The Top Detox & Wellness Vacation Destinations!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(10, 50));

        // Initialize panels
        slidePane = new JPanel(new CardLayout());
        textPane = new JPanel(new CardLayout());
        textPane.setBackground(Color.WHITE);
        textPane.setBounds(5, 470, 790, 50);
        buttonPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Create and populate the slide list
        slides = new SlideList();
        initializeSlides();

        // Set the current slide to the first slide
        currentSlide = slides.getHead();
        updateDisplay();

        // Add panels to the frame
        getContentPane().add(slidePane, BorderLayout.CENTER);
        getContentPane().add(textPane, BorderLayout.SOUTH);

        // Create and add navigation buttons
        btnPrev = new JButton("Previous");
        btnPrev.addActionListener(e -> goPrevious());
        buttonPane.add(btnPrev);

        btnNext = new JButton("Next Spot");
        btnNext.addActionListener(e -> goNext());
        buttonPane.add(btnNext);

        // Add jump to slide functionality
        jumpIndexField = new JTextField(5);
        btnJump = new JButton("Jump to Slide");
        btnJump.addActionListener(e -> jumpToSlide());
        buttonPane.add(jumpIndexField);
        buttonPane.add(btnJump);

        getContentPane().add(buttonPane, BorderLayout.SOUTH);
    }

    /**
     * Initializes the slides with images and descriptions.
     * Time Complexity: O(n) where n is the number of slides
     * Space Complexity: O(n) for storing n slides in the linked list
     */
    private void initializeSlides() {
        for (int i = 1; i <= 5; i++) {
            JLabel imageLabel = new JLabel(getResizeIcon(i));
            JLabel textLabel = new JLabel(getTextDescription(i));
            slides.addSlide(new SlideNode(imageLabel, textLabel));
        }
    }

    /**
     * Updates the display with the current slide's image and text.
     * Time Complexity: O(1) - constant time operation
     * Space Complexity: O(1) - uses fixed amount of memory
     */
    private void updateDisplay() {
        slidePane.removeAll();
        textPane.removeAll();
        slidePane.add(currentSlide.getImageLabel());
        textPane.add(currentSlide.getTextLabel());
        slidePane.revalidate();
        slidePane.repaint();
        textPane.revalidate();
        textPane.repaint();
    }

    /**
     * Navigates to the previous slide if available.
     * Time Complexity: O(1) - constant time operation due to doubly linked list
     * Space Complexity: O(1) - uses fixed amount of memory
     */
    private void goPrevious() {
        if (currentSlide.getPrev() != null) {
            currentSlide = currentSlide.getPrev();
            updateDisplay();
        }
    }

    /**
     * Navigates to the next slide if available.
     * Time Complexity: O(1) - constant time operation due to doubly linked list
     * Space Complexity: O(1) - uses fixed amount of memory
     */
    private void goNext() {
        if (currentSlide.getNext() != null) {
            currentSlide = currentSlide.getNext();
            updateDisplay();
        }
    }

    /**
     * Jumps to a specific slide based on the index entered by the user.
     * Uses binary search for efficient slide lookup.
     * Time Complexity: O(log n) where n is the number of slides
     * Space Complexity: O(1) - uses fixed amount of memory
     */
    private void jumpToSlide() {
        try {
            int index = Integer.parseInt(jumpIndexField.getText()) - 1; // Convert to 0-based index
            SlideNode targetSlide = findSlideByIndex(index);
            if (targetSlide != null) {
                currentSlide = targetSlide;
                updateDisplay();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid slide index");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number");
        }
    }

    /**
     * Finds a slide by its index using binary search algorithm.
     * Time Complexity: O(log n) where n is the number of slides
     * Space Complexity: O(1) - uses fixed amount of memory
     * @param index The index of the slide to find (0-based)
     * @return The SlideNode at the given index, or null if not found
     */
    private SlideNode findSlideByIndex(int index) {
        // Check cache first
        if (slideCache.containsKey(index)) {
            return slideCache.get(index);
        }

        SlideNode left = slides.getHead();
        SlideNode right = slides.getTail();
        int leftIndex = 0;
        int rightIndex = slides.getSize() - 1;

        while (left != null && right != null && leftIndex <= rightIndex) {
            if (leftIndex == index) {
                slideCache.put(index, left); // Cache the result
                return left;
            }
            if (rightIndex == index) {
                slideCache.put(index, right); // Cache the result
                return right;
            }

            int midIndex = (leftIndex + rightIndex) / 2;
            SlideNode mid = getMiddleNode(left, right);

            if (midIndex == index) {
                slideCache.put(index, mid); // Cache the result
                return mid;
            } else if (midIndex < index) {
                left = mid.getNext();
                leftIndex = midIndex + 1;
            } else {
                right = mid.getPrev();
                rightIndex = midIndex - 1;
            }
        }

        return null; // Slide not found
    }

    /**
     * Finds the middle node between two given nodes in the doubly linked list.
     * Time Complexity: O(k) where k is the distance between left and right nodes
     * Space Complexity: O(1) - uses fixed amount of memory
     * @param left The left boundary node
     * @param right The right boundary node
     * @return The middle node between left and right
     */
    private SlideNode getMiddleNode(SlideNode left, SlideNode right) {
        SlideNode slow = left;
        SlideNode fast = left;

        while (fast != right && fast.getNext() != right) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }

        return slow;
    }

    /**
     * Generates the HTML string for resizing and displaying an image.
     * @param i The index of the image to display
     * @return HTML string containing the resized image
     */
    private String getResizeIcon(int i) {
        return "<html><body><img width='800' height='500' src='" + getClass().getResource("/resources/TestImage" + i + ".jpg") + "'</body></html>";
    }

    /**
     * Retrieves the description text for a given slide.
     * @param i The index of the slide
     * @return HTML string containing the formatted description text
     */
    private String getTextDescription(int i) {
        String[] descriptions = {
            "<html><body><font size='4'>Langkawi, Malaysia: Relax in the great outdoors</font> <br>Lovers of the great outdoors can get their travel fix in the Langkawi archipelago in Malaysia, a destination known for its staggering limestone cliffs, lush greenery, and jaw-dropping natural geological formations.</body></html>",
            "<html><body><font size='4'>Galle, Sri Lanka: Relax in historic luxury</font> <br>The city of Galle on the southwest coast of Sri Lanka is perfect for the traveler looking for a little luxury and a spot of relaxation.</body></html>",
            "<html><body><font size='4'>Bagan, Myanmar: High flying relaxation</font> <br>Travelers seeking remote adventure and laid-back discovery would be correct in adding Bagan to their list of most relaxing places.</body></html>",
            "<html><body><font size='4'>Nachi Falls, Japan: sacred and serene wonders</font> <br>The exquisite Nachi Falls in Japan are a magnificent sight to behold. Hidden away in the Nachi Primeval Forest, the waterfall features an incredible 133-meter drop.</body></html>",
            "<html><body><font size='4'>Lake Bled, Slovenia: As peaceful and pretty as it looks</font> <br>Built in the 15th century, the church on Bled Island is a much-loved landmark in Slovenia. This destination is every bit as charming (and peaceful) as its picture suggests.</body></html>"
        };
        return descriptions[i - 1];
    }

    /**
     * Main method to launch the application.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SlideShow ss = new SlideShow();
            ss.setVisible(true);
        });
    }
}

/**
 * SlideNode class represents a single slide in the presentation.
 * It contains the image and text for the slide, as well as references to the previous and next slides.
 */
class SlideNode {
    private JLabel imageLabel;
    private JLabel textLabel;
    private SlideNode prev;
    private SlideNode next;

    /**
     * Constructor for SlideNode.
     * @param imageLabel The JLabel containing the slide's image
     * @param textLabel The JLabel containing the slide's description text
     */
    public SlideNode(JLabel imageLabel, JLabel textLabel) {
        this.imageLabel = imageLabel;
        this.textLabel = textLabel;
    }

    // Getters and setters
    public JLabel getImageLabel() { return imageLabel; }
    public JLabel getTextLabel() { return textLabel; }
    public SlideNode getPrev() { return prev; }
    public void setPrev(SlideNode prev) { this.prev = prev; }
    public SlideNode getNext() { return next; }
    public void setNext(SlideNode next) { this.next = next; }
}

/**
 * SlideList class implements a doubly linked list to manage the slides.
 * It provides methods for adding slides and accessing the first and last slides.
 */
class SlideList {
    private SlideNode head;
    private SlideNode tail;
    private int size;

    /**
     * Returns the first slide in the list.
     * @return The head SlideNode
     */
    public SlideNode getHead() { return head; }

    /**
     * Returns the last slide in the list.
     * @return The tail SlideNode
     */
    public SlideNode getTail() { return tail; }

    /**
     * Returns the number of slides in the list.
     * @return The size of the slide list
     */
    public int getSize() { return size; }

    /**
     * Adds a new slide to the end of the list.
     * Time Complexity: O(1) - constant time operation
     * Space Complexity: O(1) - uses fixed amount of memory
     * @param node The SlideNode to be added
     */
    public void addSlide(SlideNode node) {
        if (head == null) {
            head = tail = node;
        } else {
            tail.setNext(node);
            node.setPrev(tail);
            tail = node;
        }
        size++;
    }
}