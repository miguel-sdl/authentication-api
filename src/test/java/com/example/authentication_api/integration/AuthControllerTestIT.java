package com.example.authentication_api.integration;

import com.example.authentication_api.exception.ExceptionResponse;
import com.example.authentication_api.model.dto.LoginResponseDTO;
import com.example.authentication_api.repository.UserRepository;
import com.example.authentication_api.util.TokenCreator;
import com.example.authentication_api.util.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTestIT {
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    UserRepository userRepository;

    @Test
    void register_RegistersUser_WhenSuccessful() {
        ResponseEntity<Void> response = restTemplate.postForEntity("/auth/register", UserCreator.createValidRegisterDTO(), Void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));

    }

    @Test
    void register_Returns400_WhenReceivesInvalidDTO() {
        ResponseEntity<ProblemDetail> response = restTemplate.postForEntity("/auth/register", UserCreator.createInvalidRegisterDTO(), ProblemDetail.class);


        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
    }

    @Test
    void register_Returns400_WhenUserAlreadyExists() {
        userRepository.save(UserCreator.createUserToBeSaved());
        ResponseEntity<ExceptionResponse> response = restTemplate.postForEntity("/auth/register", UserCreator.createValidRegisterDTO(), ExceptionResponse.class);

        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getMessage()).isEqualTo("Nao e possivel registrar um usuario ja existente");
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
    }

    @Test
    void login_ReturnsTokenJwt_WhenSuccessful() {
        userRepository.save(UserCreator.createUserToBeSaved());
        ResponseEntity<LoginResponseDTO> response = restTemplate.postForEntity("/auth/login", UserCreator.createValidLoginDTO(), LoginResponseDTO.class);

        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().token()).isEqualTo(TokenCreator.createTokenUserRole());
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    void login_Returns403_WhenReceivesBadCredentials() {
        ResponseEntity<ProblemDetail> response = restTemplate.postForEntity("/auth/login", UserCreator.createValidRegisterDTO(), ProblemDetail.class);

        System.out.println(response);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(403));

    }
}
