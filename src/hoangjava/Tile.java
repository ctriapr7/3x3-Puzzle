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
public class Tile 
{
    private int value;
    private int row;
    private int col;
    
    public Tile(int value, int row, int col)
    {
        this.value = value;
        this.row = row;
        this.col = col;
    }
    
    public void setValue(int value)
    {
        this.value = value;
    }
    
    public void setRow(int row)
    {
        this.row = row;
    }
    
    public void setCol(int col)
    {
        this.col = col;
    }
    
    public int getValue()
    {
        return value;
    }
    
    public int getRow()
    {
        return row;
    }
    
    public int getCol()
    {
        return col;
    }
}
