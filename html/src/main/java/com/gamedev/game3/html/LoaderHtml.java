package com.gamedev.game3.html;

import com.google.gwt.core.client.EntryPoint;
import playn.html.HtmlPlatform;
import com.gamedev.game3.core.Loader;

public class LoaderHtml implements EntryPoint {

  @Override public void onModuleLoad () {
    HtmlPlatform.Config config = new HtmlPlatform.Config();
    // use config to customize the HTML platform, if needed
    HtmlPlatform plat = new HtmlPlatform(config);
    plat.assets().setPathPrefix("game3/");
    new Loader(plat);
    plat.start();
  }
}
