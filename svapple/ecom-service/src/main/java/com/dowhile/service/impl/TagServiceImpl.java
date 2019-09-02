/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Tag;
import com.dowhile.dao.TagDAO;
import com.dowhile.service.TagService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class TagServiceImpl implements TagService{

	private TagDAO tagDAO;
	/**
	 * @return the tagDAO
	 */
	public TagDAO getTagDAO() {
		return tagDAO;
	}

	/**
	 * @param tagDAO the tagDAO to set
	 */
	public void setTagDAO(TagDAO tagDAO) {
		this.tagDAO = tagDAO;
	}

	@Override
	public Tag addTag(Tag tag,int companyId) {
		// TODO Auto-generated method stub
		return getTagDAO().addTag(tag,companyId);
	}

	@Override
	public Tag updateTag(Tag tag,int companyId) {
		// TODO Auto-generated method stub
		return getTagDAO().updateTag(tag,companyId);
	}

	@Override
	public boolean deleteTag(Tag tag,int companyId) {
		// TODO Auto-generated method stub
		return getTagDAO().deleteTag(tag,companyId);
	}

	@Override
	public Tag getTagByTagId(int tagId,int companyId) {
		// TODO Auto-generated method stub
		return getTagDAO().getTagByTagId(tagId,companyId);
	}

	@Override
	public List<Tag> getAllTags(int companyId) {
		// TODO Auto-generated method stub
		return getTagDAO().getAllTags(companyId);
	}

}
