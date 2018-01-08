import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;
import java.util.ArrayList;
import static java.lang.StrictMath.abs;

public class Huluwa extends Player {
    int ranking;

    public Huluwa(int x, int y, int ranking, Field field, String name, int blood, int range) {
        super(x, y, field, name, blood, range);
        this.ranking = ranking;
        this.field = field;

        this.name = name;
        this.blood = blood;
        this.range = range;
        URL loc = this.getClass().getClassLoader().getResource("huluwa.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }

    public void beat() {
        int i;
        for(i=0;i<Field.players.size();i++) {
            Player player=Field.players.get(i);
            if (player.getClass() == Snake.class || player.getClass() == Scorpion.class || player.getClass() == Underlings.class) {
                if ((abs(((this.x()- player.x())*(this.x()-player.x())))+abs(((this.y()- player.y())*(this.y()-player.y())))) < 1600) {
                    if (player.hurt() != 0) {
                        int bleeding = player.hurt();
                        if (player.getClass() == Underlings.class) {
                            Underlings temp = (Underlings) player;
                            System.out.println(String.format("%s%d打伤%s%d,%s%d流了%s滴血。", this.name, this.ranking, player.getName(), temp.ranking2, player.getName(), temp.ranking2, bleeding));
                        } else {
                            System.out.println(String.format("%s%d打伤%s,%s流了%s滴血。", this.name, this.ranking, player.getName(), player.getName(), bleeding));
                        }
                    }
                }
            }
        }
    }
    public void move(int x, int y) {
        int nx,ny;
        if(this.y()> field.getBoardHeight()/2) {
            ny = this.y() - y;
        }
        else {
            ny = this.y() + y;
        }
        if(this.x()> field.getBoardWidth()/2) {
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

    public void run(){
        while (field.gameover()!=1&&this.getBlood()>=0) {
            Random rand = new Random();

            this.move(rand.nextInt(40), rand.nextInt(10));
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
