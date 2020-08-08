package by.epam.airport_system.bean;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

@Data
@Builder
public class Flight implements Serializable, Comparable<Flight> {
    private int id;
    private String status;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate destinationDate;
    private LocalTime destinationTime;
    private String planeModel;
    private String planeNumber;
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

    @Override
    public int compareTo(Flight other) {
        return SORT_BY_TIME_AND_DATE.compare(this, other);
    }

    private static final Comparator<Flight> SORT_BY_TIME_AND_DATE = Comparator.comparing(Flight::getDepartureDate)
            .thenComparing(Flight::getDepartureTime);

}
