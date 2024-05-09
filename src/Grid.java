public class Grid {
    private int[][] grid;
    private int numberOfRows,numberOfColumns;

    public Grid(int rows, int columns){
        grid = new int[rows][columns];
        numberOfColumns=columns;
        numberOfRows=rows;
    }
    public int rowDimension(){
        return numberOfRows;
    }

    public int columnsDimension(){
        return numberOfColumns;
    }

    public int getValue(int row, int column){
        try {
            return grid[row][column];
        } catch (Exception e) {
            System.out.println(e);
            return 7;
        }
  
    }

    public void setValue(int row, int column, int value) {
        try {
            grid[row][column] = value;
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    public void reset() {
        int rows = grid.length;
        int columns = grid[0].length;
    
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                grid[row][column] = 0;
            }
        }
    }
}
