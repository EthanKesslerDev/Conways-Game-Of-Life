import java.util.Scanner;

class Debug
{
    public static void Write(String msg)
    {
        System.out.println(msg);
    }
}

class Vector2
{
    public int x;
    public int y;

    public Vector2(int _x, int _y)
    {
        x = _x;
        y = _y;
    }
}

final class Renderer
{
    static final String Alive = "1";
    static final String Dead = "0";

    public static void Draw(Cell[][] cells, int SCREENWIDTH)
    {
        for(int y = (SCREENWIDTH - 1); y > -1; y--)
        {
            String row = "";
            for(int x = 0; x < SCREENWIDTH; x++)
            {
                //n.b. this will draw from top left to bottom right
                if(cells[x][y].alive)
                {
                    row += Alive;
                } else {
                    row += Dead;
                }
            }
            Debug.Write(row);
        }
    }
}

final class Cell
{
    public boolean alive = false;
}

class CGOL
{
    static final int SCREENWIDTH = 10;
    private static Cell[][] cells = new Cell[SCREENWIDTH][SCREENWIDTH];

    public static void main(String[] args) {
        Debug.Write("Welcome to Conway's Game Of Life!");

        Scanner scanner = new Scanner(System.in);

        //Initialize cells
        Vector2[] liveCells = {new Vector2(5, 5), new Vector2(4, 5), new Vector2(6, 5)};
        for(int x = 0; x < SCREENWIDTH; x++)
        {
            for(int y = 0; y < SCREENWIDTH; y++)
            {
                cells[x][y] = new Cell();
                for(int i = 0; i < liveCells.length; i++)
                {
                    if(x == liveCells[i].x && y == liveCells[i].y)
                    {
                        cells[x][y].alive = true;
                    }
                }
            }
        }

        //Begin game by drawing board
        Renderer.Draw(cells, SCREENWIDTH);
        Debug.Write("Press enter to progress the game!");

        String user_in = scanner.nextLine();
        while (user_in == "")
        {
            Boolean[][] new_cell_states = new Boolean[SCREENWIDTH][SCREENWIDTH];

            //Update CGOL states by iterating through the cells & updating accordingly!
            for(int x = 0; x < SCREENWIDTH; x++)
            {
                for(int y = 0; y < SCREENWIDTH; y++)
                {
                    int liveNeighbours = 0;
                    //Get all neighbours [Max 8]
                    if(x - 1 >= 0 && y - 1 >= 0)
                    {
                        if(cells[x-1][y-1].alive)
                        {
                            liveNeighbours++;
                        }
                    }
                    if(y - 1 >= 0)
                    {
                        if(cells[x][y-1].alive)
                        {
                            liveNeighbours++;
                        }
                    }
                    if(x + 1 < SCREENWIDTH && y - 1 >= 0)
                    {
                        if(cells[x+1][y-1].alive)
                        {
                            liveNeighbours++;
                        }
                    }
                    if(x - 1 >= 0)
                    {
                        if(cells[x - 1][y].alive)
                        {
                            liveNeighbours++;
                        }
                    }
                    if(x + 1 < SCREENWIDTH)
                    {
                        if(cells[x + 1][y].alive)
                        {
                            liveNeighbours++;
                        }
                    }
                    if(x - 1 >= 0 && y + 1 < SCREENWIDTH)
                    {
                        if(cells[x-1][y+1].alive)
                        {
                            liveNeighbours++;
                        }
                    }
                    if(y + 1 < SCREENWIDTH)
                    {
                        if(cells[x][y+1].alive)
                        {
                            liveNeighbours++;
                        }
                    }
                    if(x + 1 < SCREENWIDTH && y + 1 < SCREENWIDTH)
                    {
                        if(cells[x+1][y+1].alive)
                        {
                            liveNeighbours++;
                        }
                    }
                    
                    if(cells[x][y].alive)
                    {
                        new_cell_states[x][y] = true;
                        Debug.Write("Checking on a live cell!");
                        Debug.Write("This live cells has: " + Integer.toString(liveNeighbours) + " live neighbours.");
                        //ALIVE
                        if(!(liveNeighbours == 2 || liveNeighbours == 3))
                        {
                            new_cell_states[x][y] = false;
                        }
                    } else {
                        //DEAD
                        new_cell_states[x][y] = false;
                        if(liveNeighbours == 3)
                        {
                            new_cell_states[x][y] = true;
                        }
                    }
                }
            }
            //Draw
            for(int x = 0; x < SCREENWIDTH; x++)
            {
                for(int y = 0; y < SCREENWIDTH; y++)
                {
                    cells[x][y].alive = new_cell_states[x][y];
                }
            }
            Renderer.Draw(cells, SCREENWIDTH);

            //Get User Input
            Debug.Write("Press enter to progress the game!");
            user_in = scanner.nextLine();
        }
    }
}