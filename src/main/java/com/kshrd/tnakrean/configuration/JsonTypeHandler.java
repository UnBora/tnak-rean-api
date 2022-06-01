package com.kshrd.tnakrean.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import org.postgresql.util.PGobject;
import org.springframework.context.annotation.Configuration;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@MappedTypes(Object.class)
@MappedJdbcTypes(value = {JdbcType.OTHER})
@Configuration
public class JsonTypeHandler extends BaseTypeHandler {
    private static final PGobject jsonObject = new PGobject();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        jsonObject.setType("json");
        System.out.println("Parameter:" + parameter);
        try {
            jsonObject.setValue(new ObjectMapper().writeValueAsString(parameter));  //Converting java objects to json strings
        } catch (JsonProcessingException e) {
            System.out.println("Parameter:" + parameter);
            e.printStackTrace();
        }
        ps.setObject(i, jsonObject);
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        jsonObject.setType("json");
        try {
            jsonObject.setValue(new ObjectMapper().writeValueAsString(rs.getString(columnName)));
            System.out.println("json" + jsonObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("asdasdsa" + rs.getString(columnName));
        ObjectMapper mapper = new ObjectMapper();
        Object obj = null;
        try {
            obj = mapper.readValue(rs.getString(columnName), Object.class);
            System.out.println("Obj Type:" + obj.toString());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return rs.getString(columnName);                                 // Return to String
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex);
    }

}