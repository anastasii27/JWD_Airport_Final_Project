package by.epam.airport_system.service.validation.impl;

import by.epam.airport_system.dao.CrewDao;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.service.validation.ValidationPattern;
import by.epam.airport_system.service.validation.ValidationResult;
import by.epam.airport_system.service.validation.Validator;
import lombok.extern.log4j.Log4j2;
import java.util.*;

@Log4j2
public class CrewValidation extends Validator {
    private final static String CREW_NAME_PARAM = "crew_name";
    private final static String KEY1= "local.validation.crews.1";
    private final static String KEY2= "local.validation.crews.2";
    private final static String KEY3= "local.validation.crews.3";
    private final static String KEY4= "local.validation.crews.4";

    @Override
    public ValidationResult validate(Map<String, String> params) {
        ValidationResult result = getValidationResult(params);
        CrewDao crewDAO = DaoFactory.getInstance().getCrewDAO();
        
        if(!emptyValueCheck(params)){
            result.addError(KEY1);
            return result;
        }

        String crewName = params.get(CREW_NAME_PARAM);
        if(!checkWithPattern(ValidationPattern.CREW_NAME_PATTERN, crewName)){
            result.addError(KEY2);
        }

        try {
            if(crewDAO.doesCrewNameExist(crewName)){
                result.addError(KEY3);
            }
        } catch (DaoException e) {
            log.error("Error during crew name existence checking");
        }

        if(!areSameMembersInCrew(params)){
            result.addError(KEY4);
        }
        return result;
    }

    private boolean areSameMembersInCrew(Map<String, String> params){
        Set<String> members = new HashSet<>(params.values());
        return members.size()==params.size() ;
    }
}
