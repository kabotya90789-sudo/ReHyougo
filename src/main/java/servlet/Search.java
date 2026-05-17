package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Event;
import model.Pin;
import model.SearchLogic;
import model.User;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//    	User user = (User) session.getAttribute("loginUser");

//    	String user = "hyogo1111";

    	HttpSession session = req.getSession();

    	User loginUser = (User) session.getAttribute("loginUser");
    	String userId = "-1";
    	if(loginUser != null) {
    		userId = loginUser.getUserId();
    	}
    	
    	System.out.println("userId = " + userId);
    	
        String keyword = req.getParameter("keyword");
        boolean isEvt = false;
        
        System.out.println("keyword = " + keyword);

		List<String> words = new ArrayList<>();
		List<Integer> tags = new ArrayList<>();
		
		List<Pin> resultPins = new ArrayList<>();
		List<Event> resultEvents = new ArrayList<>();

		if (keyword != null && !keyword.isBlank()) {
		    // スペースで分割（半角・全角対応）
		    String[] split = keyword.trim().split("\\s+");

		    for (String w : split) {
		    	if(w.startsWith("#")) {
		    		switch(w) {
			    	case("#食べる"):
			    		tags.add(1);
			    		break;
			    	case("#遊ぶ"):
			    		tags.add(2);
			    		break;
			    	case("#観る"):
			    		tags.add(3);
			    		break;
			    	case("#買う"):
			    		tags.add(4);
			    		break;
			    	case("#休む"):
			    		tags.add(5);
			    		break;
			    	case("#イベント"):
			    		isEvt = true;
			    		break;
		    		}
		    	} else {
		    		words.add(w);
		    	}
		    }
	    	SearchLogic sl = new SearchLogic();
	    	if(isEvt) {
	    		resultEvents = sl.eventSearch(words, userId);
	    	} else {
	    		resultPins = sl.pinSearch(words, tags, userId);
	    	}
	 }
		
		
		// デバッグ
		
		System.out.println("words = " + words);
		System.out.println("tags = " + tags);
		
		System.out.println(resultEvents);
		System.out.println(resultPins);

		for (Pin p : resultPins) {
			String name = p.getName();
			System.out.println(name);
		}
		
		for (Event e : resultEvents) {
			String name = e.getName();
			System.out.println(name);
		}
		
//		出力
		
		req.setAttribute("resultEvents", resultEvents);
		req.setAttribute("resultPins", resultPins);
		req.setAttribute("keyword", keyword);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/search_result.jsp");
		dispatcher.forward(req, resp);

	}
    

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, res);
	}

}
