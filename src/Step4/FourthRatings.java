package Step4;

import Step1.Movie;
import Step1.Rating;
import Step3.Filter;
import Step3.Filters.AllFilters;
import Step3.Filters.MinRatingCountFilter;
import Step3.Filters.TrueFilter;
import Step3.MovieDatabase;
import Step3.Rater;

import java.util.*;
import java.util.stream.Collectors;

public class FourthRatings {

    public FourthRatings(String ratingsFile) {
        RaterDatabase.initialize("ratings.csv");
    }

    public List<Rating> getAverageRatings(int minimalRatings) {
        List<Rating> result = new ArrayList<>();
        ArrayList<String> movies = MovieDatabase.filterBy(new MinRatingCountFilter(minimalRatings, RaterDatabase.getRaters()));
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
        filters.addFilter(new MinRatingCountFilter(minimalRatings, RaterDatabase.getRaters()));
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
        for (Rater rater : RaterDatabase.getRaters()) {
            if (rater.hasRating(movieId)) {
                totalSum += rater.getRating(movieId);
                counter++;
            }
        }
        return totalSum / counter;
    }

    private double dotProduct(Rater me, Rater r) {
        double dotProduct = 0.0;

        ArrayList<String> sharedMovies = new ArrayList<>();
        for (String movieId : me.getItemsRated()) {
            if (r.hasRating(movieId)) sharedMovies.add(movieId);
        }

        for (String movieId : sharedMovies) {
            double rating1 = me.getRating(movieId) - 5;
            double rating2 = r.getRating(movieId) - 5;

            dotProduct += rating1 * rating2;
        }

        return dotProduct;
    }

    private List<Rating> getSimilarities(String id) {
        Rater mainRater = RaterDatabase.getRater(id);
        List<Rater> allRaters = RaterDatabase.getRaters();

        List<Rating> result = new ArrayList<>();
        for (Rater r : allRaters) {
            if (r.getID().equals(id)) continue;
            double dotProduct = this.dotProduct(mainRater, r);
            if (dotProduct > 0) result.add(new Rating(r.getID(), dotProduct));
        }
        Collections.sort(result, Comparator.comparingDouble(Rating::getValue).reversed());
        return result;
    }

    public List<Rating> getSimilarRatings(String raterId, int numSimilarRates, int minimalRaters, Filter f) {
        List<Rating> validRaters = getSimilarities(raterId).subList(0, numSimilarRates);
        List<String> allMovies = MovieDatabase.filterBy(f);
        List<Rating> result = new ArrayList<>();
        List<String> validRaterIds = validRaters.stream().map(rating -> rating.getItem()).collect(Collectors.toList());

        for (String movieId : allMovies) {
            if (MovieDatabase.getRatingCountByID(validRaterIds, movieId) < minimalRaters) continue;
            int movieRaters = 0;
            double weightedRating = 0;
            for (Rating weightRating : validRaters) {
                Rater rater = RaterDatabase.getRater(weightRating.getItem());
                if (rater.hasRating(movieId)) {
                    movieRaters++;
                    weightedRating += rater.getRating(movieId) * weightRating.getValue();
                }
            }
            result.add(new Rating(movieId, weightedRating / movieRaters));
        }
        Collections.sort(result, Comparator.comparingDouble(Rating::getValue));
        return result;
    }

}
