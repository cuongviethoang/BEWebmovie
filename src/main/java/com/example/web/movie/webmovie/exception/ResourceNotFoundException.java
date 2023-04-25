package com.example.web.movie.webmovie.exception;

public class ResourceNotFoundException extends  RuntimeException{

    // Để đảm bảo tính nhất quán và tương thích với các phiên bản Java.
    //  Biến serialVersionUID là  một số long được sử dụng để đảm bảo
    //  tính nhất quán giữa các phiên bản
    // khi đối tượng được serialize và deserialize. Nó cũng được sử dụng để
    // đảm bảo tính nhất quán giữa các phiên bản của lớp khi được chia sẻ giữa
    // các ứng dụng.
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
