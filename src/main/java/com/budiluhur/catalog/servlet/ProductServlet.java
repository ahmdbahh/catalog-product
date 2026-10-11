package com.budiluhur.catalog.servlet;

import com.budiluhur.catalog.model.Product;
import com.budiluhur.catalog.repository.ProductRepository;
import com.budiluhur.catalog.exception.ProductNotFoundException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private ProductRepository productRepository;

    @Override
    public void init() {
        // Inisialisasi Repository dan data awal
        productRepository = new ProductRepository();
        productRepository.addProduct(new Product(
            "PRD-01", "Keyboard Mechanical", 450000.0));
        productRepository.addProduct(new Product(
            "PRD-02", "Mouse Wireless Silent", 175000.0));
        productRepository.addProduct(new Product(
            "PRD-03", "Monitor Gaming 24 Inch", 2100000.0));
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        // 1. Ambil query parameter "id"
        String id = request.getParameter("id");

        // 2. Jika ada id -> tampilkan detail produk
        if (id != null && !id.trim().isEmpty()) {
            try {
                Product p = productRepository.findById(id);
                PrintWriter out = response.getWriter();

                out.println("<html>");
                out.println("<head><title>Detail Produk</title></head>");
                out.println("<body>");
                out.println("<h2>=== DETAIL PRODUK ===</h2>");
                out.println("<table border='1' cellpadding='8'>");
                out.println("<tr><th>ID Produk</th><td>"
                    + escapeHtml(p.getId()) + "</td></tr>");
                out.println("<tr><th>Nama Produk</th><td>"
                    + escapeHtml(p.getName()) + "</td></tr>");
                out.println("<tr><th>Harga Satuan</th><td>Rp"
                    + p.getPrice() + "</td></tr>");
                out.println("</table>");
                out.println("<p><a href='/products'>"
                    + "&laquo; Kembali ke daftar produk</a></p>");
                out.println("</body>");
                out.println("</html>");
            } catch (ProductNotFoundException e) {
                // 3. Produk tidak ditemukan -> status 404 + pesan merah
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                PrintWriter out = response.getWriter();

                out.println("<html>");
                out.println("<head><title>Produk Tidak Ditemukan</title></head>");
                out.println("<body>");
                out.println("<h2 style='color:red;'>Error 404: "
                    + "Produk dengan ID '" + escapeHtml(id)
                    + "' tidak ditemukan.</h2>");
                out.println("<p><a href='/products'>"
                    + "&laquo; Kembali ke daftar produk</a></p>");
                out.println("</body>");
                out.println("</html>");
            }
            return;
        }
        
        // Tanpa parameter id -> tampilkan seluruh daftar    
        PrintWriter out = response.getWriter();
        List<Product> products = productRepository.findAll();

        out.println("<html>");
        out.println("<head><title>Katalog "
            + "Produk Web</title></head>");
        out.println("<body>");
        out.println("<h2>=== DAFTAR KATALOG "
            + "PRODUK (WEB) ===</h2>");
        out.println("<table border='1' "
            + "cellpadding='8'>");
        out.println("<tr><th>ID Produk</th>"
            + "<th>Nama Produk</th><th>Harga "
            + "Satuan</th></tr>");

        for (Product p : products) {
            out.println("<tr>");
            out.println("<td>" + p.getId()
                + "</td>");
            out.println("<td>" + p.getName()
                + "</td>");
            out.println("<td>Rp" + p.getPrice()
                + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    // Mencegah input pengguna (query parameter) dirender sebagai HTML mentah
    private String escapeHtml(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#39;");
    }
}