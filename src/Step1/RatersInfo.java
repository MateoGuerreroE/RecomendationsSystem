package Step1;

import Step3.Rater;

import java.util.List;
import java.util.Map;

public class RatersInfo {
    private List<Rater> raterList;
    private Map<String, Integer> ratersMetadata;

    public RatersInfo(List<Rater> raters, Map<String, Integer> info) {
        this.raterList = raters;
        this.ratersMetadata = info;
    }

    public List<Rater> getRaterList() {
        return raterList;
    }

    public Map<String, Integer> getRatersMetadata() {
        return ratersMetadata;
    }

    public Rater getRaterById(String id) {
        int index = ratersMetadata.get(id);
        return raterList.get(index);
    }
}
