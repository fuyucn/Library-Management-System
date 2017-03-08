package edu.sjsu.cmpe275.model;

import javax.persistence.*;

/**
 * Created by fu on 12/6/16.
 */
@Entity
@Table(name = "action", schema = "cmpe275", catalog = "")
public class Action {
    private int id;
    private int bid;
    private int uid;
    private String action;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "bid")
    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    @Basic
    @Column(name = "uid")
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "action")
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Action action1 = (Action) o;

        if (id != action1.id) return false;
        if (bid != action1.bid) return false;
        if (uid != action1.uid) return false;
        if (action != null ? !action.equals(action1.action) : action1.action != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + bid;
        result = 31 * result + uid;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }
}
