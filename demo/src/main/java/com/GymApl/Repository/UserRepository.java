package com.GymApl.Repository;

import com.GymApl.Entity.EnRole;
import com.GymApl.Entity.Role;
import com.GymApl.Entity.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


@Repository
public class UserRepository {

    @Autowired
    private final DataSource dataSource;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepositoryDefault userRepositoryDefault;


    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }



    public List<Users> findAll() {

        List<Users> usersList = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                usersList.add(mapRowToUser(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }



    public Optional<Users> findById(UUID id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapRowToUser(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Users update(Users user) {
        String sql = "UPDATE users SET username = ?, password = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, user.getUsername());
            statement.setString(2, passwordEncoder(user.getPassword()));
            statement.setString(3, user.getId().toString());

            int updatedRows = statement.executeUpdate();

            if (updatedRows == 0) {
                throw new RuntimeException("Nie udało się zaktualizować danych użytkownika o ID: " + user.getId());
            }
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Błąd podczas aktualizacji użytkownika: " + e.getMessage(), e);
        }
    }



    public Optional<Users> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapRowToUser(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


public Users create(Users user) {
    String sqlUser = "INSERT INTO users (id, username, password, first_name, last_name, join_date, role, enabled) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String sqlRole = "SELECT id FROM role WHERE name = ?";

    try (Connection connection = dataSource.getConnection()) {

        PreparedStatement roleStatement = connection.prepareStatement(sqlRole);
        roleStatement.setString(1, EnRole.USER.name());
        ResultSet roleResultSet = roleStatement.executeQuery();

        if (roleResultSet.next()) {
            int roleId = roleResultSet.getInt("id");
            Role defaultRole = new Role();
            defaultRole.setId(roleId);
            defaultRole.setName(EnRole.USER);
            user.setRoles(Collections.singleton(defaultRole));
        } else {
            throw new IllegalArgumentException("Rola USER nie istnieje w bazie danych");
        }

        String rolesString = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.joining(","));



        PreparedStatement statement = connection.prepareStatement(sqlUser);
        statement.setString(1, user.getId().toString());
        statement.setString(2, user.getUsername());
        statement.setString(3, passwordEncoder(user.getPassword()));
        statement.setString(4, user.getFirstName());
        statement.setString(5, user.getLastName());
        statement.setDate(6, Date.valueOf(user.getJoin_date()));
        statement.setString(7, rolesString);
        statement.setBoolean(8, user.isEnabled());

        int rowsAffected = statement.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);

    } catch (SQLException e) {
        System.err.println("SQLException: " + e.getMessage());
        System.err.println("SQLState: " + e.getSQLState());
        System.err.println("VendorError: " + e.getErrorCode());
        for (Throwable t : e) {
            t.printStackTrace();
        }
        throw new RuntimeException("Błąd podczas tworzenia użytkownika", e);
    }
   usersRolesTable(user);

    return user;
}


    public void usersRolesTable(Users user){
        String sql = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?) ";


        try(Connection connection= dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            for(Role role : user.getRoles()) {
                if(role.getId()==null)
                    throw new IllegalArgumentException("ID roli jest nieprawidlowe");

                statement.setString(1, user.getId().toString());
                statement.setInt(2, role.getId());
                statement.addBatch();
            }
            statement.executeUpdate();


        }catch (SQLException e){
            throw new IllegalArgumentException ("Błąd podczas zapisywania ról",e);
        }

    }


    public void deleteById(UUID id){
       String sqlUserTbl = "Delete from users WHERE id= ?";
       String sqlUserRolesTbl = "Delete from user_roles WHERE user_id= ?";


       try (Connection connection = dataSource.getConnection()){


           try(PreparedStatement statement1 = connection.prepareStatement(sqlUserRolesTbl)) {
               statement1.setString(1, id.toString());
               statement1.executeUpdate();


               try (PreparedStatement statement = connection.prepareStatement(sqlUserTbl)) {
                   statement.setString(1, id.toString());

                   statement.executeUpdate();

               }
           }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Błąd podczas usuwania użytkownika: " + e.getMessage(), e);
        }
}

    //passwordEncoder
    public @NotBlank(message = "Hasło nie może być puste") @Size(min = 3, max = 20, message = "Hasło musi mieć od 3 do 20 znaków") String passwordEncoder(String password){
        return new BCryptPasswordEncoder(10).encode(password);
    }

    private Users mapRowToUser(ResultSet resultSet) throws SQLException{
        Users user = new Users();

        user.setId(resultSet.getObject("id", UUID.class));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setPassword(passwordEncoder(resultSet.getString("password")));
        user.setUsername(resultSet.getString("username"));
        user.setJoin_date(resultSet.getDate("join_date").toLocalDate());
        user.setEnabled(resultSet.getBoolean("enabled"));

        String rolesString = resultSet.getString("role");

        if (rolesString != null && !rolesString.isEmpty()) {

           String roleName = rolesString.trim();

            Role role = roleRepository.findByName(EnRole.valueOf(roleName));

            if (role == null) {

                role = new Role();
                role.setName(EnRole.valueOf(roleName));
                roleRepository.save(role);
            }

            user.setRoles(Collections.singleton(role));
        }

        return user;
    }


    public void disable(UUID id){
       String sql = "UPDATE users SET enabled = 0 WHERE id = ? ";
try(Connection connection = dataSource.getConnection();
     PreparedStatement statement = connection.prepareStatement(sql)) {

    statement.setString(1, id.toString());
    statement.executeUpdate();
    }catch (SQLException e){
    e.printStackTrace();
    }

    }


    public void enable(UUID id){
        String sql = "UPDATE users SET enabled = 1 WHERE id = ? ";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id.toString());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

}

