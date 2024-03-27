package com.example.demo.designpattern.bridge;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: kavanLi-R7000
 * @create: 2024-02-29 20:51
 * To change this template use File | Settings | File and Code Templates.
 */
@Slf4j
public class GG {
    /* fields -------------------------------------------------------------- */
    public void chase(MM mm) {
        //Gift g = new Book();
        Gift g = new WarmGift(new Flower());
        give(mm, g);
    }

    public void give(MM mm, Gift g) {
        if (log.isInfoEnabled()) {
            log.info("give {} to {}", g, mm);
        }
    }

    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */


    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
