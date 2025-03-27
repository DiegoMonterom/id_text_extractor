package com.innovatio_software.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "id_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdReaderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "document_number")
    private String documentNumber;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @Column(name = "lugar_de_nacimiento")
    private String lugarDeNacimiento;
    @Column(name = "estatura")
    private String estatura;
    @Column(name = "rh")
    private String rh;
    @Column(name = "sexo")
    private String sexo;
    @Column(name = "fecha_de_expedicion")
    private String fechaDeExpedicion;
    @Column(name = "lugar_de_expedicion")
    private String lugarDeExpedicion;
    

    /*public IdReaderModel(String firstName, String lastName, String documentNumber, String dateOfBirth, String lugarDeNacimiento, String estatura, String rh, String sexo, String fechaDeExpedicion, String lugarDeExpedicion) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.dateOfBirth = dateOfBirth;
        this.lugarDeNacimiento = lugarDeNacimiento;
        this.estatura = estatura;
        this.rh = rh;
        this.sexo = sexo;
        this.fechaDeExpedicion = fechaDeExpedicion;
        this.lugarDeExpedicion = lugarDeExpedicion;
    }*/


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocumentNumber() {
        return this.documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLugarDeNacimiento() {
        return this.lugarDeNacimiento;
    }

    public void setLugarDeNacimiento(String lugarDeNacimiento) {
        this.lugarDeNacimiento = lugarDeNacimiento;
    }

    public String getEstatura() {
        return this.estatura;
    }

    public void setEstatura(String estatura) {
        this.estatura = estatura;
    }

    public String getRh() {
        return this.rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFechaDeExpedicion() {
        return this.fechaDeExpedicion;
    }

    public void setFechaDeExpedicion(String fechaDeExpedicion) {
        this.fechaDeExpedicion = fechaDeExpedicion;
    }

    public String getLugarDeExpedicion() {
        return this.lugarDeExpedicion;
    }

    public void setLugarDeExpedicion(String lugarDeExpedicion) {
        this.lugarDeExpedicion = lugarDeExpedicion;
    }

}
