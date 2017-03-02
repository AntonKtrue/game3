package com.gamedev.game3.core.showcase;

import playn.core.Key;
import playn.core.Keyboard;
import playn.scene.GroupLayer;
import react.Closeable;
import react.UnitSlot;
import tripleplay.ui.*;
import tripleplay.ui.layout.AxisLayout;

/**
 * Created by Anton on 02.03.2017.
 */
public class Menu extends Showcase.Demo{

    public Menu() {
        super("Menu");
    }

    @Override
    public void create(final Showcase game, Closeable.Set onClose) {
        GroupLayer layer = new GroupLayer();
        game.rootLayer.add(layer);
        onClose.add(layer);

        //create out ui manager and configure it to process pointer events
        Interface iface = new Interface(game.plat, game.paint);
        onClose.add(iface);

        //create our demo intreface
        final Root root = iface.createRoot(AxisLayout.vertical().gap(15),
                SimpleStyles.newSheet(game.plat.graphics()));
        root.setSize(game.plat.graphics().viewSize);
        root.addStyles(Style.BACKGROUND.is(Background.solid(0xFF99CCFF).inset(5)));
        layer.add(root.layer);

        Group buttons;
        root.add(new Label("PlayN Demos:4"),
                buttons = new Group(AxisLayout.vertical().offStretch()),
                new Label("ESC/BACK key or two-finger tap returns to menu from demo").addStyles(Style.TEXT_WRAP.is(true)),
                new Label("(renderer: " + game.plat.graphics().getClass().getSimpleName() + " " +
                game.plat.graphics().viewSize + ")"),
                new Label("(device: " + game.deviceService.info() + ")").addStyles(Style.TEXT_WRAP.is(true)));
        int key = 1;
        for (final Showcase.Demo demo : game.demos) {
            Button button = new Button(key++ + " - " + demo.name);
            buttons.add(button);
            button.clicked().connect(new UnitSlot() {
                @Override public void onEmit() {game.activateDemo(demo);}
            });
        }
        //wire up keyboard shortcuts
        onClose.add(game.plat.input().keyboardEvents.connect(
                new Keyboard.KeySlot() {
                    @Override
                    public void onEmit(Keyboard.KeyEvent event) {
                        int demoIndex = event.key.ordinal() - Key.K1.ordinal();
                        if (demoIndex >= 0 && demoIndex < game.demos.size()) {
                            game.activateDemo(game.demos.get(demoIndex));
                        }
                    }
                }));
        //resize our root if view rotates
        onClose.add(game.rotate.connect(new UnitSlot() {
            @Override
            public void onEmit() {
                root.setSize(game.plat.graphics().viewSize);
            }
        }));
    }
}
