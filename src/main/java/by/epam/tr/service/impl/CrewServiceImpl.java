package by.epam.tr.service.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.CrewDAO;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import java.util.List;
import java.util.Map;

public class CrewServiceImpl implements CrewService {

    private CrewDAO dao = DAOFactory.getInstance().getCrewDAO();
    private boolean operationResult;

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
    public boolean createCrew(String crewName, Map<String, User> users) throws ServiceException {

        try {

            operationResult =dao.createCrew(crewName, users);

        } catch (DAOException e) {
            throw new ServiceException("Exception during crew creation");
        }
        return operationResult;
    }
}
