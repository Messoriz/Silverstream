import java.util.*;
public class Series extends Media{
    protected int endYear;
    protected ArrayList<String> seasons;
    
    public Series(String title, int year, ArrayList<String> genre, double rating, int endYear, ArrayList<String> seasons){
        super(title,year,genre,rating);
        this.endYear = endYear;
        this.seasons = seasons;
    }
    
    public int getEndYear(){
        return endYear;
    }
    
    public ArrayList<String> getSeasons(){
        return seasons;
    }
}