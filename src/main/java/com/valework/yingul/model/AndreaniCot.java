package com.valework.yingul.model;

public class AndreaniCot {
	private String Username,
	Password
	,Localidad
	,CodigoPostal
	,Provincia;

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getLocalidad() {
		return Localidad;
	}

	public void setLocalidad(String localidad) {
		Localidad = localidad;
	}

	public String getCodigoPostal() {
		return CodigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		CodigoPostal = codigoPostal;
	}

	public String getProvincia() {
		return Provincia;
	}

	public void setProvincia(String provincia) {
		Provincia = provincia;
	}

	@Override
	public String toString() {
		return "AndreaniCot [Username=" + Username + ", Password=" + Password + ", Localidad=" + Localidad
				+ ", CodigoPostal=" + CodigoPostal + ", Provincia=" + Provincia + "]";
	}
	
	

}
