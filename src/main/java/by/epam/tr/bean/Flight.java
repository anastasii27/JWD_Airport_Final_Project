package by.epam.tr.bean;

import java.io.Serializable;

public class Flight implements Serializable {

    private String planeModel;
    private String departureDate;
    private String departureTime;
    private String destinationDate;
    private String destinationTime;
    private String destinationAirport;
    private String destinationCity;
    private String destinationCountry;
    private String departureAirport;
    private String departureCity;
    private String departureCountry;
    private String groupName;
    private String destinationAirportShortName;
    private String departureAirportShortName;
    private String flightNumber;

    public Flight(){}

    public Flight(String planeModel, String departureDate, String departureTime, String destinationDate, String destinationTime,
                  String destinationCity, String departureCity, String groupName, String destinationAirportShortName, String departureAirportShortName,
                  String flightNumber) {

        this.planeModel = planeModel;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.destinationDate = destinationDate;
        this.destinationTime = destinationTime;
        this.destinationCity = destinationCity;
        this.departureCity = departureCity;
        this.groupName= groupName;
        this.destinationAirportShortName = destinationAirportShortName;
        this.departureAirportShortName = departureAirportShortName;
        this.flightNumber = flightNumber;
    }

    public Flight( String destinationDate, String destinationTime, String destinationAirport,String destinationCity, String destinationCountry,String destinationAirportShortName,
                   String departureDate, String departureTime, String departureAirport, String departureCity, String departureCountry,String departureAirportShortName) {

        this.destinationDate = destinationDate;
        this.destinationTime = destinationTime;
        this.destinationAirport = destinationAirport;
        this.destinationCity = destinationCity;
        this.destinationCountry = destinationCountry;
        this.destinationAirportShortName = destinationAirportShortName;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.departureAirport = departureAirport;
        this.departureCity = departureCity;
        this.departureCountry = departureCountry;
        this.departureAirportShortName = departureAirportShortName;
    }

    public String getPlaneModel() {
        return planeModel;
    }

    public void setPlaneModel(String planeModel) {
        this.planeModel = planeModel;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDestinationDate() {
        return destinationDate;
    }

    public void setDestinationDate(String destinationDate) {
        this.destinationDate = destinationDate;
    }

    public String getDestinationTime() {
        return destinationTime;
    }

    public void setDestinationTime(String destinationTime) {
        this.destinationTime = destinationTime;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDepartureCountry() {
        return departureCountry;
    }

    public void setDepartureCountry(String departureCountry) {
        this.departureCountry = departureCountry;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDestinationAirportShortName() {
        return destinationAirportShortName;
    }

    public void setDestinationAirportShortName(String destinationAirportShortName) {
        this.destinationAirportShortName = destinationAirportShortName;
    }

    public String getDepartureAirportShortName() {
        return departureAirportShortName;
    }

    public void setDepartureAirportShortName(String departureAirportShortName) {
        this.departureAirportShortName = departureAirportShortName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    @Override
    public String toString() {
        return getClass().getName()+ " [planeModel = " + planeModel + " departureDate = " + departureDate + " departureTime = " + departureTime  +
                " destinationDate = " + destinationDate  + " destinationTime = " + destinationTime + " destinationAirport = " + destinationAirport +
                " destinationCity = " + destinationCity + " destinationCountry = " + destinationCountry +  " departureAirport = " + departureAirport +
                " departureCity = " + departureCity + " departureCountry = " + departureCountry  + " groupName= " + groupName+
                " departureAirportShortName = " + departureAirportShortName  + " destinationAirportShortName= " + destinationAirportShortName+
                " flightNumber = " + flightNumber + " ]";
    }

    @Override
    public int hashCode() {
        return (((planeModel==null)?0:planeModel.hashCode())+ ((departureDate==null)?0:departureDate.hashCode())+
                ((departureTime==null)?0:departureTime.hashCode()) + ((destinationDate==null)?0:destinationDate.hashCode())+
                ((destinationTime==null)?0:destinationTime.hashCode())+ ((destinationAirport==null)?0:destinationAirport.hashCode())+
                ((destinationCity==null)?0:destinationCity.hashCode())+ ((destinationCountry==null)?0:destinationCountry.hashCode())+
                ((departureAirport==null)?0:departureAirport.hashCode())+ ((departureCity==null)?0:departureCity.hashCode())+
                ((departureCountry==null)?0:departureCountry.hashCode())+ ((groupName == null)?0:groupName.hashCode())+
                ((departureAirportShortName==null)?0:departureAirportShortName.hashCode())+ ((destinationAirportShortName == null)?0:destinationAirportShortName.hashCode())+
                ((flightNumber==null)?0:flightNumber.hashCode()));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Flight other = (Flight) obj;

        if (planeModel == null) {
            if(other.planeModel!= null){
                return false;
            }
        }else if(!planeModel.equals(other.planeModel)){
            return false;
        }

        if ( departureDate == null) {
            if(other. departureDate!= null){
                return false;
            }
        }else if(! departureDate.equals(other. departureDate)){
            return false;
        }

        if (departureTime == null) {
            if(other.departureTime!= null){
                return false;
            }
        }else if(!departureTime.equals(other.departureTime)) {
            return false;
        }

        if (destinationDate == null) {
            if(other.destinationDate!= null){
                return false;
            }
        }else if(!destinationDate.equals(other.destinationDate)){
            return false;
        }

        if (destinationTime == null) {
            if(other.destinationTime!= null){
                return false;
            }
        }else if(!destinationTime.equals(other.destinationTime)){
            return false;
        }

        if ( destinationAirport == null) {
            if(other. destinationAirport!= null){
                return false;
            }
        }else if(! destinationAirport.equals(other. destinationAirport)){
            return false;
        }

        if (destinationCity == null) {
            if(other.destinationCity!= null){
                return false;
            }
        }else if(!destinationCity.equals(other.destinationCity)) {
            return false;
        }

        if (destinationCountry == null) {
            if(other.destinationCountry!= null){
                return false;
            }
        }else if(!destinationCountry.equals(other.destinationCountry)){
            return false;
        }

        if ( departureAirport == null) {
            if(other. departureAirport!= null){
                return false;
            }
        }else if(! departureAirport.equals(other.departureAirport)){
            return false;
        }

        if (departureCity == null) {
            if(other.departureCity!= null){
                return false;
            }
        }else if(!departureCity.equals(other.departureCity)) {
            return false;
        }

        if (departureCountry == null) {
            if(other.departureCountry!= null){
                return false;
            }
        }else if(!departureCountry.equals(other.departureCountry)){
            return false;
        }

        if (groupName == null) {
            if(other.groupName!= null){
                return false;
            }
        }else if(!groupName.equals(other.groupName)) {
            return false;
        }

        if (departureAirportShortName == null) {
            if(other.departureAirportShortName!= null){
                return false;
            }
        }else if(!departureAirportShortName.equals(other.departureAirportShortName)) {
            return false;
        }

        if (destinationAirportShortName == null) {
            if(other.destinationAirportShortName!= null){
                return false;
            }
        }else if(!destinationAirportShortName.equals(other.destinationAirportShortName)) {
            return false;
        }

        if (flightNumber == null) {
            if(other.flightNumber!= null){
                return false;
            }
        }else if(!flightNumber.equals(other.flightNumber)) {
            return false;
        }

        return true;
    }
}
