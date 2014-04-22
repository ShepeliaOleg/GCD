package springConstructors;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: darya.alymova
 * Date: 3/7/14
 * Time: 12:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameControlLabels {
    String refineByLabel;
    String resetFilterLabel;
    String searchLabel;
    List<String> sortOptionLabels;

    public String getRefineByLabel() {
        return refineByLabel;
    }

    public void setRefineByLabel(String refineByLabel) {
        this.refineByLabel = refineByLabel;
    }

    public String getResetFilterLabel() {
        return resetFilterLabel;
    }

    public void setResetFilterLabel(String resetFilterLabel) {
        this.resetFilterLabel = resetFilterLabel;
    }

    public String getSearchLabel() {
        return searchLabel;
    }

    public void setSearchLabel(String searchLabel) {
        this.searchLabel = searchLabel;
    }

    public List<String> getSortOptionLabels() {
        return sortOptionLabels;
    }

    public void setSortOptionLabels(List<String> sortOptionLabels) {
        this.sortOptionLabels = sortOptionLabels;
    }
}
