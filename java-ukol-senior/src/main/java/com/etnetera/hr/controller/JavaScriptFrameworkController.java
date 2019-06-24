package com.etnetera.hr.controller;

import com.etnetera.hr.data.JavaScriptFrameworkDTO;
import com.etnetera.hr.data.JavaScriptFrameworkVersion;
import com.etnetera.hr.data.JavaScriptFrameworkVersionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Simple REST controller for accessing application logic.
 *  
 * @author Etnetera
 *
 */
@RestController
public class JavaScriptFrameworkController {
	
	private final JavaScriptFrameworkRepository repository;

	@Autowired
	public JavaScriptFrameworkController(JavaScriptFrameworkRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/frameworks")
	public Iterable<JavaScriptFrameworkDTO> frameworks() {
		return ((Collection<JavaScriptFramework>) repository.findAll()).stream().map(JavaScriptFramework::toDTO).collect(Collectors.toList());
	}

	@PostMapping("/frameworks")
	@Valid
	public void create(@RequestBody JavaScriptFrameworkDTO javaScriptFrameworkDTO) {
		repository.save(new JavaScriptFramework(javaScriptFrameworkDTO));
	}

	@DeleteMapping("/frameworks/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@PostMapping("/frameworks/{id}")
	public void update(@PathVariable Long id, @RequestBody JavaScriptFrameworkDTO dto) {
		if (repository.existsById(id)) {
			JavaScriptFramework framework = repository.findById(id).get();
			framework.setName(dto.getName());

			List<JavaScriptFrameworkVersion> versions = framework.getVersions();
			if (versions == null) {
				versions = new ArrayList<>();
				framework.setVersions(versions);
			}
			Optional<JavaScriptFrameworkVersion> existingVersion = versions.stream().filter(version -> version.getVersion().equals(dto.getVersion())).findFirst();
			JavaScriptFrameworkVersion version;
			if (existingVersion.isPresent()) {
				version = existingVersion.get();
			} else {
				version = new JavaScriptFrameworkVersion();
				versions.add(version);
			}
			version.setDeprecationDate(dto.getDeprecationDate());
			version.setHypeLevel(dto.getHypeLevel());
			version.setVersion(dto.getVersion());
			version.setJavaScriptFramework(framework);
			versions.sort(Comparator.comparing(JavaScriptFrameworkVersion::getVersion));

			repository.save(framework);
		}
	}

	@GetMapping("/frameworks/{id}")
	public JavaScriptFrameworkDTO find(@PathVariable Long id) {
		if (repository.existsById(id)) {
			return repository.findById(id).get().toDTO();
		} else return null;
	}

	@GetMapping("/frameworks/{id}/versions")
	public Iterable<JavaScriptFrameworkVersionDTO> getVersions(@PathVariable Long id) {
		if (repository.existsById(id)) {
			return ((Collection<JavaScriptFrameworkVersion>) repository.findById(id).get().getVersions()).stream().map(JavaScriptFrameworkVersion::toDTO).collect(Collectors.toList());
		} else return null;
	}
}
