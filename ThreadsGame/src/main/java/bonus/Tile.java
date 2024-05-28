package bonus;

public record Tile(int first,int second) {
    @Override
    public String toString(){
        return "["+first+","+second+"]";
    }
}