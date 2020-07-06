package by.epam.tr.bean;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

@Data
@Builder
public class Flight implements Serializable {
    private String status;
    private String planeModel;//todo продумать
    private String planeNumber;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate destinationDate;
    private LocalTime destinationTime;
    private String destinationAirport;
    private String destinationCity;
    private String destinationCountry;
    private String departureAirport;
    private String departureCity;
    private String departureCountry;
    private String destinationAirportShortName;
    private String departureAirportShortName;
    private String flightNumber;
    private String crew;
    private User dispatcher;
    private Plane plane;

    public static final Comparator<Flight> SORT_BY_TIME_AND_DATE = new Comparator<Flight>() {
        @Override
        public int compare(Flight flight1, Flight flight2) {
            if(flight1.getDepartureDate().isAfter(flight2.getDepartureDate())){
                return 1;
            }
            if(flight1.getDepartureDate().equals(flight2.getDepartureDate())){
                if(flight1.getDepartureTime().isAfter(flight2.getDepartureTime())){
                    return 1;
                }
                if (flight1.getDepartureTime().equals(flight2.getDepartureTime())){
                    return 0;
                }
                return -1;
            }
            return -1;
        }
    };
}