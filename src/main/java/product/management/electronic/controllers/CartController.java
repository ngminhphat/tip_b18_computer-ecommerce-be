package product.management.electronic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import product.management.electronic.dto.Cart.CartDto;
import product.management.electronic.dto.Cart.CartItemAddDto;
import product.management.electronic.dto.User.UserDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import product.management.electronic.dto.Cart.CartDto;
import product.management.electronic.dto.Cart.CartItemAddDto;
import product.management.electronic.dto.Cart.UpdateCartDto;

import product.management.electronic.services.CartService;
import product.management.electronic.services.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public ResponseEntity<CartDto> addCart(Authentication authentication, @RequestBody CartItemAddDto request) {
        String username = authentication.getName();
        UserDto user = userService.findByUsername(username);
        request.setUserId(user.getId());
        return ResponseEntity.ok(cartService.addToCart(request));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getCartByUserId/{userId}")
    public ResponseEntity<CartDto> getCartByUserId(Authentication authentication) {
        String username = authentication.getName();
        UserDto user = userService.findByUsername(username);
        return ResponseEntity.ok(cartService.getCartByUserId(user.getId()));
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<CartDto> updateCart(
            @PathVariable UUID cartId,
            @RequestBody List<UpdateCartDto> cartItemRequests) {
        CartDto updatedCart = cartService.updateCart(cartId, cartItemRequests);
        return ResponseEntity.ok(updatedCart);
    }
}

