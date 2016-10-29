/*
 * User.java		16/04/2015
 *
 * Copyright (C) 2016 ACKTA. All Rights Reserved.
 */
package br.com.ackta.clinicalgw.security;

/**
 *
 *
 */
public interface IUser {

	/**
	 * Returns an user's unique identifier
	 *
	 * @return user's id
	 */
	Long getId();

	/**
	 * Returns an user's unique username
	 *
	 * @return an user's username
	 */
	String getUsername();

	/**
	 * Returns an user's full name.
	 *
	 * @return an user's full name.
	 */
	String getName();

}
