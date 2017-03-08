package edu.sjsu.cmpe275.model;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by ZHANG JOHN on 2016/12/17.
 */
@Entity
@Table(name = "waitlist", schema = "sql3148013", catalog = "")
public class Waitlist {
    private int wid;
    private int uid;
    private int bid;
    private Timestamp eTime;
    private Integer copyid;

    @Id
    @Column(name = "wid", nullable = false)
    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    @Basic
    @Column(name = "uid", nullable = false)
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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
    @Column(name = "e_time", nullable = true)
    public Timestamp geteTime() {
        return eTime;
    }

    public void seteTime(Timestamp eTime) {
        this.eTime = eTime;
    }

    @Basic
    @Column(name = "copyid", nullable = true)
    public Integer getCopyid() {
        return copyid;
    }

    public void setCopyid(Integer copyid) {
        this.copyid = copyid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Waitlist waitlist = (Waitlist) o;

        if (wid != waitlist.wid) return false;
        if (uid != waitlist.uid) return false;
        if (bid != waitlist.bid) return false;
        if (eTime != null ? !eTime.equals(waitlist.eTime) : waitlist.eTime != null) return false;
        if (copyid != null ? !copyid.equals(waitlist.copyid) : waitlist.copyid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = wid;
        result = 31 * result + uid;
        result = 31 * result + bid;
        result = 31 * result + (eTime != null ? eTime.hashCode() : 0);
        result = 31 * result + (copyid != null ? copyid.hashCode() : 0);
        return result;
    }
}
