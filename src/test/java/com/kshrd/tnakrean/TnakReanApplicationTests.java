package com.kshrd.tnakrean;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
//@RequiredArgsConstructor
//@AutoConfigureMockMvc
class TnakReanApplicationTests {

//    @Autowired
//    private MockMvc mvc;

    @Test
    void contextLoads() {
    }

//    @Test
//    void getCourseByStudentId() throws Exception {
//        mvc.perform(get("/api/v1/classMaterial/get-course-by-studentId")
//                        .contentType("application/json")
//                        .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYWxlbiIsImlhdCI6MTY1Nzg2MzYxOCwiZXhwIjoxNjU3OTUwMDE4fQ.yfPLrWSlD4rM5VF-D21lsR9719kX8wM_9U9N-ESqmVVPRFJDu0sEUdyC_dm0Rn3Jt-gap-gvsqn5AZjen3WO8g")
//                )
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
////                .andExpect((ResultMatcher) jsonPath("$[0].responseCode", is("404")));
//    }

}
