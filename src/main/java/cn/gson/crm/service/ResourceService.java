package cn.gson.crm.service;

import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gson.crm.model.dao.ResourceDao;
import cn.gson.crm.model.domain.Resource;

@Service
public class ResourceService {

	Logger logger = Logger.getLogger(ResourceService.class);

	@Autowired
	ResourceDao resourceDao;

	/**
	 * 获取资源树
	 * @param status
	 * @return
	 */
	public Iterable<Resource> getResourceTree(Boolean status) {
		Iterable<Resource> root;
		if (status == null) {
			root = resourceDao.findByParentIsNull();
		} else {
			root = resourceDao.findByStatusAndParentIsNull(status);
		}
		this.buildTree(root, status);
		return root;
	}

	public Iterable<Resource> getResourceTree() {
		return this.getResourceTree(null);
	}

	private void buildTree(Iterable<Resource> root, Boolean status) {
		root.forEach(new Consumer<Resource>() {
			@Override
			public void accept(Resource t) {
				Iterable<Resource> chidlren;
				
				if (status == null) {
					chidlren = resourceDao.findByParent(t);
				} else {
					chidlren = resourceDao.findByStatusAndParent(status, t);
				}

				chidlren.forEach(new Consumer<Resource>() {
					@Override
					public void accept(Resource c) {
						t.getChildren().add(c);
					}
				});

				// 此处递归
				buildTree(chidlren, status);
			}
		});
	}
}
