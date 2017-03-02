package com.gamedev.game3.core.showcase;

import com.gamedev.game3.core.showcase.snake.SnakeDemo;
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Platform;
import playn.core.Touch;
import playn.scene.Pointer;
import playn.scene.SceneGame;
import react.Closeable;
import react.Signal;
import react.Slot;


import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Anton on 02.03.2017.
 */
public class Showcase extends SceneGame {
    private final Set<Key> backKeys = EnumSet.of(Key.ESCAPE, Key.BACK);
    private final Demo menuDemo = new Menu();
    private Demo activeDemo;
    private Closeable activeHandle = Closeable.Util.NOOP;
    private int activeStamp;

    public interface DeviceService {
        /** returns info on the device */
        String info();
    }

    public static abstract class Demo {
        public final String name;
        public Demo(String name) {
            this.name = name;
        }
        public abstract void create (Showcase game, Closeable.Set onClose);
    }

    public final DeviceService deviceService;

    /** a signal emitted when the device is rotated/ this should be part of the platform */
    public final Signal<Showcase> rotate = Signal.create();

    public final List<Demo> demos = new ArrayList<Demo>(); {
        //TODO add demos
        demos.add(new SnakeDemo());
    }

    public Showcase(Platform plat, DeviceService deviceService) {
        super(plat, 25);
        this.deviceService = deviceService;

        //wire up a layer pointer dispatcher
        new Pointer(plat, rootLayer, true);

        plat.input().keyboardEvents.connect(new Keyboard.KeySlot() {
            @Override
            public void onEmit(Keyboard.KeyEvent event) {
                if (event.down && backKeys.contains(event.key))
                    activateDemo(menuDemo);
            }
        });
        plat.input().touchEvents.connect(new Slot<Touch.Event[]>(){

            @Override
            public void onEmit(Touch.Event[] touches) {
                if (touches.length > 1 && touches[0].kind.isStart)
                    activateDemo(menuDemo);
            }
        });
        activateDemo(menuDemo);
    }

    public boolean shouldExitOnBack() {
        return (activeDemo == menuDemo) &&
                (plat.tick() - activeStamp) > 500L;
    }

    public void activateDemo(Demo demo) {
        activeHandle = Closeable.Util.close(activeHandle);
        activeDemo = demo;
        Closeable.Set onClose = new Closeable.Set();
        demo.create(this, onClose);
        activeHandle = onClose;
        activeStamp = plat.tick();
    }
}
