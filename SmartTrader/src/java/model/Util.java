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

    public static boolean isEmailValid(String email) {

        return email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    }

    public static boolean isPasswordValid(String password) {

        return password.matches("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$");

    }

    public static boolean isCodeValid(String code) { // Postal Code Validation

        return code.matches("^\\d{4,5}$");

    }

    public static boolean isInteger(String text) {

        return text.matches("^\\d+$");

    }

    public static boolean isDouble(String text) {

        return text.matches("^\\d+(\\.\\d{2})?$");

    }

}
