package com.example.finalapp.service.board;


import com.example.finalapp.dto.board.BoardListDto;
import com.example.finalapp.dto.board.BoardUpdateDto;
import com.example.finalapp.dto.board.BoardViewDto;
import com.example.finalapp.dto.board.BoardWriteDto;
import com.example.finalapp.dto.file.FileDto;
import com.example.finalapp.dto.page.Criteria;
import com.example.finalapp.mapper.board.BoardMapper;
import com.example.finalapp.mapper.file.FileMapper;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;
    private final FileMapper fileMapper;

//    application.properties 또는 application.yml에 저장해둔
//    file.dir 프로퍼티 값을 가져와서 아래의 필드에 넣어준다.
    @Value("${file.dir}")
    private String fileDir;

    public void registerBoard(BoardWriteDto boardWriteDto){
        boardMapper.insertBoard(boardWriteDto);
    }

    /**
     * 파일 저장 및 DB 등록 처리
     *
     * @param boardWriteDto 게시물 정보를 가진 DTO
     * @param files 여러 파일을 담은 리스트
     * @throws IOException
     */
    public void registerBoardWithFiles(BoardWriteDto boardWriteDto, List<MultipartFile> files) throws IOException{
        boardMapper.insertBoard(boardWriteDto);
        Long boardId = boardWriteDto.getBoardId();

        for (MultipartFile file : files) {
            if(file.isEmpty()){ break; }

            FileDto fileDto = saveFile(file);
            fileDto.setBoardId(boardId);
            fileMapper.insertFile(fileDto);
        }
    }

    /**
     * 실질적인 파일 저장을 담당하는 메서드
     *
     * @param file 저장할 파일 객체
     * @return boardId를 제외한 fileDto 반환
     * @throws IOException
     */
    public FileDto saveFile(MultipartFile file) throws IOException {
//        사용자가 올린 파일 이름(확장자를 포함한다.)
        String originalFilename = file.getOriginalFilename();
//        파일이름에 붙여줄 uuid 생성
        UUID uuid = UUID.randomUUID();
//        uuid와 파일이름을 합쳐준다.
        String systemName = uuid.toString() + "_" + originalFilename;

//        상위 경로와 하위 경로를 합쳐준다.
        File uploadPath = new File(fileDir, getUploadPath());

//        경로가 존재하지 않는다면(폴더가 만들어지지 않았다면)
        if(!uploadPath.exists()){
//            경로에 필요한 모든 폴더를 생성한다.
            uploadPath.mkdirs();
        }

//        전체 경로와 파일이름을 연결한다.
        File uploadFile = new File(uploadPath, systemName);

//        매개변수로 받은 Multipart 객체가 가진 파일을 우리가 만든 경로와 이름으로 저장한다.
        file.transferTo(uploadFile);

//        ++++++썸네일 저장+++++++
        String contentType = Files.probeContentType(uploadFile.toPath());

//        이미지 파일인 경우에만 처리하는 조건식
        if(contentType.startsWith("image")){
            Thumbnails.of(uploadFile)
                    .size(300, 200)
                    .toFile(new File(uploadPath, "th_" + systemName));
        }
//        +++++++++++++

        FileDto fileDto = new FileDto();
        fileDto.setUuid(uuid.toString());
        fileDto.setName(originalFilename);
        fileDto.setUploadPath(getUploadPath());

        return fileDto;
    }

    private String getUploadPath(){
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }





    public void removeBoard(Long boardId) {
        List<FileDto> fileList = fileMapper.selectList(boardId);
        fileMapper.deleteFile(boardId);
        boardMapper.deleteBoard(boardId);

        for (FileDto file : fileList) {
            File target = new File(fileDir, file.getUploadPath() + "/" + file.getUuid() + "_" + file.getName());
            File thumbnail = new File(fileDir, file.getUploadPath() + "/th_" + file.getUuid() + "_" + file.getName());

            if(target.exists()){
                target.delete();
            }

            if (thumbnail.exists()){
                thumbnail.delete();
            }
        }
    }

    public void modifyBoard(BoardUpdateDto boardUpdateDto, List<MultipartFile> files) throws IOException{
        boardMapper.updateBoard(boardUpdateDto);
        Long boardId = boardUpdateDto.getBoardId();

        fileMapper.deleteFile(boardId);

        for (MultipartFile file : files) {
            if(file.isEmpty()){ break; }

            FileDto fileDto = saveFile(file);
            fileDto.setBoardId(boardId);
            fileMapper.insertFile(fileDto);
        }
    }

    public BoardViewDto findById(Long boardId) {
        return boardMapper.selectById(boardId)
                .orElseThrow(() -> new IllegalStateException("유효하지 않은 게시물 번호"));
    }

    public List<BoardListDto> findAll(){
        return boardMapper.selectAll();
    }

    public List<BoardListDto> findAllPage(Criteria criteria){
        return boardMapper.selectAllPage(criteria);
    }

    public int findTotal(){
        return boardMapper.selectTotal();
    }
}









