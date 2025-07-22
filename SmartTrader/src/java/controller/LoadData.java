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
import hibernate.Product;
import hibernate.Status;
import hibernate.Stroage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Sanjana
 */
@WebServlet(name = "LoadData", urlPatterns = {"/LoadData"})
public class LoadData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();

        JsonObject resposObject = new JsonObject();

        resposObject.addProperty("status", false);

        Session s = HibernateUtil.getSessionFactory().openSession();

        //get Brands
        Criteria c1 = s.createCriteria(Brand.class);
        List<Brand> brandList = c1.list();

        //get Models
        Criteria c2 = s.createCriteria(Model.class);
        List<Model> modelList = c2.list();

        //get Colors
        Criteria c3 = s.createCriteria(Color.class);
        List<Color> colorList = c3.list();

        //get Qualities
        Criteria c4 = s.createCriteria(Condition.class);
        List<Condition> qualityList = c4.list();

        //get Stroage
        Criteria c5 = s.createCriteria(Stroage.class);
        List<Stroage> stroageList = c5.list();

        //loade-product-date-end
        //get = actual database ekatama call karanawa / load = session object eke data tika cash ekka thiyagannawaa
        Status status = (Status) s.load(Status.class, 2);
        Criteria c6 = s.createCriteria(Product.class);
        resposObject.addProperty("allProductCount", c6.list().size());
        c6.addOrder(Order.desc("id"));
        c6.add(Restrictions.eq("status", status));

//        Status status = new Status();
//        status.setValue("Active");
        c6.setFirstResult(0);
        c6.setMaxResults(6);

        List<Product> productList = c6.list();
        for (Product product : productList) {

            product.setUser(null);

//            System.out.println(productList);
        }

        s.close();

        resposObject.add("brandList", gson.toJsonTree(brandList));
        resposObject.add("modelList", gson.toJsonTree(modelList));
        resposObject.add("colorList", gson.toJsonTree(colorList));
        resposObject.add("qualityList", gson.toJsonTree(qualityList));
        resposObject.add("stroageList", gson.toJsonTree(stroageList));
        resposObject.addProperty("allProductCount", productList.size());
        resposObject.add("productList", gson.toJsonTree(productList));

        resposObject.addProperty("status", true);

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(resposObject));

    }

}
