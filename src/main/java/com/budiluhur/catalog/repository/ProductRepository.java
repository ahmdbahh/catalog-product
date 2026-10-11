package com.budiluhur.catalog.repository;

import com.budiluhur.catalog.model.Product;
import com.budiluhur.catalog.exception.ProductNotFoundException;
import java.util.*;

public class ProductRepository {
    private List<Product> productList = new ArrayList<>();

    public void addProduct(Product product) {
        productList.add(product);
    }

    public List<Product> findAll() {
        return productList;
    }

    public Product findById(String id) throws ProductNotFoundException {
        for (Product p : productList) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        throw new ProductNotFoundException("Produk dengan ID '" + id + "' tidak ditemukan!");
    }

    // ===== Mini Challenge =====
    public boolean deleteById(String id) throws ProductNotFoundException {
        // findById otomatis melempar ProductNotFoundException jika ID tidak ada
        Product p = findById(id);
        productList.remove(p);
        return true;
    }
}