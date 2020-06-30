package by.epam.tr.service.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.CrewDao;
import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.DaoFactory;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import java.util.Map;

public class CrewServiceImpl implements CrewService {
    private CrewDao dao = DaoFactory.getInstance().getCrewDAO();

    @Override
    public boolean createCrew(String crewName, Map<String, User> users) throws ServiceException {
        try {
            return dao.createCrew(crewName, users);
        } catch (DaoException e) {
            throw new ServiceException("Exception during crew creation", e);
        }
    }

    @Override
    public boolean doesCrewNameExist(String crewName) throws ServiceException {
        try {
            return dao.doesCrewNameExist(crewName);
        } catch (DaoException e) {
            throw new ServiceException("Exception during crew existence checking", e);
        }
    }

    @Override
    public boolean deleteCrew(String crewName) throws ServiceException {
        try {
            return dao.deleteCrew(crewName) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Exception during crew deleting", e);
        }
    }

    @Override
    public User findMainPilot(String crewName) throws ServiceException {
        try {
            return dao.findMainPilot(crewName);
        } catch (DaoException e) {
            throw new ServiceException("Exception during main pilot searching", e);
        }
    }
}
