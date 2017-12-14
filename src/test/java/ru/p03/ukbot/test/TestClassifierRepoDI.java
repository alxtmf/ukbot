/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import ru.p03.classifier.model.ClassifierRepository;
import ru.p03.ukbot.main.AppEnv;
import ru.p03.ukbot.model.ClsRole;

/**
 *
 * @author altmf
 */
public class TestClassifierRepoDI {

    private Logger log = Logger.getLogger(TestClassifierRepoDI.class.getName());

    public TestClassifierRepoDI() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    //@Ignore
    @Test
    public void testClassifierRepo() {
        AppEnv appEnv = new AppEnv();
        appEnv.init();
        Injector injector = Guice.createInjector(appEnv);
        ClassifierRepository repo = injector.getInstance(ClassifierRepository.class);
        List<ClsRole> list = repo.find(ClsRole.class);
        list.stream().forEach(r -> log.log(Level.SEVERE, r.getName()));
    }
}
