package com.laptrinhjavaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laptrinhjavaweb.entity.NewsEntity;

// Long là kiểu dữ liệu của primary key của NewsEntity (Long id)
// k cần @Repository vì thằng cha (JpaRepository) đã có rồi.
// phải khai báo @EnableTransactionManagement trong JPAConfig.java mới dùng được JpaRepository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
	
}