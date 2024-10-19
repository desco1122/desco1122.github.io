//Daniel Escobedo
//CS-499 SNHU
//Artifact 3: Databases 

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages all MongoDB operations for the SlideShow application.
 * It provides methods for CRUD operations on slides.
 */
public class MongoDBManager {
    // Constants for MongoDB connection and database details
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "slideshow_db";
    private static final String COLLECTION_NAME = "slides";

    // MongoDB client and database objects
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;

    // Static initializer to set up MongoDB connection
    static {
        try {
            mongoClient = MongoClients.create(CONNECTION_STRING);
            database = mongoClient.getDatabase(DATABASE_NAME);
            collection = database.getCollection(COLLECTION_NAME);
        } catch (Exception e) {
            // Log the exception or rethrow as a runtime exception
            System.err.println("Failed to initialize MongoDB connection: " + e.getMessage());
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    /**
     * Adds a new slide to the database.
     * @param imagePath the path to the slide image
     * @param description the description of the slide
     */
    public static void addSlide(String imagePath, String description) {
        Document slide = new Document("imagePath", imagePath)
                .append("description", description);
        collection.insertOne(slide);
    }

    /**
     * Retrieves all slides from the database.
     * @return a List of Document objects representing slides
     */
    public static List<Document> getAllSlides() {
        List<Document> slides = new ArrayList<>();
        collection.find().into(slides);
        return slides;
    }

    /**
     * Updates an existing slide in the database.
     * @param id the ObjectId of the slide to update
     * @param imagePath the new image path
     * @param description the new description
     */
    public static void updateSlide(String id, String imagePath, String description) {
        Document filter = new Document("_id", new ObjectId(id));
        Document update = new Document("$set", new Document("imagePath", imagePath)
                .append("description", description));
        collection.updateOne(filter, update);
    }

    /**
     * Deletes a slide from the database.
     * @param id the ObjectId of the slide to delete
     */
    public static void deleteSlide(String id) {
        Document filter = new Document("_id", new ObjectId(id));
        collection.deleteOne(filter);
    }

    /**
     * Closes the MongoDB client connection.
     * This method should be called when the application is shutting down.
     */
    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}