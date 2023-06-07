package com.example.qa_answer.data.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
    private int index, nonce;
    private long timeStamp;
    private String hash, previousHash;
    private String uid;
    private int token;

    public Block(int index, long timeStamp, String previousHash, String uid, int token) {
        this.index = index;
        this.timeStamp = timeStamp;
        this.previousHash = previousHash;
        this.uid = uid;
        this.token = token;
        nonce=0;
        hash=Block.calculateHash(this);
    }

    private static String calculateHash(Block block) {
        if (block!=null) {
            MessageDigest messageDigest;
            try {
                messageDigest=MessageDigest.getInstance("SHA-256");

            }
            catch (NoSuchAlgorithmException e) {
                return null;
            }
            String txt=block.getStringData();
            final  byte[] bytes=messageDigest.digest(txt.getBytes());
            final StringBuilder builder=new StringBuilder();
            for (final byte b:bytes) {
                String hex=Integer.toHexString(0xff & b);
                if (hex.length()==1) {
                    builder.append('0');
                }
                builder.append(hex);
                return builder.toString();
            }
        }
        return null;
    }

    private String getStringData() {
        return index+timeStamp+previousHash+uid+token+nonce;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public void mineBlock(int difficulty) {
            nonce=0;
            while (!getHash().substring(0,difficulty).equals(transferDifficultytoString(difficulty))) {
                nonce++;
                hash=Block.calculateHash(this);
        }
    }

    public String transferDifficultytoString(int difficulty) {
        StringBuilder builder=new StringBuilder();
        for (int i=0;i<difficulty;i++) {
            builder.append(0);
        }
        return builder.toString();
    }
}
