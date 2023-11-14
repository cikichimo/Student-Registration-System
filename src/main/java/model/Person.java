package model;

import java.util.UUID;

/**
 *
 * @author AERO
 */
public abstract class Person {
    private UUID id;
    private String name;
    private String ssn;

    public Person(String name, String ssn) {
        UUID uuid = UUID.randomUUID();
        this.setId(uuid);
        this.setName(name);
        this.setSsn(ssn);
    }

    public Person() {
        this.setId(null);
        this.setName("?");
        this.setSsn("???-??-????");
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    } 
    
    public void display()
    {
        System.out.println("Person Information");
        System.out.println("\tName: " + this.getName());
        System.out.println("\tSoc.Security No.: " + this.getSsn());
    }
}
