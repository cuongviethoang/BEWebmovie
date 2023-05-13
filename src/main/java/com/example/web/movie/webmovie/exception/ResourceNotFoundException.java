package com.example.web.movie.webmovie.exception;

public class ResourceNotFoundException extends  RuntimeException{
    // extends RuntimeException dùng đ khai báo rằng ResourceNotFoundException l 1 ngoại lệ đợc sử dụng trong java
    // để xử lí các trường hợp đặc biệt

    // Để đảm bảo tính nhất quán và tương thích với các phiên bản Java.
    //  Biến serialVersionUID là  một số long được sử dụng để đảm bảo
    //  tính nhất quán giữa các phiên bản
    //  serialVersionUID được sử dụng để đảm bảo rằng mã hash của lớp không thay đổi khi lớp này được sửa đổi.
    //  Khi serialVersionUID được định nghĩa, nó sẽ được sử dụng để tạo ra một số duy nhất để đại diện cho phiên bản hiện tại
    //  của lớp. Khi lớp được tái định nghĩa, serialVersionUID sẽ giữ nguyên giá trị của phiên bản trước đó để đảm bảo
    //  tính tương thích ngược.
    //Vì vậy, trong đoạn mã trên, serialVersionUID được sử dụng để đảm bảo rằng phiên bản của lớp ResourceNotFoundException không thay đổi khi lớp này được sửa đổi trong tương lai.
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
