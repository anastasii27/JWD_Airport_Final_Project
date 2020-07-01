package by.epam.tr.service.validation.impl;

import by.epam.tr.dao.CrewDao;
import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.DaoFactory;
import by.epam.tr.service.validation.ValidationPattern;
import by.epam.tr.service.validation.Validator;
import lombok.extern.log4j.Log4j2;
import java.util.*;

@Log4j2
public class CrewValidation extends Validator {
    private final static String CREW_NAME_PARAM = "crew_name";

    @Override
    public List<String> validate(Map<String, String> params) {
        List<String> validationResult= new ArrayList<>();
        CrewDao crewDAO = DaoFactory.getInstance().getCrewDAO();
        
        if(!emptyValueCheck(params)){
            validationResult.add("You didnt` enter some values");
            return validationResult;
        }

        String crewName = params.get(CREW_NAME_PARAM);
        if(!checkWithPattern(ValidationPattern.CREW_NAME_PATTERN, crewName)){
            validationResult.add("Illegal crew name!");
        }

        try {
            if(crewDAO.doesCrewNameExist(crewName)){
                validationResult.add("This name is already exist!");
            }
        } catch (DaoException e) {
            log.error("Error during crew name existence checking");
        }

        if(!areSameMembersInCrew(params)){
            validationResult.add("Same values");
        }
        return validationResult;
    }

    private boolean areSameMembersInCrew(Map<String, String> params){
        Set<String> members = new HashSet<>(params.values());
        return members.size()==params.size() ;
    }
}
