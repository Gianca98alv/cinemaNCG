package com.mycompany.cinemancg.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "pelicula")
@NamedQueries({
    @NamedQuery(name = "Pelicula.findAll", query = "SELECT p FROM Pelicula p")})
public class Pelicula implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "idPelicula")
    private String idPelicula;
    @Lob
    @Column(name = "poster")
    private String poster;
    @Column(name = "duracion")
    @Temporal(TemporalType.TIME)
    private Date duracion;
    @Size(max = 3)
    @Column(name = "clasificacion")
    private String clasificacion;
    @Column(name = "estreno")
    private Integer estreno;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pelicula")
    private List<Funcion> funcionList;
    private String fotoBase64;
    
    public Pelicula() {
    }

    public Pelicula(String idPelicula) {
        this.idPelicula = idPelicula;
    }

    public Pelicula(String idPelicula, String poster, Date duracion, String clasificacion, Integer estreno) {
        this.idPelicula = idPelicula;
        this.poster = poster;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.estreno = estreno;
    }

    public String getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(String idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Date getDuracion() {
        return duracion;
    }
    
    public String getStringDuracion(){
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        return sdf.format(duracion);
    }

    public void setDuracion(Date duracion) {
        this.duracion = duracion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public Integer getEstreno() {
        return estreno;
    }

    public void setEstreno(Integer estreno) {
        this.estreno = estreno;
    }

    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }
    
    public List<Funcion> getFuncionList() {
        return funcionList;
    }

    public void setFuncionList(List<Funcion> funcionList) {
        this.funcionList = funcionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPelicula != null ? idPelicula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pelicula)) {
            return false;
        }
        Pelicula other = (Pelicula) object;
        if ((this.idPelicula == null && other.idPelicula != null) || (this.idPelicula != null && !this.idPelicula.equals(other.idPelicula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.cinemancg.model.Pelicula[ idPelicula=" + idPelicula + " ]";
    }
    
}
