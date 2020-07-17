package by.epam.tr.service.validation.impl;

import by.epam.tr.dao.CrewDao;
import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.DaoFactory;
import by.epam.tr.service.validation.ValidationPattern;
import by.epam.tr.service.validation.ValidationResult;
import by.epam.tr.service.validation.Validator;
import lombok.extern.log4j.Log4j2;
import java.util.*;

@Log4j2
public class CrewValidation implements Validator {
    private final static String CREW_NAME_PARAM = "crew_name";
    private final static String LOCAL_PARAM = "local";
    private final static String KEY1= "local.validation.crews.1";
    private final static String KEY2= "local.validation.crews.2";
    private final static String KEY3= "local.validation.crews.3";
    private final static String KEY4= "local.validation.crews.4";

    @Override
    public ValidationResult validate(Map<String, String> params) {
        ValidationResult result = getValidationResult(params);
        CrewDao crewDAO = DaoFactory.getInstance().getCrewDAO();
        
        if(!emptyValueCheck(params)){
            result.addMessage(KEY1);
            return result;
        }

        String crewName = params.get(CREW_NAME_PARAM);
        if(!checkWithPattern(ValidationPattern.CREW_NAME_PATTERN, crewName)){
            result.addMessage(KEY2);
        }

        try {
            if(crewDAO.doesCrewNameExist(crewName)){
                result.addMessage(KEY3);
            }
        } catch (DaoException e) {
            log.error("Error during crew name existence checking");
        }

        if(!areSameMembersInCrew(params)){
            result.addMessage(KEY4);
        }
        return result;
    }

    private boolean areSameMembersInCrew(Map<String, String> params){
        Set<String> members = new HashSet<>(params.values());
        return members.size()==params.size() ;
    }

    private ValidationResult getValidationResult(Map<String,String> params){
        String lang = params.get(LOCAL_PARAM);
        ValidationResult result;

        if(lang == null){
            result = new ValidationResult();
        }else {
            result = new ValidationResult(lang);
        }
        return result;
    }
}
