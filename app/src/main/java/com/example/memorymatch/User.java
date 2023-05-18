package com.example.memorymatch;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String docID;

    private String uid, username, email, firstName, lastName, password;
    private int highscore, currency, hints, currentLevel;

    // may be implemented later so we can sort by order of importance on list
    // value of 1-3 with 1 being most desired items

    // needed  for the Parcelable code to work
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int size) {
            return new User[0];
        }
    };


    /** This is a "constructor" of sorts that is needed with the Parceable interface to
     * tell the intent how to create a WishListItem object when it is received from the intent
     * basically it is setting each instance variable as a String or Int
     * if the instance variables were objects themselves you would need to do more complex code
     *
     * @param parcel    the parcel that is received from the intent
     */

    public User(Parcel parcel) {
        this.uid = parcel.readString();
        this.username = parcel.readString();
        this.email = parcel.readString();
        this.firstName = parcel.readString();
        this.lastName = parcel.readString();
        this.password = parcel.readString();

        this.highscore = parcel.readInt();
        this.currency = parcel.readInt();
        this.hints = parcel.readInt();
        this.currentLevel = parcel.readInt();

        this.docID = parcel.readString();
    }

    public User(String uid, String username, String email, String firstName, String lastName, String password) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;

        this.highscore = 0;
        this.currency = 0;
        this.hints = 0;
        this.currentLevel = 0;

        this.docID = "No docID yet";
    }

    public User(){
        this.uid = "";
        this.username = "";
        this.email = "";
        this.firstName = "";
        this.lastName = "";
        this.password = "";

        this.highscore = 0;
        this.currency = 0;
        this.hints = 0;
        this.currentLevel = 0;

        this.docID = "No docID yet";
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    /**
     * This is what is used when we send the Event object through an intent
     * It is also a method that is part of the Parceable interface and is needed
     * to set up the object that is being sent.  Then, when it is received, the
     * other Event constructor that accepts a Parcel reference can "unpack it"
     *
     */
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(uid);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(password);

        dest.writeInt(highscore);
        dest.writeInt(currency);
        dest.writeInt(hints);
        dest.writeInt(currentLevel);

        dest.writeString(docID);
    }

    public String toString() {
        return " " + username + " has " + hints + " hints, a highscore of " + highscore + ", and " + currency + " currency";
    }

    public String getUsername() {
        return username;
    }

    public int getHighscore() {
        return highscore;
    }

    public int getCurrency() {
        return currency;
    }

    public int getHints() {
        return hints;
    }

    public String getEmail(){
        return email;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getPassword(){
        return password;
    }

    public int getCurrentLevel(){
        return currentLevel;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public void setHints(int hints) {
        this.hints = hints;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setCurrentLevel(int currentLevel){
        this.currentLevel = currentLevel;
    }

    public String getDocID() {
        return docID;
    }
    public void setDocID(String docID) {
        this.docID = docID;
    }
}

