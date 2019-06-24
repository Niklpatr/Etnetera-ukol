package com.etnetera.hr.data;

public class JavaScriptFrameworkVersionDTO {
    private Long id;

    private Long javaScriptFrameworkId;

    private String version;

    private String deprecationDate;

    private int hypeLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJavaScriptFrameworkId() {
        return this.javaScriptFrameworkId;
    }

    public void setJavaScriptFrameworkId(Long javaScriptFrameworkId) {
        this.javaScriptFrameworkId = javaScriptFrameworkId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeprecationDate() {
        return deprecationDate;
    }

    public void setDeprecationDate(String deprecationDate) {
        this.deprecationDate = deprecationDate;
    }

    public int getHypeLevel() {
        return hypeLevel;
    }

    public void setHypeLevel(int hypeLevel) {
        this.hypeLevel = hypeLevel;
    }
}
