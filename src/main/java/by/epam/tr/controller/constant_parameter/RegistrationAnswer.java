package by.epam.tr.controller.constant_parameter;

import java.util.HashMap;

public final class RegistrationAnswer {

    private final static String ANSWER1 ="You are successfully registered";
    private final static String ANSWER2 ="This user is already exist";
    private final static String ANSWER3 ="Check entered information!";

    private final static RegistrationAnswer instance =  new RegistrationAnswer();
    private final  HashMap<Integer, String> answers =  new HashMap<>();

    private RegistrationAnswer(){
        answers.put(1, ANSWER1);
        answers.put(0, ANSWER2);
        answers.put(-1, ANSWER3);
    }

    public  String getAnswer(int code){
        return answers.get(code);
    }

    public static  RegistrationAnswer getInstance(){
        return  instance;
    }

}
