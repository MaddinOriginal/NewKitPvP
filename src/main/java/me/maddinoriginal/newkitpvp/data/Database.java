package me.maddinoriginal.newkitpvp.data;

import me.maddinoriginal.newkitpvp.data.models.PlayerKits;
import me.maddinoriginal.newkitpvp.data.models.PlayerStats;

import java.sql.*;

public class Database {

    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (connection != null) {
            return connection;
        }

        String url = "jdbc:mysql://localhost/kit_pvp";
        String user = "root";
        String password = "";

        this.connection = DriverManager.getConnection(url, user, password);

        System.out.println("Successfully connected to the Kit-PvP Database.");

        return this.connection;
    }

    public void initializeDatabases() throws SQLException {
        initializePlayerStatsTable();
        initializePlayerKitsTable();
    }

    public void initializePlayerStatsTable() throws SQLException {
        Statement statement = getConnection().createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS player_stats(uuid varchar(36) primary key, kills int, deaths int, coins int, tokens int)";
        statement.execute(sql);
        statement.close();

        System.out.println("Successfully connected to the STATS table in the Database.");
    }

    public void initializePlayerKitsTable() throws SQLException {
        Statement statement = getConnection().createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS player_kits(uuid varchar(36) primary key, arbalist boolean, assassin boolean, blaster boolean, bomber boolean, elementalist boolean, ghost boolean, magician boolean, ninja boolean, pyromane boolean, teleporter boolean, yeti boolean)";
        statement.execute(sql);
        statement.close();

        System.out.println("Successfully connected to the KITS table in the Database.");
    }

    public PlayerStats findPlayerStatsByUUID(String uuid) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM player_stats WHERE uuid = ?");
        statement.setString(1, uuid);
        ResultSet results = statement.executeQuery();

        if (results.next()) {
            int kills = results.getInt("kills");
            int deaths = results.getInt("deaths");
            int coins = results.getInt("coins");
            int tokens = results.getInt("tokens");

            PlayerStats playerStats = new PlayerStats(uuid, kills, deaths, coins, tokens);

            statement.close();
            return playerStats;
        }

        statement.close();
        return null;
    }

    public PlayerKits findPlayerKitsByUUID(String uuid) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM player_kits WHERE uuid = ?");
        statement.setString(1, uuid);
        ResultSet results = statement.executeQuery();

        if (results.next()) {
            boolean arbalist = results.getBoolean("arbalist");
            boolean assassin = results.getBoolean("assassin");
            boolean blaster = results.getBoolean("blaster");
            boolean bomber = results.getBoolean("bomber");
            boolean elementalist = results.getBoolean("elementalist");
            boolean ghost = results.getBoolean("ghost");
            boolean magician = results.getBoolean("magician");
            boolean ninja = results.getBoolean("ninja");
            boolean pyro = results.getBoolean("pyro");
            boolean teleporter = results.getBoolean("teleporter");
            boolean yeti = results.getBoolean("yeti");


            PlayerKits playerKits = new PlayerKits(uuid, arbalist, assassin, blaster, bomber, elementalist, ghost, magician, ninja, pyro, teleporter, yeti);

            statement.close();
            return playerKits;
        }

        statement.close();
        return null;
    }

    public void createPlayerStats(PlayerStats stats) throws SQLException {

        String sql = "INSERT INTO player_stats(uuid, kills, deaths, coins, tokens) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = getConnection().prepareStatement(sql);

        statement.setString(1, stats.getUuid());
        statement.setInt(2, stats.getKills());
        statement.setInt(3, stats.getDeaths());
        statement.setInt(4, stats.getCoins());
        statement.setInt(5, stats.getTokens());

        statement.executeUpdate();
        statement.close();
    }

    public void createPlayerKits(PlayerKits kits) throws SQLException {
        String sql = "INSERT INTO player_kits(uuid, arbalist, assassin, blaster, bomber, elementalist, ghost, magician, ninja, pyromane, teleporter, yeti) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = getConnection().prepareStatement(sql);

        statement.setString(1, kits.getUuid());
        statement.setBoolean(2, kits.hasArbalist());
        statement.setBoolean(3, kits.hasAssassin());
        statement.setBoolean(4, kits.hasBlaster());
        statement.setBoolean(5, kits.hasBomber());
        statement.setBoolean(6, kits.hasElementalist());
        statement.setBoolean(7, kits.hasGhost());
        statement.setBoolean(8, kits.hasMagician());
        statement.setBoolean(9, kits.hasNinja());
        statement.setBoolean(10, kits.hasPyro());
        statement.setBoolean(11, kits.hasTeleporter());
        statement.setBoolean(12, kits.hasYeti());

        statement.executeUpdate();
        statement.close();
    }

    public void updatePlayerStats(PlayerStats stats) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("UPDATE player_stats SET kills = ?, deaths = ?, coins = ?, tokens = ? WHERE uuid = ?");

        statement.setInt(1, stats.getKills());
        statement.setInt(2, stats.getDeaths());
        statement.setInt(3, stats.getCoins());
        statement.setInt(4, stats.getTokens());
        statement.setString(5, stats.getUuid());

        statement.executeUpdate();
        statement.close();
    }

    public void updatePlayerKits(PlayerKits kits) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("UPDATE player_kits SET arbalist = ?, assassin = ?, blaster = ?, bomber = ?, elementalist = ?, ghost = ?, magician = ?, ninja = ?, pyromane = ?, teleporter = ?, yeti = ? WHERE uuid = ?");

        statement.setBoolean(1, kits.hasArbalist());
        statement.setBoolean(2, kits.hasAssassin());
        statement.setBoolean(3, kits.hasBlaster());
        statement.setBoolean(4, kits.hasBomber());
        statement.setBoolean(5, kits.hasElementalist());
        statement.setBoolean(6, kits.hasGhost());
        statement.setBoolean(7, kits.hasMagician());
        statement.setBoolean(8, kits.hasNinja());
        statement.setBoolean(9, kits.hasPyro());
        statement.setBoolean(10, kits.hasTeleporter());
        statement.setBoolean(11, kits.hasYeti());
        statement.setString(12, kits.getUuid());

        statement.executeUpdate();
        statement.close();
    }

    public void deletePlayerStats(String uuid) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("DELETE FROM player_stats WHERE uuid = ?");
        statement.setString(1, uuid);
        statement.executeUpdate();
        statement.close();
    }

    public void deletePlayerKits(String uuid) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("DELETE FROM player_kits WHERE uuid = ?");
        statement.setString(1, uuid);
        statement.executeUpdate();
        statement.close();
    }

    public void closeConnection() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
