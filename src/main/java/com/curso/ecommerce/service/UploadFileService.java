package com.curso.ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadFileService {

    private String folder = "images//";

    public String saveImage(MultipartFile file) throws IOException {

//        Validar si viene la imagen
        if(!file.isEmpty()){

//            Pasar la imagen a bytes para poder enviarla del servidor al clietne
            byte[] bytes = file.getBytes();

//            Mapeando la ruta donde se va alojar la imagen
            Path path = Paths.get(folder + file.getOriginalFilename());

//            Escribir en la ruta
            Files.write(path, bytes);

            return file.getOriginalFilename();

        }

        return "default.jpg";
    }


    public void deleteImage(String nombre){

        String ruta = "images//";

        File file = new File(ruta + nombre);

        file.delete();

    }
}
