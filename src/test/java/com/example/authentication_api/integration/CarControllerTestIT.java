package com.example.authentication_api.integration;

import com.example.authentication_api.exception.ExceptionResponse;
import com.example.authentication_api.model.entity.Car;
import com.example.authentication_api.repository.CarRepository;
import com.example.authentication_api.repository.UserRepository;
import com.example.authentication_api.util.CarCreator;
import com.example.authentication_api.util.TokenCreator;
import com.example.authentication_api.util.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarControllerTestIT {
    @Autowired
    @Qualifier("noAuth")
    TestRestTemplate restTemplateNoAuth;

    @Autowired
    @Qualifier("userRole")
    TestRestTemplate restTemplateRoleUser;

    @Autowired
    @Qualifier("adminRole")
    TestRestTemplate restTemplateRoleAdmin;

    @LocalServerPort
    int port;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarRepository carRepository;

    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name = "noAuth")
        public TestRestTemplate createTestRestTemplateNoAuth(@Value("${local.server.port}") int port) {

            RestTemplateBuilder restTemplate = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port);

            return new TestRestTemplate(restTemplate);
        }

        @Bean(name = "userRole")
        public TestRestTemplate createTestRestTemplateUserRole(@Value("${local.server.port}") int port) {

            RestTemplateBuilder restTemplate = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + TokenCreator.createTokenUserRole());

            return new TestRestTemplate(restTemplate);
        }

        @Bean(name = "adminRole")
        public TestRestTemplate createTestRestTemplateAdminRole(@Value("${local.server.port}") int port) {

            RestTemplateBuilder restTemplate = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + TokenCreator.createTokenAdminRole());

            return new TestRestTemplate(restTemplate);
        }
    }

    @Test
    void findAll_ReturnsAllCars_WhenSuccessful() {
        userRepository.save(UserCreator.createUserToBeSaved());
        carRepository.save(CarCreator.createCarToBeSaved());

        ResponseEntity<List<Car>> response = restTemplateRoleUser.exchange("/car", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(response.getBody()).isNotNull().isNotEmpty();
        Assertions.assertThat(response.getBody().size()).isEqualTo(1);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    void findAll_Returns401_WhenIsNotAuthenticated() {
        ResponseEntity<ProblemDetail> response = restTemplateNoAuth.exchange("/car", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(401));
    }

    @Test
    void findByName_ReturnsListOfCars_WhenSuccessful() {
        userRepository.save(UserCreator.createUserToBeSaved());
        carRepository.save(CarCreator.createCarToBeSaved());

        ResponseEntity<List<Car>> response = restTemplateRoleUser.exchange("/car/find?name=Honda Civic", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertThat(response.getBody()).isNotNull().isNotEmpty();
        Assertions.assertThat(response.getBody().size()).isEqualTo(1);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    void findByName_Returns401_WhenIsNotAuthenticated() {
        ResponseEntity<ProblemDetail> response = restTemplateNoAuth.exchange("/car/find?name=Honda Civic", HttpMethod.GET, null, ProblemDetail.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(401));
    }

    @Test
    void findById_ReturnsCar_WhenSuccessful() {
        userRepository.save(UserCreator.createUserToBeSaved());
        Car saved = carRepository.save(CarCreator.createCarToBeSaved());

        ResponseEntity<Car> response = restTemplateRoleUser.exchange("/car/{id}", HttpMethod.GET, null, Car.class, saved.getId());

        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody()).isEqualTo(saved);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    void findById_Returns401_WhenIsNotAuthenticated() {
        ResponseEntity<ProblemDetail> response = restTemplateNoAuth.exchange("/car/1", HttpMethod.GET, null, ProblemDetail.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(401));
    }

    @Test
    void save_SavesCar_WhenSuccessful() {
        userRepository.save(UserCreator.createUserToBeSaved());
        ResponseEntity<Car> response = restTemplateRoleUser.postForEntity("/car", CarCreator.createValidCarPostDTO(), Car.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
    }

    @Test
    void save_Returns401_WhenIsNotAuthenticated() {
        ResponseEntity<Car> response = restTemplateNoAuth.postForEntity("/car", CarCreator.createValidCarPostDTO(), Car.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(401));
    }

    @Test
    void update_UpdatesCar_WhenSuccessful() {
        userRepository.save(UserCreator.createAdminUserToBeSaved());
        Car saved = carRepository.save(CarCreator.createCarToBeSaved());
        saved.setName("nome");

        ResponseEntity<Void> response = restTemplateRoleAdmin.exchange("/car", HttpMethod.PUT, new HttpEntity<>(saved), Void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
    }

    @Test
    void update_Returns400_WhenCarIsNotFound() {
        userRepository.save(UserCreator.createAdminUserToBeSaved());

        ResponseEntity<ExceptionResponse> response = restTemplateRoleAdmin.exchange("/car", HttpMethod.PUT, new HttpEntity<>(CarCreator.createValidCarPutDTO()), ExceptionResponse.class);

        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getMessage()).isEqualTo("Carro nao encontrado");
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
    }

    @Test
    void update_Returns403_WhenIsNotAdmin() {
        userRepository.save(UserCreator.createUserToBeSaved());
        Car saved = carRepository.save(CarCreator.createCarToBeSaved());
        saved.setName("nome");

        ResponseEntity<Void> response = restTemplateRoleUser.exchange("/car", HttpMethod.PUT, new HttpEntity<>(saved), Void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(403));
    }

    @Test
    void delete_RemovesCar_WhenSuccessful() {
        userRepository.save(UserCreator.createAdminUserToBeSaved());
        Car saved = carRepository.save(CarCreator.createCarToBeSaved());


        ResponseEntity<Void> response = restTemplateRoleAdmin.exchange("/car/{id}", HttpMethod.DELETE, null, Void.class, saved.getId());

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
    }

    @Test
    void delete_Returns400_WhenCarIsNotFound() {
        userRepository.save(UserCreator.createAdminUserToBeSaved());

        ResponseEntity<ExceptionResponse> response = restTemplateRoleAdmin.exchange("/car/1", HttpMethod.DELETE, null, ExceptionResponse.class);

        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getMessage()).isEqualTo("Carro nao encontrado");
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
    }

    @Test
    void delete_Returns403_WhenIsNotAdmin() {
        userRepository.save(UserCreator.createUserToBeSaved());
        carRepository.save(CarCreator.createCarToBeSaved());

        ResponseEntity<Void> response = restTemplateRoleUser.exchange("/car/1", HttpMethod.DELETE, null, Void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(403));
    }
}

