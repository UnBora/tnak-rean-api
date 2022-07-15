package com.kshrd.tnakrean;

import com.kshrd.tnakrean.model.classmaterials.response.CourseByStudentIdResponse;
import com.kshrd.tnakrean.repository.ClassMaterialRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@MybatisTest
@Import(DataSourceConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
public class ClassMaterialUnitTest {

    @Autowired
    private ClassMaterialRepository classMaterialRepository;

    @Test
    public void canGetCourseByStudentId(){
        List<CourseByStudentIdResponse> courseByStudentId = classMaterialRepository.getCourseByStudentId(1);
        assertThat(courseByStudentId.size()).isGreaterThan(0);
    }
}
