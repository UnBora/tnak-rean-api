package com.kshrd.tnakrean.model.classmaterials.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderContent {
    String file_name;
    String file;
}
