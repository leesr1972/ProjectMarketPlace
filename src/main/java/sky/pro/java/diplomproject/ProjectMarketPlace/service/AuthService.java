package sky.pro.java.diplomproject.ProjectMarketPlace.service;

import sky.pro.java.diplomproject.ProjectMarketPlace.dto.RegisterReqDto;

public interface AuthService {
    boolean login(String userName, String password);
    <role> boolean register(RegisterReqDto registerReqDto);
}
