import javax.swing.JFrame;
import java.io.IOException;


public final class Main extends JFrame {

    private final int OFFSET = 0;


    public Main() throws IOException, ClassNotFoundException{
        InitUI();
    }

    public void InitUI() throws IOException, ClassNotFoundException{
        Field field = new Field();
        add(field);
        if(field.isLoad) {
            field.Load();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(field.getBoardWidth() + OFFSET,
                field.getBoardHeight() + 2 * OFFSET);
        setLocationRelativeTo(null);
        setTitle("HuluwaWar");
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException{
        Main main = new Main();

        main.setVisible(true);
    }
}