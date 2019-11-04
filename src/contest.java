/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RAZ
 */
public class contest {

    String contest;
    String start;
    Integer end;
    String date;
     public contest(String contest, String start, Integer end, String date) {
        this.contest = contest;
        this.start = start;
        this.end = end;
        this.date = date;
    }
    public String getcontest(){
        return contest;
    }
    public String getstart(){
        return start;
    }
    public Integer getend(){
        return end;
    }
    public String getdate(){
        return date;
    }
}
