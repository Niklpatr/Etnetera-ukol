package com.etnetera.hr.data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 * 
 * @author Etnetera
 *
 */
@Entity
public class JavaScriptFramework {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Column(nullable = false, length = 30)
	private String name;

	@OneToMany(mappedBy="javaScriptFramework", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<JavaScriptFrameworkVersion> versions;
	
	public JavaScriptFramework() {
	}

	public JavaScriptFramework(String name) {
		this.name = name;
	}

	public JavaScriptFramework(JavaScriptFrameworkDTO dto) {
		name = dto.getName();
		id = dto.getId();

		JavaScriptFrameworkVersion version = new JavaScriptFrameworkVersion();
		version.setDeprecationDate(dto.getDeprecationDate());
		version.setHypeLevel(dto.getHypeLevel());
		version.setVersion(dto.getVersion());
		version.setJavaScriptFramework(this);
		versions = new ArrayList<>();
		versions.add(version);
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

	public List<JavaScriptFrameworkVersion> getVersions() {
		return versions;
	}

	public void setVersions(List<JavaScriptFrameworkVersion> versions) {
		this.versions = versions;
	}

	public JavaScriptFrameworkDTO toDTO() {
		JavaScriptFrameworkDTO javaScriptFrameworkDTO = new JavaScriptFrameworkDTO(name);
		javaScriptFrameworkDTO.setId(id);
		if (!versions.isEmpty()) {
			JavaScriptFrameworkVersion latestVersion = versions.get(versions.size() - 1);
			javaScriptFrameworkDTO.setVersion(latestVersion.getVersion());
			javaScriptFrameworkDTO.setDeprecationDate(latestVersion.getDeprecationDate());
			javaScriptFrameworkDTO.setHypeLevel(latestVersion.getHypeLevel());
		}
		return javaScriptFrameworkDTO;
	}

	@Override
	public String toString() {
		return "JavaScriptFramework [id=" + id + ", name=" + name + "]";
	}
		
}
