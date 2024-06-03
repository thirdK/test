package com.example.finalapp.dto.file;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
public class FileDto {
    private Long fileId;
    private String name;
    private String uploadPath;
    private String uuid;
    private Long boardId;
}
