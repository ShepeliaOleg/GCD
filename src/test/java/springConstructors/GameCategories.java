package springConstructors;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: darya.alymova
 * Date: 3/6/14
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameCategories {
    List<String> catNoSubcat1;
    List<String> catNoSubcat2;
    List<String> catSubcat1_subcatA;
    List<String> catSubcat1_subcatB;
    List<String> catSubcat1_subcatC;
    List<String> catSubcat2_subcatA;
    List<String> catSubcat2_subcatB;
    List<String> catSubcat2_subcatC;
    List<String> catSubcat2_subcatD;


    public void setCatNoSubcat1(List<String> catNoSubcat1) {
        this.catNoSubcat1 = catNoSubcat1;
    }

    public void setCatNoSubcat2(List<String> catNoSubcat2) {
        this.catNoSubcat2 = catNoSubcat2;
    }

    public void setCatSubcat1_subcatA(List<String> catSubcat1_subcatA) {
        this.catSubcat1_subcatA = catSubcat1_subcatA;
    }

    public void setCatSubcat1_subcatB(List<String> catSubcat1_subcatB) {
        this.catSubcat1_subcatB = catSubcat1_subcatB;
    }

    public void setCatSubcat1_subcatC(List<String> catSubcat1_subcatC) {
        this.catSubcat1_subcatC = catSubcat1_subcatC;
    }

    public void setCatSubcat2_subcatA(List<String> catSubcat2_subcatA) {
        this.catSubcat2_subcatA = catSubcat2_subcatA;
    }

    public void setCatSubcat2_subcatB(List<String> catSubcat2_subcatB) {
        this.catSubcat2_subcatB = catSubcat2_subcatB;
    }

    public void setCatSubcat2_subcatC(List<String> catSubcat2_subcatC) {
        this.catSubcat2_subcatC = catSubcat2_subcatC;
    }

    public void setCatSubcat2_subcatD(List<String> catSubcat2_subcatD) {
        this.catSubcat2_subcatD = catSubcat2_subcatD;
    }

    public List<String> getCatNoSubcat1() {
        return catNoSubcat1;
    }

    public List<String> getCatNoSubcat2() {
        return catNoSubcat2;
    }

    public List<String> getCatSubcat1_subcatA() {
        return catSubcat1_subcatA;
    }

    public List<String> getCatSubcat1_subcatB() {
        return catSubcat1_subcatB;
    }

    public List<String> getCatSubcat1_subcatC() {
        return catSubcat1_subcatC;
    }

    public List<String> getCatSubcat2_subcatA() {
        return catSubcat2_subcatA;
    }

    public List<String> getCatSubcat2_subcatB() {
        return catSubcat2_subcatB;
    }

    public List<String> getCatSubcat2_subcatC() {
        return catSubcat2_subcatC;
    }

    public List<String> getCatSubcat2_subcatD() {
        return catSubcat2_subcatD;
    }


    public List<String> getCatSubcat1(){
        List<String> a = getCatSubcat1_subcatA();
        List<String> b = getCatSubcat1_subcatB();
        List<String> c = getCatSubcat1_subcatC();
        a.addAll(b);
        a.addAll(c);
        return a;
    }

    public List<String> getCatSubcat2(){
        List<String> a = getCatSubcat2_subcatA();
        List<String> b = getCatSubcat2_subcatB();
        List<String> c = getCatSubcat2_subcatC();
        List<String> d = getCatSubcat2_subcatD();
        a.addAll(b);
        a.addAll(c);
        a.addAll(d);
        return a;
    }

    public List<String> getAllGames(){
        List<String> cat1 = getCatNoSubcat1();
        List<String> cat2 = getCatNoSubcat2();
        List<String> cat3 = getCatSubcat1();
        List<String> cat4 = getCatSubcat2();
        cat1.addAll(cat2);
        cat1.addAll(cat3);
        cat1.addAll(cat4);
        return cat1;

    }
}
