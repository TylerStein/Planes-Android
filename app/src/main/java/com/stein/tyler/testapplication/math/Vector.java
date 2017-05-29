package com.stein.tyler.testapplication.math;

/**
 * Created by Richard on 5/15/2017.
 */

/**Euclidian vector interface
 * @param <T> Vector elements type
 * @param <P> Number precision
 */
public interface Vector<T extends Vector<T, P>, P extends Number>{
    /**@return A copy of this vector
     * */
    T copy();

    /**Set this vector's properties to match the other'
     * @param other The vector to copy the properties of
     * @return This vector
     */
    T set(T other);

    /**@return The vector's length/distance
     * */
    float length();

    /**Faster than {@link Vector#length()} because there is no square root calculation
    @returns The vector's length squared
     */
    float length2();

    /**Limit the length of this vector
     * @param length Desired vector magnitude/length
     * @return This vector
     * */
    T setLength(P length);

    /**Limit the length of this vector (squared)
     * @param length Desired vector magnitude/length squared
     * @return This vector
     * */
    T setLength2(P length);

    /**Clamp this vector's magnitude
     * @param min Minimum length
     * @param max Maximum length
     * @return This vector
     */
    T clamp(P min, P max);


    /**Normalize this vector
     * @return This vector*/
    T normalize();

    /**Add this vector to another
     * @param other The vector to add
     * @return This vector
     * */
    T add(T other);

    /**Subtract this vector from anoth
     * @param other The vector to subtract
     * @return This vector
     */
    T subtract(T other);

    /**Calculate the dot product between this vector and another
     * @param other The vector to calculate with
     * @return The resulting dot product
     */
    float dot(T other);

    /**Scale this vector by a scalar amount
     * @param scl Scaling to apply to vector values
     * @return This vector
     */
    T scale(float scl);

    /**Calculate the distance between this point and the other
     * @param other The point to check distance against
     * @return The distance between this vector and the other
     */
    float distance(T other);

    /**Calculate the distance without using square root, faster than {@link Vector#scale(float)}
     * @param other The point to check distance against
     * @return The square distance between this vector and the other
     */
    float distance2(T other);

    /**Linear interpolate this vector towards a target
     * @param target Desired final vector
     * @param a Percentage of interpolation between this and the target (0.0 - 1.0)
     * @return This vector, linearly interpolated
     */
    T interpolate(T target, float a);

    /**Set this to a random unit vector
     * @return This vector, randomized
     */
    T setRandom();

    /**Check if this is a unit length vector
     * @return true if this is a unit vector
     */
    boolean isUnit();

    /**Check if this is a vector within the unit of a given margin
     * @param margin The unit margin to check within
     * @return true if this vector is within the unit margin
     */
    boolean isUnit(final float margin);

    /**Compares this vector with another using supplied epsilon for fuzzy equality testing
     * @param other The other vector to check this against
     * @param range The range of acceptance for fuzzy equality testing (really small)
     * @return true if the vector equality is within the given range
     */
    boolean epsilonEquals(T other, float range);

    /**Sets this vector's elements to 0
     * @return This vector, zeroed out
     */
    T zero();
}
