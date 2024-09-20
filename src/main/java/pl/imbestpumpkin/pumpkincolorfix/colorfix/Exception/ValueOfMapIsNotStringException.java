package pl.imbestpumpkin.pumpkincolorfix.colorfix.Exception;

public class ValueOfMapIsNotStringException extends RuntimeException {
    public ValueOfMapIsNotStringException() {
        System.err.println("Map does not have String type as value!");
    }
}
