/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.Brand;
import hibernate.Color;
import hibernate.HibernateUtil;
import hibernate.Model;
import hibernate.Product;
import hibernate.Condition;
import hibernate.Status;
import hibernate.Stroage;
import hibernate.User;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Sanjana
 */
@MultipartConfig
@WebServlet(name = "SaveProduct", urlPatterns = {"/SaveProduct"})
public class SaveProduct extends HttpServlet {

    private static final int PENDING_STAUTUS_ID = 1;//pending product

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
        Part part1 = request.getPart("image1");
        Part part2 = request.getPart("image2");
        Part part3 = request.getPart("image3");

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

        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);

        Session s = HibernateUtil.getSessionFactory().openSession();

//start validation
        if (request.getSession().getAttribute("user") == null) {

            responseObject.addProperty("message", "Please signin!");

        } else if (!Util.isInteger(branId)) {

            responseObject.addProperty("message", "Invalid brand!");

        } else if (Integer.parseInt(branId) == 0) {

            responseObject.addProperty("message", "Please select a brand");

        } else if (!Util.isInteger(modelId)) {

            responseObject.addProperty("message", "Invalid Model");

        } else if (Integer.parseInt(modelId) == 0) {

            responseObject.addProperty("message", "Please select a model!");

        } else if (title.isEmpty()) {

            responseObject.addProperty("message", "Product title cannot be empty");

        } else if (description.isEmpty()) {

            responseObject.addProperty("message", "Product description cannot be empty");

        } else if (!Util.isInteger(strogeId)) {

            responseObject.addProperty("message", "Invalid Storage");

        } else if (Integer.parseInt(strogeId) == 0) {

            responseObject.addProperty("message", "Please select a valid storage");

        } else if (!Util.isInteger(colorId)) {

            responseObject.addProperty("message", "Invali Color");

        } else if (Integer.parseInt(colorId) == 0) {

            responseObject.addProperty("message", "Please select a valid color");

        } else if (!Util.isInteger(conditionId)) {

            responseObject.addProperty("message", "Invalid condition");

        } else if (Integer.parseInt(conditionId) == 0) {

            responseObject.addProperty("message", "Please select a valid condition");

        } else if (!Util.isDouble(price)) {

            responseObject.addProperty("message", "Invalid price");

        } else if (Double.parseDouble(price) <= 0) {

            responseObject.addProperty("message", "Price ust be grater than 0");

        } else if (!Util.isInteger(qty)) {

            responseObject.addProperty("message", "Invalid quantity");

        } else if (Integer.parseInt(qty) <= 0) {

            responseObject.addProperty("message", "Quantity must be grater than 0");

        } else if (part1.getSubmittedFileName() == null) {

            responseObject.addProperty("message", "Product image one is required");

        } else if (part2.getSubmittedFileName() == null) {

            responseObject.addProperty("message", "Product image two is required");

        } else if (part3.getSubmittedFileName() == null) {

            responseObject.addProperty("message", "Product image three is required");

        } else {

            Brand brand = (Brand) s.get(Brand.class, Integer.valueOf(branId));

            if (brand == null) {

                responseObject.addProperty("message", "Please select a valid brand name");

            } else {

                Model model = (Model) s.get(Model.class, Integer.parseInt(modelId));

                if (model == null) {

                    responseObject.addProperty("message", "Please select a validvalid model");

                } else {

                    if (model.getBrand().getId() != brand.getId()) {

                        responseObject.addProperty("message", "Plese select a suitable model");

                    } else {

                        Stroage storage = (Stroage) s.get(Stroage.class, Integer.valueOf(strogeId));

                        if (storage == null) {

                            responseObject.addProperty("message", "Please select a validvalid storage");

                        } else {

                            Color color = (Color) s.get(Color.class, Integer.valueOf(colorId));

                            if (color == null) {

                                responseObject.addProperty("message", "Please select a validvalid color");

                            } else {

                                Condition quality = (Condition) s.get(Condition.class, Integer.valueOf(conditionId));

                                if (quality == null) {

                                    responseObject.addProperty("message", "Please select a validvalid quality");

                                } else {

                                    //call product entity for add the new product and set product details
                                    Product p = new Product();
                                    p.setTitle(title);
                                    p.setModel(model);
                                    p.setDescription(description);
                                    p.setPrice(Double.parseDouble(price));
                                    p.setQty(Integer.parseInt(qty));
                                    p.setColor(color);
                                    p.setStroage(storage);
                                    p.setCondition(quality);

                                    //get the status and set pening status
                                    Status status = (Status) s.get(Status.class, SaveProduct.PENDING_STAUTUS_ID); // set pending for til verify                                    
                                    p.setStatus(status);

                                    //get the user from session in the request
                                    User user = (User) request.getSession().getAttribute("user");

                                    //create criteria search for get user in user entity
                                    Criteria c1 = s.createCriteria(User.class);

                                    //checl is the email is equal to the emain in user table
                                    c1.add(Restrictions.eq("email", user.getEmail()));

                                    //get the unique data
                                    User u1 = (User) c1.uniqueResult();

                                    //set user to the product
                                    p.setUser(u1);

                                    //product adding data
                                    p.setCreated_at(new Date());

                                    //get the product id as int,save the data to the product table
                                    int id = (int) s.save(p);

                                    //start the saving
                                    s.beginTransaction().commit();
                                    s.close();

                                    //C:\Users\pneth\Documents\NetBeansProjects\SmartTrader\web\product-images
                                    String appPath = getServletContext().getRealPath("");//full path of the web pages folder

                                    String newPath = appPath.replace("build" + File.separator + "web", "web" + File.separator + "product-images");

                                    File productFolder = new File(newPath, String.valueOf(id));
                                    productFolder.mkdir();

                                    //for part 1
                                    File file1 = new File(productFolder, "image1.png");
                                    Files.copy(part1.getInputStream(), file1.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                    //for part 2
                                    File file2 = new File(productFolder, "image2.png");
                                    Files.copy(part2.getInputStream(), file2.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                    //for part 3
                                    File file3 = new File(productFolder, "image3.png");
                                    Files.copy(part3.getInputStream(), file3.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                    //System.out.println(appPath);
                                    responseObject.addProperty("status", true);

                                }
                            }
                        }

                    }

                }

            }

        }

        //end the validation
        //send response to the user
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseObject));

//        Model model = (Model) s.load(Model.class, Integer.parseInt(modelId));
//        Stroage stroage = (Stroage) s.load(Stroage.class, Integer.parseInt(strogeId));
//        Color color = (Color) s.load(Color.class, Integer.parseInt(colorId));
//        Condition condition = (Condition) s.load(Condition.class, Integer.parseInt(conditionId));
//        Status status = (Status) s.load(Status.class, 1); //Pending
//        User user = (User) request.getSession().getAttribute("user");
//
//        Product p = new Product();
//
//        p.setModel(model);
//        p.setTitle(title);
//        p.setDescription(description);
//        p.setStroage(stroage);
//        p.setColor(color);
//        p.setCondition(condition);
//        p.setPrice(Double.parseDouble(price));
//        p.setQty(Integer.parseInt(qty));
//        p.setStatus(status);
//        p.setUser(user);
//        p.setCreated_at(new Date());
//
//        int id = (int) s.save(p);
//
//        s.save(p);
//        s.beginTransaction().commit();
//
//        //image Uploading
//        Part part1 = request.getPart("image1");
//        Part part2 = request.getPart("image2");
//        Part part3 = request.getPart("image3");
//
////      C:\Users\Sanjana\Documents\NetBeansProjects\Smart-Trade\SmartTrader\build\web|#]
//        String appPath = getServletContext().getRealPath(""); //Projrct Full Path of the web pages foloders
////      System.out.println(appPath);
//        String newPath = appPath.replace("build" + File.separator + "web", "web" + File.separator + "product-images");
//
//        File productFolder = new File(newPath, String.valueOf(id));
//        productFolder.mkdir();
//
//        File file1 = new File(productFolder, "image1.png");
//        Files.copy(part1.getInputStream(), file1.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//        File file2 = new File(productFolder, "image2.png");
//        Files.copy(part2.getInputStream(), file2.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//        File file3 = new File(productFolder, "image3.png");
//        Files.copy(part3.getInputStream(), file3.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//        System.out.println(appPath);
//
//        //send Response
//        response.setContentType("application/json");
//        String toJson = gson.toJson(responJsonObject);
//        response.getWriter().write(toJson);
    }

}
