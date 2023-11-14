package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import model.Course;

/**
 *
 * @author AERO
 */
public class CourseDAO {

    private static Connection con;
    private static PreparedStatement st;
    private static HashMap<String, Course> courses = new HashMap<String, Course>();
    
    public static void registerCourse(Course c)
    {
        try{
            con = BaseDAO.getCon();
            
            String query = "INSERT INTO courses VALUES ('%s', '%s', '%s','%d')";
            query = String.format(query, 
                    c.getId(),
                    c.getCourseNo(),
                    c.getCourseName(),
                    c.getCredits());
            st = con.prepareStatement(query);
            st.executeUpdate();
            courses.put(c.getCourseNo(), c);
        }
        catch (SQLException ex) {
            System.err.print("Error saving the data: " + 
                    ex.getMessage());
            System.exit(1);
        }
        finally
        {
            BaseDAO.closeCon(con);
        }        
    }
    
    public static Course getCourse(String id) {
        Course c = null;
        try{
            con = BaseDAO.getCon();
            String query = "SELECT * FROM courses WHERE id = '%s'";
            query = String.format(query, id);
            st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            if(rs.next() != false)
            {
                c = new Course(
                    rs.getString("courseno"),
                    rs.getString("name"),
                    rs.getInt("credits"));
                c.setId(UUID.fromString(id));
            }
            else
            {
                System.out.println("Data not found!");
                System.exit(0);
            }
        }
        catch (SQLException ex) {
            System.err.print("Error getting the data: " + 
                    ex.getMessage());
            System.exit(1);
        }
        finally
        {
            BaseDAO.closeCon(con);
        }        
        return c;
    }
    
     public static Course getCourseByNumber(String courseNo) {
        Course c = null;
        try{
            con = BaseDAO.getCon();
            String query = "SELECT * FROM courses WHERE courseno = '%s'";
            query = String.format(query, courseNo);
            st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            if(rs.next() != false)
            {
                c = new Course(
                    rs.getString("courseno"),
                    rs.getString("name"),
                    rs.getInt("credits"));
                c.setId(UUID.fromString(rs.getString("id")));
            }
            else
            {
                System.out.println("Data not found!");
                System.exit(0);
            }
        }
        catch (SQLException ex) {
            System.err.print("Error getting the data: " + 
                    ex.getMessage());
            System.exit(1);
        }
        finally
        {
            BaseDAO.closeCon(con);
        }        
        return c;
    }
     
    public static HashMap<String, Course> getCourseCatalog()
    {
        try{
            con = BaseDAO.getCon();
            String query = "select * from courses";
            st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next())
            {
                Course c = new Course(
                        rs.getString("courseno"),
                        rs.getString("name"),
                        rs.getInt("credits"));
                c.setId(UUID.fromString(rs.getString("id")));
                courses.put(rs.getString("courseno"), c);   
            }       
        }
        catch (SQLException ex) {
            System.err.print("Error getting the data: " + 
                    ex.getMessage());
            System.exit(1);
        }
        finally
        {
            BaseDAO.closeCon(con);
        }
        return courses;
    }
    
    public void displayCourseCatalog()
    {
        for(Course c : courses.values())
        {
            c.display();
            System.out.println("");
        }
    }
}
