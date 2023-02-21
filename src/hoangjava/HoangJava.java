/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoangjava;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeMap;


public class HoangJava 
{
    private ArrayList<Node> statesVisited = new ArrayList<>();
    private ArrayList<Node> leaves = new ArrayList<>();
    private int initialBlankRow = 0;
    private int initialBlankCol = 0;
    private static final int MAX_ROW = 3;
    private static final int MAX_COL = 3;
    private int[][] initialState = new int[MAX_ROW][MAX_COL];
    private TreeMap<Integer, Tile> goalState = new TreeMap<Integer, Tile>();
    Scanner triScanner = new Scanner(System.in);

    public HoangJava()
    {
        int tileValue = 1;
        
        for(int r=0; r<MAX_ROW; r++)
        {
            for(int c=0; c<MAX_COL; c++)
            {
                Tile myTile = new Tile(tileValue, r, c);
                goalState.put(myTile.getValue(), myTile);
                tileValue++;
            }
        }
        
        tileValue = 0;
        Tile myTile = new Tile(tileValue, (MAX_ROW - 1), (MAX_COL - 1));
        goalState.put(myTile.getValue(), myTile);
        
        initialState = readTiles();

        
        printTiles(initialState, "Initial States");
        
        if(!isSolvable(initialState))
        {
            System.out.println("Puzzle is not solvable!");
            System.exit(0);
        }
    }
    
     //Check to see if two arrays have same elements  
    public boolean isArrayEqual(int[][] array1, int [][] array2)
    {
        boolean equal = true; 
        
        for(int r = 0; r< MAX_ROW ;r++)
        {
            for(int c = 0; c<MAX_COL; c++)
            {
                if(array1[r][c] != array2[r][c])
                {
                    equal = false; 
                    r = MAX_ROW;
                    c = MAX_COL;
                }
            }
        }
        
        return equal; 
    }
    
    
      
   
    //read each tile from terminal
    public int[][] readTiles()
    {
        int[][] tempTiles=new int[MAX_ROW][MAX_COL];
        boolean correct=false;
        for (int r=0; r< MAX_ROW; r++)
        {
            for (int c=0; c<MAX_COL; c++)
            {
                correct=false;
                while(!correct)
                {
                    System.out.println("Enter Tile" + r + " " + c + ": ");
                    try 
                    {
                        tempTiles[r][c]= triScanner.nextInt();
                        correct=true;
                        if (tempTiles[r][c]==0)
                        {
                            //register the initial position of blank tile
                            initialBlankRow=r;
                            initialBlankCol=c;
                        }
                    }
                    catch (InputMismatchException e)
                    {
                        System.out.println("Enter digits only!");
                        triScanner.nextLine();
                    }
                }
            }
        }
        return tempTiles;
    }
    
    
    
    public int[][] transferArray(int[][] targetArray)
    {
        int[][] tempTiles = new int[MAX_ROW][MAX_COL];
        
        for(int r=0; r<MAX_ROW; r++)
        {
            for(int c=0; c<MAX_COL; c++)
            {
                tempTiles[r][c] = targetArray[r][c];
            }
        }
        
        return tempTiles;
    }
   
        
    public void printTiles(int[][] givenState, String mess)
    {
        System.out.println(mess);
        for (int r=0; r< MAX_ROW; r++)
        {
            for (int c=0; c<MAX_COL; c++)
            {
                if (givenState[r][c] < 10)
                {
                    System.out.print(" ");
                }
                System.out.print(givenState[r][c] + " ");
            }
            System.out.println();
        }
    }
  
