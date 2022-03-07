package fr.vertours.buddtwo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IndexControllerTest {

    @Autowired
    private IndexController classUnderTest;

    @Test
    void showBuddyIndexPage() {
        ModelAndView mvActual = classUnderTest.showBuddyIndexPage();
        assertEquals("index", mvActual.getViewName());
    }
}