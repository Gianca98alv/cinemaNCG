package com.mycompany.cinemancg.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "funcion")
@NamedQueries({
    @NamedQuery(name = "Funcion.findAll", query = "SELECT f FROM Funcion f")})
public class Funcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFuncion")
    private Integer idFuncion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Precio")
    private Float precio;
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "fechaFin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcion")
    private List<Tiquete> tiqueteList;
    @JoinColumn(name = "idPelicula", referencedColumnName = "idPelicula")
    @ManyToOne(optional = false)
    private Pelicula pelicula;
    @JoinColumn(name = "idSala", referencedColumnName = "idSala")
    @ManyToOne(optional = false)
    private Sala sala;

    public Funcion() {
    }

    public Funcion(Integer idFuncion, Float precio, Date fechaInicio) {
        this.idFuncion = idFuncion;
        this.precio = precio;
        this.fechaInicio = fechaInicio;
    }
    
    public String getStringDateInicio(){
        return fixGsonFormat(fechaInicio);
    }

    public String getStringDateFin(){
        return fixGsonFormat(fechaFin);
    }
    
    public Funcion(Integer idFuncion) {
        this.idFuncion = idFuncion;
    }

    public Integer getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(Integer idFuncion) {
        this.idFuncion = idFuncion;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Tiquete> getTiqueteList() {
        return tiqueteList;
    }

    public void setTiqueteList(List<Tiquete> tiqueteList) {
        this.tiqueteList = tiqueteList;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInicio);
        calendar.add(Calendar.HOUR_OF_DAY, pelicula.getDuracion().getHours());
        calendar.add(Calendar.MINUTE, pelicula.getDuracion().getMinutes());
        fechaFin = calendar.getTime();
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFuncion != null ? idFuncion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcion)) {
            return false;
        }
        Funcion other = (Funcion) object;
        if ((this.idFuncion == null && other.idFuncion != null) || (this.idFuncion != null && !this.idFuncion.equals(other.idFuncion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.cinemancg.model.Funcion[ idFuncion=" + idFuncion + " ]";
    }

    private String fixGsonFormat(Date date){
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(date.getTime());
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            String time = sdf.format(date);
            
            if(cal.get(Calendar.AM_PM) == 0){
                time += cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":00";
            } else {
                time += (cal.get(Calendar.HOUR) + 12) + ":" + cal.get(Calendar.MINUTE) + ":00";
            }
            return time;
        } catch (Exception ex) {
            return "";
        }
    }
}
