package by.epam.tr.bean;

import java.io.Serializable;

public class User implements Serializable{

    private String name;
    private String login;
    private String password;

    public User(){}

    public User(String name, String login, String password){

        this.name = name;
        this.login = login;
        this.password = password;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getClass().getName() +"name" + name + "login " + login + "password " + password;
    }

    @Override
    public int hashCode() {
        return (((password==null)?0:password.hashCode())+ ((login==null)?0:login.hashCode())+ ((name==null)?0:name.hashCode()));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User other = (User) obj;

        if (login == null) {
            if(other.login!= null)
                return false;
        }else if(!login.equals(other.login))
            return false;

        if (password == null) {
            if(other.password!= null)
                return false;
        }else if(!password.equals(other.password))
            return false;

        if (name == null) {
            if(other.name!= null)
                return false;
        }else if(!name.equals(other.name))
            return false;

        return true;
    }
}

