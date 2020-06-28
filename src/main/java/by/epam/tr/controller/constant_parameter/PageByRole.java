package by.epam.tr.controller.constant_parameter;

import java.util.HashMap;

public final class PageByRole {
    private final static String PILOT ="pilot";
    private final static String ADMIN ="admin";
    private final static String DISPATCHER ="dispatcher";
    private final static String STEWARDESS ="steward";
    private final static PageByRole instance =  new PageByRole();
    private final HashMap<String, String> pages =  new HashMap<>();

    private PageByRole(){
        pages.put(ADMIN, JSPPageName.ADMIN_PAGE);
        pages.put(PILOT, JSPPageName.USER_PAGE);
        pages.put(DISPATCHER, JSPPageName.USER_PAGE);
        pages.put(STEWARDESS, JSPPageName.USER_PAGE);
    }

    public  String getPage(String role){
        return pages.get(role);
    }

    public static PageByRole getInstance(){
        return  instance;
    }
}
