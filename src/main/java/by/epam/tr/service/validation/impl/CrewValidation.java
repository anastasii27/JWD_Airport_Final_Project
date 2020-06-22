package by.epam.tr.service.validation.impl;

import by.epam.tr.dao.CrewDAO;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.service.validation.ValidationPattern;
import by.epam.tr.service.validation.Validator;
import java.util.*;

public class CrewValidation extends Validator {


    @Override
    public List<String> validate(Map<String, String> params) {

        List<String> result= new ArrayList<>();

        String firstPilot = params.get("first_pilot");
        String secondPilot = params.get("second_pilot");
        CrewDAO crewDAO = DAOFactory.getInstance().getCrewDAO();
        String crewName = params.get("crew_name");

        if(!emptyValueCheck(params)){
            result.add("You didnt` enter some values");
            return result;
        }


        if(!checkWithPattern(ValidationPattern.CREW_NAME_PATTERN, crewName)){
            result.add("Illegal crew name!");
        }

//        if(crewDAO.doesCrewNameExist(crewName)){
//            result.add("This name is already exist!");
//        }

        if(firstPilot.length()==0){
            result.add("You didn`t choose first pilot");
        }

        if(secondPilot.length()==0){
            result.add("You didn`t choose second pilot");
        }

        if(firstPilot.equals(secondPilot)){
            result.add("Pilots are the same");
        }

        if(params.get("steward1") == null){
            result.add("There are no stewards");
        }

        return result;
    }

}
