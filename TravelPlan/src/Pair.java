public class Pair<T,U> {
    private T first;
    private U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }


    @Override
    public String toString(){
        return "<"+first+","+second+">";
    }

    @Override
    public boolean equals(Object obj){
        if(obj==null||!(obj instanceof Pair<?,?>)){
                return false;
        }
        Pair<?,?> other = (Pair<?,?>) obj;
        return first.equals(other.getFirst())&&second.equals(other.getSecond());
    }
}
