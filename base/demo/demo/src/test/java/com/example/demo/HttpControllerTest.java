package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-13 09:44
 * To change this template use File | Settings | File and Code Templates.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class HttpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.123").value("1231"));
    }

    @Test
    void testPost() throws Exception {
        // application/json 方式
        //Yst2ApiRequest1010 request = new Yst2ApiRequest1010();
        //request.setReqTraceNum("1234567890");
        //request.setSignNum("1234567890");
        //request.setMemberRole("1");
        //request.setName("张三");
        //request.setCerType("1");
        //request.setCerNum("123456789012345678");
        //request.setAcctNum("123456789012345678");
        //request.setPhone("13800000000");
        //request.setBindType("6");
        //request.setValidDate("0321");
        //request.setCvv2("123");

        //ObjectMapper mapper = new ObjectMapper();
        //String json = mapper.writeValueAsString(request);

        // application/x-www-form-urlencoded 方式
        MultiValueMap <String, String> params = new LinkedMultiValueMap <>();
        params.add("reqTraceNum", "1234567890");
        params.add("signNum", "1234567890");
        params.add("memberRole", "1");
        params.add("name", "张三");
        params.add("cerType", "1");
        params.add("cerNum", "123456789012345678");
        params.add("acctNum", "123456789012345678");
        params.add("phone", "13800000000");
        params.add("bindType", "6");
        params.add("validDate", "0321");
        params.add("cvv2", "123");

        mockMvc.perform(MockMvcRequestBuilders.post("/yst2/tm/applyPersonalRealNameBindCard1010")
                        //.contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept("application/json;charset=UTF-8")
                        //.content(json)
                        .params(params)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

}