    public ArrayList<Tile> findCandidates(Node node)
    {

        int blankRow = node.getMovedRow();
        int blankCol = node.getMovedCol();
        int repeatValue = node.getMovedValue();
        int[][] allTiles = transferArray(node.getStateTiles());
    
        ArrayList<Tile> candidates = new ArrayList<>(4);
        
        if(blankRow - 1 >= 0)
        {
            if(allTiles[blankRow-1][blankCol] != repeatValue)
            {
                Tile topTile = new Tile(allTiles[blankRow-1][blankCol], (blankRow-1), blankCol);
                candidates.add(topTile);
                
            }
        }
    
        if(blankRow + 1 < MAX_ROW)
        {
            if(allTiles[blankRow+1][blankCol] != repeatValue)
            {
                Tile bottomTile = new Tile(allTiles[blankRow+1][blankCol], (blankRow+1), blankCol);
                candidates.add(bottomTile);
            }
        }
        
        if(blankCol - 1 >= 0)
        {
            if(allTiles[blankRow][blankCol-1] != repeatValue)
            {
                Tile leftTile = new Tile(allTiles[blankRow][blankCol-1], blankRow, (blankCol-1));
                candidates.add(leftTile);
            }
        }
    
        if(blankCol + 1 < MAX_COL)
        {
            if(allTiles[blankRow][blankCol+1] != repeatValue)
            {
                Tile rightTile = new Tile(allTiles[blankRow][blankCol+1], blankRow, (blankCol+1));
                candidates.add(rightTile);
            }
        }
        
    
        return candidates;
    }
    
  
    public void generateNodes(ArrayList<Tile> candidates, Node parentNode)
    {
        int f;
        int[][] allTiles = transferArray(parentNode.getStateTiles());
        int blankRow = parentNode.getMovedRow();
        int blankCol = parentNode.getMovedCol();
        int level = parentNode.getLevel();
        
        for(Tile tile : candidates)
        {
            allTiles[tile.getRow()][tile.getCol()] = 0;
            allTiles[blankRow][blankCol] = tile.getValue();
            int[][] tempTiles = transferArray(allTiles);
            allTiles[tile.getRow()][tile.getCol()] = tile.getValue();
            allTiles[blankRow][blankCol] = 0;
        
            if(!isRepeatState(statesVisited, tempTiles))
            {
                f = calculateFScore(tempTiles, level); 
                Node newNode = new Node(f, tempTiles, tile.getValue(), tile.getRow(), tile.getCol(), level + 1, parentNode);

                insertSorted(newNode, leaves);
            }
        }
    }
    
