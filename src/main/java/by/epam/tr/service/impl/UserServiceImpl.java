package by.epam.tr.service.impl;

import by.epam.tr.bean.Group;
import by.epam.tr.bean.User;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.dao.UserDAO;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.UserService;
import by.epam.tr.service.validation.UserValidation;
import by.epam.tr.service.validation.ValidationFactory;
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
    public int registration(User user, String login, String password) throws ServiceException {

        UserDAO dao = DAOFactory.getInstance().getUserDAO();
        UserValidation userValidation = ValidationFactory.getInstance().getUserValidation();

        if(!userValidation.check(user, login, password)) {
            return -1;
        }

        try {

            if(dao.doesUserExist(login)){
                return 0;

            }else if(dao.addNewUser(user, login, password)){
                return 1;
            }

        } catch (DAOException e) {
            throw new ServiceException("Exception during registration!");
        }

        return -1;
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
    public ArrayList<User> userByGroup(String groupName) throws ServiceException {

        UserDAO dao = DAOFactory.getInstance().getUserDAO();
        ArrayList <User> users;

        try {

            users = dao.userByGroup(groupName);

            if(users.size()==0){
                return null;
            }

        } catch (DAOException e) {
            throw new ServiceException("Exception during users getting!");
        }
        return users;
    }
}
