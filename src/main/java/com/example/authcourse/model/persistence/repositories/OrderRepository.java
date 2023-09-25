package com.example.authcourse.model.persistence.repositories;

import com.example.authcourse.model.persistence.User;
import com.example.authcourse.model.persistence.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<UserOrder, Long> {
	List<UserOrder> findByUser(User user);
}
