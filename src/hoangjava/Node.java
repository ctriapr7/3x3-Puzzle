/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoangjava;

/**
 *
 * @author hoangcongtri
 */
public class Node 
{
    private int f;
    private int[][] stateTiles;
    private int movedValue;
    private int movedRow;
    private int movedCol;
    private int level;
    private Node parentNode;
    
    public Node(int f, int[][] stateTiles, int movedValue,
            int movedRow, int movedCol, int level, Node parentNode)
    {
        this.f = f;
        this.stateTiles = stateTiles;
        this.movedValue = movedValue;
        this.movedRow = movedRow;
        this.movedCol = movedCol;
        this.level = level;
        this.parentNode = parentNode;
    }
    
    public void setF(int f)
    {
        this.f = f;
    }
    
    public void setStateTiles(int[][] stateTiles)
    {
        this.stateTiles = stateTiles;
    }
    
    public void setMovedValue(int movedValue)
    {
        this.movedValue = movedValue;
    }
    
    public void setMovedRow(int movedRow)
    {
        this.movedRow = movedRow;
    }
    
    public void setMovedCol(int movedCol)
    {
        this.movedCol = movedCol;
    }
    
    public void setLevel(int level)
    {
        this.level = level;
    }
    
    public void setParentNode(Node parentNode)
    {
        this.parentNode = parentNode;
    }
    
    public int getF()
    {
        return f;
    }
    
    public int[][] getStateTiles()
    {
        return stateTiles;
    }

    public int getMovedValue()
    {
        return movedValue;
    }
    
    public int getMovedRow()
    {
        return movedRow;
    }
    
    public int getMovedCol()
    {
        return movedCol;
    }
    
    public int getLevel()
    {
        return level;
    }
    
    public Node getParentNode()
    {
        return parentNode;
    }
}
