package Step2;

import Step1.Rating;

import java.util.List;

public class MovieRunnerAverage {
    public void printAverageRatings() {
        SecondRatings methods = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");
        System.out.println("Movies: " + methods.getMovieSize());
        System.out.println("Raters: " + methods.getRaterSize());

        List<Rating> avgRatings = methods.getAverageRatings(12);
        System.out.printf("Number or ratings of movies with more than %d raters: %d\n", 12, avgRatings.size());
        for (Rating rating : avgRatings) {
            System.out.printf("%.2f %s\n", rating.getValue(), methods.getTitle(rating.getItem()));
        }
    }

    public void getAverageRatingOneMovie() {
        SecondRatings methods = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");
        String movieId = methods.getID("Vacation");
        double average = methods.getAverageByID(movieId, 3);
        System.out.println("Average rating for movie is: " + average);
    }
}
