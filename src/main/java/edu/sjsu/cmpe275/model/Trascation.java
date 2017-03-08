package edu.sjsu.cmpe275.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by ZHANG JOHN on 2016/12/17.
 */
@Entity
@Table(name = "trascation", schema = "cmpe275", catalog = "")
public class Trascation {
    private int tid;
    private int uid;
    private int bookid;
    private Timestamp bTime;
    private Timestamp rTime;
    private Timestamp eTime;
    private int copyid;

    @Id
    @Column(name = "tid", nullable = false)
    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
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
    @Column(name = "bookid", nullable = false)
    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    @Basic
    @Column(name = "b_time", nullable = false)
    public Timestamp getbTime() {
        return bTime;
    }

    public void setbTime(Timestamp bTime) {
        this.bTime = bTime;
    }

    @Basic
    @Column(name = "r_time", nullable = true)
    public Timestamp getrTime() {
        return rTime;
    }

    public void setrTime(Timestamp rTime) {
        this.rTime = rTime;
    }

    @Basic
    @Column(name = "e_time", nullable = false)
    public Timestamp geteTime() {
        return eTime;
    }

    public void seteTime(Timestamp eTime) {
        this.eTime = eTime;
    }

    @Basic
    @Column(name = "copyid", nullable = false)
    public int getCopyid() {
        return copyid;
    }

    public void setCopyid(int copyid) {
        this.copyid = copyid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trascation that = (Trascation) o;

        if (tid != that.tid) return false;
        if (uid != that.uid) return false;
        if (bookid != that.bookid) return false;
        if (copyid != that.copyid) return false;
        if (bTime != null ? !bTime.equals(that.bTime) : that.bTime != null) return false;
        if (rTime != null ? !rTime.equals(that.rTime) : that.rTime != null) return false;
        if (eTime != null ? !eTime.equals(that.eTime) : that.eTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tid;
        result = 31 * result + uid;
        result = 31 * result + bookid;
        result = 31 * result + (bTime != null ? bTime.hashCode() : 0);
        result = 31 * result + (rTime != null ? rTime.hashCode() : 0);
        result = 31 * result + (eTime != null ? eTime.hashCode() : 0);
        result = 31 * result + copyid;
        return result;
    }
}
