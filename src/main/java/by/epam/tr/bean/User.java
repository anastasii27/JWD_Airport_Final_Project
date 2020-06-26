package by.epam.tr.bean;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

@Data
@Builder
public class User implements Serializable{

    private String role;
    private String name;
    private String surname;
    private String email;
    private String careerStartYear;

//    public User(){}
//
//    public User(String role,String name, String surname, String email, String careerStartYear){
//
//        this.role = role;
//        this.name = name;
//        this.surname = surname;
//        this.email = email;
//        this.careerStartYear = careerStartYear;
//    }
//
//    public User(String name, String surname, String role){
//
//        this.name = name;
//        this.surname = surname;
//        this.role = role;
//    }
//
//    public User(String name, String surname){
//
//        this.name = name;
//        this.surname = surname;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getSurname() {
//        return surname;
//    }
//
//    public void setSurname(String surname) {
//        this.surname = surname;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getCareerStartYear() {
//        return careerStartYear;
//    }
//
//    public void setCareerStartYear(String career_start_year) {
//        this.careerStartYear = careerStartYear;
//    }
//
//
//    @Override
//    public String toString() {
//        return getClass().getName() +" [role = " + role + " name = "+ name + " surname = "+
//                surname +" email = "+ email+ " career start year = " + careerStartYear + " ]";
//    }
//
//    @Override
//    public int hashCode() {
//        return (((role==null)?0:role.hashCode())+((name==null)?0:name.hashCode())+ ((surname==null)?0:surname.hashCode())+ ((email==null)?0:email.hashCode())+ ((careerStartYear==null)?0:careerStartYear.hashCode()));
//    }
//
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null || getClass() != obj.getClass())
//            return false;
//        User other = (User) obj;
//
//        if (role == null) {
//            if(other.role!= null){
//                return false;
//            }
//        }else if(!role.equals(other.role)){
//            return false;
//        }
//
//        if (name == null) {
//            if(other.name!= null){
//                return false;
//            }
//        }else if(!name.equals(other.name)){
//            return false;
//        }
//
//        if (surname == null) {
//            if(other.surname!= null){
//                return false;
//            }
//        }else if(!surname.equals(other.surname)){
//            return false;
//        }
//
//        if (email == null) {
//            if(other.email!= null){
//                return false;
//            }
//        }else if(!email.equals(other.email)){
//            return false;
//        }
//
//        if (careerStartYear == null) {
//            if(other.careerStartYear!= null){
//                return false;
//            }
//        }else if(!careerStartYear.equals(other.careerStartYear)){
//            return false;
//        }
//        return true;
//    }
}

