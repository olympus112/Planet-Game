package PlanetGame;

class Vector {

    private double x;
    private double y;

    Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    static Vector fromPolar(double r, double angle){
        return new Vector(r*Math.cos(angle),r*Math.sin(angle));
    }

    public Vector addVector(Vector a){
        return new Vector(x+a.x, y+a.y);
    }
    public Vector add(double a){
        return new Vector(x+a, y+a);
    }
    public Vector invert(){
        return new Vector(-x, -y);
    }
    public Vector multiply(double i){
        return new Vector(x*i, y*i);
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double getLength() {
        return Math.sqrt(x*x+y*y);
    }
    public void setLength(double r) {
        double l = getLength();
        double tempSin = y / l;
        double tempCos = x / l;
        x = tempCos*r;
        y = tempSin*r;
    }
    public double getAngle() {
        return Math.atan2(y, x);
    }
    public void setAngle(double angle) {
        double r = Math.sqrt(x*x+y*y);
        x = r*Math.sin(angle);
        y = r*Math.cos(angle);
    }
}