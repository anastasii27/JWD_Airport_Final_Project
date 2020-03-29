package by.epam.tr.service;

import by.epam.tr.bean.Group;
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
            if(doesUserExist(user)){
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
    public ArrayList<Group> getUserGroups(String login) throws ServiceException {

        UserDAO dao = DAOFactory.getInstance().getUserDAO();
        ArrayList <Group> groups;

        try {
            groups = dao.getUserGroups(login);

            if(groups.size()==0){
                return null;
            }
        } catch (DAOException e) {
            throw new ServiceException("Exception during users group getting!");
        }
        return groups;
    }

    @Override
    public int getUserInfo(String login) throws ServiceException {

        int index;

        try {

            index = findUserIndexInList(login);

        } catch (DAOException e) {
            throw new ServiceException("Exception during getting users info!");
        }
        return index;
    }

    @Override
    public ArrayList<User> getAllUsersInfo() throws ServiceException {

        UserDAO dao = DAOFactory.getInstance().getUserDAO();
        ArrayList <User> users;

        try {

            users = dao.getUsersInfo();

        } catch (DAOException e) {
            throw new ServiceException("Exception during all users getting");
        }
        return users;
    }

    private boolean doesUserExist(User user) throws DAOException {

        UserDAO dao = DAOFactory.getInstance().getUserDAO();

        for (User user1:dao.getUsersInfo()) {
            if(user.getLogin().equals(user1.getLogin())){
                return true;
            }
        }

        return false;
    }

    private int findUserIndexInList(String login) throws DAOException {

        UserDAO dao = DAOFactory.getInstance().getUserDAO();

        for (User user1:dao.getUsersInfo()) {

            if (login.equals(user1.getLogin())) {
                return dao.getUsersInfo().indexOf(user1);
            }
        }

        return -1;
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
