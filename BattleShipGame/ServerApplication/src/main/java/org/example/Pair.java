package org.example;

public class Pair<T,E> {

    T player1;
    E player2;

    public Pair(T player1, E player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public String toString() {
        return "(" + player1 + ", " + player2 + ")";
    }

}
