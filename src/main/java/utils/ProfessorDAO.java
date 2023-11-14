package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import model.Professor;
import model.Section;

/**
 *
 * @author AERO
 */
public class ProfessorDAO {

    private static Connection con;
    private static PreparedStatement st;
    private static HashMap<String, Professor> faculty = new HashMap<String, Professor>();

    public static void registerProfessor(Professor p) {
        try {
            con = BaseDAO.getCon();
            String query = "INSERT INTO professors VALUES ('%s', '%s', '%s', '%s', '%s')";
            query = String.format(query,
                    p.getId(),
                    p.getSsn(),
                    p.getName(),
                    p.getTitle(),
                    p.getDepartment());
            st = con.prepareStatement(query);
            st.executeUpdate();
            faculty.put(p.getSsn(), p);
        } catch (SQLException ex) {
            System.err.print("Error saving the data: "
                    + ex.getMessage());
            System.exit(1);
        } finally {
            BaseDAO.closeCon(con);
        }
    }

    public static Professor getProfessor(String id) {
        Professor p = null;
        try {
            con = BaseDAO.getCon();
            String query = "SELECT * FROM professors WHERE id = '%s'";
            query = String.format(query, id);
            st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            if (rs.next() != false) {
                p = new Professor(
                        rs.getString("name"),
                        rs.getString("ssn"),
                        rs.getString("title"),
                        rs.getString("department"));
                p.setId(UUID.fromString(id));
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
        return p;
    }

    public static Professor getProfessorBySsn(String ssn) {
        Professor p = null;
        try {
            con = BaseDAO.getCon();
            String query = "SELECT * FROM professors WHERE ssn = '%s'";
            query = String.format(query, ssn);
            st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            if (rs.next() != false) {
                p = new Professor(
                        rs.getString("name"),
                        rs.getString("ssn"),
                        rs.getString("title"),
                        rs.getString("department"));
                p.setId(UUID.fromString(rs.getString("id")));
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
        return p;
    }

    public static HashMap<String, Professor> getFaculty() {
        try {
            con = BaseDAO.getCon();
            String query = "select * from professors";
            st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Professor p = new Professor(
                        rs.getString("name"),
                        rs.getString("ssn"),
                        rs.getString("title"),
                        rs.getString("dept"));
                p.setId(UUID.fromString(rs.getString("id")));
                faculty.put(rs.getString("ssn"), p);
            }

        } catch (SQLException ex) {
            System.err.print("Error getting the data: "
                    + ex.getMessage());
            System.exit(1);
        } finally {
            BaseDAO.closeCon(con);
        }
        return faculty;
    }

    public void displayFaculty() {
        for (Professor p : faculty.values()) {
            p.display();
            System.out.println("");
        }
    }

    public static void addTaughtCourse(Professor p, Section s) {
        p.agreeToTeach(s);
        try {
            con = BaseDAO.getCon();
            System.out.println(s.getId());
            System.out.println(p.getId());
            System.out.println("");
            String query = "UPDATE sections SET instructor = '%s' WHERE id = '%s'";
            query = String.format(query,
                    p.getId(),
                    s.getId());
            st = con.prepareStatement(query);
            st.executeUpdate();
        } catch (SQLException ex) {
            System.err.print("Error saving the data: "
                    + ex.getMessage());
            System.exit(1);
        } finally {
            BaseDAO.closeCon(con);
        }
    }
}
