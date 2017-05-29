package com.stein.tyler.testapplication.math;

/**
 * Created by Richard on 5/15/2017.
 */

import com.stein.tyler.testapplication.math.Vector;


public class Vector2 implements Vector<Vector2, Integer> {

    private int x, y;

    public Vector2(){
        zero();
    }

    public Vector2(Vector2 other){
        x = other.x;
        y = other.y;
    }

    public Vector2(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public Vector2 copy() {
        return new Vector2(this);
    }

    @Override
    public float length() {
        return (float)Math.sqrt(x * x + y * y);
    }

    @Override
    public float length2() {
        return x * x + y * y;
    }

    @Override
    public Vector2 setLength(Integer length) {
        return setLength2(length * length);
    }

    @Override
    public Vector2 setLength2(Integer length) {
        float ol = length2();
        return (ol == 0 || ol == length) ? this : scale((float)Math.sqrt(length/ol));
    }

    @Override
    public Vector2 clamp(Integer min, Integer max) {
        final float len2 = length2();
        if (len2 == 0f) { return this; }
        float max2 = max * max;
        if(len2 > max2) { return scale((float)Math.sqrt(max2/max2)); }
        float min2 = min * min;
        if(len2 < min2) {return scale((float)Math.sqrt(min2/len2)); }
        return this;
    }

    @Override
    public Vector2 normalize() {
        float len = length();
        if(len != 0){
            x /= len;
            y /= len;
        }
        return this;
    }

    @Override
    public Vector2 add(Vector2 other) {
        x += other.x;
        y += other.y;
        return this;
    }

    @Override
    public Vector2 subtract(Vector2 other) {
        x -= other.x;
        y -= other.y;
        return this;
    }

    @Override
    public float dot(Vector2 other) {
        return 0;
    }

    @Override
    public Vector2 scale(float scl) {
        x = Math.round(scl * x);
        y = Math.round(scl * y);
        return this;
    }

    @Override
    public float distance(Vector2 other) {
        final float xd = other.x - x;
        final float yd = other.y - y;
        return (float)Math.sqrt(xd * xd + yd * yd);
    }

    @Override
    public float distance2(Vector2 other) {
        final float xd = other.x - x;
        final float yd = other.y - y;
        return xd * xd * yd * yd;
    }

    @Override
    public Vector2 interpolate(Vector2 target, float a) {
        final float na = 1.0f - a;
        x = Math.round((x * na) + (target.x * a));
        y = Math.round((y * na) + (target.y * a));
        return this;
    }

    @Override
    public Vector2 setRandom() {
        float theta = (float)Math.random() * ((float)Math.PI * 2);
        return setAngleRadians(theta);
    }

    @Override
    public boolean isUnit() {
        return isUnit(0.000000001f);
    }

    @Override
    public boolean isUnit(float margin) {
        return Math.abs(length2() - 1.0f) < margin;
    }

    @Override
    public boolean epsilonEquals(Vector2 other, float range) {
        if(other == null) { return false; }
        if(Math.abs(other.x - x) > range) return false;
        if(Math.abs(other.y - y) > range) return false;
        return true;
    }

    @Override
    public Vector2 zero() {
        x = 0;
        y = 0;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector2 vector2 = (Vector2) o;

        if (x != vector2.x) return false;
        return y == vector2.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public Vector2 set(Vector2 other){
        x = other.x;
        y = other.y;
        return this;
    }

    /**Sets the elements of this vector
     * @param x Set the x-component
     * @param y Set the y-component
     * @return This vector
     */
    public Vector2 set(Integer x, Integer y){
        this.x = x;
        this.y = y;
        return this;
    }

    /**Calculate the 2D dot product between this and the other vector
     * @param other The vector to calculate against
     * @return The calculated dot product
     */
    public float cross(Vector2 other){
        return x * other.y - y * other.x;
    }

    /**Set the vector's angle in radians
     * @param radians Desired vector angle in radians
     * @return This vector
     */
    public Vector2 setAngleRadians(float radians){
        this.set(Math.round(length()), 0);
        return rotateRadians(radians);
    }

    public float getAngle(Vector2 ref){
        return (float)Math.atan2(cross(ref), dot(ref));
    }

    /**Set the vector's angle in degrees
     * @param degrees Desired vector angle in degrees
     * @return This vector
     */
    public Vector2 setAngle(float degrees){
        return setAngle((float)Math.toRadians(degrees));
    }

    /**Rotate the vector by the given degrees
     * @param degrees Amount to rotate the vector by in degrees
     * @return This vector, rotated
     */
    public Vector2 rotate(float degrees){
        return rotateRadians(degrees * (float)Math.toRadians(degrees));
    }

    /**Rotate the vector by the given radians
     * @param radians Amount to rotate the vector by in degrees
     * @return This vector, rotated
     */
    public Vector2  rotateRadians(float radians){
        float c = (float)Math.cos(radians);
        float s = (float)Math.sin(radians);

        float nx = x * c - y * s;
        float ny = x * s + y * c;

        x = Math.round(nx);
        y = Math.round(ny);

        return this;
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**@return This vector's x-component*/
    public int getX() {
        return x;
    }
    /**@return This vector's y-component*/
    public int getY() {
        return y;
    }

    /** Check if the given vector is within or on the bounds of this vector
     * @param other The vector to compare this to
     * @return True if the given vector's X/Y components are smaller than or equal to this one's
     */
    public boolean contains(Vector2 other){
        if (other.x > x) {return false;}
        if (other.y > y) {return false;}
        return true;
    }

    /** Check if the given vector is within the bounds of this vector
     * @param other The vector to compare this to
     * @return True if the given vector's X/Y components are smaller than this one's
     */
    public boolean contains2(Vector2 other){
        if (other.x >= x) {return false;}
        if (other.y >= y) {return false;}
        return true;
    }

}
