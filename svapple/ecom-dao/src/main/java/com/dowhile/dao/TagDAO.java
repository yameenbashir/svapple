/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.Tag;

/**
 * @author Yameen Bashir
 *
 */
public interface TagDAO {

	Tag addTag(Tag tag,int companyId);
	Tag updateTag(Tag tag,int companyId);
	boolean deleteTag(Tag tag,int companyId);
	Tag getTagByTagId(int tagId,int companyId);
	List<Tag> getAllTags(int companyId);
}
