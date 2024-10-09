package fr.groupbees.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class Validator<T> {

    private final T object;
    private final List<String> errorMessages;

    public Validator(T object, List<String> errorMessages) {
        this.object = object;
        this.errorMessages = errorMessages;
    }

    public static <T> Validator<T> of(final T object) {
        requireNonNull(object);
        return new Validator<>(object, new ArrayList<>());
    }

    public <R> Validator<T> validate(final Function<T, R> projection,
                                     final Predicate<R> predicateOnField,
                                     final String message) {

        final boolean result = predicateOnField.test(projection.apply(object));

        if (!result) {
            errorMessages.add(message);
        }

        return this;
    }

    public <U> Validator<U> thenTo(final Function<T, U> toOtherObject) {
        return new Validator<>(toOtherObject.apply(object), errorMessages);
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public T getOrElseThrow() {
        if (errorMessages.isEmpty()) {
            return object;
        }

        var exception = new IllegalArgumentException();
        errorMessages.forEach(m -> exception.addSuppressed(new IllegalArgumentException(m)));

        throw exception;
    }
}
