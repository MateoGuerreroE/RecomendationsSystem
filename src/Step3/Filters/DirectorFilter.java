package Step3.Filters;

import Step3.Filter;
import Step3.MovieDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class DirectorFilter implements Filter {

    private ArrayList<String> directorNames;

    public DirectorFilter(String directorNames) {
        String[] directorArray = directorNames.split("\\s*,\\s*");
        this.directorNames = new ArrayList<>(Arrays.asList(directorArray));
    }

    @Override
    public boolean satisfies(String id) {
        String directors = MovieDatabase.getDirector(id);
        for (String director : directorNames) {
            if (directors.toLowerCase().contains(director.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