    //check whether the puzzle is solvable or not
    
    
     public boolean isSolvable(int[][] allTiles)
    {
        int[] linearArray = new int[(MAX_ROW * MAX_COL) - 1];
        boolean status=true;
        int index=0;
        int counter=0;
        int blankRow=0;
        boolean isEven;
        
        //determine if the board is odd or even (3x3=9 or 4x4=16)
        if ((MAX_ROW * MAX_COL) %2 ==0)
        {
            isEven=true;
        }  
        else 
        {
            isEven=false;
        }
        
        //convert to linear array
        for (int r=0; r< MAX_ROW; r++)
        {
            for (int c=0; c< MAX_COL; c++)
            {
                if (allTiles[r][c] !=0)
                {
                    linearArray[index]= allTiles[r][c];
                    index++;   
                }
                else 
                {
                    blankRow=r; 
                }
            }
        }
        
        //count the inversions 
        for (int i=0; i< linearArray.length; i++)
        {
            for (int j=0; j<i; j++)
            {
                if (linearArray[i] < linearArray[j])
                {
                    counter++;
                }
            }
        }
        
        //if even board
        if (isEven)
        {
            //add the row number of the blank row to inversions
            counter=counter + blankRow;
            //if counter is even it is not solvable
            if (counter % 2==0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        //if board is odd
        else 
        {
            //if counter is even it is solvable
            if (counter % 2==0)
            {
                return true;
            }
            else 
            {
                return false;
            }
        }
    }

    
    public int calculateGscore(int g)
    {
        int gScore = g;
        return gScore;
    }
    
   
    
    public int calculateHScore(int[][] currentState)  
    {
        int distance = 0;
        
        for(int r=0; r<MAX_ROW; r++)
        {
            for(int c=0; c<MAX_COL; c++)
            {
                if(currentState[r][c] != 0)
                {
                    Tile goalTile = goalState.get(currentState[r][c]);
                    distance = distance 
                            + Math.abs(r - goalTile.getRow()) 
                            + Math.abs(c-goalTile.getCol());
                }
            }
        }
        
        return distance;
    }
    

    
    public int calculateFScore(int[][] currentState, int g)
    {
        int fScore = calculateHScore(currentState) + (g + 1);
        return fScore;
    }

    
    public String findDirection(int currentRow, int currentCol, int nextRow, int nextCol)
    {
        if(currentRow < nextRow)
        {
            return "Up";
        }
        
        else if(currentRow > nextRow)
        {
            return "Down";
        }
        
        else if(currentCol < nextCol)
        {
            return "Left";
        }
        
        else if(currentCol > nextCol)
        {
            return "Right";
        }
        return "No Move";
    }
    
   
    public void displaySolution(Node finalNode)
    {
        Node tempNode = finalNode;
        ArrayList<String> direction = new ArrayList<>();
        int currentRow;
        int currentCol;
        int nextRow;
        int nextCol;
        
        while(tempNode != null)
        {
            printTiles(tempNode.getStateTiles(),"");
            currentRow = tempNode.getMovedRow();
            currentCol = tempNode.getMovedCol();
            tempNode = tempNode.getParentNode();
    
            if(tempNode != null)
            {
                nextRow = tempNode.getMovedRow();
                nextCol = tempNode.getMovedCol();
                direction.add(0,findDirection(currentRow, currentCol, nextRow, nextCol));
            }
        }
        System.out.println(direction);
        System.out.println("Number of Moves: " + direction.size());
    }
    
    public void aStar()
    {
        ArrayList<Tile> candidateList;
        Node selectedState = null;
        boolean goalFound = false;
        int f;
        int level = -1;
        
        if(puzzleDone(initialState))
        {
            goalFound = true;
        }
        
        else
        {
            int[][] tempTiles = transferArray(initialState);
            f = calculateFScore(tempTiles, level);
            Node initialNode = new Node(f, tempTiles, -1, initialBlankRow, initialBlankCol, 0, null);
            statesVisited.add(initialNode);
            selectedState = initialNode;
        }
    
        while(!goalFound)
        {
            candidateList = findCandidates(selectedState);
            generateNodes(candidateList, selectedState);
            
    
            selectedState = leaves.remove(0);
            
            
            if(puzzleDone(selectedState.getStateTiles()))
            {
                goalFound = true;
            }
 
            statesVisited.add(selectedState);
        }
        
        displaySolution(selectedState);
        System.out.println("Reached goal state!");
    }   

   
    
    public boolean puzzleDone(int[][] initialState)
    {
        boolean done = true;
        int tileValue = 1;
        int maxTiles = MAX_ROW * MAX_COL;
    
        for(int r=0; r<MAX_ROW; r++)
        {
            for(int c=0; c<MAX_COL; c++)
            {
                if(initialState[r][c] != tileValue)
                {
                    done=false;
                    return done;
                }
                
                tileValue = (tileValue + 1) % (maxTiles);
            }
        }
    
        return done;
    }
    
    
    public boolean isRepeatState(ArrayList<Node> statesVisited, int[][] currentState)
    {
        for(Node eachState : statesVisited)
        {
            if(isArrayEqual(eachState.getStateTiles(), currentState))
            {
                return true;
            }
        }
        return false;
    }
    
    public void insertSorted(Node node, ArrayList<Node> nodeList)
    {
        boolean done = false;
        int i=0;
        
        while(i < nodeList.size())
        {
            Node tempNode = nodeList.get(i);
            
            if(!done && (node.getF() <= tempNode.getF()))
            {
                nodeList.add(i, node);
                done = true;
            }
            
            if(isArrayEqual(node.getStateTiles(), tempNode.getStateTiles()))
            {
                if(node.getF() > tempNode.getF())
                {
                    i = nodeList.size();
                    done = true;
                }
                
                else
                {
                    nodeList.remove(tempNode);
                }
            }
            i++;
        }
            
        if(!done)
        {
            nodeList.add(node);
        }           
    } 

    
    //main class
    public static void main(String[] args) 
    {
        HoangJava newObject = new HoangJava();
        newObject.aStar();   
    }
}
