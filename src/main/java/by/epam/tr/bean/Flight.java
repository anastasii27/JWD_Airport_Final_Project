package by.epam.tr.bean;

import java.io.Serializable;

public class Flight implements Serializable {

    private String departure_city;
    private String arrival_city;
    private String flight_name;
    private String departure_time;

    public Flight(String departure_city, String arrival_city, String flight_name, String departure_time) {

        this.departure_city = departure_city;
        this.arrival_city = arrival_city;
        this.flight_name = flight_name;
        this.departure_time = departure_time;
    }

    public String getDeparture_city() {
        return departure_city;
    }

    public void setDeparture_city(String departure_city) {
        this.departure_city = departure_city;
    }

    public String getArrival_city() {
        return arrival_city;
    }

    public void setArrival_city(String arrival_city) {
        this.arrival_city = arrival_city;
    }

    public String getFlight_name() {
        return flight_name;
    }

    public void setFlight_name(String flight_name) {
        this.flight_name = flight_name;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    @Override
    public String toString() {
        return getClass().getName() +"departure_city" + departure_city + "arrival_city" + arrival_city + "flight_name" + flight_name+ "departure_time"+ departure_time;
    }

    @Override
    public int hashCode() {
        return (((departure_city==null)?0:departure_city.hashCode())+ ((arrival_city ==null)?0:arrival_city .hashCode())+ ((flight_name==null)?0:flight_name.hashCode())
                + ((departure_time==null)?0:departure_time.hashCode()));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Flight other = (Flight) obj;

        if (departure_city == null) {
            if(other.departure_city!= null)
                return false;
        }else if(!departure_city.equals(other.departure_city))
            return false;

        if (arrival_city == null) {
            if(other.arrival_city!= null)
                return false;
        }else if(!arrival_city.equals(other.arrival_city))
            return false;

        if (flight_name == null) {
            if(other.flight_name!= null)
                return false;
        }else if(!flight_name.equals(other.flight_name))
            return false;

        if (departure_time == null) {
            if(other.departure_time!= null)
                return false;
        }else if(!departure_time.equals(other.departure_time))
            return false;
        return true;
    }

}
