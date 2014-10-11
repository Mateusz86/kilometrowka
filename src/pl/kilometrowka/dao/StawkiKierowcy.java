package pl.kilometrowka.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table STAWKI_KIEROWCY.
 */
public class StawkiKierowcy {

    private Long id;
    private Integer km;
    private Double cena;

    public StawkiKierowcy() {
    }

    public StawkiKierowcy(Long id) {
        this.id = id;
    }

    public StawkiKierowcy(Long id, Integer km, Double cena) {
        this.id = id;
        this.km = km;
        this.cena = cena;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

}
