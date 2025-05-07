package org.skillmatch.securityservice.Controllers;


import lombok.RequiredArgsConstructor;
import org.skillmatch.securityservice.DAO.Entity.PersonEntity;
import org.skillmatch.securityservice.JWT.JWTUtil;
import org.skillmatch.securityservice.Security.AuthenticationService;
import org.skillmatch.securityservice.Services.SecurityPersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/security/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JWTUtil jwtUtil;
    private final SecurityPersonService securityPersonService;

    @RequestMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody PersonEntity personEntity) {
        return Mono.just(personEntity)
                .map(person -> authenticationService.authenticate(person.getUsername(),person.getPassword()))
                .map(personAuth->jwtUtil.generateJWT(personAuth))
                .map(token-> "Bearer " + token)
                .map(ResponseEntity::ok);
    }
    @PostMapping("/registration")
    public Mono<ResponseEntity<?>> register(@RequestBody PersonEntity personEntity) {
        return Mono.just(securityPersonService.addPerson(personEntity))
                .map(ResponseEntity::ok);
    }
    @GetMapping("/validate")
    public Mono<ResponseEntity<String>> validate(@RequestParam String token) {
        return Mono.just(token)
                .flatMap(jwt -> jwtUtil.validateJWT(jwt)
                        ? Mono.just(ResponseEntity.ok("success"))
                        : Mono.just(ResponseEntity.badRequest().body("Invalid token"))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body("Invalid token"))));
    }

}
