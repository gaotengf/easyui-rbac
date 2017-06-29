package cn.gson.crm.model.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cn.gson.crm.model.domain.Member;

@Repository
public interface MemberDao extends PagingAndSortingRepository<Member, Long> {

	public Page<Member> findByUserNameLike(Pageable pg,String userName);
	
	public Member findByUserName(String userName);
}
