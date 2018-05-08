package de.oth.mocker;

/**
 * functional Interface that can be used for compare methods on integers
 */
@FunctionalInterface
public interface IntCompare {
    public boolean compare(int a);
}
