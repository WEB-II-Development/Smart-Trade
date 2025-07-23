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
 * @author pneth
 */
@WebServlet(name = "LoadData", urlPatterns = {"/LoadData"})
public class LoadData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);

        Gson gson = new Gson();

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();

        //get brands
        Criteria c1 = s.createCriteria(Brand.class);
        List<Brand> BrandList = c1.list();

        //get brands
        //get Models
        Criteria c2 = s.createCriteria(Model.class);

        List<Model> ModelList = c2.list();

        //get Models
        //get Color
        Criteria c3 = s.createCriteria(Color.class);
        List<Color> ColorList = c3.list();

        //get Color
        //get storage
        Criteria c4 = s.createCriteria(Stroage.class);
        List<Stroage> StorageList = c4.list();

        //get storage
        // get Quality
        Criteria c5 = s.createCriteria(Condition.class);
        List<Condition> QualityList = c5.list();

        // get Quality
        
        //load product data
        //Status status = (Status) s.load(Status.class, 2);
        Status status = (Status) s.get(Status.class, 2);
        Criteria c6 = s.createCriteria(Product.class);
        //get product form decending order from id
        c6.addOrder(Order.desc("id"));
        
        //search the data where id is equal to 2 from product
        c6.add(Restrictions.eq("status",status));
        //set the all product count in database ("Active products")
        responseObject.addProperty("allProductCount", c6.list().size());
        //set the first value
        c6.setFirstResult(0);
        
        //maximum results
        c6.setMaxResults(6);
        
        List<Product> productList = c6.list();
        
        for(Product product :productList){
            
            product.setUser(null);
            
        }
        
        // load product data
        
        responseObject.add("brandList", gson.toJsonTree(BrandList));
        responseObject.add("modelList", gson.toJsonTree(ModelList));
        responseObject.add("qualityList", gson.toJsonTree(QualityList));
        responseObject.add("colorList", gson.toJsonTree(ColorList));
        responseObject.add("storageList", gson.toJsonTree(StorageList));        
        responseObject.add("productList", gson.toJsonTree(productList));

        System.out.println(responseObject);

        responseObject.addProperty("status", true);

        s.close();

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseObject));
        //create JsonObject to send data to client side

    }

}