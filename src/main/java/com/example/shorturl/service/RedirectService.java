package com.example.shorturl.service;

import com.example.shorturl.entity.Redirect;
import com.example.shorturl.exception.BadRequestException;
import com.example.shorturl.exception.NotFoundException;
import com.example.shorturl.repository.RedirectRepository;
import com.example.shorturl.request.RedirectCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RedirectService {

    private RedirectRepository redirectRepository;
    @Autowired
    public RedirectService(RedirectRepository redirectRepository) {
        this.redirectRepository = redirectRepository;
    }

    public Optional<Redirect> createRedirect(RedirectCreationRequest redirectCreationRequest){
        if(redirectRepository.existsByAlias((redirectCreationRequest.getAlias()))){
            throw new BadRequestException("Alias already exists");
        }
        System.out.println("Redirect request = " + redirectCreationRequest.toString());
        Redirect redirect = redirectRepository.save(new Redirect(redirectCreationRequest.getAlias(), redirectCreationRequest.getUrl()));
        System.out.println("Redirect = " + redirect.toString());
        return Optional.ofNullable(redirect);
    }

    public Redirect getRedirect(String alias){
        Redirect redirect = redirectRepository.findByAlias(alias)
                .orElseThrow(() -> new NotFoundException("Alias does not exist"));

        return redirect;
    }
}
