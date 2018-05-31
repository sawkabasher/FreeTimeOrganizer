package fto.ee.swk.freetimeorganizer;

public class DataObject
{
    private String EventName;        //Event_Name_JSON
    private String EventDate;        //Event_Date_JSON
    private String EventEndDate;     //Event_End_Date_JSON
    private String EventTime;        //Event_Time_JSON
    private String EventLocation;    //Event_Location_JSON
    private String EventCity;        //Event_City_JSON
    private String EventCountry;     //Event_Country_JSON
    private String EventGenre;       //Event_Genre_JSON
    private String EventInfo;        //Event_Info_JSON
    private String EventLink;        //Event_Link_JSON
    private String EventImageURL;    //Image_URL_JSON
    private String EventPrice;       //Event_Price_JSON
    private String EventID;          //Event_ID_JSON
    private String EventDescription;

    public String getEventDescription() {
        return EventDescription;
    }

    public void setEventDescription(String eventDescription) {
        EventDescription = eventDescription;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public String getEventEndDate() {
        return EventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        EventEndDate = eventEndDate;
    }

    public String getEventTime() {
        return EventTime;
    }

    public void setEventTime(String eventTime) {
        EventTime = eventTime;
    }

    public String getEventLocation() {
        return EventLocation;
    }

    public void setEventLocation(String eventLocation) {
        EventLocation = eventLocation;
    }

    public String getEventCity() {
        return EventCity;
    }

    public void setEventCity(String eventCity) {
        EventCity = eventCity;
    }

    public String getEventCountry() {
        return EventCountry;
    }

    public void setEventCountry(String eventCountry) {
        EventCountry = eventCountry;
    }

    public String getEventGenre() {
        return EventGenre;
    }

    public void setEventGenre(String eventGenre) {
        EventGenre = eventGenre;
    }

    public String getEventInfo() {
        return EventInfo;
    }

    public void setEventInfo(String eventInfo) {
        EventInfo = eventInfo;
    }

    public String getEventLink() {
        return EventLink;
    }

    public void setEventLink(String eventLink) {
        EventLink = eventLink;
    }

    public String getEventImageURL() {
        return EventImageURL;
    }

    public void setEventImageURL(String eventImageURL) {
        EventImageURL = eventImageURL;
    }

    public String getEventPrice() {
        return EventPrice;
    }

    public void setEventPrice(String eventPrice) {
        EventPrice = eventPrice;
    }

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String eventID) {
        EventID = eventID;
    }








    public String toString(){
        return  "EventName"        + " " + EventName         + ", " +
                "EventDate"        + " " + EventDate         + ", " +
                "EventEndDate"     + " " + EventEndDate      + ", " +
                "EventTime"        + " " + EventTime         + ", " +
                "EventLocation"    + " " + EventLocation     + ", " +
                "EventCity"        + " " + EventCity         + ", " +
                "EventCountry"     + " " + EventCountry      + ", " +
                "EventGenre"       + " " + EventGenre        + ", " +
                "EventInfo"        + " " + EventInfo         + ", " +
                "EventLink"        + " " + EventLink         + ", " +
                "EventImageURL"    + " " + EventImageURL     + ", " +
                "EventPrice"       + " " + EventPrice        + ", " +
                "EventID"          + " " + EventID           + ", " +
                "EventDescription" + " " + EventDescription;
    }
}
