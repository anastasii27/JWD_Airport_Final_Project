package by.epam.tr.bean;

import java.io.Serializable;

public class User implements Serializable{

    private String role;
    private String login;
    private String name;
    private String surname;
    private String email;
    private int career_start_year;

    public User(){}

    public User(String role,String name, String surname, String email, int career_start_year){

        this.role = role;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.career_start_year = career_start_year;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCareer_start_year() {
        return career_start_year;
    }

    public void setCareer_start_year(int career_start_year) {
        this.career_start_year = career_start_year;
    }


    @Override
    public String toString() {
        return getClass().getName() +" [role = " + role + " login = " + login + " name = "+ name + " surname = "+
                surname +" email = "+ email+ " career start year = " + career_start_year + "]";
    }

    @Override
    public int hashCode() {
        return (((role==null)?0:role.hashCode())+ ((login==null)?0:login.hashCode())
                + ((name==null)?0:name.hashCode())+ ((surname==null)?0:surname.hashCode())+ ((email==null)?0:email.hashCode())+ 31*career_start_year);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User other = (User) obj;

        if (role == null) {
            if(other.role!= null){
                return false;
            }
        }else if(!role.equals(other.role)){
            return false;
        }

        if (login == null) {
            if(other.login!= null){
                return false;
            }
        }else if(!login.equals(other.login)){
            return false;
        }

        if (name == null) {
            if(other.name!= null){
                return false;
            }
        }else if(!name.equals(other.name)){
            return false;
        }

        if (surname == null) {
            if(other.surname!= null){
                return false;
            }
        }else if(!surname.equals(other.surname)){
            return false;
        }

        if (email == null) {
            if(other.email!= null){
                return false;
            }
        }else if(!email.equals(other.email)){
            return false;
        }

        if(career_start_year != other.career_start_year){
            return false;
        }
        return true;
    }
}

