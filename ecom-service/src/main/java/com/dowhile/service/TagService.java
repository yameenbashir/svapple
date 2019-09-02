/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.Tag;

/**
 * @author Yameen Bashir
 *
 */
public interface TagService {

	Tag addTag(Tag tag,int companyId);
	Tag updateTag(Tag tag,int companyId);
	boolean deleteTag(Tag tag,int companyId);
	Tag getTagByTagId(int tagId,int companyId);
	List<Tag> getAllTags(int companyId);
}
