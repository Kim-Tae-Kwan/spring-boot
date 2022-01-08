package com.study.springsecurity.service;

import com.study.springsecurity.model.dto.*;

public interface SignService {
    LoginResponseDto login(LoginRequestDto request);
    void logout(LogoutRequestDto request);
    SignupResponseDto singUp(SignupRequestDto request);
    ReissueDto reissue(ReissueDto reissueDto);

}
