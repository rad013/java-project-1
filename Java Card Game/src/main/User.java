package main;

public class User {
    private String userName;
    private String pass;
    private int score;
    public User(String userName, String pass, int score) {
	this.userName = userName;
	this.pass = pass;
	this.score = score;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    

}
