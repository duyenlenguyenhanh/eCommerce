package com.example.authcourse.model.persistence.repositories;

import com.example.authcourse.model.persistence.Cart;
import com.example.authcourse.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUser(User user);
}
