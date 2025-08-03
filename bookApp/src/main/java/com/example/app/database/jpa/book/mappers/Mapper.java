package com.example.app.database.jpa.book.mappers;

public interface Mapper<A, B> {

    B mapTo(A a);

    A mapFrom(B b);

}