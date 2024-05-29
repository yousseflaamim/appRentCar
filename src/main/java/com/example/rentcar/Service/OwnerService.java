package com.example.rentcar.Service;


import com.example.rentcar.JWT.JwtTokenUtil;
import com.example.rentcar.modell.Owner;
import com.example.rentcar.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    private OwnerRepository ownerRepository;

    private JwtTokenUtil jwtTokenUtil;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository,
                        JwtTokenUtil jwtTokenUtil,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.ownerRepository = ownerRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    public String registerOwner(Owner owner) {

        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        ownerRepository.save(owner);
        return jwtTokenUtil.generateToken(owner.getUsername());
    }

    public String login(String username, String password) {
        Owner owner = ownerRepository.findByUsername(username);

        if (owner != null) {
            boolean passwordMatch = passwordEncoder.matches(password, owner.getPassword());
            if (passwordMatch) {
                return jwtTokenUtil.generateToken(username);
            } else {

                return "Incorrect password";
            }
        } else {

            return "User not found";
        }
    }

    public Owner findOwnerByUsername(String username) {
        return ownerRepository.findByUsername(username);
    }
}






