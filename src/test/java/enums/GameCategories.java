package enums;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sergiich on 6/3/14.
 */
public enum GameCategories {
    cat1No("/cat_no_subcat1", "gtsatq", "dctw", "car", "rodz", "gts50", "gtscnb", "ro", "bjl"),
    cat2No("/cat_no_subcat2", "gts186","bl","gts187","vf63","qop","gts185","frr_g","gts195","wv_s","gts188","gts198"),
    cat1("/cat_subcat1", "gts197","pnp","gts43","gts196","grel","gts203","gts52","gts215","gts207","tob","rol","gts177","gts220","splsc","gts170"),
    cat2("/cat_subcat2", "atw","bld","cr","ba","cheaa","drd","fnf","gtsdnc","elr","dw","ghl","irl","bib","glr","hr","kn","kkg","jp","po","jb50"),
    cat1SubA("/cat_subcat1/subcat_a", "gts197","pnp","gts43","gts196","grel"),
    cat1SubB("/cat_subcat1/subcat_b", "gts203","gts52","gts215","gts207","tob"),
    cat1SubC("/cat_subcat1/subcat_c", "rol","gts177","gts220","splsc","gts170"),
    cat2SubA("/cat_subcat2/subcat_a", "atw","bld","cr","ba","cheaa"),
    cat2SubB("/cat_subcat2/subcat_b", "drd","fnf","gtsdnc","elr","dw"),
    cat2SubC("/cat_subcat2/subcat_c", "ghl","irl","bib","glr","hr"),
    cat2SubD("/cat_subcat2/subcat_d", "kn","kkg","jp","po","jb50"),
    groupTop(new GameCategories[]{cat1No, cat2No, cat1, cat2}),
    groupSub1(new GameCategories[]{cat1SubA, cat1SubB, cat1SubC}),
    groupSub2(new GameCategories[]{cat2SubA, cat2SubB, cat2SubC, cat2SubD}),
    groupAll(new GameCategories[]{cat1No, cat2No, cat1, cat2});

    private final ArrayList<String> urls;
    private final ArrayList<String> games;

    private GameCategories(String url, String... games) {
        this.games = new ArrayList<>(Arrays.asList(games));
        urls=new ArrayList<>(Arrays.asList(url));
    }

    private GameCategories(String url) {
        this.games = new ArrayList<>();
        urls=new ArrayList<>(Arrays.asList(url));
    }

    private GameCategories(GameCategories[] categories) {
        games = new ArrayList<>();
        urls=new ArrayList<>();
        for(GameCategories group:categories){
            games.addAll(group.getGames());
            urls.add(group.getUrl());
        };
    }

    public String getUrl(){
        return urls.get(0);
    }

    public ArrayList<String> getUrls(){
        return urls;
    }

    public ArrayList<String> getGames(){
        return games;
    }

    public String getLastGame(){
        return games.get(games.size()-1);
    }
}
