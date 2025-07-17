package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.HibernateUtil;
import hibernate.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Sanjana
 */
@WebServlet(name = "SignIn", urlPatterns = {"/SignIn"})
public class SignIn extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();
        JsonObject signIn = gson.fromJson(request.getReader(), JsonObject.class);

        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);

        final String email = signIn.get("email").getAsString();
        String password = signIn.get("password").getAsString();

//        System.out.println(email);
//        System.out.println(password);
        if (email.isEmpty()) {

            responseObject.addProperty("message", "Email can be empty!");

        } else if (!Util.isEmailValid(email)) {

            responseObject.addProperty("message", "Please enter a Valide email!");

        } else if (password.isEmpty()) {

            responseObject.addProperty("message", "Password can be empty!");

        } else {

            Session s = HibernateUtil.getSessionFactory().openSession();

            Criteria c = s.createCriteria(User.class);

            Criterion crt1 = Restrictions.eq("email", email);
            Criterion crt2 = Restrictions.eq("password", password);

            c.add(crt1);
            c.add(crt2);

            if (c.list().isEmpty()) {

                responseObject.addProperty("message", "Invalide ceredentials!");

            } else {

                User u = (User) c.list().get(0);

                responseObject.addProperty("status", true);

                //Add session 
                HttpSession ses = request.getSession();

                if (!u.getVerification().equals("Verifide")) { // not verifide

                    ses.setAttribute("email", email);

                    responseObject.addProperty("message", "1"); // Not Verifide User

                } else { //Verifide

                    ses.setAttribute("user", u);
                    responseObject.addProperty("message", "2"); // Verifide User

                }

            }

            s.close();

        }

        String responseText = gson.toJson(responseObject);
        response.setContentType("application/json");
        response.getWriter().write(responseText);

    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        
//        JsonObject responseObject = new JsonObject();
//        
//        if (request.getSession().getAttribute("email") != null || request.getSession().getAttribute("user") != null) {
//            
////            String email = request.getSession().getAttribute("email").toString();
////            
////            SessionFactory sf = HibernateUtil.getSessionFactory();
////            Session s = sf.openSession();
////            
////            Criteria c = s.createCriteria(User.class);
////            c.add(Restrictions.eq("email", email));
////            
////            if (!c.list().isEmpty()) {
////                
////            }
//
////        responseObject.addProperty("status", true);
//            responseObject.addProperty("message", "1");
//            
//        } else {
//
////        responseObject.addProperty("status", false);
//            responseObject.addProperty("message", "2");
//            
//        }
//        
//        Gson gson = new Gson();
//        String toJson = gson.toJson(responseObject);
//        response.setContentType("application/json");
//        response.getWriter().write(toJson);
//        
//    }
}
