package com.gamedev.game3.html;

import com.gamedev.game3.core.Arcanoid;
import com.gamedev.game3.core.HelloGame;
import com.gamedev.game3.core.showcase.Showcase;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import playn.html.HtmlPlatform;
import com.gamedev.game3.core.Reversi;

import java.io.IOException;

public class LoaderHtml implements EntryPoint {

  @Override public void onModuleLoad () {
    HtmlPlatform.Config config = new HtmlPlatform.Config();
    // use config to customize the HTML platform, if needed
    HtmlPlatform plat = new HtmlPlatform(config);
    plat.assets().setPathPrefix("game3/");
    plat.graphics().setSize(1024, 640);
    //new Reversi(plat);
    //new Arcanoid(plat);
   // new HelloGame(plat);
    new Showcase(plat, new Showcase.DeviceService() {
      @Override
      public String info() {
        return "HTML [userAgent=" + Window.Navigator.getUserAgent() + "]";
      }
    });
    plat.start();
  }
}
