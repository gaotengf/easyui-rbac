package cn.gson.crm.model.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cn.gson.crm.model.domain.Role;

@Repository
public interface RoleDao extends PagingAndSortingRepository<Role, Long> {

}
