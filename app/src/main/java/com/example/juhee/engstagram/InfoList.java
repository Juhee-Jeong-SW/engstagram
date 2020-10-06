package com.example.juhee.engstagram;

public class InfoList {
    String homeList;
    String boardNum;
    String boardWord;
    String boardMean;
    String boardName;
    String boardDate;
    String boardLike;


    public InfoList(String boardNum, String boardWord, String boardMean, String boardName, String boardDate, String boardLike) {

        this.boardNum = boardNum;
        this.boardWord = boardWord;
        this.boardMean = boardMean;
        this.boardName = boardName;
        this.boardDate = boardDate;
        this.boardLike = boardLike;
    }

    public String getBoardNum() { return boardNum; }

    public void setBoardNum(String boardNum) {
        this.boardNum = boardNum;
    }

    public String getBoardWord() {
        return boardWord;
    }

    public void setBoardWord(String boardWord) {
        this.boardWord = boardWord;
    }

    public String getBoardMean() {
        return boardMean;
    }

    public void setBoardMean(String boardMean) {
        this.boardMean = boardMean;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardDate() {
        return boardDate;
    }

    public void setBoardDate(String boardDate) {
        this.boardDate = boardDate;
    }

    public String getBoardLike() {
        return boardLike;
    }

    public void setBoardLike(String boardLike) {
        this.boardLike = boardLike;
    }
}
