package by.epam.tr.service.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.CrewDAO;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import java.util.Map;

public class CrewServiceImpl implements CrewService {

    private CrewDAO dao = DAOFactory.getInstance().getCrewDAO();

    @Override
    public boolean createCrew(String crewName, Map<String, User> users) throws ServiceException {
        try {
            return dao.createCrew(crewName, users);
        } catch (DAOException e) {
            throw new ServiceException("Exception during crew creation", e);
        }
    }

    @Override
    public boolean doesCrewNameExist(String crewName) throws ServiceException {
        try {
            return dao.doesCrewNameExist(crewName);
        } catch (DAOException e) {
            throw new ServiceException("Exception during crew existence checking", e);
        }
    }

    @Override
    public boolean deleteCrew(String crewName) throws ServiceException {
        int changedRowsAmount;
        try {
            changedRowsAmount = dao.deleteCrew(crewName);
        } catch (DAOException e) {
            throw new ServiceException("Exception during crew deleting", e);
        }
        return changedRowsAmount != 0;
    }

    @Override
    public User findMainPilot(String crewName) throws ServiceException {
        try {
            return dao.findMainPilot(crewName);
        } catch (DAOException e) {
            throw new ServiceException("Exception during main pilot searching", e);
        }
    }
}
