package by.epam.tr.service.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.dao.UserDAO;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.UserService;
import java.util.ArrayList;

public class UserServiceImpl implements UserService {

    private final static String ANSWER1 = "This user is already exist";
    private final static String ANSWER2 = "You are successfully registered";
    private final static String ANSWER3 = "Something goes wrong!Registration is failed";


    @Override
    public boolean singIn(String login, String password) throws ServiceException {

        UserDAO  dao = DAOFactory.getInstance().getUserDAO();
        ArrayList <String> usersLogin;
        ArrayList <String> usersPassword;
        String user;

        try {
          usersLogin = dao.getUserInfo("login");
          usersPassword =  dao.getUserInfo("password");

          for (int i=0;i<usersLogin.size();i++){
             user =  usersLogin.get(i)+ " "+ usersPassword.get(i);
             if(user.equals(login+ " "+ password))
                  return true;
          }
        } catch (DAOException e) {
            throw new ServiceException("Exception during sing in!");
        }

        return false;
    }

    @Override
    public String registration(User user) throws ServiceException {

        UserDAO dao = DAOFactory.getInstance().getUserDAO();

        try {
            if(dao.getUserInfo("login").contains(user.getLogin())){
                return ANSWER1;
            }else if(dao.addNewUser(user)== true){
                return ANSWER2;
            }

        } catch (DAOException e) {
            throw new ServiceException("Exception during registration!");
        }

        return ANSWER3;
    }
}
