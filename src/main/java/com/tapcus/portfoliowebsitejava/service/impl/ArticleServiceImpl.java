package com.tapcus.portfoliowebsitejava.service.impl;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.tapcus.portfoliowebsitejava.dao.ArticleDao;
import com.tapcus.portfoliowebsitejava.dao.MemberDao;
import com.tapcus.portfoliowebsitejava.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

import static com.google.cloud.storage.BlobInfo.newBuilder;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleDao articleDao;

    @Override
    public Integer uploadArticle(Integer memberId, String title, String introduction, String content, MultipartFile cover, String git_file_path) throws IOException {

        // 上傳圖片到 firebase，返回 url
        String coverUrl = upload(cover);
        return articleDao.createArticle(memberId, title, introduction, content, coverUrl, git_file_path);

    }

    // 上傳整合
    public String upload(MultipartFile multipartFile) throws IOException {
        try {
            String fileName = multipartFile.getOriginalFilename();                        // to get original file name
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.

            File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
            String TEMP_URL = this.uploadFile(file, fileName);                                   // to get uploaded file link
            file.delete();                                                                // to delete the copy of uploaded file stored in the project folder
            return TEMP_URL;                     // Your customized response
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("檔案上傳發生錯誤");
        }
    }

    // 上傳主體，最後返回下載路徑
    private String uploadFile(File file, String fileName) throws IOException {
        // 你的 bucket name
        BlobId blobId = BlobId.of("fileupload-33ca0.appspot.com", fileName);
        BlobInfo blobInfo = newBuilder(blobId).setContentType("media").build();

        File temp = new File("src\\main\\resources\\firebase.json");
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(temp));
        System.out.println(credentials);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format("https://firebasestorage.googleapis.com/v0/b/fileupload-33ca0.appspot.com/o/%s?alt=media", URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    // MultipartFile 轉 File
    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    // 獲取副檔名
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
