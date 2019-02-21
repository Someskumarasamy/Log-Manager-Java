package com.pmp.log_manager;

import java.io.File;

public class Refresh {
    Refresh(File dat[]) {
        Thread sik = new SearchInKey(dat);
        sik.setName("SearchInFileThread");
        sik.start();
    }

}
