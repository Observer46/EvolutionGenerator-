package agh.cs.lab3;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }
    public boolean precedes(Vector2d other){
        return (this.x<=other.x && this.y<=other.y) ? true : false;
    }

    public boolean follows(Vector2d other){
        return (this.x>=other.x && this.y>=other.y) ? true : false;
    }

    public Vector2d upperRight(Vector2d other){
        return new Vector2d(Math.max(this.x, other.x),Math.max(this.y,other.y));
    }

    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Math.min(this.x, other.x),Math.min(this.y,other.y));
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public boolean  equals(Object other){
        if(this == other)   return true;
        if(!(other instanceof Vector2d))    return false;
        Vector2d that = (Vector2d) other;
        return (this.x==that.x && this.y==that.y) ? true : false;
    }

    public Vector2d opposite(){
        return new Vector2d((-1)*this.x,(-1)*this.y);
    }

    public static Vector2d toUnitVec (int direction){
        Vector2d unitVector;
        switch (direction){
            case 0:
                unitVector=new Vector2d(0,1);
                break;
            case 1:
                unitVector=new Vector2d(1,1);
                break;
            case 2:
                unitVector=new Vector2d(1,0);
                break;
            case 3:
                unitVector=new Vector2d(1,-1);
                break;
            case 4:
                unitVector=new Vector2d(0,-1);
                break;
            case 5:
                unitVector=new Vector2d(-1,-1);
                break;
            case 6:
                unitVector=new Vector2d(-1,0);
                break;
            case 7:
                unitVector=new Vector2d(-1,1);
                break;
            default:
                unitVector=new Vector2d(0,0);
        }
        return unitVector;
    }

    @Override
    public int hashCode(){
        int hash=13;
        hash+=this.x*31;
        hash+=this.y*17;
        return hash;
    }
}
