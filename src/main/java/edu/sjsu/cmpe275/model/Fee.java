package edu.sjsu.cmpe275.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;

/**
 * Created by fu on 12/6/16.
 */
@Entity
@Table(name = "fee", schema = "cmpe275", catalog = "")
public class Fee {
    private int fid;
    private Integer uid;
    private Integer fee;

    @Id
    @Column(name = "fid")
    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    @Basic
    @Column(name = "uid")
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "fee")
    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fee fee1 = (Fee) o;

        if (fid != fee1.fid) return false;
        if (uid != null ? !uid.equals(fee1.uid) : fee1.uid != null) return false;
        if (fee != null ? !fee.equals(fee1.fee) : fee1.fee != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fid;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (fee != null ? fee.hashCode() : 0);
        return result;
    }
}
