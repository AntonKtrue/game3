package com.gamedev.game3.core.showcase.sprites;

import com.gamedev.game3.core.showcase.Showcase;
import playn.core.Clock;
import playn.core.Image;
import playn.core.Platform;
import playn.core.Sound;
import playn.scene.GroupLayer;
import playn.scene.ImageLayer;
import playn.scene.Pointer;
import pythagoras.f.IDimension;
import react.Closeable;
import react.Slot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton on 02.03.2017.
 */
public class SpritesDemo extends Showcase.Demo {
    public SpritesDemo () {super("Sprites");}

    @Override
    public void create(final Showcase game, Closeable.Set onClose) {
        final GroupLayer layer = new GroupLayer();
        game.rootLayer.add(layer);
        onClose.add(layer);

        final Sound ding = game.plat.assets().getSound("images/showcase/sprites/ding");
        Image bgImage = game.plat.assets().getImage("images/showcase/background.png");
        final IDimension viewSize = game.plat.graphics().viewSize;
        ImageLayer bg = new ImageLayer(bgImage).setSize(viewSize);
        layer.add(bg);

        final List<Pea> peas = new ArrayList<>();

        bg.events().connect(new Pointer.Listener() {
            @Override
            public void onStart(Pointer.Interaction iact) {
                peas.add(newPea(game.plat, layer, ding, viewSize.width()/2, viewSize.height()/2));
            }
        });

        peas.add(newPea(game.plat, layer, ding, viewSize.width() / 2, viewSize.height() / 2));

        onClose.add(game.paint.connect(new Slot<Clock>() {
            @Override
            public void onEmit(Clock clock) {
                for (Pea pea : peas)
                    pea.update(clock);
            }
        }));

    }
    private Pea newPea(Platform plat, GroupLayer layer, Sound ding, float x, float y) {
        ding.play();
        return new Pea(plat, layer, x ,y);
    }
}
