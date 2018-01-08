import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;

import static java.lang.StrictMath.abs;

public class Scorpion extends Player {
    public Scorpion(int x, int y, Field field, String name, int blood, int range){
        super(x, y, field, name, blood, range);
        this.field = field;

        this.name = name;
        this.blood = blood;
        this.range = range;
        URL loc = this.getClass().getClassLoader().getResource("scorpion.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }

    public void move(int x, int y) {
        int nx,ny;
        if(this.y()> field.getBoardHeight()/2) {
            ny = this.y() - y;
        }
        else {
            ny = this.y() + y;
        }
        if(this.x() < field.getBoardWidth()/2) {
            nx = this.x() - x;
        }
        else {
            nx = this.x() + x;
        }

        if(!field.exist(nx,ny)) {
            this.setX(nx);
            this.setY(ny);
        }
    }
    public void beat() {
        int i;
        for(i=0;i<Field.players.size();i++) {
            Player player=Field.players.get(i);
            if (player.getClass() == Huluwa.class || player.getClass() == Grandpa.class) {
                if ((abs(((this.x()- player.x())*(this.x()-player.x())))+abs(((this.y()- player.y())*(this.y()-player.y())))) < 2500) {
                    if (player.hurt() != 0) {
                        int bleeding = player.hurt();
                        if (player.getClass() == Huluwa.class) {
                            Huluwa temp = (Huluwa) player;
                            System.out.println(String.format("%s打伤%s%d,%s%d流了%s滴血。", this.name, player.getName(), temp.ranking, player.getName(), temp.ranking, bleeding));
                        } else {
                            System.out.println(String.format("%s打伤%s,%s流了%s滴血。", this.name, player.getName(), player.getName(), bleeding));
                        }
                    }
                }
            }
        }
    }
    public void run(){
        while (field.gameover()!=1&&this.getBlood()>=0) {
            Random rand = new Random();

            this.move(-rand.nextInt(40),  rand.nextInt(10));
            this.dead();
            this.beat();
            this.printState();
            try {

                Thread.sleep(rand.nextInt(1000) + 1000);
                this.field.FieldRepaint();
                if(this.field.isLoad) {
                    this.field.Load();
                }
            } catch (Exception e) {

            }
        }
    }

}
