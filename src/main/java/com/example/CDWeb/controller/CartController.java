package com.example.CDWeb.controller;

import com.example.CDWeb.model.*;
import com.example.CDWeb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private CartItemRepository cartItemRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy giỏ hàng");
        }

        List<CartItem> cartItems = cartService.findByCartId(cart.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("cartId", cart.getId());
        response.put("userId", cart.getUserId());

        List<Map<String, Object>> products = cartItems.stream().map(item -> {
            Map<String, Object> productInfo = new HashMap<>();
            Product product = item.getProduct();
            Size size = item.getSize();
            Color color = item.getColor();

            productInfo.put("id", product.getId());
            productInfo.put("name", product.getName());
            productInfo.put("quantity", item.getQuantity());
            productInfo.put("img", product.getImg());
            // Size
            Map<String, Object> sizeInfo = new HashMap<>();
            sizeInfo.put("id", size.getId());
            sizeInfo.put("value", size.getValue()); // ví dụ name: "42" hoặc "XL"
            productInfo.put("size", sizeInfo);

            // Color
            Map<String, Object> colorInfo = new HashMap<>();
            colorInfo.put("id", color.getId());
            colorInfo.put("name", color.getName()); // ví dụ: "Red", "Black"
            productInfo.put("color", colorInfo);

            return productInfo;
        }).toList();

        response.put("products", products);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartUpdateRequest request , @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Không có token hoặc token không hợp lệ
        }

        // Lấy token từ header
        String token = authorizationHeader.substring(7); // Loại bỏ phần "Bearer " ở đầu

        // Kiểm tra token trước khi thực hiện các thao tác
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  // Token không hợp lệ
        }
        Cart cart = cartService.getCartByUserId(request.getUserId());
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(request.getUserId());
            cartService.saveCart(cart);
        }

        CartItem existingItem = cartService.findByCartIdAndProductIdAndSizeIdAndColorId(
                cart.getId(), request.getProductId(), request.getSizeId(), request.getColorId()
        );

        Product product = productService.getProductById(request.getProductId());

        Size size = sizeService.getSizeById(request.getSizeId());

        Color color = colorService.getColorById(request.getColorId());
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            cartService.saveItem(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product); // or load from repo
            newItem.setSize(size);
            newItem.setColor(color);
            newItem.setQuantity(request.getQuantity());
            cartService.saveItem(newItem);
        }

        return ResponseEntity.ok("Thêm sản phẩm vào giỏ hàng thành công!");
    }

    @PostMapping("/decrease")
    public ResponseEntity<?> decreaseCartItem(@RequestBody CartUpdateRequest request , @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Không có token hoặc token không hợp lệ
        }

        // Lấy token từ header
        String token = authorizationHeader.substring(7); // Loại bỏ phần "Bearer " ở đầu

        // Kiểm tra token trước khi thực hiện các thao tác
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  // Token không hợp lệ
        }
        Cart cart = cartService.getCartByUserId(request.getUserId());
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy giỏ hàng");
        }

        CartItem existingItem = cartService.findByCartIdAndProductIdAndSizeIdAndColorId(
                cart.getId(), request.getProductId(), request.getSizeId(), request.getColorId()
        );


        if (existingItem != null) {
            if(existingItem.getQuantity()>=1){
                existingItem.setQuantity(existingItem.getQuantity() - request.getQuantity());
                cartService.saveItem(existingItem);
            }else{
                cartService.deleteItem(existingItem);
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm trong giỏ hàng");
        }

        return ResponseEntity.ok("Giảm số lượng thành công");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteCartItem(@RequestBody CartUpdateRequest request , @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Không có token hoặc token không hợp lệ
        }

        // Lấy token từ header
        String token = authorizationHeader.substring(7); // Loại bỏ phần "Bearer " ở đầu

        // Kiểm tra token trước khi thực hiện các thao tác
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  // Token không hợp lệ
        }
        Cart cart = cartService.getCartByUserId(request.getUserId());
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy giỏ hàng");
        }

        CartItem existingItem = cartService.findByCartIdAndProductIdAndSizeIdAndColorId(
                cart.getId(), request.getProductId(), request.getSizeId(), request.getColorId()
        );

        if (existingItem != null) {
                cartService.deleteItem(existingItem);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm trong giỏ hàng");
        }

        return ResponseEntity.ok("Xóa sản phẩm thành công");
    }
}
