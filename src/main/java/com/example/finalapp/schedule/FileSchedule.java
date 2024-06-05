package com.example.finalapp.schedule;

import com.example.finalapp.dto.file.FileDto;
import com.example.finalapp.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileSchedule {
    private final FileService fileService;

    @Value("${file.dir}")
    private String fileDir;

    /*
    cron 표현식
    7 개의 정보를 표현하는 식
    초 분 시 일 월 요일 년도 (년도는 생략 가능)
    * : 와일드 카드 (매 번)
    ? : 설정값 없음 (일, 요일에서만 사용 가능)
    / : 증가 표현 0/15 -> 0부터 15마다 (0, 15, 30, 45, .....)

    0 0/5 * * * ? : 5분 마다
    0 0 12 * * ? : 매일 낮 12시 마다
     */

    @Scheduled(cron = "0 0 3 * * ?")
    public void checkFiles() {
        log.info("File Check!!!");
        log.info("------------------------------");

//        이전 파일 리스트를 뽑아온다.
        List<FileDto> oldList = fileService.findOldList();

//        List<Path> fileListPaths = new ArrayList<>();
//        for(FileDto dto : oldList){
//            String name = dto.getName();
//            String uuid = dto.getUuid();
//            String uploadPath = dto.getUploadPath();
//
//            Path path = Paths.get(fileDir, uploadPath, uuid + "_" + name);
//            fileListPaths.add(path);
//        }

//        이전 파일들의 전체 경로를 List<Path> 타입으로 저장한다.
        List<Path> fileListPaths = oldList.stream()
                .map(dto -> Paths.get(fileDir, dto.getUploadPath(), dto.getUuid() + "_" + dto.getName()))
                .collect(Collectors.toList());

        oldList.stream()
                .map(dto -> Paths.get(fileDir, dto.getUploadPath(), "th_" + dto.getUuid() + "_" + dto.getName()))
                .forEach(fileListPaths::add);

//        이전 파일들이 들어있는 경로(파일이름을 제외한)를 파일객체로 저장한다.
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String oldUploadPath = now.format(formatter);

        File directory = Paths.get(fileDir, oldUploadPath).toFile();

//        File 객체는 폴더안에 있는 모든 파일 목록을 불러오는 기능이 있다. -> listFiles()이다.
//        불러온 파일들을 file 매개변수로 받아 DB에서 가져온 fileListPaths 리스트에 존재하는지
//        검사하고 DB에 존재하지 않는 파일이면 삭제한다.
        File[] files = directory.listFiles(file -> !fileListPaths.contains(file.toPath()));

        if(files == null) { return; }

        for (File file : files) {
            log.info(file.getPath() + " delete!!!!");
            file.delete();
        }
    }

}








