package com.app.data.document;

import com.app.core.base.BaseDocument;

public class Book extends BaseDocument {
    private static final long serialVersionUID = 6368189789098970987L;

    private String name;

    private String pwd;

    private String address;

    public Book(String name, String pwd, String address) {
        this.name = name;
        this.pwd = pwd;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}