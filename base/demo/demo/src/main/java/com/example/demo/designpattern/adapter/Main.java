package com.example.demo.designpattern.adapter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: kavanLi-R7000
 * @create: 2024-02-29 19:56
 * To change this template use File | Settings | File and Code Templates.
 */
@Slf4j
public class Main {
    /* fields -------------------------------------------------------------- */
    public static void main(String[] args) throws InterruptedException {

        try (FileInputStream fileInputStream = new FileInputStream("C:\\FurMark_0001.log");
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
             BufferedReader reader = new BufferedReader(inputStreamReader)
        ) {
            String line = reader.readLine();
            while (line != null && !line.equals("")) {
                line = reader.readLine();
                if (log.isInfoEnabled()) {
                    log.info("{}", line);
                }
            }
        } catch (Exception e) {
            log.warn("{}", e);
        }
        //Thread.sleep(10000);
    }

    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */


    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
