package models;

import java.io.Serializable;
import java.util.Date;

public class Habit implements Serializable {

    private String name;
    private String description;
    private Date createAt;
    private Person owner;

    public Habit(String name, String description, Person owner) {
        this.name = name;
        this.description = description;
        this.createAt = new Date();
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

}
