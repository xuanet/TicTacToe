import java.util.ArrayList;

public class tttLine {

    int player;
    int comp;
    ArrayList<Integer> spaces;

    public tttLine(int a, int b, int c) {
        player = 0;
        comp = 0;

        spaces = new ArrayList();
        spaces.add(a);
        spaces.add(b);
        spaces.add(c);

    }

    public void add(int k) {
        if (k==1) player++;
        else comp++;
        
    }
}

