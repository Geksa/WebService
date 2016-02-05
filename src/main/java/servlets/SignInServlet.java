package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class SignInServlet extends HttpServlet {

    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    //get public user profile
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        CheckUserProfile(request, response);


    }

    private void CheckUserProfile(HttpServletRequest request, HttpServletResponse response) {

        StringBuffer responseText=new StringBuffer();
        int loginOkCode=200;
        int loginBadCode=401;

        Map<String, Object> pageInputVariables = new HashMap<>();
        String login =(String)request.getParameterMap().get("login")[0];
        String pass =(String)request.getParameterMap().get("password")[0];
        UserProfile userProfile=accountService.getUserByLogin(login);

        if (userProfile!=null){
            responseText.append("Authorized: ");
            responseText.append(login);
            response.setStatus(loginOkCode);
        }
        else {
            responseText.append("Unauthorized");
            response.setStatus(loginBadCode);
        }
        Map<String, Object> pageVariables = createPageVariablesMap(request, responseText.toString());

        try {
            response.getWriter().println(PageGenerator.instance().getPage("page.html", pageVariables));
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }

        response.setContentType("text/html;charset=utf-8");
    }

    //sign up
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        CheckUserProfile(request, response);

    }

    //change profile
    public void doPut(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

    }

    //unregister
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request, String responseText) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("value", responseText);
        return pageVariables;
    }
}
