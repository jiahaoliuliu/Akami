package com.jiahaoliuliu.akami.model;

/**
 * Created by jiahaoliuliu on 7/2/16.
 *
 * This is the company where they money has been used
 */
public class Company {

    // The unique id of the company
    private String id;

    // The name of the company
    private String name;

    // The id of the image in the res folder
    private int imageResourceId;

    public Company(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (getId() != null ? !getId().equals(company.getId()) : company.getId() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + getImageResourceId();
        return result;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageResourceId=" + imageResourceId +
                '}';
    }
}
