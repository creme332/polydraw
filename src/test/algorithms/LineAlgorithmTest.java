import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import java.util.Arrays;

public class LineAlgorithmTest {

    @Test
    public void testDrawDDA() {
        int x0 = 2, y0 = 3, x1 = 10, y1 = 8;
        int[][] expected = {
            {2, 3}, {3, 3}, {4, 4}, {5, 4}, {6, 5}, {7, 5}, {8, 6}, {9, 7}, {10, 8}
        };
        
        int[][] result = LineAlgorithm.drawDDA(x0, y0, x1, y1);
        assertArrayEquals("DDA Line Algorithm test failed", expected, result);
    }

    @Test
    public void testDrawBresenham() {
        int x0 = 2, y0 = 3, x1 = 10, y1 = 8;
        int[][] expected = {
            {2, 3}, {3, 3}, {4, 4}, {5, 4}, {6, 5}, {7, 5}, {8, 6}, {9, 7}, {10, 8}
        };
        
        int[][] result = LineAlgorithm.drawBresenham(x0, y0, x1, y1);
        assertArrayEquals("Bresenham's Line Algorithm test failed", expected, result);
    }

    @Test
    public void testDrawDDAHorizontal() {
        int x0 = 1, y0 = 1, x1 = 5, y1 = 1;
        int[][] expected = {
            {1, 1}, {2, 1}, {3, 1}, {4, 1}, {5, 1}
        };
        
        int[][] result = LineAlgorithm.drawDDA(x0, y0, x1, y1);
        assertArrayEquals("DDA Horizontal Line test failed", expected, result);
    }

    @Test
    public void testDrawBresenhamHorizontal() {
        int x0 = 1, y0 = 1, x1 = 5, y1 = 1;
        int[][] expected = {
            {1, 1}, {2, 1}, {3, 1}, {4, 1}, {5, 1}
        };
        
        int[][] result = LineAlgorithm.drawBresenham(x0, y0, x1, y1);
        assertArrayEquals("Bresenham Horizontal Line test failed", expected, result);
    }

    @Test
    public void testDrawDDAVertical() {
        int x0 = 1, y0 = 1, x1 = 1, y1 = 5;
        int[][] expected = {
            {1, 1}, {1, 2}, {1, 3}, {1, 4}, {1, 5}
        };
        
        int[][] result = LineAlgorithm.drawDDA(x0, y0, x1, y1);
        assertArrayEquals("DDA Vertical Line test failed", expected, result);
    }

    @Test
    public void testDrawBresenhamVertical() {
        int x0 = 1, y0 = 1, x1 = 1, y1 = 5;
        int[][] expected = {
            {1, 1}, {1, 2}, {1, 3}, {1, 4}, {1, 5}
        };
        
        int[][] result = LineAlgorithm.drawBresenham(x0, y0, x1, y1);
        assertArrayEquals("Bresenham Vertical Line test failed", expected, result);
    }
}

