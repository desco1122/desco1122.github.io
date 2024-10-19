//Daniel Escobedo
//CS-499 SNHU
//Artifact 3: Databases 

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import com.google.gson.Gson;

/**
 * Servlet implementation class for managing slides via a web interface.
 * This servlet handles CRUD operations for slides, interacting with MongoDB.
 */
@WebServlet("/slides")
public class SlideManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Gson gson = new Gson();

    /**
     * Handles GET requests. Retrieves all slides from the database and returns them as JSON.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Document> slides = MongoDBManager.getAllSlides();
        String jsonSlides = gson.toJson(slides);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonSlides);
            out.flush();
        }
    }

    /**
     * Handles POST requests. Adds a new slide to the database.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imagePath = request.getParameter("imagePath");
        String description = request.getParameter("description");
        
        if (imagePath != null && !imagePath.isEmpty() && description != null && !description.isEmpty()) {
            MongoDBManager.addSlide(imagePath, description);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * Handles PUT requests. Updates an existing slide in the database.
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String imagePath = request.getParameter("imagePath");
        String description = request.getParameter("description");
        
        if (id != null && !id.isEmpty() && imagePath != null && !imagePath.isEmpty() && description != null && !description.isEmpty()) {
            MongoDBManager.updateSlide(id, imagePath, description);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * Handles DELETE requests. Deletes a slide from the database.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        
        if (id != null && !id.isEmpty()) {
            MongoDBManager.deleteSlide(id);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}