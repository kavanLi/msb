package com.msb.controller;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;

@RestController
public class FileController {
    @RequestMapping("/single")
    public Mono<String> singleFile(@RequestPart("file")Mono<FilePart> file){
        return file.map(filePart -> {
            Path tempFile = null;
            try {
                 tempFile = Files.createTempFile("file-", filePart.filename());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("文件路径：" + tempFile.toAbsolutePath());
            //异步文件channel
            AsynchronousFileChannel channel = null;
            try {
                //打开指定文件写操作channel
                channel = AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);
            } catch (IOException e) {
                e.printStackTrace();
            }

            DataBufferUtils.write(filePart.content(),channel,0)
                    .doOnNext(System.out::println)
                    .doOnComplete(()->{
                        System.out.println("文件拷贝完成");
                    }).subscribe();
            // 封装了我们的文件信息
            return tempFile;
        }).map(tmp -> tmp.toFile())
                .flatMap(fileSingle -> file.map(FilePart::filename));
    }

    @RequestMapping("/multi")
    public Mono<List<String>> multiFiles(@RequestPart("file") Flux<FilePart> filePartFlux){
        return filePartFlux.map(filePart -> {
            Path tempFile = null;
            try {
                 tempFile = Files.createTempFile("mfile-", filePart.filename());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("文件路径：" + tempFile.toAbsolutePath());
            // 底层用的是一个零拷贝
            filePart.transferTo(tempFile.toFile());
            return tempFile;
        }).map(file -> file.toFile())
                .flatMap(fileSingle ->filePartFlux.map(FilePart::filename)).collectList();
    }
}
