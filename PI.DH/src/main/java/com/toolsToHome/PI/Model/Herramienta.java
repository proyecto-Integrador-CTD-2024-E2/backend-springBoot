package com.toolsToHome.PI.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Herramientas")

public class Herramienta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< HEAD
    public Herramienta(Long id, Long stock, String nombre) {
        this.id = id;
        this.stock = stock;
        this.nombre = nombre;
    }
=======
>>>>>>> 170a6db611478c52f6694a7574cfa9a45c89c966

    @ManyToOne
    @JoinColumn(name = "Categoria_id", referencedColumnName= "id")
    private Categoria categoria;

    @Column(nullable = false)
    private Long stock;

    @Column(nullable = false)
    private Long precio;


    @Column(nullable = false, length = 1000)
    private boolean disponibilidad;


    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, length = 1000)
    private String descripcion;

    @JsonIgnore
    @ManyToMany(mappedBy = "herramientasFavoritas")
    private List<Usuario>  usuariosFavoritos = new ArrayList<>();


    @ManyToMany
    @JoinTable(name = "Herramienta_Categoria",
            joinColumns = @JoinColumn(name = "herramienta_id"),
            inverseJoinColumns = @JoinColumn(name = "caracteristica_id"))
    private List<Caracteristicas> caracteristicas = new ArrayList<>();



    @OneToMany(mappedBy = "herramienta", cascade = CascadeType.ALL)
    private List<Imagen> imagenes = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "herramientaId", fetch = FetchType.LAZY)
    private Set<Reserva> reserva = new HashSet<>();
    @JsonIgnoreProperties("herramienta_idReseña")
    @OneToMany(mappedBy = "herramienta_idReseña", fetch = FetchType.LAZY)
    private Set<Reseña>reseñas=new HashSet<>();


    public Herramienta(Categoria categoria, Long stock, Long precio, String nombre, String descripcion, List<Caracteristicas> caracteristicas, List<Imagen> imagenes, Set<Reseña> reseñas) {
        this.categoria = categoria;
        this.stock = stock;
        this.precio = precio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.caracteristicas = caracteristicas;
        this.imagenes = imagenes;
        this.reseñas = reseñas;
    }

    public Herramienta(Categoria categoria, Long stock, Long precio, String nombre, String descripcion, List<Imagen> imagenes) {
        this.categoria = categoria;
        this.stock = stock;
        this.precio = precio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenes = imagenes;
    }


    public Herramienta(Categoria categoria, Long stock, Long precio, String nombre, String descripcion, List<Caracteristicas> caracteristicas, List<Imagen> imagenes) {
        this.categoria = categoria;
        this.stock = stock;
        this.precio = precio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.caracteristicas = caracteristicas;
        this.imagenes = imagenes;
    }


    public Herramienta(Long id) {
        this.id = id;
    }
}
/*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long stock;
    @Column(nullable = false)
    private Long precio;
    @Column(nullable = false)
    private boolean disponibilidad;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "Categoria_id", referencedColumnName= "id")
    private Categoria categoria;

    @OneToMany(mappedBy = "herramientaId",cascade = CascadeType.MERGE)
    private Set<Reserva> reserva = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "herramienta_caracteristica",
            joinColumns = @JoinColumn(name = "herramienta_id"),
            inverseJoinColumns = @JoinColumn(name = "caracteristica_id"))
    private List<Caracteristicas> caracteristicas = new ArrayList<>();

    @OneToMany(mappedBy = "herramienta", cascade = CascadeType.ALL)
    private List<Imagen> imagenes = new ArrayList<>();

<<<<<<< HEAD
    public Herramienta(Long id) {
        this.id = id;
    }
}
=======
    @JsonIgnore
    @ManyToMany(mappedBy = "herramientasFavoritas")
    private List<Usuario>  usuariosFavoritos = new ArrayList<>();*/
>>>>>>> 170a6db611478c52f6694a7574cfa9a45c89c966
