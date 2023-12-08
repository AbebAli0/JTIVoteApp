package com.example.jtivoteapp;

public class Vote {
    private int voteId;
    private int candidateId;
    private long startTime;

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}

