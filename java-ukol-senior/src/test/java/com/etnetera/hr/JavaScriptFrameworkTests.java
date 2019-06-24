package com.etnetera.hr;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.data.JavaScriptFrameworkDTO;
import com.etnetera.hr.data.JavaScriptFrameworkVersion;
import com.etnetera.hr.data.JavaScriptFrameworkVersionDTO;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 * Class used for Spring Boot/MVC based tests.
 * 
 * @author Etnetera
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaScriptFrameworkTests {

    private static final String NAME = "Foo-gular";
    private static final String DIFFERENT_NAME = "PhewJS";
    private static final String VERSION = "1.0.0";
    private static final String NEWER_VERSION = "2.0.0";
    private static final int HYPE_LEVEL = 3;
    private static final int SLIGHTLY_MORE_HYPE = 4;
    private static final String DEPRECATION_DATE = "2019-06-24";
    private static final Long ID = 42L;

    @Autowired
    private JavaScriptFrameworkRepository javaScriptFrameworkRepository;

    private JavaScriptFrameworkController javaScriptFrameworkController;

    private JavaScriptFramework createJavaScriptFramework() {
        JavaScriptFramework javaScriptFramework = new JavaScriptFramework(NAME);

        JavaScriptFrameworkVersion version = new JavaScriptFrameworkVersion();
        version.setHypeLevel(HYPE_LEVEL);
        version.setDeprecationDate(DEPRECATION_DATE);
        version.setVersion(VERSION);
        version.setJavaScriptFramework(javaScriptFramework);
        List<JavaScriptFrameworkVersion> versions = new ArrayList<>();
        versions.add(version);
        javaScriptFramework.setVersions(versions);

        return javaScriptFramework;
    }

    @Before
    public void setUp() {
        javaScriptFrameworkController = new JavaScriptFrameworkController(javaScriptFrameworkRepository);
    }

    @Test
    @Transactional
    public void createTest() {
        JavaScriptFramework javaScriptFramework = createJavaScriptFramework();
        javaScriptFrameworkController.create(javaScriptFramework.toDTO());

        Assert.assertEquals(javaScriptFrameworkRepository.count(), 1);
        Iterable<JavaScriptFramework> frameworks = javaScriptFrameworkRepository.findAll();
        JavaScriptFramework first = frameworks.iterator().next();
        Assert.assertEquals(first.getName(), NAME);

        Assert.assertEquals(first.getVersions().size(), 1);
        Assert.assertEquals(first.getVersions().get(0).getVersion(), VERSION);
        Assert.assertEquals(first.getVersions().get(0).getHypeLevel(), HYPE_LEVEL);
        Assert.assertEquals(first.getVersions().get(0).getDeprecationDate(), DEPRECATION_DATE);
    }

    @Test
    @Transactional
    public void deleteTest() {
        JavaScriptFramework javaScriptFramework = createJavaScriptFramework();
        javaScriptFrameworkRepository.save(javaScriptFramework);

        Assert.assertEquals(javaScriptFrameworkRepository.count(), 1);

        javaScriptFrameworkController.delete(javaScriptFramework.getId());

        Assert.assertEquals(javaScriptFrameworkRepository.count(), 0);
    }

    @Test
    @Transactional
    public void findTest() {
        JavaScriptFramework javaScriptFramework = createJavaScriptFramework();
        javaScriptFrameworkRepository.save(javaScriptFramework);

        Assert.assertEquals(javaScriptFrameworkRepository.count(), 1);

        JavaScriptFrameworkDTO result = javaScriptFrameworkController.find(javaScriptFramework.getId());

        Assert.assertEquals(result.getId(), javaScriptFramework.getId());
        Assert.assertEquals(result.getName(), NAME);
        Assert.assertEquals(result.getVersion(), VERSION);
        Assert.assertEquals(result.getHypeLevel(), HYPE_LEVEL);

        result = javaScriptFrameworkController.find(ID);
        Assert.assertNull(result);
    }

    @Test
    @Transactional
    public void updateNameTest() {
        JavaScriptFramework javaScriptFramework = createJavaScriptFramework();
        javaScriptFrameworkRepository.save(javaScriptFramework);

        Assert.assertEquals(javaScriptFrameworkRepository.count(), 1);

        JavaScriptFrameworkDTO dto = javaScriptFramework.toDTO();
        dto.setName(DIFFERENT_NAME);
        javaScriptFrameworkController.update(javaScriptFramework.getId(), dto);

        Assert.assertEquals(javaScriptFrameworkRepository.count(), 1);
        JavaScriptFramework result = javaScriptFrameworkRepository.findAll().iterator().next();
        Assert.assertEquals(result.getId(), javaScriptFramework.getId());
        Assert.assertEquals(result.getName(), DIFFERENT_NAME);

        Assert.assertEquals(result.getVersions().size(), 1);
    }

    @Test
    @Transactional
    public void updateNameNonexistentIdTest() {
        JavaScriptFramework javaScriptFramework = createJavaScriptFramework();
        javaScriptFrameworkRepository.save(javaScriptFramework);

        Assert.assertEquals(javaScriptFrameworkRepository.count(), 1);

        JavaScriptFrameworkDTO dto = javaScriptFramework.toDTO();
        dto.setName(DIFFERENT_NAME);
        javaScriptFrameworkController.update(ID, dto);

        Assert.assertEquals(javaScriptFrameworkRepository.count(), 1);
        JavaScriptFramework result = javaScriptFrameworkRepository.findAll().iterator().next();
        Assert.assertEquals(result.getId(), javaScriptFramework.getId());
        Assert.assertEquals(result.getName(), NAME);
    }

    @Test
    @Transactional
    public void updateExistingVersionTest() {
        JavaScriptFramework javaScriptFramework = createJavaScriptFramework();
        javaScriptFrameworkRepository.save(javaScriptFramework);

        Assert.assertEquals(javaScriptFrameworkRepository.count(), 1);

        JavaScriptFrameworkDTO dto = javaScriptFramework.toDTO();
        dto.setHypeLevel(SLIGHTLY_MORE_HYPE);
        javaScriptFrameworkController.update(javaScriptFramework.getId(), dto);

        Assert.assertEquals(javaScriptFrameworkRepository.count(), 1);
        JavaScriptFramework result = javaScriptFrameworkRepository.findAll().iterator().next();
        Assert.assertEquals(result.getName(), NAME);
        Assert.assertEquals(result.getVersions().size(), 1);
        Assert.assertEquals(result.getVersions().get(0).getHypeLevel(), SLIGHTLY_MORE_HYPE);
    }

    @Test
    @Transactional
    public void updateAddVersionTest() {
        JavaScriptFramework javaScriptFramework = createJavaScriptFramework();
        javaScriptFrameworkRepository.save(javaScriptFramework);

        Assert.assertEquals(javaScriptFrameworkRepository.count(), 1);

        JavaScriptFrameworkDTO dto = javaScriptFramework.toDTO();
        dto.setVersion(NEWER_VERSION);
        dto.setHypeLevel(SLIGHTLY_MORE_HYPE);
        javaScriptFrameworkController.update(javaScriptFramework.getId(), dto);

        Assert.assertEquals(javaScriptFrameworkRepository.count(), 1);
        JavaScriptFramework result = javaScriptFrameworkRepository.findAll().iterator().next();
        Assert.assertEquals(result.getName(), NAME);
        Assert.assertEquals(result.getVersions().size(), 2);
        Assert.assertEquals(result.getVersions().get(0).getVersion(), VERSION);
        Assert.assertEquals(result.getVersions().get(0).getHypeLevel(), HYPE_LEVEL);
        Assert.assertEquals(result.getVersions().get(1).getVersion(), NEWER_VERSION);
        Assert.assertEquals(result.getVersions().get(1).getHypeLevel(), SLIGHTLY_MORE_HYPE);
    }

    @Test
    @Transactional
    public void getVersionsTest() {
        JavaScriptFramework javaScriptFramework = createJavaScriptFramework();
        JavaScriptFrameworkVersion version = new JavaScriptFrameworkVersion();
        version.setHypeLevel(SLIGHTLY_MORE_HYPE);
        version.setDeprecationDate(DEPRECATION_DATE);
        version.setVersion(NEWER_VERSION);
        version.setJavaScriptFramework(javaScriptFramework);
        javaScriptFramework.getVersions().add(version);
        javaScriptFrameworkRepository.save(javaScriptFramework);

        Iterable<JavaScriptFrameworkVersionDTO> versions = javaScriptFrameworkController.getVersions(javaScriptFramework.getId());
        Assert.assertEquals(((Collection<JavaScriptFrameworkVersionDTO>)versions).size(), 2);
        Iterator<JavaScriptFrameworkVersionDTO> iterator = versions.iterator();
        JavaScriptFrameworkVersionDTO versionDTO = iterator.next();
        Assert.assertEquals(versionDTO.getVersion(), VERSION);
        Assert.assertEquals(versionDTO.getDeprecationDate(), DEPRECATION_DATE);
        Assert.assertEquals(versionDTO.getHypeLevel(), HYPE_LEVEL);

        versionDTO = iterator.next();
        Assert.assertEquals(versionDTO.getVersion(), NEWER_VERSION);
        Assert.assertEquals(versionDTO.getDeprecationDate(), DEPRECATION_DATE);
        Assert.assertEquals(versionDTO.getHypeLevel(), SLIGHTLY_MORE_HYPE);
    }

}
