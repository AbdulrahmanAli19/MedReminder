package com.example.itijavaproject.data;

public class Repository {

    private static Repository repository;

    private Repository() {
    }

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

}
