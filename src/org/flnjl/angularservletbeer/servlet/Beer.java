package org.flnjl.angularservletbeer.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.flnjl.angularservletbeer.validator.BeerValidator;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class Beer
 */
@WebServlet("/Beer")
public class Beer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Beer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		org.flnjl.angularservletbeer.model.Beer beer = org.flnjl.angularservletbeer.model.Beer.getBeer(request.getParameter("id"));
		ObjectMapper mapper = new ObjectMapper();
		String jsonList = mapper.writeValueAsString(beer);	
		
		response.getWriter().append(jsonList);
		response.setContentType("text/html;charset=UTF-8");		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		StringBuilder beerBuffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    try {
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	beerBuffer.append(line);
	        }
	    } finally {
	        reader.close();
	    }
	    String beerJson = beerBuffer.toString();
	    ObjectMapper mapper = new ObjectMapper();
	    org.flnjl.angularservletbeer.model.Beer beer = mapper.readValue(beerJson, org.flnjl.angularservletbeer.model.Beer.class);
	    
	    if (BeerValidator.validate(beer)) {
	    	beer.save();
	    	response.getWriter().append("{\"success\": 1, \"beer\": \"" + beer.getId() + "\"}");
	    }
	    else {
	    	List<String> errors = BeerValidator.getErrors();
	    	
	    	ObjectMapper mapperErrors = new ObjectMapper();
			String jsonErrors = mapperErrors.writeValueAsString(errors);
			response.getWriter().append("{\"success\": 0, \"errors\": " + jsonErrors + "}");
			response.setContentType("application/json;charset=UTF-8");	
	    }
	}

}
