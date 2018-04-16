package fto.ee.swk.freetimeorganizer;

/**
 * Created by Juned on 2/8/2017.
 */
/*
Event_Name_JSON
Event_Date_JSON
Event_End_Date_JSON
Event_Time_JSON
Event_Location_JSON
Event_City_JSON
Event_Country_JSON
Event_Genre_JSON
Event_Info_JSON
Event_Link_JSON
Image_URL_JSON
Event_Price_JSON
Event_ID_JSON
*/
public class DataAdapter
{
    private String EventName;
    private String EventDate;
    private String EventEndDate;
    private String EventTime;
    private String EventLocation;
    private String EventCity;
    private String EventCountry;
    private String EventGenre;
    private String EventInfo;
    private String EventLink;
    private String EventImageURL;
    private String EventPrice;
    private String EventID;
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



//    public String TicketPrice;
//    public String EventLocation;



    public String toString(){
        return "\nid: " + EventID +
               "\ntitle: " + EventName +
               "\ndate: " + EventDate +
               "\ntime: " + EventTime +
               "\ncity: " + EventCity;
    }
}
