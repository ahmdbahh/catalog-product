package com.budiluhur.catalog;

import com.budiluhur.catalog.model.Product;
import com.budiluhur.catalog.repository.ProductRepository;
import com.budiluhur.catalog.exception.ProductNotFoundException;

public class Application {
    public static void main(String[] args) {
        ProductRepository repo = new ProductRepository();

        repo.addProduct(new Product("PRD-01", "Keyboard Mechanical", 450000.0));
        repo.addProduct(new Product("PRD-02", "Mouse Wireless", 175000.0));

        System.out.println("=== DAFTAR SELURUH PRODUK ===");
        repo.findAll().forEach(System.out::println);

        System.out.println("\n=== PENCARIAN PRODUK ===");
        try {
            Product p = repo.findById("PRD-99"); // ID tidak ditemukan
            System.out.println("Ditemukan: " + p);
        } catch (ProductNotFoundException e) {
            System.err.println("Error Terjadi: " + e.getMessage());
        }

        // ===== Mini Challenge: hapus produk =====
        System.out.println("\n=== HAPUS PRODUK ===");
        try {
            boolean deleted = repo.deleteById("PRD-02"); // ID ada
            System.out.println("Produk PRD-02 berhasil dihapus: " + deleted);
        } catch (ProductNotFoundException e) {
            System.err.println("Error Terjadi: " + e.getMessage());
        }

        try {
            repo.deleteById("PRD-99"); // ID tidak ada
            System.out.println("Produk PRD-99 berhasil dihapus");
        } catch (ProductNotFoundException e) {
            System.err.println("Error Terjadi: " + e.getMessage());
        }

        System.out.println("\n=== DAFTAR PRODUK SETELAH PENGHAPUSAN ===");
        repo.findAll().forEach(System.out::println);
    }
}