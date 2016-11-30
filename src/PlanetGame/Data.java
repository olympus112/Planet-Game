package PlanetGame;

/**
 * Created by Pieter on 10/11/2016.
 */
public class Data {

    public static class Coords {
        private int x;
        private int y;

        public Coords(int x, int y) {
            super();
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public String toString(){
            return "("+x+","+y+")";
        }

    }
}
