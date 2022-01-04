package com.freeefly.learningspringwebflux.reactive;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@Service
public class CartService {
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public CartService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    Mono<Cart> addToCart(String cartId, String id) {
        return this.cartRepository.findById(cartId)
                .defaultIfEmpty(new Cart(cartId))
                .flatMap(cart -> cart.getCartItems().stream()
                        .filter(cartItem -> cartItem.getItem().getId().equals(id))
                        .findAny()
                        .map(cartItem -> {
                            cartItem.increment();
                            return Mono.just(cart);
                        })
                        .orElseGet(() ->
                                itemRepository.findById(id)
                                        .doOnNext(item -> {
                                            CartItem cartItem = new CartItem(item);
                                            cart.getCartItems().add(cartItem);
                                        })
                                        .map(item -> cart)
                        )
                )
                .flatMap(cart -> cartRepository.save(cart))
                ;
    }
}
