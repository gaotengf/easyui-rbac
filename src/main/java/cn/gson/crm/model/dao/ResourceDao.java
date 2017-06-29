package cn.gson.crm.model.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cn.gson.crm.model.domain.Resource;

@Repository
public interface ResourceDao extends PagingAndSortingRepository<Resource, Long> {

	List<Resource> findByParentIsNull();
	
	List<Resource> findByParent(Resource resource);
	
	
	//查找可用的资源
	List<Resource> findByStatusAndParentIsNull(Boolean status);
	
	List<Resource> findByStatusAndParent(Boolean status, Resource resource);
	
	List<Resource> findByStatus(Boolean status,Sort sort);
}
