package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import model.Course;
import model.Professor;
import model.ScheduleOfClasses;
import model.Section;

/**
 *
 * @author AERO
 */
public class SectionDAO {

    private static Connection con;
    private static PreparedStatement st;
    private static ScheduleOfClasses schedule;

     public static void registerSection(Section s, String semester)
    {   schedule =  new ScheduleOfClasses(semester);
        try{
            con = BaseDAO.getCon();
            Course c = CourseDAO.getCourseByNumber(
                    s.getRepresentedCourse().getCourseNo());
            String query = "INSERT INTO sections"
                    + "(id, sectionno, day, time, course, room, capacity, semester)"
                    + " VALUES ('%s', '%d', '%c', '%s', '%s', '%s', '%d', '%s')";
            query = String.format(query, 
                    s.getId(),
                    s.getSectionNo(),
                    s.getDayOfWeek(),
                    s.getTimeOfDay(),
                    c.getId(),
                    s.getRoom(),
                    s.getSeatingCapacity(),
                    semester);
            st = con.prepareStatement(query);
            st.executeUpdate();
            schedule.addSection(s);
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
     
    public static Section getSection(int secNo) {
        Section s = null;
        try {
            con = BaseDAO.getCon();
            String query = "SELECT * FROM sections WHERE sectionno = '%d'";
            query = String.format(query, secNo);
            st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            Course c = CourseDAO.getCourse(rs.getString("course"));
            Professor p =  ProfessorDAO.getProfessor(rs.getString("instructor"));
            if (rs.next() != false) {
                s = new Section(
                        rs.getInt("sectionno"),
                        rs.getString("day").charAt(0),
                        rs.getString("time"),
                        c,
                        rs.getString("room"),
                        rs.getInt("capacity"));
                s.setInstructor(p);
            } else {
                System.out.println("Data not found!");
                System.exit(0);
            }
        } catch (SQLException ex) {
            System.err.print("Error getting the data: "
                    + ex.getMessage());
            System.exit(1);
        } finally {
            BaseDAO.closeCon(con);
        }
        return s;
    }
    
    public static ScheduleOfClasses initializerScheduleOfClasses(String semester)
    {
        schedule = new ScheduleOfClasses(semester);
        try{
            con = BaseDAO.getCon();
            String query = "SELECT * FROM sections WHERE semester = '%s'";
            query = String.format(query, semester);
            st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next())
            {
                Course c = CourseDAO.getCourse(rs.getString("course"));
                Section s = new Section(
                        rs.getInt("sectionno"),
                        rs.getString("day").charAt(0),
                        rs.getString("time"),
                        c,
                        rs.getString("room"),
                        rs.getInt("capacity"));
                s.setInstructor(ProfessorDAO.getProfessor(rs.getString("instructor")));
                schedule.addSection(s);
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
        return schedule;
    }
}
