package com.etnetera.hr.data;

import javax.validation.constraints.NotBlank;

public class JavaScriptFrameworkDTO {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String version;

    private String deprecationDate;

    private int hypeLevel;

    public JavaScriptFrameworkDTO() {
    }

    public JavaScriptFrameworkDTO(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "JavaScriptFramework [id=" + id + ", name=" + name + "]";
    }

}

