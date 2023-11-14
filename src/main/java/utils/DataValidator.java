package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * @author AERO
 */
public class DataValidator {

    public String validateRequiredField(String input) {
        String res = null;
        try {
            if (input == null || "".equalsIgnoreCase(input)) {
                res = "Field is required!";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public String validateName(String name) {
        String res = null;
        try {
            if (Pattern.matches("^[a-z,A-Z, ]+$", name) == false) {
                res = "Invalid Name!";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public String validateDate(String dateStr) {
        String res = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date d1 = sdf.parse(dateStr);
            String temp = sdf.format(d1);
            if (dateStr.equals(temp)) {
            } else {
                res = "Invalid Date!!";
            }
        } catch (Exception e) {
        }
        return res;
    }

    public String validatePass(String pass) {
        String res = null;
        try {
            if (Pattern.matches(".*[^\\w\\s].*", pass) == false) {
                res = "Special Symbol is required!";
            }
            if (pass.length() < 6) {
                res = "length is too short, min length 6!";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
