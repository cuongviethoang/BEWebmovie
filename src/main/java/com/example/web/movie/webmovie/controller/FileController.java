package com.example.web.movie.webmovie.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin
@RestController
@RequestMapping("/api/file")
public class FileController {

    private final Path root = Paths.get("src/main/resources/static/Pics");

    // http://localhost:8081/api/file/getImg
    @GetMapping(
            value = "getImg",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<byte[]> getImage(@RequestParam String path) throws IOException {
        ClassPathResource imgFile = new ClassPathResource("static/Pics/" + path);


        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }

    // http://localhost:8081/api/file/upload
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam(value = "files", required = false) MultipartFile[] files) {

        String filename = "";
        for(MultipartFile file: files) {
            save(file);
            filename += file.getOriginalFilename() + ",";
        }
        return ResponseEntity.ok(filename.substring(0, filename.length()-1));
    }

    public void save(MultipartFile file) { // MultipartFile là một interface trong Spring Framework, được sử dụng để xử lý các tệp tin được tải lên từ người dùng.
        try {
            if(!this.root.resolve(file.getOriginalFilename()).toFile().exists()) { // điều kiện kiểm tra nếu tên tệp tin được tải lên đã tồn tại trong root
                // Phương thức resolve của đối tượng Path được sử dụng để tạo đường dẫn đầy đủ của tệp tin, kết hợp với đường dẫn của root
                // file.getOriginalFilename() là một phương thức của đối tượng MultipartFile trong Spring Framework, được sử dụng để trả về tên gốc của tệp tin được tải lên.
                // Phương thức toFile() được sử dụng để chuyển đổi đường dẫn của tệp tin thành đối tượng File.

                InputStream in = file.getInputStream();

                Files.copy(in, this.root.resolve(file.getOriginalFilename()));
                // Files.copy dùng để sao chép dữ liệu từ inputstream vào đường dẫn được gán cho cho biến root
                // đối tượng InputStream là một lớp trừu tượng được sử dụng để đọc dữ liệu byte từ một nguồn dữ liệu.

                in.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);

            //đối tượng Resource được sử dụng để đại diện cho một tài nguyên (resource), có thể là một tệp tin,
            // một đường dẫn URL hoặc bất kỳ tài nguyên nào khác trong ứng dụng của bạn.
            //UrlResource  để đại diện cho một tài nguyên được định vị bởi một URL.
            Resource resource = new UrlResource(file.toUri()); //  chuyển đổi đường dẫn file thành một đối tượng URI (Uniform Resource Identifier) trong định dạng chuẩn.

            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new RuntimeException("counld not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
