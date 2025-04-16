package be.kuleuven;

import java.util.List;

import org.jdbi.v3.core.Jdbi;

public class SpelerRepositoryJDBIimpl implements SpelerRepository {
  private final Jdbi jdbi;

  // Constructor
  SpelerRepositoryJDBIimpl(String connectionString, String user, String pwd) {
    // TODO: vul verder aan of verbeter
    //this.jdbi = null;
    this.jdbi = Jdbi.create(connectionString, user, pwd);
  }

  @Override
  public void addSpelerToDb(Speler speler) {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    //throw new UnsupportedOperationException("Unimplemented method 'addSpelerToDb'");
    jdbi.useTransaction(handle -> {
      handle.createUpdate("INSERT INTO speler (tennisvlaanderenId, naam, punten) VALUES (:tennisvlaanderenId, :naam, :punten)")
        .bindBean(speler)
        .execute();
    });
  }

  @Override
  public Speler getSpelerByTennisvlaanderenId(int tennisvlaanderenId) {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    //throw new UnsupportedOperationException("Unimplemented method 'getSpelerByTennisvlaanderenId'");
    return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM speler WHERE tennisvlaanderenId = :tennisvlaanderenId")
        .bind("tennisvlaanderenId", tennisvlaanderenId)
        .mapTo(Speler.class)
        .findOne()
        .orElseThrow(() -> new InvalidSpelerException(tennisvlaanderenId + "")));
  }

  @Override
  public List<Speler> getAllSpelers() {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    //throw new UnsupportedOperationException("Unimplemented method 'getAllSpelers'");
    return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM speler")
        .mapTo(Speler.class)
        .list());
  }

  @Override
  public void updateSpelerInDb(Speler speler) {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    throw new UnsupportedOperationException("Unimplemented method 'updateSpelerInDb'");
  }

  @Override
  public void deleteSpelerInDb(int tennisvlaanderenid) {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    throw new UnsupportedOperationException("Unimplemented method 'deleteSpelerInDb'");
  }

  @Override
  public String getHoogsteRankingVanSpeler(int tennisvlaanderenid) {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    throw new UnsupportedOperationException("Unimplemented method 'getHoogsteRankingVanSpeler'");
  }

  @Override
  public void addSpelerToTornooi(int tornooiId) {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    throw new UnsupportedOperationException("Unimplemented method 'addSpelerToTornooi'");
  }

  @Override
  public void removeSpelerFromTornooi(int tornooiId) {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    throw new UnsupportedOperationException("Unimplemented method 'removeSpelerFromTornooi'");
  }
}
