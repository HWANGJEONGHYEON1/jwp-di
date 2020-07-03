package core.di.factory;

import core.annotation.Repository;
import core.annotation.Service;
import core.annotation.web.Controller;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.QnaController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DefaultBeanFactoryTest {

    private static final Logger log = LoggerFactory.getLogger(DefaultBeanFactoryTest.class);

    private DefaultBeanFactory defaultBeanFactory;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setup() {
        final BeanScanner beanScanner = new BeanScanner("core.di.factory.example");
        final Set<Class<?>> rawClasses = beanScanner.loadClasses(Controller.class, Service.class, Repository.class);
        assertThat(rawClasses).isNotNull();
        log.debug("classes: {}", rawClasses);
        defaultBeanFactory = new DefaultBeanFactory(rawClasses);
    }

    @Test
    public void di() throws Exception {
        QnaController qnaController = defaultBeanFactory.getBean(QnaController.class);

        assertNotNull(qnaController);
        assertNotNull(qnaController.getQnaService());

        MyQnaService qnaService = qnaController.getQnaService();
        assertNotNull(qnaService.getUserRepository());
        assertNotNull(qnaService.getQuestionRepository());
    }

}