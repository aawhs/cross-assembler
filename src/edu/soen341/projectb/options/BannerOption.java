//Banner option implementation
package edu.soen341.projectb.options;

import java.io.File;

import static java.lang.System.exit;

public class BannerOption extends Option {
    //Set banner option and enable it
    public BannerOption() {
        super(new String[]{"-b"},"-banner");
        setEnable(true);
    }
    // Returns actual banner
    @Override
    public void process() {
        System.out.println("wcOO Version 1.2\n" +
                "Copyright (C) Concordia University 2020. All Rights Reserved.\n" +
                "Written by Ahmed Ali\n");

        exit(1);
    }

    //**
    @Override
    public void setFiles(File file) {

    }

}