package by.epam.tr.service;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.User;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.dao.UserDAO;
import java.util.ArrayList;

public class UserServiceImpl implements UserService {

    private final static String ANSWER1 = "This user is already exist";
    private final static String ANSWER2 = "You are successfully registered";
    private final static String ANSWER3 = "Something goes wrong!Registration is failed";
    private final static String ANSWER4 = "true";
    private final static String ANSWER5 = "Wrong login or password! Try again";
    private final static String ANSWER6 = "You have sent an empty form! Try again";

    @Override
    public String singIn(String login, String password) throws ServiceException {

        UserDAO  dao = DAOFactory.getInstance().getUserDAO();

        if(emptyFormValidation(login, password)){
            return ANSWER6;
        }

        try {

            if(dao.singIn(login, password)){
                return ANSWER4;
            }

        } catch (DAOException e) {
            throw new ServiceException("Exception during sing in!");
        }

        return ANSWER5;
    }

    @Override
    public String registration(User user) throws ServiceException {

        UserDAO dao = DAOFactory.getInstance().getUserDAO();

        if(emptyFormValidation(user.getName(), user.getLogin(), user.getPassword())) {
            return ANSWER6;
        }

        try {
            if(dao.getUserInfo("login").contains(user.getLogin())){
                return ANSWER1;
            }else if(dao.addNewUser(user)){
                return ANSWER2;
            }

        } catch (DAOException e) {
            throw new ServiceException("Exception during registration!");
        }

        return ANSWER3;
    }

    @Override
    public ArrayList<Flight> flightsInfo() throws ServiceException {

        UserDAO dao = DAOFactory.getInstance().getUserDAO();
        ArrayList<Flight> flight;

        try {
            flight = dao.getFlightInfo();
        } catch (DAOException e) {
            throw new ServiceException("Exception during getting flight info!");
        }
        return flight;
    }

    private static boolean emptyFormValidation(String name, String login, String password){

        if(name.length()== 0|| login.length()==0 || password.length()==0){
            return true;
        }

        return false;
    }

    private static boolean emptyFormValidation( String login, String password){

        if(login.length()==0 || password.length()==0) {
            return true;
        }

        return false;
    }

}
