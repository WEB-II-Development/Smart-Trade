/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.Color;
import hibernate.Condition;
import hibernate.HibernateUtil;
import hibernate.Model;
import hibernate.Product;
import hibernate.Status;
import hibernate.Stroage;
import hibernate.User;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Util;
import org.hibernate.Session;

/**
 *
 * @author Sanjana
 */
@MultipartConfig
@WebServlet(name = "SaveProduct", urlPatterns = {"/SaveProduct"})
public class SaveProduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String branId = request.getParameter("brandId");
        String modelId = request.getParameter("modelId");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String strogeId = request.getParameter("strogeId");
        String colorId = request.getParameter("colorId");
        String conditionId = request.getParameter("conditionId");
        String price = request.getParameter("price");
        String qty = request.getParameter("qty");
        String image1 = request.getParameter("image1");
        String image2 = request.getParameter("image2");
        String image3 = request.getParameter("image3");

//        System.out.println(branId);
//        System.out.println(modelId);
//        System.out.println(title);
//        System.out.println(description);
//        System.out.println(strogeId);
//        System.out.println(colorId);
//        System.out.println(conditionId);
//        System.out.println(price);
//        System.out.println(qty);
//        System.out.println(image1);
//        System.out.println(image2);
//        System.out.println(image3);
        Gson gson = new Gson();

        JsonObject responJsonObject = new JsonObject();
        responJsonObject.addProperty("status", false);

        Session s = HibernateUtil.getSessionFactory().openSession();

        //Validation
//        if (request.getSession().getAttribute("user") == null) {
//
//            responJsonObject.addProperty("message", "Please signin!");
//
//        } else if (!Util.isInteger(modelId)) {
//
//            responJsonObject.addProperty("message", "Invalid Model");
//
//        } else if (Integer.parseInt(modelId) == 0) {
//
//            responJsonObject.addProperty("message", "Please select a model!");
//
//        } else {
//
//            Model model = (Model) s.get(Model.class, Integer.parseInt(modelId));
//
//            if (model == null) {
//
//                responJsonObject.addProperty("message", "invalid model!");
//
//            } else {
//
//                if (!String.valueOf(model.getBrand().getId()).equals(branId)) {
//
//                }
//
//            }
//        }
//        Session s = HibernateUtil.getSessionFactory().openSession();

        Model model = (Model) s.load(Model.class, Integer.parseInt(modelId));
        Stroage stroage = (Stroage) s.load(Stroage.class, Integer.parseInt(strogeId));
        Color color = (Color) s.load(Color.class, Integer.parseInt(colorId));
        Condition condition = (Condition) s.load(Condition.class, Integer.parseInt(conditionId));
        Status status = (Status) s.load(Status.class, 1); //Pending
        User user = (User) request.getSession().getAttribute("user");

        Product p = new Product();

        p.setModel(model);
        p.setTitle(title);
        p.setDescription(description);
        p.setStroage(stroage);
        p.setColor(color);
        p.setCondition(condition);
        p.setPrice(Double.parseDouble(price));
        p.setQty(Integer.parseInt(qty));
        p.setStatus(status);
        p.setUser(user);
        p.setCreated_at(new Date());

        int id = (int) s.save(p);

        s.save(p);
        s.beginTransaction().commit();

        //image Uploading
        Part part1 = request.getPart("image1");
        Part part2 = request.getPart("image2");
        Part part3 = request.getPart("image3");

//      C:\Users\Sanjana\Documents\NetBeansProjects\Smart-Trade\SmartTrader\build\web|#]
        String appPath = getServletContext().getRealPath(""); //Projrct Full Path of the web pages foloders
//      System.out.println(appPath);
        String newPath = appPath.replace("build" + File.separator + "web", "web" + File.separator + "product-images");

        File productFolder = new File(newPath, String.valueOf(id));
        productFolder.mkdir();

        File file1 = new File(productFolder, "image1.png");
        Files.copy(part1.getInputStream(), file1.toPath(), StandardCopyOption.REPLACE_EXISTING);

        File file2 = new File(productFolder, "image2.png");
        Files.copy(part2.getInputStream(), file2.toPath(), StandardCopyOption.REPLACE_EXISTING);

        File file3 = new File(productFolder, "image3.png");
        Files.copy(part3.getInputStream(), file3.toPath(), StandardCopyOption.REPLACE_EXISTING);

        System.out.println(appPath);

        //send Response
        response.setContentType("application/json");
        String toJson = gson.toJson(responJsonObject);
        response.getWriter().write(toJson);
    }

}
