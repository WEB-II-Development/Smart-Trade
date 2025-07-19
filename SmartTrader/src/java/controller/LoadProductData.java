/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.Brand;
import hibernate.Color;
import hibernate.Condition;
import hibernate.HibernateUtil;
import hibernate.Model;
import hibernate.Stroage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import netscape.javascript.JSObject;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author Sanjana
 */
@WebServlet(name = "LoadProductData", urlPatterns = {"/LoadProductData"})
public class LoadProductData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("OK");

        Gson gson = new Gson();

        JsonObject resposObject = new JsonObject();

        resposObject.addProperty("status", false);

        Session s = HibernateUtil.getSessionFactory().openSession();

        //get Brands
        Criteria c1 = s.createCriteria(Brand.class);
        List<Brand> brandList = c1.list();

//        for (Brand brand : brandList) {
//
//            System.out.println(brand.getId());
//            System.out.println(brand.getName());
//
//        }

        //get Models
        Criteria c2 = s.createCriteria(Model.class);
        List<Model> modelList = c2.list();

//        for (Model model : modelList) {
//
//            System.out.println(model.getId());
//            System.out.println(model.getName());
//            System.out.println(model.getBrand().getId());
//            System.out.println(model.getBrand().getName());
//
//        }

        //get Colors
        Criteria c3 = s.createCriteria(Color.class);
        List<Color> colorList = c3.list();

//        for (Color color : colorList) {
//
//            System.out.println(color.getId());
//            System.out.println(color.getValue());
//
//        }

        //get Qualities
        Criteria c4 = s.createCriteria(Condition.class);
        List<Condition> qualityList = c4.list();

//        for (Quality quality : qualityList) {
//
//            System.out.println(quality.getId());
//            System.out.println(quality.getValue());
//
//        }

        //get Stroage
        Criteria c5 = s.createCriteria(Stroage.class);
        List<Stroage> stroageList = c5.list();

//        for (Stroage stroage : stroageList) {
//
//            System.out.println(stroage.getId());
//            System.out.println(stroage.getValue());
//
//        }

        s.close();

        resposObject.add("brandList", gson.toJsonTree(brandList));
        resposObject.add("modelList", gson.toJsonTree(modelList));
        resposObject.add("colorList", gson.toJsonTree(colorList));
        resposObject.add("qualityList", gson.toJsonTree(qualityList));
        resposObject.add("stroageList", gson.toJsonTree(stroageList));

        resposObject.addProperty("status", true);

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(resposObject));

    }

}
