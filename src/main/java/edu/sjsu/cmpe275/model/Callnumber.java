package edu.sjsu.cmpe275.model;

import javax.persistence.*;

/**
 * Created by ZHANG JOHN on 2016/12/17.
 */
@Entity
@Table(name = "callnumber", schema = "sql3148013", catalog = "")
public class Callnumber {
    private int cnid;
    private int bid;
    private String callnumber;
    private int copyid;
    private String avaliable;

    @Id
    @Column(name = "cnid", nullable = false)
    public int getCnid() {
        return cnid;
    }

    public void setCnid(int cnid) {
        this.cnid = cnid;
    }

    @Basic
    @Column(name = "bid", nullable = false)
    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    @Basic
    @Column(name = "callnumber", nullable = false, length = 255)
    public String getCallnumber() {
        return callnumber;
    }

    public void setCallnumber(String callnumber) {
        this.callnumber = callnumber;
    }

    @Basic
    @Column(name = "copyid", nullable = false)
    public int getCopyid() {
        return copyid;
    }

    public void setCopyid(int copyid) {
        this.copyid = copyid;
    }

    @Basic
    @Column(name = "avaliable", nullable = false, length = 255)
    public String getAvaliable() {
        return avaliable;
    }

    public void setAvaliable(String avaliable) {
        this.avaliable = avaliable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Callnumber that = (Callnumber) o;

        if (cnid != that.cnid) return false;
        if (bid != that.bid) return false;
        if (copyid != that.copyid) return false;
        if (callnumber != null ? !callnumber.equals(that.callnumber) : that.callnumber != null) return false;
        if (avaliable != null ? !avaliable.equals(that.avaliable) : that.avaliable != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cnid;
        result = 31 * result + bid;
        result = 31 * result + (callnumber != null ? callnumber.hashCode() : 0);
        result = 31 * result + copyid;
        result = 31 * result + (avaliable != null ? avaliable.hashCode() : 0);
        return result;
    }
}
