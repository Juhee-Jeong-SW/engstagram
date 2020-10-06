package com.example.juhee.engstagram;

public class BookList {

    String bookList;
    String bookUser;
    String bookWord;
    String bookMean;

    public BookList(String bookWord, String bookMean) {

        //this.bookUser = bookUser;
        this.bookWord = bookWord;
        this.bookMean = bookMean;
    }

//    public String getBookUser() {
//        return bookUser;
//    }
//
//    public void setBookUser(String bookUser) {
//        this.bookUser = bookUser;
//    }

    public String getBookWord() {
        return bookWord;
    }

    public void setBookWord(String bookWord) {
        this.bookWord = bookWord;
    }

    public String getBookMean() {
        return bookMean;
    }

    public void setBookMean(String bookMean) {
        this.bookMean = bookMean;
    }
}
