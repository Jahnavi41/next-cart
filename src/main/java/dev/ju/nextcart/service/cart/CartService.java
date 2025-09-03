package dev.ju.nextcart.service.cart;

import dev.ju.nextcart.exceptions.BadRequestException;
import dev.ju.nextcart.model.Cart;
import dev.ju.nextcart.repository.CartItemRepository;
import dev.ju.nextcart.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new BadRequestException("Cart not found!"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart() {
        Cart cart = new Cart();
        return cartRepository.save(cart).getId();
    }
}
