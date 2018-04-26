package fto.ee.swk.freetimeorganizer;

public class GenreDataObject {
    private int id;
    private String genre;


    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


    //красивый вывод
    public String toString(){
        return  id + ", " + genre;
    }
}