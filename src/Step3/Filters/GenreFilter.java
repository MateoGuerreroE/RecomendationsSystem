package Step3.Filters;

import Step3.Filter;
import Step3.MovieDatabase;

public class GenreFilter implements Filter {
    private String genre;
    public GenreFilter(String genre) {
        this.genre = genre;
    }
    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getGenres(id).toLowerCase().contains(this.genre.toLowerCase());
    }
}
