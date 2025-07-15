/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.HibernateUtil;
import hibernate.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Mail;
import model.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Sanjana
 */
@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUp extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        System.out.println("OK");
        Gson gson = new Gson();

        JsonObject user = gson.fromJson(request.getReader(), JsonObject.class);

        String firstname = user.get("firstname").getAsString();
        String lastname = user.get("lastname").getAsString();
        final String email = user.get("email").getAsString();
        String password = user.get("password").getAsString();

//        System.out.println(firstname);
//        System.out.println(lastname);
//        System.out.println(email);
//        System.out.println(password);
//        Save
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();

        User u = new User();
        u.setFirst_name(firstname);
        u.setLast_name(lastname);
        u.setEmail(email);
        u.setPassword(password);

        // genarate verification code
        final String verificationCode = Util.genrateCode();
        u.setVerification(verificationCode);

        u.setCreated_at(new Date());

        s.save(u);
        s.beginTransaction().commit();

        // send Email
        new Thread(new Runnable() {
            @Override
            public void run() {

                Mail.sendMail(email, "SmartTrade - Verification", "<h1>" + verificationCode + "</h1>");

            }
        }).start();

    }

}
