package com.smartcommunity.verification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface EmailOtpRepository extends JpaRepository<EmailOtp,UUID> {
 Optional<EmailOtp> findTopByEmailOrderByLastSentAtDesc(String email);
 void deleteByEmail(String email);
}
