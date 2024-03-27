package com.example.demo.designpattern.decorator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        File f = new File("c:/work/test.data");
        FileOutputStream fos = new FileOutputStream(f);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write("http://www.mashibing.com");
        bw.flush();
        bw.close();
    }
}
