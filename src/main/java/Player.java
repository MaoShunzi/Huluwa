import java.awt.Image;
import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;
import java.lang.*;

import static java.lang.StrictMath.abs;

public class Player extends Thing2D implements Runnable {
    protected Field field;
    protected int blood;//血量
    protected int range;//掉血范围
    protected String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // 构造函数
    public Player(int x, int y, Field field, String name, int blood, int range) {
        super(x, y);

        this.field = field;

        this.name = name;
        this.blood = blood;
        this.range = range;

  /*      URL loc = this.getClass().getClassLoader().getResource("绿娃.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);*/
    }

    public void move(int x, int y) {
   /*     int nx,ny;
        if(this.y()> field.getBoardHeight()/2) {
            ny = this.y() - y;
        }
        else {
            ny = this.y() + y;
        }
        if(this.x()> field.getBoardWidth()) {
            nx = this.x() - x;
        }
        else {
            nx = this.x() + x;
        }

        this.setX(nx);
        this.setY(ny);*/
    }
    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

// 击打
    public void beat() {
   /*     if(player.getClass()==Snake.class||player.getClass()==Scorpion.class||player.getClass()==Underlings.class) {
            if (abs((this.x() - player.x())) < 50 || abs((this.y() - player.y())) < 50) {

                int bleeding = player.hurt();
                System.out
                        .println(String.format("%s打伤%s,%s流了%s滴血。", this.name, player.getName(), player.getName(), bleeding));
            }
        }*/
    }

// 被打，受伤
    public int hurt() {
        if(this.blood>0) {
            Random random = new Random();
            int bleeding = random.nextInt(this.range) + 1;
            if (bleeding > this.blood) {
                bleeding = this.blood;
            }

            this.blood -= bleeding;

            return bleeding;
        }
        else
            return 0;
    }
//死亡
    public boolean dead() {
        if (this.blood <= 0) {
            if (this.getClass() == Underlings.class) {
                Underlings temp = (Underlings) this;
                System.out.println(String.format("%s%d被打死了", this.name, temp.ranking2));
            } else if (this.getClass() == Huluwa.class) {
                Huluwa temp = (Huluwa) this;
                System.out.println(String.format("%s%d被打死了", this.name, temp.ranking));
            } else {
                System.out.println(String.format("%s被打死了", this.name));
            }
            this.blood = -100;

       /*     if(this.getClass()==Underlings.class) {
                field.underlings.remove(this);
            }
            else if(this.getClass()==Huluwa.class) {
                field.huluwas.remove(this);
            }
            Field.players.remove(this);*/
        }
        return true;
    }

// 输出状态
    public void printState() {
 /*       int flag=0;
        for(int i=0;i<Field.players.size();i++) {
            if(Field.players.get(i)==this) {
                flag=1;
            }
        }
        if(flag==1) {
            if (this.blood == 0) {

                System.out.println(String.format("%s被打死了", this.name));

            } else {

             //   System.out.println(String.format("%s还剩%s滴血", this.name, this.blood));

            }
        }*/

    }

    public void run() {
     /*   while (!Thread.interrupted()) {
            Random rand = new Random();

            this.move(rand.nextInt(10), rand.nextInt(10));
            try {

                Thread.sleep(rand.nextInt(1000) + 1000);
                this.field.repaint();

            } catch (Exception e) {

            }
        }*/
    }
}