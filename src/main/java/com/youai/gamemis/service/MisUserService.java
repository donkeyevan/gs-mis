package com.youai.gamemis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youai.gamemis.exception.MisException;
import com.youai.gamemis.model.MisUser;
import com.youai.gamemis.model.dao.MisUserDAO;
import com.youai.gamemis.util.MD5Util;

@Service("misUserService")
public class MisUserService {
	public static enum ADDUSER_RESULT{ SUCCESS, USERNAME_EXISTED }
	@Autowired
	MisUserDAO userDao;
	public List<MisUser> list(){
		List<MisUser> users = userDao.list();
		return users;
	}
	
	public String add(String username, String create_by ) throws MisException{
		MisUser user = userDao.findByName( username );
		if( user != null ){
			throw new MisException( "User with the same name has been exsited!" );
		}
		user = new MisUser();
		user.setName( username );
		user.setStatus( 1 );
		user.setCreateAt( new java.util.Date());
		user.setCreateBy( create_by );
		user.setPassword(MD5Util.toMD5("youai"));
		userDao.add( user );
		return user.getId();
	}
	
	public MisUser get( String userId ) {
		MisUser user = userDao.find( userId );
		return user;
	}
	
	public MisUser getUserByName( String userName ){
		return userDao.findByName( userName );
	}
	
	public void delete( String id ){
		userDao.delete( id );
	}
	
	public void update(MisUser user){
		userDao.updateUser(user);
	}
	
}
