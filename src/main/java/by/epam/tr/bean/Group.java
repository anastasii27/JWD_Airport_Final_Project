package by.epam.tr.bean;

import java.io.Serializable;

public class Group implements Serializable {

    private String name;
    private String dateOfCreating;

    public Group(String name, String dateOfCreating) {
        this.name = name;
        this.dateOfCreating = dateOfCreating;
    }
    public Group(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfCreating() {
        return dateOfCreating;
    }

    public void setDateOfCreating(String dateOfCreating) {
        this.dateOfCreating = dateOfCreating;
    }

    @Override
    public String toString() {
        return getClass().getName()+ " [name = " + name + " dateOfCreating = " + dateOfCreating + "]";
    }

    @Override
    public int hashCode() {
        return (((name==null)?0:name.hashCode())+((dateOfCreating==null)?0:dateOfCreating.hashCode()));
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Group other = (Group) obj;

        if (name == null) {
            if(other.name!= null){
                return false;
            }
        }else if(!name.equals(other.name)){
            return false;
        }
        if (dateOfCreating == null) {
            if(other.dateOfCreating!= null){
                return false;
            }
        }else if(!dateOfCreating.equals(other.dateOfCreating)){
            return false;
        }

        return true;
    }
}
