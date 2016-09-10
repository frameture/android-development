package com.study.miwok;


public class Word {
    private String miwokWord;
    private String englishWord;
    private int iconId;
    private int audioId;


    public Word(String miwokWord, String englishWord, int iconId, int audioId) {
        this.englishWord = englishWord;
        this.miwokWord = miwokWord;
        this.iconId = iconId;
        this.audioId = audioId;
    }

    public Word(String miwokWord, String englishWord, int audioId) {
        this.englishWord = englishWord;
        this.miwokWord = miwokWord;
        this.audioId = audioId;
    }

    public String getEnglishWord(){
        return englishWord;
    }

    public String getMiwokWord() {
        return miwokWord;
    }

    public int getIconId() {
        if(iconId > 0)
            return iconId;
        else
            return -1;
    }

    public int getAudioId() {
        return audioId;
    }
}
