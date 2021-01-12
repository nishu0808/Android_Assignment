package com.example.androidassignment.model;

public class Books
{

    private String description;

    private String title;

    private String author;

    private String publisher;

    private String contributor;

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getContributor ()
    {
        return contributor;
    }

    public void setContributor (String contributor)
    {
        this.contributor = contributor;
    }

    public String getAuthor ()
    {
        return author;
    }

    public void setAuthor (String author)
    {
        this.author = author;
    }


    public String getPublisher ()
    {
        return publisher;
    }

    public void setPublisher (String publisher)
    {
        this.publisher = publisher;
    }
}
