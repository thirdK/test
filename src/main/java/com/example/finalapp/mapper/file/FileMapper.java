package com.example.finalapp.mapper.file;

import com.example.finalapp.dto.file.FileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    void insertFile(FileDto fileDto);

    void deleteFile(Long boardId);

    List<FileDto> selectList(Long boardId);

    List<FileDto> selectOldList();
}















