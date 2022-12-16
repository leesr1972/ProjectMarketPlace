package sky.pro.java.diplomproject.ProjectMarketPlace.dto;

import lombok.Data;

@Data
public class RegisterReqDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
