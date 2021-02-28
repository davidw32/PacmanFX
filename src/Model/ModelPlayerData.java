package Model;

import java.io.Serializable;

public class ModelPlayerData implements Model, Serializable {
    protected String username;
    protected int score;

    public ModelPlayerData(){
    }
    public ModelPlayerData(String username, int score){
        this.username = username;
        this.score = score;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public void setScore(int score){
        this.score = score;
    }
    public String getUsername(){
        return this.username;
    }
    public int getScore() {
        return score;
    }
}
