package by.epam.tr.service.impl;

import by.epam.tr.dao.CrewDAO;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import java.time.LocalDate;

public class CrewServiceImpl implements CrewService {

    private final static int MAX_DAY = 30;

    @Override
    public String userCrewForNearestFlight(String surname, String email) throws ServiceException {

        CrewDAO dao = DAOFactory.getInstance().getCrewDAO();
        String crewName;
        LocalDate lastDayOgRange = LocalDate.now().plusDays(MAX_DAY);

        try {

            crewName = dao.userCrewForNearestFlight(surname, email, lastDayOgRange);

        } catch (DAOException e) {
            throw new ServiceException("Exception during user`s group getting!");
        }
        return crewName;
    }
}
