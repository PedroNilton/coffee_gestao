package br.com.coffeegestao;

import br.com.coffeegestao.database.DatabaseInitializer;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initialize();

        System.out.println("Coffee Gestão iniciadod");

    }
}
