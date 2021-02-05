package demo.benchmarks.power.test.NEq;
public class newV{
    public static int snippet(int x, int y) {
        int result = 0; 
        int path = 0;
        if (x > 0) {
            if (y == x * x) {
                path = 1;
            } 
            else {
                path = 2;
            }
            if (y > 8) {
                if (path == 1)
                    result = 3;
                if (path == 2)
                    result = 13+5;//change
            } 
            else {
                if (path == 1)
                    result = 4;
                if (path == 2)
                    result = 14;
            }
        }
        result = result + 10;//change
        return result;
    }
}