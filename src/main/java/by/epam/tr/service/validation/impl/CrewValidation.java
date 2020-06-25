package by.epam.tr.service.validation.impl;

import by.epam.tr.dao.CrewDAO;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.service.validation.ValidationPattern;
import by.epam.tr.service.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;

public class CrewValidation extends Validator {
    private Logger LOGGER = LogManager.getLogger(getClass());

    @Override
    public List<String> validate(Map<String, String> params) {
        List<String> result= new ArrayList<>();
        CrewDAO crewDAO = DAOFactory.getInstance().getCrewDAO();
        
        if(!emptyValueCheck(params)){
            result.add("You didnt` enter some values");
            return result;
        }

        String crewName = params.get("crew_name");
        if(!checkWithPattern(ValidationPattern.CREW_NAME_PATTERN, crewName)){
            result.add("Illegal crew name!");
        }

        try {
            if(crewDAO.doesCrewNameExist(crewName)){
                result.add("This name is already exist!");
            }
        } catch (DAOException e) {
            LOGGER.error("Error during crew name existence checking");
        }

        if(!areSameMembersInCrew(params)){
            result.add("Same values");
        }
        return result;
    }

    private boolean areSameMembersInCrew(Map<String, String> params){
        Set<String> members = new HashSet<>(params.values());
        return members.size()==params.size() ;
    }

}
