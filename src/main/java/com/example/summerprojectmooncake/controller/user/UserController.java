package com.example.summerprojectmooncake.controller.user;

import com.example.summerprojectmooncake.entity.ERole;
import com.example.summerprojectmooncake.entity.Role;
import com.example.summerprojectmooncake.entity.User;
import com.example.summerprojectmooncake.entity.VerificationToken;
import com.example.summerprojectmooncake.event.RegistrationCompleteEvent;
import com.example.summerprojectmooncake.event.listen.RegistrationCompleteEventListener;
import com.example.summerprojectmooncake.jwt.JwtTokenProvider;
import com.example.summerprojectmooncake.payload.request.LoginRequest;
import com.example.summerprojectmooncake.payload.request.SignUpRequest;
import com.example.summerprojectmooncake.payload.response.JwtResponse;
import com.example.summerprojectmooncake.payload.response.MessageResponse;
import com.example.summerprojectmooncake.repository.VerificationTokenRepository;
import com.example.summerprojectmooncake.security.CustomUserDetails;
import com.example.summerprojectmooncake.service.RoleService;
import com.example.summerprojectmooncake.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(value = "*")
@Slf4j
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private RegistrationCompleteEventListener eventListener;
    @Autowired
    private HttpServletRequest servletRequest;
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("this email already exists"));
        }
        User user = userService.registerUser(signUpRequest);
        // publish registration event
        publisher.publishEvent(new RegistrationCompleteEvent(user,applicationUrl(request)));
        return ResponseEntity.ok(new MessageResponse("Success! please check your email to complete your registration"));
    }
    @GetMapping("/verifyEmail")
    public ResponseEntity<?> verifyEmail(@RequestParam("token")String token){
        String url = applicationUrl(servletRequest)+"/api/v1/auth/resend-verification_token?token="+token;
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if(verificationToken.getUser().isEnable()){
            return ResponseEntity.badRequest().body(new MessageResponse("this account has already been verified, please login"));
        }
        String verificationResult = userService.validateToken(token);
        if(verificationResult.equals("valid")){
            return ResponseEntity.ok(new MessageResponse("email verified successfully. now you can login to your account"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(verificationResult)+
                ", <a href=\""+url+"\"> get a new verification link</a>");
    }
    @GetMapping("/resend-verification_token")
    public String resendVerificationToken(@RequestParam("token") String oldToken,HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User theUser = verificationToken.getUser();
        resendVerificationTokenEmail(theUser,applicationUrl(request),verificationToken);
        return "A new verification link has been send to your email, please check to complete your registration";
    }

    private void resendVerificationTokenEmail(User theUser, String applicationUrl, VerificationToken verificationToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/api/v1/auth/verifyEmail?token="+verificationToken.getToken();
        eventListener.sendVerificationEmail(url);
        log.info("again click the link to verify your registration : {}",url);
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        if(!userService.checkEmailAndPassword(loginRequest))
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(401, "this account does not exist"));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = tokenProvider.generateToken(customUserDetails);
        List<String> listRoles = customUserDetails.getAuthorities().stream()
                .map(item->item.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, customUserDetails.getEmail(),
                customUserDetails.getPhoneNumber(), customUserDetails.getName(), listRoles));
    }

}
