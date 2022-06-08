package com.kshrd.tnakrean.repository.provider;

import com.kshrd.tnakrean.model.classmaterials.request.FolderRequest;
import org.apache.ibatis.jdbc.SQL;

public class FolderProvider {
    public String createFolder() {
        return new SQL() {{
            INSERT_INTO("folder");
            VALUES("folder_name,parent_id", "#{folder.folder_name},#{folder.parent_id}");
        }}.toString();
    }

    public String createFolderDetail(){
        return new SQL() {{
            INSERT_INTO("folder_detail");
            VALUES("folder_id,class_materials_detail_id,content",
                    "#{folder.folder_id},#{folder.class_materials_detail_id},#{folder.content," +
                            "jdbcType = OTHER,typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler}");
        }}.toString();
    }
}
