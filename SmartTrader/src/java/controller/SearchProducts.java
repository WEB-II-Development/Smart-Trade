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
@WebServlet(name = "SearchProducts", urlPatterns = {"/SearchProducts"})
public class SearchProducts extends HttpServlet {

    private static final int MAX_RESULT = 6;

    private static final int ACTIVE_ID = 2;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();
        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);

        JsonObject requestObject = gson.fromJson(request.getReader(), JsonObject.class);

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();

        Criteria c1 = s.createCriteria(Product.class);//get all products form the filter

        //----------------brand-------------------------------
        if (requestObject.has("brandName")) {

            String brandName = requestObject.get("brandName").getAsString();

            //get brand detaiils
            Criteria c2 = s.createCriteria(Brand.class);
            c2.add(Restrictions.eq("name", brandName));
            Brand brand = (Brand) c2.uniqueResult();

            //-----------model---------------------
            //filter by model using brand for get brand details        
            Criteria c3 = s.createCriteria(Model.class);
            c3.add(Restrictions.eq("brand", brand));
            List<Model> modelList = c3.list();

            //filter product by using modellist
            c1.add(Restrictions.in("model", modelList));

            //-------------model end-------------------
            //------------------brand end--------------------
        }

        if (requestObject.has("conditionName")) {

            //-------------condition--------------------------------
            String qualityVale = requestObject.get("conditionName").getAsString();

            //get quality details
            Criteria c4 = s.createCriteria(Condition.class);
            c4.add(Restrictions.eq("value", qualityVale));
            Condition quality = (Condition) c4.uniqueResult();

            //filter product by quality
            c1.add(Restrictions.eq("condition", quality));

            //---------------------condition end----------------------
        }

        if (requestObject.has("colorName")) {

            //----------------------color--------------------
            String colorValue = requestObject.get("colorName").getAsString();

            //get color details
            Criteria c5 = s.createCriteria(Color.class);
            c5.add(Restrictions.eq("value", colorValue));
            Color color = (Color) c5.uniqueResult();

            //filter product from color
            c1.add(Restrictions.eq("color", color));

            //----------------------color end------------------            
        }

        if (requestObject.has("storageValue")) {

            //----------------------Storage--------------------
            String storageValue = requestObject.get("storageValue").getAsString();

            Criteria c6 = s.createCriteria(Stroage.class);
            c6.add(Restrictions.eq("value", storageValue));
            Stroage storage = (Stroage) c6.uniqueResult();

            //filter product from storage
            c1.add(Restrictions.eq("storage", storage));

            //----------------------Storage end------------------            
        }

        if (requestObject.has("priceStart") && requestObject.has("priceEnd")) {

            double priceStart = requestObject.get("priceStart").getAsDouble();
            double priceEnd = requestObject.get("priceEnd").getAsDouble();

            c1.add(Restrictions.ge("price", priceStart));
            c1.add(Restrictions.le("price", priceEnd));

        }

        //product sorting
        if (requestObject.has("sortValue")) {

            String sortValue = requestObject.get("sortValue").getAsString();

            if (sortValue.equals("Sort by Latest")) {

                c1.addOrder(Order.desc("id"));

            } else if (sortValue.equals("Sort by Oldest")) {

                c1.addOrder(Order.asc("id"));

            } else if (sortValue.endsWith("Sort by Name")) {

                c1.addOrder(Order.asc("title"));

            } else if (sortValue.equals("Sort by Price")) {

                c1.addOrder(Order.asc("price"));

            }

        }

        //product sorting
        if (requestObject.has("firstResult")) {

            int firstresult = requestObject.get("firstResult").getAsInt();
            c1.setFirstResult(firstresult);
            //get the max value from search products in criteria search
            c1.setMaxResults(SearchProducts.MAX_RESULT);

        }

        //get fiiltered product list from all above filters
        Status status = (Status) s.get(Status.class, 2); // if you dont know this this is magic number // this is for status (get Active product)
        c1.add(Restrictions.eq("status", status));
        List<Product> productList = c1.list();

        //set user details null
        for (Product product : productList) {

            product.setUser(null);

        }

        //hibernate session close
        s.close();

        //send data as response to frontend
        responseObject.add("productList", gson.toJsonTree(productList));
        responseObject.addProperty("status", true);
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseObject));

    }
    
}
