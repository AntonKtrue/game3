package com.gamedev.game3.core.showcase.snake;

import com.gamedev.game3.core.showcase.Showcase;
import playn.core.*;
import playn.scene.GroupLayer;
import playn.scene.ImageLayer;
import pythagoras.f.Transform;
import react.Closeable;
import react.Slot;

/**
 * Created by Anton on 02.03.2017.
 */
public class SnakeDemo extends Showcase.Demo {

    private Key direction = Key.UNKNOWN;
    private Key direction_red = Key.UNKNOWN;
    private final Sound click;
    public SnakeDemo(final Showcase game) {
        super("SnakeW/A/S/DandArrows");
        this.click = game.plat.assets().getSound("sounds/click");
    }
    @Override
    public void create(final Showcase game, Closeable.Set onClose) {
        //create a group layer to hold everything
        GroupLayer layer = new GroupLayer();
        game.rootLayer.add(layer);
        onClose.add(layer);

        //create and add background image layer
        Image bgImage = game.plat.assets().getImage("images/showcase/background.png");
        layer.add(new ImageLayer(bgImage).setSize(game.plat.graphics().viewSize).setDepth(-1));

                        //create our snake segments
        final Image segImage = game.plat.assets().getImage("images/showcase/sprites/pea.png");
        final Image segImageRed = game.plat.assets().getImage("images/showcase/sprites/pea_red.png");
        final ImageLayer[] segments = new ImageLayer[25];
        final ImageLayer[] segments_red = new ImageLayer[25];
        for (int ii = 0; ii < segments.length; ii++) {
            segments[ii] = new ImageLayer(segImage);
            segments_red[ii] = new ImageLayer(segImageRed);
            segments[ii].setDepth(50);
            segments_red[ii].setDepth(50);
            layer.add(segments[ii]);
            layer.add(segments_red[ii]);
        }
        Transform t = segments[0].transform();
        t.setTy(10);
        t.setTx(10);
        t = segments_red[0].transform();
        t.setTy(600);
        t.setTx(950);

        onClose.add(game.plat.input().keyboardEvents.connect(new Keyboard.KeySlot() {
            @Override
            public void onEmit(Keyboard.KeyEvent event) {

                if(event.key.equals(Key.UP) ||
                        event.key.equals(Key.DOWN) ||
                        event.key.equals(Key.RIGHT) ||
                        event.key.equals(Key.LEFT))
                    direction = event.key;
                if(event.key.equals(Key.W) ||
                        event.key.equals(Key.A) ||
                        event.key.equals(Key.S) ||
                        event.key.equals(Key.D))
                    direction_red = event.key;
            }
        }));

        onClose.add(game.paint.connect(new Slot<Clock>() {
            private float dx = 5, dy = 5, dd = 1;
            @Override
            public void onEmit(Clock clock) {

                                        //the tail segments play follow the  leader
                for (int ii = segments.length-1; ii>0; ii--) {
                    ImageLayer cur = segments[ii], prev = segments[ii-1];
                    ImageLayer cur_red = segments_red[ii], prev_red = segments_red[ii-1];
                    Transform t1 = cur.transform(), t2 = prev.transform();
                    Transform t1_red = cur_red.transform(), t2_red = prev_red.transform();
                    t1.setTx(t2.tx());
                    t1.setTy(t2.ty());
                    t1.setUniformScale(t2.uniformScale());
                    t1_red.setTx(t2_red.tx());
                    t1_red.setTy(t2_red.ty());
                    t1_red.setUniformScale(t2_red.uniformScale());
                    cur.setDepth(prev.depth());
                    cur_red.setDepth(prev_red.depth());
                }

                                    //and  the head segment leads the way
                ImageLayer first = segments[0];
                Transform t = first.transform();
                float nx, ny;
                float nx_red, ny_red;
                switch (direction) {
                    case UP:
                        nx = t.tx();
                        ny = t.ty() - dy;
                        if(ny < 0) {
                            ny = t.ty();
                            direction = Key.DOWN;
                            click.play();
                        }
                        break;
                    case RIGHT:
                        nx = t.tx() + dx;
                        ny = t.ty();
                        if(nx > game.plat.graphics().viewSize.width()-37)
                        {
                            nx = t.tx();
                            direction = Key.LEFT;
                            click.play();
                        }
                        break;
                    case DOWN:
                        nx = t.tx();
                        ny = t.ty() + dy;
                        if(ny > game.plat.graphics().viewSize.height()-37)
                        {
                            ny = t.ty();
                            direction = Key.UP;
                            click.play();
                        }
                        break;
                    case LEFT:
                        nx = t.tx() - dy;
                        ny = t.ty();
                        if(nx < 0)
                        {
                            nx = t.tx();
                            direction = Key.RIGHT;
                            click.play();
                        }
                        break;
                        default:
                            nx = t.tx();
                            ny = t.ty();
                }
                t.setTx(nx);
                t.setTy(ny);
                ImageLayer first_red = segments_red[0];
                Transform t_red = first_red.transform();
                switch (direction_red) {
                    case W:
                        nx_red = t_red.tx();
                        ny_red = t_red.ty() - dy;
                        if(ny_red < 0) {
                            ny_red = t_red.ty();
                            direction_red = Key.S;
                            click.play();
                        }

                        break;
                    case D:
                        nx_red = t_red.tx() + dx;
                        ny_red = t_red.ty();
                        if(nx_red > game.plat.graphics().viewSize.width()-37)
                        {
                            nx_red = t_red.tx();
                            direction_red = Key.A;
                            click.play();
                        }
                        break;
                    case S:
                        nx_red = t_red.tx();
                        ny_red = t_red.ty() + dy;
                        if(ny_red > game.plat.graphics().viewSize.height()-37)
                        {
                            ny_red = t_red.ty();
                            direction_red = Key.W;
                            click.play();
                        }
                        break;
                    case A:
                        nx_red = t_red.tx() - dy;
                        ny_red = t_red.ty();
                        if(nx_red < 0)
                        {
                            nx_red = t_red.tx();
                            direction_red = Key.D;
                            click.play();
                        }
                        break;
                        default:
                            nx_red = t_red.tx();
                            ny_red = t_red.ty();
                }
                t_red.setTx(nx_red);
                t_red.setTy(ny_red);

                /**ImageLayer first = segments[0];
                Transform t = first.transform();
                float nx = t.tx() + dx, ny = t.ty() + dy, nd = first.depth() + dd;
                if (nx < 0 || nx > game.plat.graphics().viewSize.width()) {
                    dx *= -1;
                    nx += dx;
                }
                if (ny < 0 || ny > game.plat.graphics().viewSize.height()) {
                    dy *= -1;
                    ny += dy;
                }
                if (nd < 25 || nd > 125) {
                    dd *= -1;
                    nd += dd;
                }
                t.setTx(nx);
                t.setTy(ny);
                t.setUniformScale(nd/50f);
                first.setDepth(nd);*/
            }
        }));
    }
}
