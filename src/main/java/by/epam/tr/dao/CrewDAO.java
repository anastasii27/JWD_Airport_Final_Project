package by.epam.tr.dao;

import java.time.LocalDate;

public interface CrewDAO {

    String userCrewForNearestFlight(String surname, String email, LocalDate lastDayOfRange) throws DAOException;
}
