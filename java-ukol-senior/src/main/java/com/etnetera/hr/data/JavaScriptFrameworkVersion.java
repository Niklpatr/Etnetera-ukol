package com.etnetera.hr.data;

import javax.persistence.*;

@Entity
public class JavaScriptFrameworkVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="versions", nullable=false)
    private JavaScriptFramework javaScriptFramework;

    @Column(length=20)
    private String version;

    @Column(length=10)
    private String deprecationDate;

    @Column
    private int hypeLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JavaScriptFramework getJavaScriptFramework() {
        return javaScriptFramework;
    }

    public void setJavaScriptFramework(JavaScriptFramework javaScriptFramework) {
        this.javaScriptFramework = javaScriptFramework;
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

    public JavaScriptFrameworkVersionDTO toDTO() {
        JavaScriptFrameworkVersionDTO dto = new JavaScriptFrameworkVersionDTO();
        dto.setId(id);
        dto.setJavaScriptFrameworkId(javaScriptFramework.getId());
        dto.setDeprecationDate(deprecationDate);
        dto.setHypeLevel(hypeLevel);
        dto.setVersion(version);

        return dto;
    }
}
