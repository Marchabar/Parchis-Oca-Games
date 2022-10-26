package com.japarejo.springmvc.member;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member,Long> {

	Member findByName(String nombre);

}
