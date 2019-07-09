package com.spapi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FriendsConnection {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	Integer connectedUserA;
	Integer connectedUserB;

	Integer subscribeUserA;
	Integer subscribeUserB;

	Integer blockUserA;
	Integer blockUserB;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getConnectedUserA() {
		return connectedUserA;
	}

	public void setConnectedUserA(Integer connectedUserA) {
		this.connectedUserA = connectedUserA;
	}

	public Integer getConnectedUserB() {
		return connectedUserB;
	}

	public void setConnectedUserB(Integer connectedUserB) {
		this.connectedUserB = connectedUserB;
	}

	public Integer getSubscriberUserA() {
		return subscribeUserA;
	}

	public void setSubscribeUserA(Integer subscribeUserA) {
		this.subscribeUserA = subscribeUserA;
	}

	public Integer getSubscribeUserB() {
		return subscribeUserB;
	}

	public void setSubscribeUserB(Integer subscribeUserB) {
		this.subscribeUserB = subscribeUserB;
	}

	public Integer getBlockUserA() {
		return blockUserA;
	}

	public void setBlockUserA(Integer blockUserA) {
		this.blockUserA = blockUserA;
	}

	public Integer getBlockUserB() {
		return blockUserB;
	}

	public void setBlockUserB(Integer blockUserB) {
		this.blockUserB = blockUserB;
	}

}
