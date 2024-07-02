package Step3;

import Step1.FirstRatings;
import Step1.Rating;
import Step3.Filters.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThirdRatings {

    private List<Rater> myRaters;
    private FirstRatings methods;
    public ThirdRatings() {
        this("ratings.csv");
    }

    public ThirdRatings(String ratingsFile) {
        this.methods = new FirstRatings("ratedMoviesfull.csv", ratingsFile);
        this.myRaters = this.methods.loadRaters().getRaterList();
    }

    public List<Rating> getAverageRatings(int minimalRatings) {
        List<Rating> result = new ArrayList<>();
        ArrayList<String> movies = MovieDatabase.filterBy(new MinRatingCountFilter(minimalRatings, this.myRaters));
        // TODO Repetitive logic, separate.
        for(String movieId : movies) {
            Rating rating = new Rating(movieId, this.getAverageByID(movieId));
            if (rating.getValue() > 0) result.add(rating);
        }
        Collections.sort(result);
        return result;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRatings, Filter filterCriteria) {
        ArrayList<Rating> result = new ArrayList<>();
        AllFilters filters = new AllFilters();
        filters.addFilter(new MinRatingCountFilter(minimalRatings, this.myRaters));
        filters.addFilter(filterCriteria);
        ArrayList<String> matchedIds = MovieDatabase.filterBy(filters);
        System.out.println("Movies found: " + matchedIds.size());
        for(String movieId : matchedIds) {
            Rating rating = new Rating(movieId, this.getAverageByID(movieId));
            if (rating.getValue() > 0) result.add(rating);
        }
        Collections.sort(result);
        return result;
    }

    private double getAverageByID(String movieId) {
        double totalSum = 0.0;
        int counter = 0;
        for (Rater rater : this.myRaters) {
            if (rater.hasRating(movieId)) {
                totalSum += rater.getRating(movieId);
                counter++;
            }
        }
        return totalSum / counter;
    }

    public List<Rater> getRaters() {
        return this.myRaters;
    }


}
