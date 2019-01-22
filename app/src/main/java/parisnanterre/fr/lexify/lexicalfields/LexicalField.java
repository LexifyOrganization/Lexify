package parisnanterre.fr.lexify.lexicalfields;

class LexicalField {

    private int id;
    private String wordFr;
    private String wordEn;
    private String descFr;
    private String descEn;

    public LexicalField(int id, String wordFr, String wordEn, String descFr, String descEn) {
        this.id = id;
        this.wordFr = wordFr;
        this.wordEn = wordEn;
        this.descFr = descFr;
        this.descEn = descEn;
    }
    public LexicalField() {

    }

    public String getWord(String lang) {
        if(lang.equals("en"))
            return wordEn;
        else if(lang.equals("fr"))
            return wordFr;
        else
            return wordEn;
    }

    public String getDesc(String lang) {
        if(lang.equals("en"))
            return descEn;
        else if(lang.equals("fr"))
            return descFr;
        else
            return descEn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWordFr() {
        return wordFr;
    }

    public void setWordFr(String wordFr) {
        this.wordFr = wordFr;
    }

    public String getWordEn() {
        return wordEn;
    }

    public void setWordEn(String wordEn) {
        this.wordEn = wordEn;
    }

    public String getDescFr() {
        return descFr;
    }

    public void setDescFr(String descFr) {
        this.descFr = descFr;
    }

    public String getDescEn() {
        return descEn;
    }

    public void setDescEn(String descEn) {
        this.descEn = descEn;
    }
}
