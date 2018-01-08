import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.*;
import java.util.Random;

import static java.lang.StrictMath.abs;

public class Field extends JPanel {

    private final int OFFSET = 0;
    private final int SPACE = 25;

    private ArrayList tiles = new ArrayList();
    public static ArrayList<Player> players =new ArrayList();
    public ArrayList<Player> underlings = new ArrayList();
    public ArrayList<Player> huluwas = new ArrayList();
    public Player grandpa;
    public Player snake;
    public Player scorpion;
    public ArrayList worlds = new ArrayList();
    //Player player;

    public boolean isLoad = false;
    public ArrayList allx =new ArrayList();
    public ArrayList ally =new ArrayList();



    private int w = 0;
    private int h = 0;
    private boolean completed = false;

    private String level =
            "";


    public Field() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld();
    }

    public int getBoardWidth() {
        return this.w;
    }

    public int getBoardHeight() {
        return this.h;
    }

    public final void initWorld() {

        int x = OFFSET;
        int y = OFFSET;

        Tile a;
        Player b;
        int ranking=1;
        int ranking2=1;
        String name;
    //    int blood=100;
     //   int range=30;

        String HuluwaForm = Grandpa.chooseFormation();
        String SnakeForm = Snake.chooseFormation();
        for(int i  = 0; i < (20 + 1) * 12; i++){
            if(i % (20 + 1) >= 0 && i % (20 + 1) < 10) {
                level += HuluwaForm.charAt(i);
            }
            else level += SnakeForm.charAt(i);
        }

        for (int i = 0; i < level.length(); i++) {

            char item = level.charAt(i);

            if (item == '\n') {
                y += SPACE;
                if (this.w < x) {
                    this.w = x+SPACE;
                }
                x = OFFSET;
            }else if(item=='B') {
                a = new Tile(x, y);
                tiles.add(a);
                x += SPACE;
            } else if (item == '.') {
             //   a = new Tile(x, y);
            //   tiles.add(a);
                x += SPACE;
            } else if (item == '@') {
                b = new Huluwa(x, y, ranking, this, "葫芦娃", 200, 25);
                huluwas.add(b);
                players.add(b);
                ranking++;
                x += SPACE;
            }else if(item=='G') {
                grandpa = new Grandpa(x, y, this, "爷爷", 200, 30);
                players.add(grandpa);
                x += SPACE;
            }else if(item=='S') {
                snake = new Snake(x, y, this, "蛇精", 250, 25);
                players.add(snake);
                x += SPACE;
            }else if(item=='C') {
                scorpion = new Scorpion(x, y, this, "蝎子精", 250, 25);
                players.add(scorpion);
                x += SPACE;
            }else if(item=='U') {
                b = new Underlings(x, y, ranking2, this, "小喽啰", 200, 35);
                underlings.add(b);
                ranking2++;
                players.add(b);
                x += SPACE;
            }else if (item == ' ') {
                x += SPACE;
            }

            h = y+2*SPACE;
        }

     //   player = new Player(0+ OFFSET,0+OFFSET, this);

    }

    public void buildWorld(Graphics g) {

        g.setColor(new Color(250, 240, 170));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

    //    ArrayList world = new ArrayList();
        worlds.addAll(tiles);


//        world.add(player);
        worlds.addAll(players);

        for (int i = 0; i < worlds.size(); i++) {

            Thing2D item = (Thing2D) worlds.get(i);

            if (item instanceof Player) {
                g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
            } else {
                g.drawImage(item.getImage(), item.x(), item.y(), this);
            }

            if (completed) {
                g.setColor(new Color(0, 0, 0));
                g.drawString("Completed", 25, 20);
            }

        }
    }

    public boolean exist(int x, int y) {
        int x0 = OFFSET;
        int y0= OFFSET;

       boolean isexist=false;

        if(!players.isEmpty()) {
            for(Player p:players) {
                if(abs(p.x()-x)<10 && abs(p.y()-y)<10 && !p.dead()) {
                    isexist=true;
                }
            }
        }
        return isexist;
    }

    public int gameover() {
        int issuccess=1;
        int isfail=0;
        for(int i=0;i<underlings.size();i++) {
            if(underlings.get(i).getBlood()>0) {
                issuccess=0;
            }
        }
        if(snake.getBlood()>0)
            issuccess=0;
        if(scorpion.getBlood()>0)
            issuccess=0;
        for(int i=0;i<huluwas.size();i++) {
            if(huluwas.get(i).getBlood()>0) {
                isfail=1;
            }
        }
        if(grandpa.getBlood()>0)
            isfail=1;
        if(issuccess==1&&isfail==1) {
            System.out.println(String.format("葫芦娃们和爷爷胜利啦！"));
            return 1;
        }
        else if(issuccess==0&&isfail==0) {
            System.out.println(String.format("葫芦娃们和爷爷被打败了:("));
            return 1;
        }
        return 0;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        buildWorld(g);
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    public void FieldRepaint() throws IOException,ClassNotFoundException{
        repaint();
        FileOutputStream os = new FileOutputStream("war.txt");
        ObjectOutputStream oos = new ObjectOutputStream(os);
        if(!players.isEmpty()) {
            for(Player p:players) {
                oos.writeObject(p);
            }
        }
        oos.flush();
        oos.close();
   //     FileInputStream is = new FileInputStream("war.data");
    //    ObjectInputStream ois = new ObjectInputStream(is);
    }
    public void Load() throws IOException,ClassNotFoundException{
        System.out.println("正在加载...");
        FileInputStream is = new FileInputStream("war.txt");
        ObjectInputStream ois = new ObjectInputStream(is);
        while(ois != null) {
            Player p=(Player)ois.readObject();
        }
        ois.close();
    }


    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (completed) {
                return;
            }


            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {
                for(Object  b: worlds)
                    if(b instanceof Player) {
                        new Thread((Player)b).start();
                    }
            }

            else if (key == KeyEvent.VK_ENTER) {
                restartLevel();
            }
            else if(key == KeyEvent.VK_L) {
                isLoad=true;
             //   Load();
            }
            /*
            if (key == KeyEvent.VK_LEFT) {


                player.move(-SPACE, 0);

            } else if (key == KeyEvent.VK_RIGHT) {


                player.move(SPACE, 0);

            } else if (key == KeyEvent.VK_UP) {


                player.move(0, -SPACE);

            } else if (key == KeyEvent.VK_DOWN) {


                player.move(0, SPACE);

            } else if (key == KeyEvent.VK_S) {

                new Thread(player).start();

            } else if (key == KeyEvent.VK_R) {
                restartLevel();
            }*/

            repaint();
        }
    }

    public void restartLevel() {

        tiles.clear();
        players.clear();
        worlds.clear();
        initWorld();
        if (completed) {
            completed = false;
        }
    }
}