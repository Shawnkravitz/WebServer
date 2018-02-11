package com.homeautomation.webserver;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit4.*;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ResponseControllerTest {

    @Autowired
    private ResponseController controller;

    @Test
    public void controllerInit() {
        assertThat(controller).isNotNull();
    }
}
