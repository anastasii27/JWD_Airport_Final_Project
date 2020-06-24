package by.epam.tr.service.validation.impl;

import by.epam.tr.dao.CrewDAO;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.service.validation.ValidationPattern;
import by.epam.tr.service.validation.Validator;
import java.util.*;

public class CrewValidation extends Validator {

    @Override
    public List<String> validate(Map<String, String> params) {
        List<String> result= new ArrayList<>();
        
        if(!emptyValueCheck(params)){
            result.add("You didnt` enter some values");
            return result;
        }

        String firstPilot = params.get("first_pilot");
        CrewDAO crewDAO = DAOFactory.getInstance().getCrewDAO();
        String crewName = params.get("crew_name");

        if(!checkWithPattern(ValidationPattern.CREW_NAME_PATTERN, crewName)){
            result.add("Illegal crew name!");
        }

        try {
            if(crewDAO.doesCrewNameExist(crewName)){
                result.add("This name is already exist!");
            }
        } catch (DAOException e) {
            //
        }

        if(firstPilot.length()==0){
            result.add("You didn`t choose first pilot");
        }



        if(params.get("steward1") == null){
            result.add("There are no stewards");
        }

        return result;
    }
}
