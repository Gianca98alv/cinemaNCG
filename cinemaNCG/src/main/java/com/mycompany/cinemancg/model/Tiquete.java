/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cinemancg.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author User
 */
@Entity
@Table(name = "tiquete")
@NamedQueries({
    @NamedQuery(name = "Tiquete.findAll", query = "SELECT t FROM Tiquete t")})
public class Tiquete implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTiquete")
    private Integer idTiquete;
    @Column(name = "fila")
    private Integer fila;
    @Column(name = "columna")
    private Integer columna;
    @JoinColumn(name = "idFactura", referencedColumnName = "idFactura")
    @ManyToOne(optional = false)
    private Factura factura;
    @JoinColumn(name = "idFuncion", referencedColumnName = "idFuncion")
    @ManyToOne(optional = false)
    private Funcion funcion;

    public Tiquete() {
    }

    public Tiquete(Integer idTiquete) {
        this.idTiquete = idTiquete;
    }

    public Tiquete(Integer idTiquete, Integer fila, Integer columna) {
        this.idTiquete = idTiquete;
        this.fila = fila;
        this.columna = columna;
    }

    public Integer getIdTiquete() {
        return idTiquete;
    }

    public void setIdTiquete(Integer idTiquete) {
        this.idTiquete = idTiquete;
    }

    public Integer getFila() {
        return fila;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public Integer getColumna() {
        return columna;
    }

    public void setColumna(Integer columna) {
        this.columna = columna;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTiquete != null ? idTiquete.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tiquete)) {
            return false;
        }
        Tiquete other = (Tiquete) object;
        if ((this.idTiquete == null && other.idTiquete != null) || (this.idTiquete != null && !this.idTiquete.equals(other.idTiquete))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.cinemancg.model.Tiquete[ idTiquete=" + idTiquete + " ]";
    }
    
}
