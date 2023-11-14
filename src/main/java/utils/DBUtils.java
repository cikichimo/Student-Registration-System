package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import static utils.BaseDAO.DB_HOST;
import static utils.BaseDAO.DB_NAME;
import static utils.BaseDAO.DB_PASS;
import static utils.BaseDAO.DB_USER;


/**
 *
 * @author AERO
 */
public class DBUtils {

    public static void createDB() {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://" + DB_HOST + ":3306", DB_USER, DB_PASS);
            String sql = "drop schema if exists " + DB_NAME;
            PreparedStatement st = con.prepareStatement(sql);
            st.executeUpdate();
            System.out.println(DB_NAME + " dropped");
            sql = "create schema " + DB_NAME;
            st = con.prepareStatement(sql);
            st.executeUpdate();
            con.close();
            System.out.println(DB_NAME + " created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runSQL(String sql) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://" + DB_HOST + ":3306/" + DB_NAME, DB_USER, DB_PASS);
            PreparedStatement st = con.prepareStatement(sql);
            st.executeUpdate();
            con.close();
            System.out.println("sql completed!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        createDB();
//        for (String sql : SQLS) {
//            runSQL(sql);
//        }
//    }
}
