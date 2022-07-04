package com.kshrd.tnakrean.model.classmaterials.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCountResponse {
   private Integer class_material_id;
   private Integer total_comment;
}
