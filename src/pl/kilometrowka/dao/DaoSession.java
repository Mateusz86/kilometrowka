package pl.kilometrowka.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import pl.kilometrowka.dao.StawkiKierowcy;
import pl.kilometrowka.dao.Profil;
import pl.kilometrowka.dao.Tydzien;
import pl.kilometrowka.dao.StawkiPasazera;
import pl.kilometrowka.dao.Dzien;
import pl.kilometrowka.dao.Trasa;

import pl.kilometrowka.dao.StawkiKierowcyDao;
import pl.kilometrowka.dao.ProfilDao;
import pl.kilometrowka.dao.TydzienDao;
import pl.kilometrowka.dao.StawkiPasazeraDao;
import pl.kilometrowka.dao.DzienDao;
import pl.kilometrowka.dao.TrasaDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig stawkiKierowcyDaoConfig;
    private final DaoConfig profilDaoConfig;
    private final DaoConfig tydzienDaoConfig;
    private final DaoConfig stawkiPasazeraDaoConfig;
    private final DaoConfig dzienDaoConfig;
    private final DaoConfig trasaDaoConfig;

    private final StawkiKierowcyDao stawkiKierowcyDao;
    private final ProfilDao profilDao;
    private final TydzienDao tydzienDao;
    private final StawkiPasazeraDao stawkiPasazeraDao;
    private final DzienDao dzienDao;
    private final TrasaDao trasaDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        stawkiKierowcyDaoConfig = daoConfigMap.get(StawkiKierowcyDao.class).clone();
        stawkiKierowcyDaoConfig.initIdentityScope(type);

        profilDaoConfig = daoConfigMap.get(ProfilDao.class).clone();
        profilDaoConfig.initIdentityScope(type);

        tydzienDaoConfig = daoConfigMap.get(TydzienDao.class).clone();
        tydzienDaoConfig.initIdentityScope(type);

        stawkiPasazeraDaoConfig = daoConfigMap.get(StawkiPasazeraDao.class).clone();
        stawkiPasazeraDaoConfig.initIdentityScope(type);

        dzienDaoConfig = daoConfigMap.get(DzienDao.class).clone();
        dzienDaoConfig.initIdentityScope(type);

        trasaDaoConfig = daoConfigMap.get(TrasaDao.class).clone();
        trasaDaoConfig.initIdentityScope(type);

        stawkiKierowcyDao = new StawkiKierowcyDao(stawkiKierowcyDaoConfig, this);
        profilDao = new ProfilDao(profilDaoConfig, this);
        tydzienDao = new TydzienDao(tydzienDaoConfig, this);
        stawkiPasazeraDao = new StawkiPasazeraDao(stawkiPasazeraDaoConfig, this);
        dzienDao = new DzienDao(dzienDaoConfig, this);
        trasaDao = new TrasaDao(trasaDaoConfig, this);

        registerDao(StawkiKierowcy.class, stawkiKierowcyDao);
        registerDao(Profil.class, profilDao);
        registerDao(Tydzien.class, tydzienDao);
        registerDao(StawkiPasazera.class, stawkiPasazeraDao);
        registerDao(Dzien.class, dzienDao);
        registerDao(Trasa.class, trasaDao);
    }
    
    public void clear() {
        stawkiKierowcyDaoConfig.getIdentityScope().clear();
        profilDaoConfig.getIdentityScope().clear();
        tydzienDaoConfig.getIdentityScope().clear();
        stawkiPasazeraDaoConfig.getIdentityScope().clear();
        dzienDaoConfig.getIdentityScope().clear();
        trasaDaoConfig.getIdentityScope().clear();
    }

    public StawkiKierowcyDao getStawkiKierowcyDao() {
        return stawkiKierowcyDao;
    }

    public ProfilDao getProfilDao() {
        return profilDao;
    }

    public TydzienDao getTydzienDao() {
        return tydzienDao;
    }

    public StawkiPasazeraDao getStawkiPasazeraDao() {
        return stawkiPasazeraDao;
    }

    public DzienDao getDzienDao() {
        return dzienDao;
    }

    public TrasaDao getTrasaDao() {
        return trasaDao;
    }

}
