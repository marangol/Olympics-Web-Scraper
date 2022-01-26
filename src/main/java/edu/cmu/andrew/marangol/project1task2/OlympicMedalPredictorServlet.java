package edu.cmu.andrew.marangol.project1task2;

/*
 * @author Martin Arango (marangol)
 *
 * Copied code and comment structure from Lab 2
 * This file is the servlet acting as the controller
 * There are two views - index.jsp and result.jsp
 * It decides between the two by determining if there is a country selected or
 * not. If there is no parameter, then it uses the index.jsp view, as a
 * starting place. If there is a country selected, then it computes the prediction
 * and uses the result.jsp view.
 * The model is provided by OlympicMedalPredictorModel.java.
 * To run project remember to change the deployment application context to:
 * /OlympicMedalPredictor-1.0-SNAPSHOT
 */
import java.io.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "OlympicMedalPredictorServlet", value = "/OlympicMedalPredictorServlet")
public class OlympicMedalPredictorServlet extends HttpServlet{

    OlympicMedalPredictorModel ompm = null; // The "business model" for this app

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        ompm = new OlympicMedalPredictorModel();
    }

    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // get the search parameter if it exists
        String country = request.getParameter("country");

        String nextView;
        /*
         * Check if the country parameter is present.
         * If not, then give the user instructions and prompt for a country.
         * If there is a search parameter, then do the search and return the result.
         */
        if (country != null) {
            // use model to do the scraping and choose the result view
            ompm.doWorldometer(country);
            ompm.doOlympics(country);
            ompm.doExpectedMedalCount();
            ompm.doFlag(country);
            // Pass attributes to the view
            request.setAttribute("gdp",ompm.getGdp());
            request.setAttribute("population",ompm.getPopulation());
            request.setAttribute("country",country);
            request.setAttribute("goldMedals",ompm.getGoldMedals());
            request.setAttribute("silverMedals",ompm.getSilverMedals());
            request.setAttribute("bronzeMedals",ompm.getBronzeMedals());
            request.setAttribute("weightedMedalCount",ompm.getWheightedMedalCount());
            request.setAttribute("expectedMedalCount", ompm.getExpectedMedalCount());
            request.setAttribute("flagHTML", ompm.getFlagHTML());

            // Pass the user search string (pictureTag) also to the view.
            nextView = "result.jsp";
        } else {
            // no search parameter so choose the index view
            nextView = "index.jsp";
        }
        // Transfer control over the correct "view"
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }

}
