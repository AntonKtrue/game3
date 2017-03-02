package com.gamedev.game3.java;

import playn.java.LWJGLPlatform;

import com.gamedev.game3.core.Loader;

public class LoaderJava {

  public static void main (String[] args) {
    LWJGLPlatform.Config config = new LWJGLPlatform.Config();
    // use config to customize the Java platform, if needed
    LWJGLPlatform plat = new LWJGLPlatform(config);
    new Loader(plat);
    plat.start();
  }
}
