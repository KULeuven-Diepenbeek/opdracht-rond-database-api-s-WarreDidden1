package be.kuleuven;

import java.sql.Connection;
import java.util.List;
import java.sql.PreparedStatement;

public class SpelerRepositoryJDBCimpl implements SpelerRepository {
  private Connection connection;

  // Constructor
  SpelerRepositoryJDBCimpl(Connection connection) {
    // TODO: vul contructor verder aan
    this.connection = connection;
  }

  @Override
  public void addSpelerToDb(Speler speler) {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    //throw new UnsupportedOperationException("Unimplemented method 'addSpelerToDb'");
    try {
      PreparedStatement prepared = (PreparedStatement) connection
          .prepareStatement("INSERT INTO speler (tennisvlaanderenId, naam, punten) VALUES (?, ?, ?);");
      prepared.setInt(1, speler.getTennisvlaanderenid()); // First questionmark
      prepared.setString(2, speler.getNaam()); // Second questionmark
      prepared.setInt(3, speler.getPunten()); // Third questionmark
      prepared.executeUpdate();

      prepared.close();
      connection.commit();
    } catch (Exception e) {
      throw new RuntimeException(e);
      }
    }


  @Override
  public Speler getSpelerByTennisvlaanderenId(int tennisvlaanderenId) {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    //throw new UnsupportedOperationException("Unimplemented method 'getSpelerByTennisvlaanderenId'");
    Speler gevonden_speler = null;
    try {
      PreparedStatement prepared = (PreparedStatement) connection
          .prepareStatement("SELECT * FROM speler WHERE tennisvlaanderenId = ?;");
      prepared.setInt(1, tennisvlaanderenId); // First questionmark
      var resultSet = prepared.executeQuery();
      if (resultSet.next()) {
        gevonden_speler = new Speler(resultSet.getInt("tennisvlaanderenId"), resultSet.getString("naam"),
            resultSet.getInt("punten"));
      }
      if (gevonden_speler == null) {
        throw new InvalidSpelerException(tennisvlaanderenId + "");
      }
      prepared.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return gevonden_speler;
  }

  @Override
  public List<Speler> getAllSpelers() {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    //throw new UnsupportedOperationException("Unimplemented method 'getAllSpelers'");
    List<Speler> spelers = new java.util.ArrayList<>();
    try {
      PreparedStatement prepared = (PreparedStatement) connection.prepareStatement("SELECT * FROM speler;");
      var resultSet = prepared.executeQuery();
      while (resultSet.next()) {
        Speler speler = new Speler(resultSet.getInt("tennisvlaanderenId"), resultSet.getString("naam"),
            resultSet.getInt("punten"));
        spelers.add(speler);
      }
      prepared.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return spelers;
  }

  @Override
  public void updateSpelerInDb(Speler speler) {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    //throw new UnsupportedOperationException("Unimplemented method 'updateSpelerInDb'");
    //controlleer of de speler bestaat in de database
    getSpelerByTennisvlaanderenId(speler.getTennisvlaanderenid());
    try {
      PreparedStatement prepared = (PreparedStatement) connection
          .prepareStatement("UPDATE speler SET naam = ?, punten = ? WHERE tennisvlaanderenId = ?;");
      prepared.setString(1, speler.getNaam()); // First questionmark
      prepared.setInt(2, speler.getPunten()); // Second questionmark
      prepared.setInt(3, speler.getTennisvlaanderenid()); // Third questionmark
      prepared.executeUpdate();

      prepared.close();
      connection.commit();
    
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteSpelerInDb(int tennisvlaanderenid) {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    //throw new UnsupportedOperationException("Unimplemented method 'deleteSpelerInDb'");
    //controlleer of de speler bestaat in de database
    getSpelerByTennisvlaanderenId(tennisvlaanderenid);
    try {
      PreparedStatement prepared = (PreparedStatement) connection
          .prepareStatement("DELETE FROM speler WHERE tennisvlaanderenId = ?;");
      prepared.setInt(1, tennisvlaanderenid); // First questionmark
      prepared.executeUpdate();

      prepared.close();
      connection.commit();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getHoogsteRankingVanSpeler(int tennisvlaanderenid) {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    //throw new UnsupportedOperationException("Unimplemented method 'getHoogsteRankingVanSpeler'");
    String hoogsteRanking = null;
    try {
      PreparedStatement prepared = (PreparedStatement) connection
          .prepareStatement("SELECT * FROM speler WHERE tennisvlaanderenId = ?;");
      prepared.setInt(1, tennisvlaanderenid); // First questionmark
      var resultSet = prepared.executeQuery();
      if (resultSet.next()) {
        hoogsteRanking = "Hoogst geplaatst in het tornooi van " + resultSet.getString("naam") + " met plaats in de "
            + resultSet.getString("punten") + ".";
      }
      prepared.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return hoogsteRanking;
  }

  @Override
  public void addSpelerToTornooi(int tornooiId) {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    //throw new UnsupportedOperationException("Unimplemented method 'addSpelerToTornooi'");
    try {
      PreparedStatement prepared = (PreparedStatement) connection
          .prepareStatement("INSERT INTO tornooi_speler (tornooiId, spelerId) VALUES (?, ?);");
      prepared.setInt(1, tornooiId); // First questionmark
      prepared.setInt(2, 1); // Second questionmark, dit is een placeholder, je moet hier de juiste spelerId gebruiken
      prepared.executeUpdate();

      prepared.close();
      connection.commit();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void removeSpelerFromTornooi(int tornooiId) {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    
    //throw new UnsupportedOperationException("Unimplemented method 'removeSpelerFromTornooi'");
    try {
      PreparedStatement prepared = (PreparedStatement) connection
          .prepareStatement("DELETE FROM tornooi_speler WHERE tornooiId = ?;");
      prepared.setInt(1, tornooiId); // First questionmark
      prepared.executeUpdate();

      prepared.close();
      connection.commit();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
