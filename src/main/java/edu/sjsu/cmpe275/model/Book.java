package edu.sjsu.cmpe275.model;

import javax.persistence.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
/**
 * Created by fu on 12/6/16.
 */
@Entity
@Table(name = "book", schema = "cmpe275", catalog = "")
public class Book {
    private int bid;
    private String title;
    private String author;
    private String callNumber;
    private String publisher;
    private int year;
    private String location;
    private int number;
    private String status;
    private String keywords;

    @Basic
    @Id
    @Column(name = "bid")
    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name = "call_number")
    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    @Basic
    @Column(name = "publisher")
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Basic
    @Column(name = "year")
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "keywords")
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (bid != book.bid) return false;
        if (year != book.year) return false;
        if (number != book.number) return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (callNumber != null ? !callNumber.equals(book.callNumber) : book.callNumber != null) return false;
        if (publisher != null ? !publisher.equals(book.publisher) : book.publisher != null) return false;
        if (location != null ? !location.equals(book.location) : book.location != null) return false;
        if (status != null ? !status.equals(book.status) : book.status != null) return false;
        if (keywords != null ? !keywords.equals(book.keywords) : book.keywords != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bid;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (callNumber != null ? callNumber.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + number;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (keywords != null ? keywords.hashCode() : 0);
        return result;
    }

}
