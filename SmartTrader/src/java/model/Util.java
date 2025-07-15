/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Sanjana
 */
public class Util {

    public static String genrateCode() {

        int r = (int) (Math.random() * 1000000);
        return String.format("%06d", r);

    }

}
