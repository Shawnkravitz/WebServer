package com.homeautomation.webserver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.json.*;
import org.springframework.boot.test.json.*;
import org.springframework.test.context.junit4.*;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;


@JsonTest
@RunWith(SpringRunner.class)
public class JsonTests {

    @Autowired
    private JacksonTester<Node> json;

    private static final String NAME = "node1";
    private static final String STATE = "true";

    private Node node;
    @Before
    public void setup() {
        node = new Node(NAME, STATE);
    }

    @Test
    public void nameSerializes() throws IOException {
        assertThat(this.json.write(node))
                .extractingJsonPathStringValue("@.name")
                .isEqualTo(NAME);
    }
}
