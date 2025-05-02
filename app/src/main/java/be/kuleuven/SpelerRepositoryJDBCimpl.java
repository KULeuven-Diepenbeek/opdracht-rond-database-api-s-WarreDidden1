package be.kuleuven;

import java.sql.Connection;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
      prepared.setInt(1, speler.getTennisvlaanderenId()); // First questionmark
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
    getSpelerByTennisvlaanderenId(speler.getTennisvlaanderenId());
    try {
      PreparedStatement prepared = (PreparedStatement) connection
          .prepareStatement("UPDATE speler SET naam = ?, punten = ? WHERE tennisvlaanderenId = ?;");
      prepared.setString(1, speler.getNaam()); // First questionmark
      prepared.setInt(2, speler.getPunten()); // Second questionmark
      prepared.setInt(3, speler.getTennisvlaanderenId()); // Third questionmark
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
      getSpelerByTennisvlaanderenId(tennisvlaanderenid);
    String resultString = null;

    try {
        PreparedStatement prepared = (PreparedStatement) connection
          .prepareStatement("SELECT t.clubnaam, w.finale, w.winnaar " +
                            "FROM wedstrijd w " +
                            "JOIN tornooi t ON w.tornooi = t.id " +
                            "WHERE (w.speler1 = ? OR w.speler2 = ?) " +
                            "ORDER BY w.finale ASC " +
                            "LIMIT 1");
        prepared.setInt(1, tennisvlaanderenid);
        prepared.setInt(2, tennisvlaanderenid);
        ResultSet result = prepared.executeQuery();

        if (result.next()) {
          String clubnaam = result.getString("clubnaam");
          int finale = result.getInt("finale");
          Integer winnaar = result.getObject("winnaar") != null ? result.getInt("winnaar") : null;

          String finaleString;
          if (finale == 1 && winnaar != null && winnaar == tennisvlaanderenid) {
             finaleString = "winst";
          } else if (finale == 1) {
            finaleString = "finale";
          } else if (finale == 2) {
            finaleString = "halve-finale";
          } else if (finale == 4) {
            finaleString = "kwart-finale";
          } else {
            finaleString = "lager dan de kwart-finales";
          }

          resultString = "Hoogst geplaatst in het tornooi van " + clubnaam + " met plaats in de " + finaleString;
        } else {
          resultString = "Geen resultaten van deze speler";
        }

        result.close();
        prepared.close();
        connection.commit();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return resultString;
  }


  @Override
  public void addSpelerToTornooi(int tornooiId, int tennisvlaanderenId) {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    //throw new UnsupportedOperationException("Unimplemented method 'addSpelerToTornooi'");
    getSpelerByTennisvlaanderenId(tennisvlaanderenId);
    try {
      PreparedStatement prepared = (PreparedStatement) connection
        .prepareStatement("INSERT INTO speler_speelt_tornooi (speler, tornooi) VALUES (?, ?)");
        prepared.setInt(1, tennisvlaanderenId);
        prepared.setInt(2, tornooiId);
        prepared.executeUpdate();

        prepared.close();
        connection.commit();
    } catch (Exception e){
      throw new RuntimeException(e);
    }
  }

  @Override
  public void removeSpelerFromTornooi(int tornooiId, int tennisvlaanderenId) {
    // TODO: verwijder de "throw new UnsupportedOperationException" en schrijf de code die de gewenste methode op de juiste manier implementeerd zodat de testen slagen.
    
    //throw new UnsupportedOperationException("Unimplemented method 'removeSpelerFromTornooi'");
    getSpelerByTennisvlaanderenId(tennisvlaanderenId);
    try {
      PreparedStatement prepared = (PreparedStatement) connection
        .prepareStatement("DELETE FROM speler_speelt_tornooi WHERE speler = ? AND tornooi = ?");
        prepared.setInt(1, tennisvlaanderenId);
        prepared.setInt(2, tornooiId);
        prepared.executeUpdate();

        prepared.close();
        connection.commit();
    } catch (Exception e){
      throw new RuntimeException(e);
    }
  }
}
