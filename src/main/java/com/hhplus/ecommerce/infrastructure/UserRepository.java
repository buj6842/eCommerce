package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
