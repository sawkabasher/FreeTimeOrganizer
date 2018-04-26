package fto.ee.swk.freetimeorganizer;

public class CityDataObject {
    private String city;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    //красивый вывод
    public String toString(){
        return "city" + city;
    }
}
