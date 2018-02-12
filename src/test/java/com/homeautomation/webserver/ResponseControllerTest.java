package com.homeautomation.webserver;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.*;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;

@WebMvcTest(ResponseController.class)
@RunWith(SpringRunner.class)
public class ResponseControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ResponseController responseController;

    private static final String NAME = "node1";
    private static final String STATE = "true";
    private static final String DESCRIPTION = "node one";
    private Node node;

    @Before
    public void setup() {
        node = new Node(NAME, STATE, DESCRIPTION);
    }

    @Test
    public void controllerInit() {
        assertThat(responseController).isNotNull();
    }

    @Test
    public void getNodes() throws Exception {
        Node node1 = new Node(NAME, STATE, DESCRIPTION);

        List<Node> allNodes = singletonList(node1);
        given(responseController.findAllNodes()).willReturn(allNodes);

        mvc.perform(get("/nodes")
                    .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].name", is(node1.getName())))
                    .andExpect(jsonPath("$[0].state", is(node1.getState())))
                    .andExpect(jsonPath("$[0].description", is(node1.getDescription())));
    }
}