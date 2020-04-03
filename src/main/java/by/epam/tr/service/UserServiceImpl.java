package by.epam.tr.service;

import by.epam.tr.bean.Group;
import by.epam.tr.bean.User;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.dao.UserDAO;
import java.util.ArrayList;

public class UserServiceImpl implements UserService {

    @Override
    public User singIn(String login, String password) throws ServiceException {

        UserDAO  dao = DAOFactory.getInstance().getUserDAO();
        User user;

        try {

            user = dao.singIn(login,password);

        } catch (DAOException e) {
            throw new ServiceException("Exception during singing in!");
        }

        return user;
    }

    @Override
    public boolean registration(User user, String login, String password) throws ServiceException {

        UserDAO dao = DAOFactory.getInstance().getUserDAO();

        try {

            if(dao.doesUserExist(login)){

                return false;

            }else if(dao.addNewUser(user, login, password)){

                return true;
            }

        } catch (DAOException e) {
            throw new ServiceException("Exception during registration!");
        }

        return false;
    }

    @Override
    public ArrayList<Group> userGroups(String login) throws ServiceException {

        UserDAO dao = DAOFactory.getInstance().getUserDAO();
        ArrayList <Group> groups;

        try {
            groups = dao.userGroups(login);

            if(groups.size()==0){
                return null;
            }
        } catch (DAOException e) {
            throw new ServiceException("Exception during users group getting!");
        }
        return groups;
    }


    @Override
    public ArrayList<User> allUsersInfo() throws ServiceException {

        UserDAO dao = DAOFactory.getInstance().getUserDAO();
        ArrayList <User> users;

        try {

            users = dao.allUsersInfo();

        } catch (DAOException e) {
            throw new ServiceException("Exception during all users getting");
        }
        return users;
    }
}
