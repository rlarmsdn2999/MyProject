package org.zerock.mreview.controller;

import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.mreview.dto.UploadResultDTO;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class UploadController {
    @Value("${org.zerock.upload.path}")
    private String uploadPath;
    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles){
        List<UploadResultDTO> resultDTOList = new ArrayList<>();
        for(MultipartFile uploadFile: uploadFiles){
            if(uploadFile.getContentType().startsWith("image")==false){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);
            String folderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);
            try{
                uploadFile.transferTo(savePath);
                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator +
                        "s_" +uuid + "_"+fileName;
                File thumbnailFile = new File(thumbnailSaveName);
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100,100);
                resultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }
    private String makeFolder(){
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);
        File uploadPathFolder = new File(uploadPath, folderPath);
        if(uploadPathFolder.exists() == false){
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName, String size){
        ResponseEntity<byte[]> result = null;
        try{
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
            File file = new File(uploadPath + File.separator+srcFileName);
            if(size != null && size.equals("1")){
                file = new File(file.getParent(), file.getName().substring(2));
            }
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName){
        String srcFileName=null;
        try{
            srcFileName = URLDecoder.decode(fileName, "UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);
            boolean result = file.delete();
            File thumbnail = new File(file.getParent(), "s_"+file.getName());
            result = thumbnail.delete();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}