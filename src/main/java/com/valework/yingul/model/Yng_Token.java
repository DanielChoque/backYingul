package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_Token {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tokenId", nullable = false, updatable = false)
    private long tokenId;
	private String apiKey;
	private String secretKey;
	private String token;
	private String refresh_token;
	
	public long getTokenId() {
		return tokenId;
	}
	public void setTokenId(long tokenId) {
		this.tokenId = tokenId;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	@Override
	public String toString() {
		return "Yng_Token [tokenId=" + tokenId + ", apiKey=" + apiKey + ", secretKey=" + secretKey + ", token=" + token
				+ ", refresh_token=" + refresh_token + "]";
	}
	
	
	
	

}
