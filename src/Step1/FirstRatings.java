package Step1;

import java.util.*;
import java.util.stream.Collectors;
import Common.CSVReader;
import Step3.EfficientRater;
import Step3.Rater;


public class FirstRatings {
   private CSVReader moviesReader;
   private CSVReader ratersReader;

   public FirstRatings() {
      this.moviesReader = new CSVReader("ratedmoviesfull.csv", Movie.class);
      this.ratersReader = new CSVReader("ratings.csv", EfficientRater.class);
   }

   public FirstRatings(String movie, String rater) {
      this.moviesReader = new CSVReader(movie, Movie.class);
      this.ratersReader = new CSVReader(rater, EfficientRater.class);
   }

   public List<Movie> loadMovies() {
      return this.moviesReader.parseCSV();
   }

   public RatersInfo loadRaters() {
      List<Map<String, String>> rawInfo = this.ratersReader.rawParseCSV();
      return this.createRaters(rawInfo);
   }

   public void testingMoviesMethod() {
      List<Movie> sample = loadMovies();
      List<Movie> filteredMoviesByGenre = filterByGenre(sample, "Comedy");
      List<Movie> filteredMoviesByTime = filterByLength(sample, 150, true);
      System.out.println("----- All movies: " + sample.size());
//      for (Movie movie : sample) {
//         System.out.println(movie.getTitle());
//      }
      System.out.println("----- Genre filtered movies: " + filteredMoviesByGenre.size());
//      for (Movie movie : filteredMoviesByGenre) {
//         System.out.println(movie.getTitle());
//         System.out.println(movie.getGenres());
//      }
      System.out.println("----- Time filtered movies: " + filteredMoviesByTime.size());
//      for (Movie movie : filteredMoviesByTime) {
//         System.out.println(movie.getTitle());
//         System.out.println(movie.getMinutes());
//      }
      getDirectorCount(sample);
   }

   private List<Movie> filterByGenre(List<Movie> movies, String genre) {
      return movies.stream().filter(movie -> movie.getGenres().toLowerCase().contains(genre.toLowerCase())).collect(Collectors.toList());
   }

   private List<Movie> filterByLength(List<Movie> movies, int length, Boolean higher) {
      return movies.stream().filter(movie -> higher ? movie.getMinutes() > length : movie.getMinutes() < length).collect(Collectors.toList());
   }

   private void getDirectorCount(List<Movie> movies) {
      Map<String, Integer> directorCount = new HashMap<>();

      for(Movie movie : movies) {
         String[] directors = movie.getDirector().split(",");
         for (String director : directors) {
            director = director.trim();
            directorCount.put(director, directorCount.getOrDefault(director, 0) + 1);
         }
      }

      int maxMovies = directorCount.values().stream().max(Integer::compare).orElse(0);

      List<String> topDirectors = directorCount.entrySet().stream()
              .filter(entry -> entry.getValue() == maxMovies)
              .map(Map.Entry::getKey)
              .collect(Collectors.toList());

      System.out.println("Max number of movies by any director: " + maxMovies);
      System.out.println("Directors who directed that many movies: " + topDirectors);
   }

   public void testingMethod2() {
      RatersInfo records = loadRaters();
      List<Rater> raterList = records.getRaterList();
      System.out.println("Total raters: " + raterList.size());
      System.out.println("---- Number of ratings by EfficientRater ID");
      Rater objective1 = records.getRaterById("193");
      System.out.printf("EfficientRater %s has %s ratings \n", objective1.getID(), objective1.numRatings());
      List<Rater> maxRaters = this.getRatersWithMaxReviews(raterList);
      Rater sampleRater = maxRaters.get(0);
      System.out.println("--- Max number of reviews: ");
      System.out.printf("Max reviews: %s done by %s\n", sampleRater.numRatings(), sampleRater.getID());
      System.out.printf("Raters with this number of reviews: %s \n", maxRaters.size());
      int ratingsFromMovie = this.getRatingNumberFromMovie(raterList, "1798709");
      System.out.printf("Movie %s has %d ratings\n", "1798709", ratingsFromMovie);
      System.out.printf("Total number of movies rated: %s", this.getTotalOfMoviesRated(raterList));
   }

   private RatersInfo createRaters(List<Map<String, String>> rawInfo) {
      List<Rater> records = new ArrayList<>();
      Map<String, Integer> idRecords = new HashMap<>();
      int internalIndex = 0;
      for (Map<String, String> rawItem : rawInfo) {
         String raterId = rawItem.get("myID");
         if (idRecords.containsKey(raterId)) {
            int index = idRecords.get(raterId);
            Rater existingRater = records.get(index);
            existingRater.addRating(rawItem.get("movie_id"), Double.parseDouble(rawItem.get("rating")));
         } else {
            Rater newRater = new EfficientRater(raterId);
            newRater.addRating(rawItem.get("movie_id"), Double.parseDouble(rawItem.get("rating")));
            records.add(newRater);
            idRecords.put(raterId, internalIndex);
            internalIndex++;
         }
      }
      return new RatersInfo(records, idRecords);
   }

   private List<Rater> getRatersWithMaxReviews(List<Rater> raters) {
      List<Rater> records = new ArrayList<>();
      int numberOfReviews = 0;
      for (Rater rater : raters) {
         if (rater.numRatings() > numberOfReviews) numberOfReviews = rater.numRatings();
      }
      for (Rater rater : raters) {
         if (rater.numRatings() == numberOfReviews) records.add(rater);
      }
      return records;
   }

   public int getRatingNumberFromMovie(List<Rater> raters, String movieID) {
      int ratingsCount = 0;
      for (Rater rater : raters) {
         List<String> ratedItems = rater.getItemsRated();
         if (ratedItems.contains(movieID)) ratingsCount++;
      }
      return ratingsCount;
   }

   private int getTotalOfMoviesRated(List<Rater> raters) {
      List<String> movieIdsRated = new ArrayList<>();
      for (Rater rater : raters) {
         List<String> ratedItems = rater.getItemsRated();
         for (String item : ratedItems) {
            if (!movieIdsRated.contains(item)) movieIdsRated.add(item);
         }
      }
      return movieIdsRated.size();
   }

}

