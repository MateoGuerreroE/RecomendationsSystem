package Step4;

import java.util.*;

import Common.CSVReader;
import Step1.Rating;
import Step3.EfficientRater;
import Step3.Rater;
import org.apache.commons.csv.*;

public class RaterDatabase {
    private static HashMap<String, Rater> ourRaters;

    private static void initialize() {
        // this method is only called from addRatings
        if (ourRaters == null) {
            ourRaters = new HashMap<String,Rater>();
        }
    }

    public static void initialize(String filename) {
        if (ourRaters == null) {
            ourRaters= new HashMap<String,Rater>();
            addRatings(filename);
        }
    }

    public static void addRatings(String filename) {
        initialize();
        CSVReader reader = new CSVReader(filename, Rating.class);
        List<Map<String, String>> raterList = reader.rawParseCSV();
        for(Map<String,String> rawRec : raterList) {
            String id = rawRec.get("myID");
            String item = rawRec.get("movie_id");
            String rating = rawRec.get("rating");
            addRaterRating(id,item,Double.parseDouble(rating));
        }
    }

    public static void addRaterRating(String raterID, String movieID, double rating) {
        initialize();
        Rater rater =  null;
        if (ourRaters.containsKey(raterID)) {
            rater = ourRaters.get(raterID);
        }
        else {
            rater = new EfficientRater(raterID);
            ourRaters.put(raterID,rater);
        }
        rater.addRating(movieID,rating);
    }

    public static Rater getRater(String id) {
        initialize();

        return ourRaters.get(id);
    }

    public static ArrayList<Rater> getRaters() {
        initialize();
        ArrayList<Rater> list = new ArrayList<Rater>(ourRaters.values());

        return list;
    }

    public static int size() {
        return ourRaters.size();
    }



}