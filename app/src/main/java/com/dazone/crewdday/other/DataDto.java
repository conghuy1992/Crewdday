package com.dazone.crewdday.other;

/**
 * Created by david on 12/23/15.
 */
public class DataDto {
    protected int id  =0;
    protected String title = "";
    protected String content = "";

    public DataDto() {
        super();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
