package by.epam.tr.service.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.CrewDAO;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import java.util.List;

public class CrewServiceImpl implements CrewService {

    private CrewDAO dao = DAOFactory.getInstance().getCrewDAO();

    @Override
    public List<User> crewMembers(String crewName) throws ServiceException {

        List<User> crew;
        try {

            crew =dao.crewMembers(crewName);

        } catch (DAOException e) {
            throw new ServiceException("Exception during crew members getting");
        }
        return crew;
    }

    @Override
    public boolean createCrew(String crewName) throws ServiceException {

        boolean resultOfCreationOperation;
        try {

            resultOfCreationOperation =dao.createCrew(crewName);

        } catch (DAOException e) {
            throw new ServiceException("Exception during crew creation");
        }
        return resultOfCreationOperation;
    }

    @Override
    public boolean addMemberToCrew(List<User> users){
        return false;
    }
}
