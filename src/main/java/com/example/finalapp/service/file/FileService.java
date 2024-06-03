package com.example.finalapp.service.file;

import com.example.finalapp.dto.file.FileDto;
import com.example.finalapp.mapper.file.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FileService {
    private final FileMapper fileMapper;

    public void registerFile(FileDto fileDto){
        fileMapper.insertFile(fileDto);
    }

    public void removeFile(Long boardId) {
        fileMapper.deleteFile(boardId);
    }

    public List<FileDto> findList(Long boardId) {
        return fileMapper.selectList(boardId);
    }

    public List<FileDto> findOldList(){
        return fileMapper.selectOldList();
    }
}















