package by.epam.airport_system.presentation;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDate;

public class DateRangeTag extends TagSupport {
    private final static String DATE_PATTERN ="^(202[0-9])[-]([0]?[1-9]|[1][0-2])[-]([0]?[1-9]|[1|2][0-9]|[3][0|1])$";
    private static final String NUMBER_PATTERN ="\\d+";
    private String startDate;
    private String rangeLength;

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setRangeLength(String rangeLength) {
        this.rangeLength = rangeLength;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        try {
            if(checkWithPatter( startDate, DATE_PATTERN) && checkWithPatter(rangeLength, NUMBER_PATTERN)) {
                LocalDate date = LocalDate.parse(startDate);
                int range = Integer.parseInt(rangeLength);

                out.print(date + "  –  " + date.plusDays(range));
            }else {
                out.print(" ");
            }
        } catch (IOException e) {
           throw new JspException(e);
        }
        return SKIP_BODY;
    }

    private boolean checkWithPatter(String value, String pattern){
        return value.trim().matches(pattern);
    }
}
